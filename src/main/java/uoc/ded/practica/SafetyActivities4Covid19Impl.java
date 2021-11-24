package uoc.ded.practica;

import java.time.LocalDate;
import java.util.Date;

import uoc.ded.practica.exceptions.ActivityNotFoundException;
import uoc.ded.practica.exceptions.LimitExceededException;
import uoc.ded.practica.exceptions.NoActivitiesException;
import uoc.ded.practica.exceptions.NoRatingsException;
import uoc.ded.practica.exceptions.NoRecordsException;
import uoc.ded.practica.exceptions.OrganizationNotFoundException;
import uoc.ded.practica.exceptions.UserNotFoundException;
import uoc.ded.practica.exceptions.UserNotInActivityException;
import uoc.ded.practica.model.Activity;
import uoc.ded.practica.model.Organization;
import uoc.ded.practica.model.Ticket;
import uoc.ded.practica.model.User;
import uoc.ded.practica.model.Record;
import uoc.ded.practica.util.OrderedVector;
import uoc.ded.practica.util.OrderedVectorDictionary;
import uoc.ei.tads.ExcepcioContenidorPle;
import uoc.ei.tads.ExcepcioPosicioInvalida;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.PilaVectorImpl;

public class SafetyActivities4Covid19Impl implements SafetyActivities4Covid19 {
	
	// Users as a simple vector
	private User users[];
	private int numUsers;
	
	// Organizations as a simple vector
	private Organization organizations[];
	private int numOrganizations;
	
	// Records as a stack
	private PilaVectorImpl<Record> records;
	private int rejectedRecords;
	private int totalRecords;
	
	// Activities as a ordered vector dictionary (ordered by its id)
	private OrderedVectorDictionary<String, Activity> activities;
	
	// Best activities as an ordered vector (ordered by its rating)
	private OrderedVector<Activity> bestActivities;
	
	// Most active user as a pointer
	private User mostActiveUser;
	
	public SafetyActivities4Covid19Impl() {
		// Initialize the array of users
		this.users = new User[U];
		this.numUsers = 0;
		
		// Initialize the array of organizations
		this.organizations = new Organization[O];
		this.numOrganizations = 0;
		
		// Initialize the stack of records and its counters of rejected and total records
		this.records = new PilaVectorImpl<Record>();
		this.rejectedRecords = 0;
		this.totalRecords = 0;
		
		// Initialize the ordered vector dictionary of activities
		this.activities = new OrderedVectorDictionary<String, Activity>(A, Activity.COMPARATOR);
		
		// Initialize the ordered vector of best activities
		this.bestActivities = new OrderedVector<Activity>(A, Activity.COMPARATOR_RATING);
		
		// Initialize the most active user with null as long as there are no users yet
		this.mostActiveUser = null;
	}

	@Override
	public void addUser(String userId, String name, String surname, LocalDate birthday, boolean covidCertificate) throws ExcepcioContenidorPle {
		// Get the user from the user array this.users
		User user = this.getUser(userId);
		
		// The user has to be added, as long as he/she doesn't exist yet, at the last available position of the array
		if (user == null) {
			// Check if the array of users is already full
			if (this.numUsers >= U)
				throw new ExcepcioContenidorPle();
			
			this.users[numUsers] = new User(userId, name, surname, birthday, covidCertificate);
			this.numUsers++;
		}
		// Update its information if he/she exists
		else {
			user.setName(name);
			user.setSurname(surname);
			user.setBirthday(birthday);
			user.setCovidCertificate(covidCertificate);
		}
	}

	@Override
	public void addOrganization(int organizationId, String name, String description) throws ExcepcioPosicioInvalida {
		// Check if the position is valid: among 0 and O-1
		if (organizationId >= O)
			throw new ExcepcioPosicioInvalida();
		
		// Get the organization from the organization array this.organizations
		Organization organization = this.getOrganization(organizationId);
		
		// The organization has to be added, as long as it doesn't exist yet, at the same position of the array than its id
		if (organization == null) {
			this.organizations[organizationId] = new Organization(organizationId, name, description);
			this.numOrganizations++;
		}
		// Update its information if he/she exists
		else {
			organization.setName(name);
			organization.setDescription(description);
		}
	}

	@Override
	public void addRecord(String recordId, String actId, String description, Date date, Mode mode, int num, int organizationId) throws OrganizationNotFoundException {
		// Get the organization from the organization array this.organizations
		Organization org = this.getOrganization(organizationId);
		
		// Throw an exception if the organization doesn't exists in the array
		if (org == null)
			throw new OrganizationNotFoundException();
		
		// Initialize the record with its values received
		Record record = new Record(recordId, actId, description, date, mode, num, organizationId);
		
		// Add to the stack and increment the total records
		this.records.empilar(record);
		this.totalRecords++;
	}

