package com.ericsson.homework.prototypeapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@PropertySource("classpath:app.conf")
public class SubscriberService {
	
	public static JsonObject jsonObj;

	@Value("${data_file_path}")
	public String filePath;
	
	private List<Subscriber> subscribers;
	
	@PostConstruct
	public void init() {
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			File file = ResourceUtils.getFile(filePath);
			if(file.length() != 0) {
				jsonObj = objectMapper.readValue(file, JsonObject.class);
				subscribers = jsonObj.getSubscribers();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public List<Subscriber> getAllSubscribers() {
		return subscribers;
	}
	
	@Cacheable(value="subscribers")
	public Subscriber getSubscriberById(int id) { 
		if(subscribers != null) {
			for(int i=0; i<subscribers.size(); i++) {
				if(subscribers.get(i).getId() == id) {
					return subscribers.get(i);
				}
			}
		}
		return null;
	}

	public void addSubscriber(Subscriber subscriber) {
		if(subscribers == null) {
			subscribers = new ArrayList();
		}
		subscribers.add(subscriber);
	}

	public boolean updateSubscriberInfo(Subscriber subscriber) {
		Subscriber theSubscriber = getSubscriberById(subscriber.getId());
		
		if(theSubscriber == null) {
			return false;
		} else {
			theSubscriber.setId(subscriber.getId());
			theSubscriber.setName(subscriber.getName());
			theSubscriber.setMsisdn(subscriber.getMsisdn());			
			return true;
		}
	}

	public boolean deleteSubscriber(Subscriber subscriber) {
		Subscriber theSubscriber = getSubscriberById(subscriber.getId());
		
		if(theSubscriber == null) {
			return false;
		} else {
			subscribers.remove(theSubscriber);		
			return true;
		}
	}
}
