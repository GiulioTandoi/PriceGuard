package it.alecsandria.pg.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.alecsandria.pg.entities.ExtractionResult;
import it.alecsandria.pg.entities.Product;
import it.alecsandria.pg.entities.Seller;
import it.alecsandria.pg.entities.Site;
import it.alecsandria.pg.models.RobotResult;
import it.alecsandria.pg.models.Wrapper;
import it.alecsandria.pg.models.WrapperAttributes;
import it.alecsandria.pg.repositories.ProductRepository;
import it.alecsandria.pg.repositories.SellerRepository;
import it.alecsandria.pg.repositories.Seller_SitesRepository;
import it.alecsandria.pg.service.PriceGuardService;

@Service
public class PriceGuardServiceImpl implements PriceGuardService {

	@Autowired
	Seller_SitesRepository seller_sitesRepo;

	@Autowired
	SellerRepository sellerRepo;

	@Autowired
	ProductRepository productRepo;

	private Logger logger = LoggerFactory.getLogger(getClass());
	private List<Seller> sellers = new ArrayList<Seller>();
	private List<Site> sites = new ArrayList<Site>();
	private List<Product> sellerProducts = new ArrayList<Product>();

	@Override
	@Scheduled(cron = "0/2 * * ? * *")
	public void executeGuardDuty() {

		try {

			Wrapper robotResultsWrapper = new Wrapper();

			// Retrieve all sellers from DB
			sellers = sellerRepo.findAll();

			// Retrieve sites and products for each seller
			for (Seller seller : sellers) {
				logger.info("--------- Seller " + seller.getName() + " --------- ");
				int idSeller = seller.getId();

				// Retrieve all product of the seller
				sellerProducts = productRepo.findAllByidSeller(idSeller);

				for (Object obj[] : seller_sitesRepo.findAllByIdSeller(idSeller)) {
					Site site = new Site();
					site.setId((int) obj[0]);
					site.setName((String) obj[1]);
					site.setUrlRobotService((String) obj[2]);
					// add site to sites list to insert into DB
					sites.add(site);

					// call to the robot url for this seller
					try {
						logger.info("--------- Calling robot " + site.getUrlRobotService() + " ---------");
						robotResultsWrapper = robotCall(sellerProducts, seller.getSearch_label(),
								site.getUrlRobotService());
						logger.info("--------- Call completed for site " + site.getUrlRobotService() + " ---------");
					} catch (Exception e) {
						logger.error("Error calling robot service " + site.getUrlRobotService() + " for seller "
								+ seller.getName() + " ----->> " + e.getMessage());
					}

					// Retrieve data from dealer page
					try {
						logger.info(" --------- Scraping dealers pages for each product ---------");
						List<ExtractionResult> extractionresults = getPageDealerData(site.getName(), robotResultsWrapper);
						logger.info(" --------- RETRIEVED data from dealers pages all products ---------");
					} catch (IOException ie) {
						logger.error("Error reteving data from dealer page ----->> " + ie.getMessage());
					}
				}

			}

		} catch (Exception e) {
			logger.error("Error retrieving data ------>> " + e.getMessage());
		}

	}

	@Override
	public Wrapper robotCall(List<Product> products, String searchLabel, String urlRobotService) {

		String json;
		RestTemplate restTemplate;
		HttpHeaders headers;
		Wrapper robotResultsWrapper = new Wrapper();

		for (Product product : products) {

			if (product.getId() == 72) {

				json = "{\"parameters\":[{\"variableName\":\"input\",\"attribute\":[{\"type\":\"text\",\"name\":\"prodotto\",\"value\":\""
						+ product.getDescription() + "\"}]}]}";

				restTemplate = new RestTemplate();
				headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> request = new HttpEntity<String>(json, headers);

				String response = restTemplate.postForObject(urlRobotService, request, String.class);

				logger.info("Robot call response for product " + product.getId() + " ----->> " + response);

				ObjectMapper mapper = new ObjectMapper();

				try {
					robotResultsWrapper = mapper.readValue(response, Wrapper.class);
					robotResultsWrapper.setIdProduct(product.getId());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}

		return robotResultsWrapper;

	}

	@Override
	public List<ExtractionResult> getPageDealerData(String eCommerceSiteName, Wrapper robotResultsWrapper)
			throws IOException {

		List<ExtractionResult> extractionResults = new ArrayList<ExtractionResult>();
		ExtractionResult extraction = new ExtractionResult();
		String redirectLink;
		String dealerPageLink = "";
		String pIvaRegex = "\\D([0-9]{11})\\D";
		Pattern pattern = Pattern.compile(pIvaRegex);
		Matcher matcher;

		for (WrapperAttributes productAttributes : robotResultsWrapper.getValues()) {
		
			// RobotResult representes an item extracted for that product (e.g. prezzo or link of the single product ads) 
			for (RobotResult result : productAttributes.getAttribute()) {

				extraction = new ExtractionResult();
				extraction.setIdPoduct(robotResultsWrapper.getIdProduct());
				
				if (result.getName().equals("link")) {

					redirectLink = "https://www." + eCommerceSiteName + (String) result.getValue();
					URL redirectUrl = new URL(redirectLink);
					HttpURLConnection httpConn;
					try{
						
						// this block Take redirected url contained in Location tag in the http header and fire the new url 
						httpConn = (HttpURLConnection) redirectUrl.openConnection();
						httpConn.setInstanceFollowRedirects(false);
						httpConn.connect();
						System.out.println(httpConn.getRequestMethod());
						System.out.println(httpConn.getResponseCode());
						httpConn.getResponseCode();
						dealerPageLink = httpConn.getHeaderField("Location").toString();
						
						System.out.println(httpConn.getHeaderFields());
						System.out.println(httpConn.getURL());
						extraction.setUrlDealer(dealerPageLink);
						System.out.println(dealerPageLink);

					}catch (Exception e) {
						logger.error("Error redirecting to dealer page, link: " + redirectLink + " ------ >>" + e.getMessage());
					}
					
					Document htmlDealerPage = Jsoup.connect(dealerPageLink).get();
					Elements body = htmlDealerPage.getElementsByTag("body");
					
					matcher = pattern.matcher(body.text());

					while (matcher.find()) {

						extraction.setpIvaDealer(matcher.group());
						System.out.println("Found pIva ------ >>" + extraction.getpIvaDealer());
					}

				} else if (result.getName().equals("prezzo")) {

					extraction.setFoundedPrice(Float.parseFloat((String) result.getValue()));
					System.out.println("Found price ------ >> " + extraction.getFoundedPrice());
				}

			}
			
		}

		extractionResults.add(extraction);

		return extractionResults;
	}

}
