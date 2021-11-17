package uoc.ded.practica.model;

import uoc.ei.tads.*;
import java.time.LocalDate;

public class User {
	
	private String id;
	private String name;
	private String surname;
	private LocalDate birthday;
	private boolean covidCertificate;
	private LlistaEncadenada<Activity> activities;
	
	public User(String id, String name, String surname, LocalDate birthday, boolean covidCertificate) {
		this.setId(id);
		this.setName(name);
		this.setSurname(surname);
		this.setBirthday(birthday);
		this.activities = new LlistaEncadenada<Activity>();
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public void setCovidCertificate(boolean covidCertificate) {
		this.covidCertificate = covidCertificate;
	}
	
	public boolean hasCovidCertificate() {
		return covidCertificate;
	}
	
	// Add an activity to the linked list of them
	public void addActivity(Activity activity) {
		this.activities.afegirAlFinal(activity);
	}
	
	// Check if the linked list of activities contain the activity of the parameter
	public boolean isInActivity(Activity activity) {
		
		boolean found = false;
		
		for (Iterador<Activity> it = this.activities(); it.hiHaSeguent() && !found;) {
			if (it.seguent().equals(activity)) {
				found = true;
			}
		}
		
		return found;
	}
	
	// Return an iterator with all the activities of this user
	public Iterador<Activity> activities() {
		return this.activities.elements();
	}
	
	// Get the amount of activities of this user
	public int numActivities() {
		return this.activities.nombreElems();
	}
	
}
