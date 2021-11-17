package uoc.ded.practica.model;

import uoc.ei.tads.*;

public class Organization {
	
	private int id;
	private String name;
	private String description;
	private LlistaEncadenada<Activity> activities;
	
	public Organization(int id, String name, String description) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.activities = new LlistaEncadenada<Activity>();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	// Return an iterator with all the activities of this organization
	public Iterador<Activity> activities() {
		return this.activities.elements();
	}
	
	// Insert an activity to the linked list of activities
	public void insertActivity(Activity activity) {
		this.activities.afegirAlFinal(activity);
	}
	
	// Get the amount of activities of this organization
	public int numActivities() {
		return this.activities.nombreElems();
	}
	
}
