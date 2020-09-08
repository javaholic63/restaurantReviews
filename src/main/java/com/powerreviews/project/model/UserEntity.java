package com.powerreviews.project.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "appuser")
public class UserEntity extends BaseEntity implements Serializable {
	
	

    /**
	 * 
	 */
	private static final long serialVersionUID = -5108946183080326267L;
	
	public UserEntity(){
		super();
	}
	
	
    public UserEntity(String name, List reviews) {
        this();
        this.name = name;
        this.reviews = reviews;
    }
	
	
    @Column(name = "name", unique = true, nullable = false, length = 100)
    @Setter
	@Getter
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appuser", cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonSerialize(using = CustomListSerializer.class)
    @Setter
	@Getter
    private List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();


    @Override
    public String toString() {
        return "UserEntity{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", reviews='" + reviews + '\'' +
                '}';
    }
    
}