	@Override
	public void updateRecord(Status status, Date date, String description) throws NoRecordsException {
		// No records to be updated in the stack
		if (this.records.estaBuit())
			throw new NoRecordsException();
		
		// Get the record from the stack this.records and remove it from there
		Record record = this.records.desempilar();
		
		// In this case, if the record doesn't exist, the method desempilar will throw ExcepcioContenidorBuit
		// so it's not necessary to check if record is null or not
		
		// If the record is rejected, increment its counter
		if (status == Status.DISABLED)
			this.rejectedRecords++;
		// If the record is validated, generate the new activity and add it
		// to this.activities, this.bestActivities and organization activities
		else if (status == Status.ENABLED) {
			// Initialize the activity
			Activity activity = new Activity(record.getActId(), record.getDescription(), record.getDate(), record.getMode(), record.getMaxAssistants(), record.getOrganizationId());
			
			// Add it to the ordered vector dictionary of activities
			this.activities.afegir(record.getActId(), activity);
			// Add it to the ordered vector of best activities
			this.bestActivities.add(activity);
			
			// Search the organization by its id to add the activity
			Organization org = this.getOrganization(record.getOrganizationId());
			
			// The organization org must exist in the record, because of this project
			// doesn't have to code defensive programming, this instance doesn't have to be checked
			
			// Add the activity to the linked list of activities of that organization
			org.insertActivity(activity);
		}
		
		// Print the date and the description of the record whether or not is rejected
		System.out.println("[" + date.toString() + "] " + description);
	}

	@Override
	public void createTicket(String userId, String actId) throws UserNotFoundException, ActivityNotFoundException, LimitExceededException {
		// Get the user from the user array this.users
		User user = this.getUser(userId);
		
		// Check if the user is in the array throw an exception if not
		if (user == null)
			throw new UserNotFoundException();
	
		// Get the activity from the ordered vector dictionary this.activities
		Activity activity = this.getActivity(actId);
		
		// Check if activity is in the ordered vector dictionary and throw an exception if not
		if (activity == null)
			throw new ActivityNotFoundException();
		
		// Check if the activity already has reach the maximium capacity
		// and throw an exception in that case
		if (activity.isFull())
			throw new LimitExceededException();
		
		// Create a new ticket for that activity
		activity.createTicket(user);
		// Add this activity to user linked list of activities
		user.addActivity(activity);
		// Update the most active user according to its number of activities
		this.updateMostActiveUser(user);
	}

	@Override
	public Ticket assignSeat(String actId) throws ActivityNotFoundException {
		// Get the activity from the ordered vector dictionary this.activities
		Activity activity = this.getActivity(actId);
		
		// Check if activity is in the ordered vector dictionary and throw an exception if not
		if (activity == null)
			throw new ActivityNotFoundException();
		
		// Get a ticket from the queue of tickets of this activity
		return activity.getTicket();
	}

	@Override
	public void addRating(String actId, Rating rating, String message, String userId) throws ActivityNotFoundException, UserNotFoundException, UserNotInActivityException {
		// Get the activity from the ordered vector dictionary this.activities
		Activity activity = this.getActivity(actId);
		
		// Check if activity is in the ordered vector dictionary and throw an exception if not
		if (activity == null)
			throw new ActivityNotFoundException();
		
		// Get the user from the user array this.users
		User user = this.getUser(userId);
		
		// Check if the user is in the array throw an exception if not
		if (user == null)
			throw new UserNotFoundException();
		
		// Check if the user is in this activity and throw and exception if not
		if (!user.isInActivity(activity))
			throw new UserNotInActivityException();
		
		// Initialize the rating
		uoc.ded.practica.model.Rating activityRating = new uoc.ded.practica.model.Rating(rating, message, user);
		
		// Add it to linked list of ratings of that activity
		activity.addRating(activityRating);
		
		// Update the ordered vector deleting and adding the activity to be ordered by itself
		this.bestActivities.update(activity);
	}

	@Override
	public Iterador<uoc.ded.practica.model.Rating> getRatings(String actId) throws ActivityNotFoundException, NoRatingsException {
		// Get the activity from the ordered vector dictionary this.activities
		Activity activity = this.getActivity(actId);
		
		// Check if activity is in the ordered vector dictionary and throw an exception if not
		if (activity == null)
			throw new ActivityNotFoundException();
		
		// If the activity doesn't have any rating, throw an exception
		if (activity.getRatingNum() == 0)
			throw new NoRatingsException();
		
		// Return the iterator of ratings of this activity
		return activity.ratings();
	}

