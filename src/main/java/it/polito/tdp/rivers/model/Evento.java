package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.time.LocalTime;


public class Evento implements Comparable<Evento>{
	
	enum EventType {
		ARRIVAL, 
		NORMAL_EXIT, //f_out_min
		SUPER_EXIT, //10*f_out_min (5% di prob)
		TRACIMAZIONE //C>Q --> l'eccesso deve uscire
	} ;

	private LocalDate time ;
	private EventType type ;
	public Evento(LocalDate localDate, EventType type) {
		super();
		this.time = localDate;
		this.type = type;
	}
	
	public LocalDate getTime() {
		return time;
	}

	public void setTime(LocalDate time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	
	@Override //a seconda del tempo relativo all'evento
	public int compareTo(Evento other) {
		return this.time.compareTo(other.time);
	}
	
	public 	String toString() {
		return time+" "+type.toString();
	}
	

}
