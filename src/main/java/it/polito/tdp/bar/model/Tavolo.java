package it.polito.tdp.bar.model;

public class Tavolo {
	
	public int nPosti; 
	public boolean occupato;
	
	
	public Tavolo(int nPosti, boolean occupato) {
		this.nPosti = nPosti;
		this.occupato = occupato;
	}
	
	public int getnPosti() {
		return nPosti;
	}
	public void setnPosti(int nPosti) {
		this.nPosti = nPosti;
	}
	public boolean isOccupato() {
		return occupato;
	}
	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}
	
	

}
