package it.alecsandria.pg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.alecsandria.pg.entities.Product;
import it.alecsandria.pg.entities.Seller;
import it.alecsandria.pg.entities.Site;
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
					sites.add(site);

					// call to the robot for this seller by its url
					try {
						logger.info("--------- Calling robot " + site.getUrlRobotService() + " ---------");
						robotCall(sellerProducts,seller.getSearch_label(), site.getUrlRobotService());
						
					} catch (Exception e) {
						logger.error("Error calling robot service " + site.getUrlRobotService() + " for seller "
								+ seller.getName() + " ----->> " + e.getMessage());
					}
				}

			}

		} catch (Exception e) {
			logger.error("Error during retrieving data ------>> " + e.getMessage());
		}

	}

	@Override
	public void robotCall(List<Product> products, String searchLabel, String urlRobotService) {

		String json;
		RestTemplate restTemplate;
		HttpHeaders headers;

		for (Product product : products) {

			json = "{\"parameters\":[{\"variableName\":\"input\",\"attribute\":[{\"type\":\"text\",\"name\":\"prodotto\",\"value\":\""
					+ product.getDescription() + "\"}]}]}";

			restTemplate = new RestTemplate();
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> request = new HttpEntity<String>(json, headers);

			String response = restTemplate.postForObject(urlRobotService, request, String.class);
			
			System.out.println(response);
			

		}

	}

}
