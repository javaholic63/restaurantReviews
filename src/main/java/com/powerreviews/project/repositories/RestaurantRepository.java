package com.powerreviews.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.powerreviews.project.model.RestaurantEntity;

public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Long>{

	// I rather have the database auto generate the ID	
	//	@Query("SELECT MAX(id) FROM restaurant")
	//    int maxId();

	//	@Query("SELECT r FROM RestaurantEntity r WHERE LOWER(r.type) = LOWER(:type)")
	@Query("SELECT name, type, AVG(rating) AS AvgRating FROM Restaurant r, Review rev WHERE r.id = rev.restaurant_id "
			+ "AND LOWER(r.type) = LOWER(:type) "
			+ "GROUP BY name, type "
			+ "ORDER BY AVG(rating) DESC")
	public List<RestaurantEntity> findByType(@Param("type") String type);
}
