package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;

public class Event implements Comparable<Event> {
	
	private Duration time; 
	private int nPersone; 
	private Duration durata; 
	private double tolleranza; 
	private Tavolo tavolo;
	private EventType type; 
	
	public enum EventType{
		ARRIVO_GRUPPO_CLIENTI,
		
		TAVOLO_LIBERATO
	}
	
	
	
	public Event(Duration time, int nPersone, Duration durata, double tolleranza, Tavolo tavolo, EventType type) {
		
		this.time = time;
		this.nPersone = nPersone;
		this.durata = durata;
		this.tolleranza = tolleranza;
		this.tavolo = tavolo;
		this.type = type;
	}

	

	
	public Duration getTime() {
		return time;
	}


	public void setTime(Duration time) {
		this.time = time;
	}


	public int getnPersone() {
		return nPersone;
	}


	public void setnPersone(int nPersone) {
		this.nPersone = nPersone;
	}


	public Duration getDurata() {
		return durata;
	}


	public void setDurata(Duration durata) {
		this.durata = durata;
	}


	public double getTolleranza() {
		return tolleranza;
	}


	public void setTolleranza(double tolleranza) {
		this.tolleranza = tolleranza;
	}


	public Tavolo getTavolo() {
		return tavolo;
	}


	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}


	public EventType getType() {
		return type;
	}


	public void setType(EventType type) {
		this.type = type;
	}


	@Override
	public int compareTo(Event o) {
		
		return this.time.compareTo(o.time);
	}

	
}
