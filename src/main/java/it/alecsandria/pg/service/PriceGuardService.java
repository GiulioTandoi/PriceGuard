package it.alecsandria.pg.service;

import java.io.IOException;
import java.util.List;

import it.alecsandria.pg.entities.ExtractionResult;
import it.alecsandria.pg.entities.Product;
import it.alecsandria.pg.models.RobotResult;
import it.alecsandria.pg.models.Wrapper;

public interface PriceGuardService {
	
	public void executeGuardDuty();
	public Wrapper robotCall(List<Product> products,String searchLabel, String urlRobotService);
	public List<ExtractionResult> extractResultData(String eCommerceSiteName, Wrapper robotResultsWrapper) throws IOException ;
	public boolean insertExtractions(List<ExtractionResult> extractionsResults);

}
