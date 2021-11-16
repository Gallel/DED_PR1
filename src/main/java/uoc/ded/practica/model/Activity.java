package uoc.ded.practica.model;

import java.util.Comparator;
import java.util.Date;

import uoc.ded.practica.SafetyActivities4Covid19.Mode;
import uoc.ei.tads.CuaVectorImpl;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.LlistaEncadenada;

public class Activity {

	private String actId;
	private String description;
	private Date date;
	private Mode mode;
	private int num;
	private int organizationId;
	private LlistaEncadenada<uoc.ded.practica.model.Rating> ratings;
	private CuaVectorImpl<Ticket> tickets;
	private int totalTickets;
	
	public Activity(String actId, String description, Date date, Mode mode, int num, int organizationId) {
		this.setActId(actId);
		this.setDescription(description);
		this.setDate(date);
		this.setMode(mode);
		this.setMaxAssistants(num);
		this.setOrganizationId(organizationId);
		this.ratings = new LlistaEncadenada<uoc.ded.practica.model.Rating>();
		this.tickets = new CuaVectorImpl<Ticket>();
		this.totalTickets = 0;
	}
	
	public static Comparator<String> COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String id1, String id2) {
			return id1.compareTo(id2);
		}
	};
	
	public static Comparator<Activity> COMPARATOR_RATING = new Comparator<Activity>() {
		@Override
		public int compare(Activity act1, Activity act2) {
			return act2.rating().compareTo(act1.rating());
		}
	};

	public void setActId(String actId) {
		this.actId = actId;
	}
	
	public String getActId() {
		return actId;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMaxAssistants(int num) {
		this.num = num;
	}
	
	public int getMaxAssistants() {
		return num;
	}
	
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	
	public int getOrganizationId() {
		return organizationId;
	}

	public Double rating() {
		int num = this.ratings.nombreElems();
		
		if (num == 0)
			return 0.0;
		
		int rating = 0;
		
		for (Iterador<uoc.ded.practica.model.Rating> it = this.ratings(); it.hiHaSeguent();) {
			rating += ((uoc.ded.practica.SafetyActivities4Covid19.Rating) it.seguent().getRating()).getValue();
		}
		
		return (double)rating / (double)num;
	}
	
	public Iterador<uoc.ded.practica.model.Rating> ratings() {
		return this.ratings.elements();
	}
	
	public void addRating(uoc.ded.practica.model.Rating rating) {
		this.ratings.afegirAlFinal(rating);
	}
	
	public int getRatingNum() {
		return this.ratings.nombreElems();
	}
	
	public boolean isFull() {
		return this.totalTickets == this.getMaxAssistants();
	}
	
	public void createTicket(User user) {
		this.totalTickets++;
		Ticket ticket = new Ticket(this.totalTickets, user);
		this.tickets.encuar(ticket);
	}
	
	public Ticket getTicket() {
		return this.tickets.desencuar();
	}
	
	public int getAvailableTickets() {
		return this.num - this.totalTickets;
	}
}
