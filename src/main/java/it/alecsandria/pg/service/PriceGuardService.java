package it.alecsandria.pg.service;

import java.util.List;

import it.alecsandria.pg.entities.Product;

public interface PriceGuardService {
	
	public void executeGuardDuty();
	public void robotCall(List<Product> products,String searchLabel, String urlRobotService);
}
