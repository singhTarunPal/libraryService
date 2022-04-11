package com.bits.library.dao.ws.impl;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bits.library.dao.ws.BookServiceStub;
import com.bits.library.model.Book;
import com.bits.library.service.Implementation.IssueServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class BookServiceStubImpl implements BookServiceStub {

	@Value("${service.book.uri}")
	private String uri;

	private static final Logger LOGGER = LogManager.getLogger(IssueServiceImpl.class);

	@Override
	public List<Book> getBookById(String bookId) {
		try {
			LOGGER.info("getBookById: " + bookId);
			String getURL = Strings.concat(uri, bookId);

			LOGGER.info("BookServiceStub:getURL: " + getURL);
			RestTemplate restTemplate = getCustomRestTemplate(new RestTemplateBuilder());
			String resultJSONArray = restTemplate.getForObject(getURL, String.class);

			LOGGER.info("Book WS-result: " + resultJSONArray);
			Type listType = new TypeToken<List<Book>>() {}.getType();

			return new Gson().fromJson(resultJSONArray, listType);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Book WS-failed");
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
