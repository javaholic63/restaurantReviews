package com.powerreviews.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.powerreviews.project.errors.BadUserNameException;
import com.powerreviews.project.model.ReviewEntity;
import com.powerreviews.project.model.UserEntity;
import com.powerreviews.project.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;


/**
 * AppUser Controller exposes RESTful endpoints to create or update a user
 */

@Slf4j
@RestController
public class AppUserController {
	
    private final UserRepository userRepository;
    
    public AppUserController(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }	
    
    
    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity appuser) {
    	
    	log.debug(appuser.toString());
    	
    	System.out.println("User Object: " + appuser.toString());
    	
    	String uName = appuser.getName();
    	
    	
    	if(uName.equalsIgnoreCase("Darth Vader") || uName.equalsIgnoreCase("AC Slater")){
    		
    		throw new BadUserNameException(uName);
    	}
        userRepository.save(appuser);
        return new ResponseEntity<>(appuser, new HttpHeaders(), HttpStatus.CREATED);
    }

}
