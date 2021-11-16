package uoc.ded.practica.model;

public class Ticket {
	
	private int seat;
	private User user;

	public Ticket(int seat, User user) {
		this.setSeat(seat);
		this.setUser(user);
	}
	
	public void setSeat(int seat) {
		this.seat = seat;
	}
	
	public int getSeat() {
		return seat;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
