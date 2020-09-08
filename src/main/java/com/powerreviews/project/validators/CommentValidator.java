package com.powerreviews.project.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommentValidator implements ConstraintValidator<Comment, String>{

	List<String> BadWordsFoundInComments = new ArrayList<String>();

	List<String> badWords = Arrays.asList("lit", "hella", "chill", "bro");

	public boolean isValid(String comment, ConstraintValidatorContext context) {

		if(comment == null){
			return false;
		}

		char[] charArray = comment.toCharArray();
		String inStr[] = comment.split(" ");
		List<String> strList = new ArrayList<String>(Arrays.asList(inStr));

		BadWordsFoundInComments = strList.stream()
				.filter(badWords::contains)
				.collect(Collectors.toList());


		if(charArray.length > 200){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Number characters in comment " + charArray.length + " has exceeded the 200 character limit")
			.addConstraintViolation();	
			
			return false;
		}

		if(!BadWordsFoundInComments.isEmpty()){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("The following words in comment " + BadWordsFoundInComments + " are not allowed")
			.addConstraintViolation();
		}

		return BadWordsFoundInComments.isEmpty();

	}

}
