package com.powerreviews.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.powerreviews.project.model.ReviewEntity;

public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {

}
