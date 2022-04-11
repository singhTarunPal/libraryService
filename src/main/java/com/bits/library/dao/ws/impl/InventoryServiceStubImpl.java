package com.bits.library.dao.ws.impl;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bits.library.dao.ws.InventoryServiceStub;
import com.bits.library.model.BookInventory;
import com.bits.library.service.Implementation.IssueServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class InventoryServiceStubImpl implements InventoryServiceStub {

	@Value("${service.bookInventory.uri}")
	private String uri;

	private static final Logger LOGGER = LogManager.getLogger(IssueServiceImpl.class);

	public List<BookInventory> getInventoryForABook(String bookId) {
		try {
			String getURL = Strings.concat(uri, bookId);
			
			LOGGER.info("InventoryServiceStub:getURL: " + getURL);
			RestTemplate restTemplate = getCustomRestTemplate(new RestTemplateBuilder());
			String resultJSONArray = restTemplate.getForObject(getURL, String.class);

			LOGGER.info("Inventory WS-result: " + resultJSONArray);
			Type listType = new TypeToken<List<BookInventory>>() {}.getType();

			return new Gson().fromJson(resultJSONArray, listType);
		} catch (Exception e) {
			LOGGER.error("Inventory WS-failed", e);
			LOGGER.info("Inventory WS-failed");
			e.printStackTrace();
			return null;
		}
	}

	public BookInventory saveInventoryForABook(BookInventory bookInventory) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> request = new HttpEntity<String>(new Gson().toJson(bookInventory), headers);

			RestTemplate restTemplate = getCustomRestTemplate(new RestTemplateBuilder());

			ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

			LOGGER.info("WS-response: " + response);
			// String resultJSON = result.substring(1, (result.length()-1)) ;

			return new Gson().fromJson(response.getBody(), BookInventory.class);
		} catch (Exception e) {
			LOGGER.error("Inventory WS-failed", e);
			LOGGER.info("Inventory WS-failed");
			e.printStackTrace();
			return null;
		}
	}
	
	private RestTemplate getCustomRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		
		 return restTemplateBuilder
		            .setConnectTimeout(Duration.ofSeconds(10))
		            .setReadTimeout(Duration.ofSeconds(10))
		            .build();
	}
}
