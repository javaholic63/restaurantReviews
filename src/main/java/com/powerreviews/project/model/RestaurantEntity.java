package com.powerreviews.project.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.powerreviews.project.core.BaseEntity;
import com.powerreviews.project.customserializer.CustomListSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurant")
public class RestaurantEntity extends BaseEntity implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1495472002038555265L;
	
    public RestaurantEntity(){
    	super();
    }

    public RestaurantEntity(String name, String type, String latitude, String longitude, List<ReviewEntity> reviews) {
    	this();
    	this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reviews = reviews;
    }

    @Column(name = "name", unique = true, nullable = false, length = 100)
    @Setter
	@Getter
    private String name;

    @Column(name = "type", unique = false, nullable = false, length = 100)
    @Setter
	@Getter
    private String type;

    @Column(name = "latitude", unique = false, nullable = false, length = 100)
    @Setter
	@Getter
    private String latitude;

    @Column(name = "longitude", unique = false, nullable = false, length = 100)
    @Setter
	@Getter
    private String longitude;
    
	
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonSerialize(using = CustomListSerializer.class)
    @Setter
	@Getter
    private List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();
    




    @Override
    public String toString() {
        return "RestaurantEntity{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", lat='" + latitude + '\'' +
                ", long='" + longitude + '\'' +
                '}';
    }
}
