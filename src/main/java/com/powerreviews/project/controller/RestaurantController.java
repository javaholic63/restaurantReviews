package com.powerreviews.project.controller;

import com.powerreviews.project.model.RestaurantEntity;
import com.powerreviews.project.model.ReviewEntity;
import com.powerreviews.project.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class RestaurantController {


	private final RestaurantRepository restaurantRepository;

	public RestaurantController(@Autowired RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}

	@ResponseBody
	@RequestMapping(value = "/restaurant/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantEntity> getById(@PathVariable Long id) {

		if(!restaurantRepository.existsById(id)){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
//		else{
//
//			RestaurantEntity restaurant = restaurantRepository.findById(id).orElse(null);
//			return new ResponseEntity<>(restaurant, new HttpHeaders(), restaurant == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
//		}
		
		RestaurantEntity restaurant = restaurantRepository.findById(id).orElse(null);
		return new ResponseEntity<>(restaurant, new HttpHeaders(), restaurant == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);

	}


	@ResponseBody
	@RequestMapping(value = "/restaurant", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RestaurantEntity>> get(@RequestParam String type) {
		List<RestaurantEntity> restaurants = restaurantRepository.findByType(type); 
		
		log.debug("Restaurant List before sorting: " + restaurants);

		//sort by highest average rating
		Collections.sort(restaurants, new Comparator<RestaurantEntity>() {

			@Override
			public  int compare(RestaurantEntity o1, RestaurantEntity o2) {
				int value = 0;

				List<ReviewEntity> revLst1 = o1.getReviews();
				List<ReviewEntity> revLst2 = o2.getReviews();

				double averageRev1 = revLst1
						.stream()
						.mapToInt(r -> r.getRating())
						.average().getAsDouble();


				double averageRev2 = revLst2
						.stream()
						.mapToInt(r -> r.getRating())
						.average().getAsDouble();
				
				log.debug("restaurant 1 review average: " + averageRev1);
				log.debug("restaurant 2 review average: " + averageRev2);

				if(averageRev1 < averageRev2){
					value = 1;
				}else if (averageRev1 > averageRev2){
					value = -1;
				}			

				log.debug("compared value: " + value); 
				
				return value;
				
			}
		});
				
		log.debug("Restaurant List after sorting: " + restaurants);

		return new ResponseEntity<>(restaurants, new HttpHeaders(), restaurants == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}


	@ResponseBody
	@RequestMapping(value = "/restaurant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantEntity> post(@RequestBody RestaurantEntity restaurant) {
		restaurantRepository.save(restaurant);
		return new ResponseEntity<>(restaurant, new HttpHeaders(), HttpStatus.CREATED);
	}



	@ResponseBody
	@RequestMapping(value = "/restaurant/{id}/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantEntity> updateRestaurant(@PathVariable("id") long id, @RequestBody RestaurantEntity restaurant) {
		
			
		RestaurantEntity newRestaurant = new RestaurantEntity();
		boolean result = restaurantRepository.findById(restaurant.getId()).isPresent();
		
		if (!result) {
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		
		newRestaurant.setLatitude(restaurant.getLatitude());
		newRestaurant.setLongitude(restaurant.getLongitude());
		newRestaurant.setName(restaurant.getName());
		newRestaurant.setType(restaurant.getType());
		restaurantRepository.save(newRestaurant);
		return new ResponseEntity<>(newRestaurant, new HttpHeaders(), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/restaurant/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantEntity> delete(@PathVariable Long id) {
		restaurantRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
