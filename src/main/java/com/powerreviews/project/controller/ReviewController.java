package com.powerreviews.project.controller;

import javax.validation.Valid;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.powerreviews.project.errors.BadUserNameException;
import com.powerreviews.project.errors.ResourceNotFoundException;
import com.powerreviews.project.model.ReviewEntity;
import com.powerreviews.project.repositories.ReviewRepository;
import com.powerreviews.project.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
public class ReviewController {


	private final UserRepository userRepository;

	private final ReviewRepository reviewRepository;

	public ReviewController(@Autowired UserRepository userRepository, @Autowired ReviewRepository reviewRepository) {
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
	}


	@ResponseBody
	@RequestMapping(value = "/user/{userId}/reviews", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @Valid ReviewEntity createReview(@PathVariable (value = "userId") Long userId, @Valid @RequestBody ReviewEntity review) throws PSQLException {

		log.debug("user ID: " + userId);

		return userRepository.findById(userId).map(user -> {
			review.setAppuser(user);
			log.debug("Display new review: " + review.toString());					
			return reviewRepository.save(review);	
		}).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));

	}

}
