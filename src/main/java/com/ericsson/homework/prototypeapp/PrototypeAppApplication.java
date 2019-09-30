package com.ericsson.homework.prototypeapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.boot.SpringApplication;
import com.ericsson.homework.prototypeapp.SubscriberService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class PrototypeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrototypeAppApplication.class, args);
	}
	
   @Scheduled(cron="${cron_expression}")
	private static void refreshJsonFile() {
	   
	   File file;
	   
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			file = ResourceUtils.getFile("D:\\data.json");
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
			
			JsonObject data = SubscriberService.jsonObj;
			if(data != null) {
				mapper.writeValue(file, data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
