package it.alecsandria.pg.service.impl;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import it.alecsandria.pg.service.PriceGuardService;

@Service
public class PriceGuardServiceImpl implements PriceGuardService {

    private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	@Scheduled(cron = "0/5 * * ? * *")
	public void executePriceGuardJob() {
		logger.info("Ehi sono partitoooo"); 
	}

}
