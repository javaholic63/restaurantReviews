package com.powerreviews.project.core;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected final Long id;

    protected BaseEntity() {
        id = null;
    }

	public Long getId() {
		return id;
	}   
}
