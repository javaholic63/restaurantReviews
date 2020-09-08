package com.powerreviews.project.customserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.powerreviews.project.model.ReviewEntity;

public class CustomListSerializer extends StdSerializer<List<ReviewEntity>>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4883135058253705228L;

	public CustomListSerializer() {
		this(null);
	}

	public CustomListSerializer(Class<List<ReviewEntity>> t) {
		super(t);
	}

	@Override
	public void serialize(
			List<ReviewEntity> reviews, 
			JsonGenerator generator, 
			SerializerProvider provider) 
					throws IOException, JsonProcessingException {
		
		
		
		Collections.sort(reviews, Comparator.comparing(ReviewEntity::getReviewDate));
		
		List<String> sortedReviews = new ArrayList<>();
		for (ReviewEntity review : reviews) {
			
			sortedReviews.add(review.toString());
		}
				
		generator.writeObject(sortedReviews);
	}

}
