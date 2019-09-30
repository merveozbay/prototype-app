package com.ericsson.homework.prototypeapp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriberController {
	private static final Logger logger = LoggerFactory.getLogger(SubscriberController.class);
	
	@Value("${data_file_path}")
	public String filePath;
	
	@Autowired
	SubscriberService subService;
	
	@GetMapping("/subscribers")
    public  List<Subscriber> getAllSubscribers()
    {
		logger.info("/getAllSubscribers");
        return subService.getAllSubscribers();
    }
	
	@GetMapping("/subscribers/{id}")
    public Subscriber getSubscriberByID(@PathVariable int id)
    {
		logger.info("/getSubscriberById is " + id);
        return subService.getSubscriberById(id);
    }
	
	@PostMapping(path= "/subscriber", consumes = "application/json", produces = "application/json")
    public void addSubscribe(@RequestBody Subscriber subscriber)
    {
		logger.info("/subscriber[POST] id=" + subscriber.getId() + ", name=" + subscriber.getName() + ", " + subscriber.getMsisdn());
        subscriber.setId(subscriber.getId());
         
        subService.addSubscriber(subscriber);
    }
	
	@PutMapping(path= "/subscriber", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateSubscriber(@RequestBody Subscriber subscriber)
    {   
		logger.info("/subscriber[PUT] id=" + subscriber.getId() + ", name=" + subscriber.getName() + ", " + subscriber.getMsisdn());
		if(subService.updateSubscriberInfo(subscriber)) {
			return new ResponseEntity(HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Subscriber Not Found!", HttpStatus.NOT_FOUND);
		}
    }

	@DeleteMapping(path= "/subscriber", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteSubscriber(@RequestBody Subscriber subscriber)
    {   
		logger.info("/subscriber[DELETE] id=" + subscriber.getId());
		if(subService.deleteSubscriber(subscriber)) {
			return new ResponseEntity(HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Subscriber Not Found!", HttpStatus.NOT_FOUND);
		}
    }

	
}
