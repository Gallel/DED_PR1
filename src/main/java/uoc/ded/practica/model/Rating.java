package uoc.ded.practica.model;

import uoc.ded.practica.SafetyActivities4Covid19;

public class Rating {
	
	private SafetyActivities4Covid19.Rating rating;
	private String message;
	private User user;
	
	public Rating(SafetyActivities4Covid19.Rating rating, String message, User user) {
		this.setRating(rating);
		this.setMessage(message);
		this.setUser(user);
	}

	public void setRating(SafetyActivities4Covid19.Rating rating) {
		this.rating = rating;
	}
	
	public Object getRating() {
		return rating;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

}
