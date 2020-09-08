package com.powerreviews.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.powerreviews.project.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
    
//	Let the database auto generate the ID
//	@Query("SELECT MAX(id) FROM restaurant")
//    int maxId();
}
