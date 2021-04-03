package com.d.microservices.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.d.microservices.model.CurrencyExchange;
import com.d.microservices.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeResource {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeResource.class);

	private Environment environment;
	private CurrencyExchangeRepository currencyExchangeRepository;

	@Autowired
	public CurrencyExchangeResource(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
		this.environment = environment;
		this.currencyExchangeRepository = currencyExchangeRepository;
	}

	@RequestMapping(value = "/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		logger.info("retrieveExchangeValue method is called with from {} to {}", from, to);
		
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		if(null == currencyExchange) {
			throw new RuntimeException("Unable to find data for "+from+ " to "+to );
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}

}