	@Override
	public Activity bestActivity() throws ActivityNotFoundException {
		// If there are no activities, throw an exception
		if (this.bestActivities.estaBuit())
			throw new ActivityNotFoundException();
		
		// As long as the best activities ordered vector is already ordered, just return the first position
		return this.bestActivities.get(0);
	}

	@Override
	public User mostActiveUser() throws UserNotFoundException {
		// If there aren't any user active, throw an exception
		if (this.mostActiveUser == null)
			throw new UserNotFoundException();
		
		// As long as the mostActiveUser is already updated, just return the pointer
		return this.mostActiveUser;
	}

	@Override
	public double getInfoRejectedRecords() {
		// Return rejectedRecords/totalRecords
		return (double)this.numRejectedRecords()/(double)this.numRecords();
	}

	@Override
	public Iterador<Activity> getAllActivities() throws NoActivitiesException {
		// If there are no activities, throw an exception
		if (this.activities.estaBuit())
			throw new NoActivitiesException();
		
		// Return the iterator of activities
		return this.activities.elements();
	}

	@Override
	public Iterador<Activity> getActivitiesByOrganization(int organizationId) throws NoActivitiesException {
		// Get the organization from the organization array this.organizations
		// The organization has to be in the array of organizations
		Organization org = this.getOrganization(organizationId);
		
		// Throw an exception if the organization doesn't have any activity
		if (org.numActivities() == 0)
			throw new NoActivitiesException();
		
		// Return the iterator of activities of the organization searched
		return org.activities();
	}

	@Override
	public Iterador<Activity> getActivitiesByUser(String userId) throws NoActivitiesException {
		// Get the user from the user array this.users
		// The user has to be in the array of users
		User user = this.getUser(userId);
		
		// Throw an exception if the user doesn't participate in any activity
		if (user.numActivities() == 0)
			throw new NoActivitiesException();
		
		// Return the iterator of activities of the user searched
		return user.activities();
	}

	@Override
	public User getUser(String userId) {
		// Search the user by its id and return it if it has been found
		for (int i = 0; i < this.numUsers; i++) {
			if (this.users[i].getId() == userId)
				return this.users[i];
		}
		
		// Return null if the for ends without returning a user
		return null;
	}

	@Override
	public Organization getOrganization(int organizationId) {
		// Return an organization by its id
		return this.organizations[organizationId];
	}

	@Override
	public Record currentRecord() {
		// Return the record of the top of the stack of records this.records
		return this.records.cim();
	}

	@Override
	public int numUsers() {
		// Return the number of users in the array of users this.users
		return this.numUsers;
	}

	@Override
	public int numOrganizations() {
		// Return the number organizations in the array of organizations this.organizations
		return this.numOrganizations;
	}

	@Override
	public int numPendingRecords() {
		// Return the number of pendings records, the number of the elements in the stack
		return this.records.nombreElems();
	}

	@Override
	public int numRecords() {
		// Return the number of records according its count
		return this.totalRecords;
	}

	@Override
	public int numRejectedRecords() {
		// Return the number of records rejected according its counter
		return this.rejectedRecords;
	}

	@Override
	public int numActivities() {
		// Return the number of activies from the ordered vector dictionary this.activities
		return this.activities.nombreElems();
	}

	@Override
	public int numActivitiesByOrganization(int organizationId) {
		// Return the number of activities of an organization by its id,
		// the organization has to be in the array of organizations
		return this.getOrganization(organizationId).numActivities();
	}

	@Override
	public Activity getActivity(String actId) {
		// Return the activity by its id from the ordered vector dictionary this.activities
		return this.activities.consultar(actId);
	}

	@Override
	public int availabilityOfTickets(String actId) {
		// Return the number of tickets of activity by its id,
		// the activity has to be in the ordered vector dictionary this.activities
		return this.getActivity(actId).getAvailableTickets();
	}
	
	private void updateMostActiveUser(User user) {
		// If there are no most active user yet, update it with user from the param
		if (this.mostActiveUser == null)
			this.mostActiveUser = user;
		else {
			// Only update the user if the user from param has more activities than the old one
			if (this.mostActiveUser.numActivities() < user.numActivities())
				this.mostActiveUser = user;
		}
	}
	
}
