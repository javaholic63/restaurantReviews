package com.powerreviews.project.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import com.powerreviews.project.core.BaseEntity;
import com.powerreviews.project.validators.Comment;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "review")
public class ReviewEntity extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1046555161707161163L;


	public ReviewEntity(){
		super();
	}

	public ReviewEntity(UserEntity appuser, RestaurantEntity restaurant, String comment, int rating, LocalDate reviewDate){
		this();
		this.appuser = appuser;
		this.restaurant = restaurant;
		this.comment = comment;
		this.rating = rating;
		this.reviewDate = reviewDate;
	}

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurant_id")
	@Setter
	@Getter
	private RestaurantEntity restaurant;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "appuser_id")
	@Setter
	@Getter
	private UserEntity appuser;

	@Column(name = "comment", unique = false, nullable = true, length = 200)
//	@Size(max=200, message="Review comment should not exceed 200 characters")
	@Comment
	@NotEmpty(message = "Please provide review comment")
	@Setter
	@Getter
	private String comment;

	@Column(name = "rating", unique = false, nullable = false, length = 4)
	@Min(value = 1, message = "Rating can't be less than 1 or bigger than 5")
	@Max(5)
	@Setter
	@Getter
	private int rating;

	@Column(name = "review_date")
	@Setter
	@Getter
	private LocalDate reviewDate = LocalDate.now();


	@Override
	public String toString() {
		return "Review{" +
				"id=" + id +
				", reviewer='" + appuser.getName() + '\'' +
				", comment='" + comment + '\'' +
				", rating='" + rating + '\'' +
				", date='" + reviewDate + '\'' +
				'}';
	}

}
