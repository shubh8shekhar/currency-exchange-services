package com.d.microservices.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@RequestMapping(value="/sample-api",method=RequestMethod.GET)
	//@Retry(name="sample-api", fallbackMethod = "hardcodedResponse")
	@CircuitBreaker(name="default",fallbackMethod = "hardcodedResponse")
	@RateLimiter(name="default")
	public String sampleApi() {
		logger.info("Sample Api call received");
		ResponseEntity<String> entity =  new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
		return entity.getBody();
	}
	
	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}
	
	@RequestMapping(value="/sample-api-rl",method=RequestMethod.GET)
	@RateLimiter(name="sample-api-rl")
	public String sampleApiRateLimiter() {
		logger.info("Sample Api call received for rate limit");
		return "sample-api";
	}
}
