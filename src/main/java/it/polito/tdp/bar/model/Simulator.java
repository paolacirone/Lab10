package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	//modello del mondo
	private List<Tavolo> tavoli; 
	
	
	//settare parametri simulazione
	private int NUM_EVENTI = 2000; 
	private int TEMPO_ARRIVOMAX =10; 
	private int NUM_PERSONEMAX =10; 
	private int DURATA_MINIMA=60; 
	private int DURATA_MAX=120; 
	private double TOLLERANZA_MAX=0.9; 
	private double OCCUPAZIONE_MIN =0.5; 
	
	//output
	private Statistiche stat;
	
	//coda degli eventi
	private PriorityQueue<Event> queue; 
	
	
	private void caricaTavoli() {
		this.tavoli= new ArrayList<>(); 
		
		aggiungiTavolo(2,10);
		aggiungiTavolo(4,8);
		aggiungiTavolo(4,6);
		aggiungiTavolo(5,4);
		
		//ordino la lista in modo che i più piccoli siano i primi
		Collections.sort(this.tavoli, new Comparator<Tavolo>(){

			@Override
			public int compare(Tavolo o1, Tavolo o2) {
				
				return o1.getnPosti()-o2.getnPosti();
			}
			
		});
		
		
	}
	
	public void caricaEventi() {
		Duration arrivo = Duration.ofMinutes(0); //parto al minuto  0
		
		for(int i=0; i<this.NUM_EVENTI; i++) {
			
			int numPersone =(int) (Math.random()*this.NUM_PERSONEMAX+1);
			Duration durata = Duration.ofMinutes(this.DURATA_MINIMA + (int) (Math.random()*(this.DURATA_MAX-this.DURATA_MINIMA)));
			double tolleranza = Math.random()*this.TOLLERANZA_MAX;
			Event e = new Event(arrivo, numPersone, durata, tolleranza,null, EventType.ARRIVO_GRUPPO_CLIENTI);
			
			this.queue.add(e);
			arrivo= arrivo.plusMinutes(1 + (int) (Math.random()*this.TEMPO_ARRIVOMAX ));
		}
	}

	public void init() {
		caricaTavoli(); 
		this.queue = new PriorityQueue<>(); 
		caricaEventi(); 
		this.stat= new Statistiche();
	}

	public void run() {
		while(!queue.isEmpty()) {
			Event e = queue.poll(); 
			System.out.println(e);
			processEvent(e);
		}
	}
	
	
	private void processEvent(Event e) {
	
		switch(e.getType()) {
		
		case ARRIVO_GRUPPO_CLIENTI: 
			
			stat.addNumClientiTot(e.getnPersone());
			
			//cerca tavolo
			Tavolo trovato=null; 
			for(Tavolo t: this.tavoli) {
				if(!t.isOccupato() && t.getnPosti()>=e.getnPersone() && t.getnPosti()*this.OCCUPAZIONE_MIN<=e.getnPersone()) {
					trovato=t;
					break;
				}
			}
			if(trovato!=null) {
				System.out.format("Sedute %d persone a tavolo con %d posti ", e.getnPersone(), trovato.getnPosti());
			    stat.addNumClientiSoddisfatti(e.getnPersone());
			    trovato.setOccupato(true);

				// si alzeranno
				queue.add(new Event(e.getTime().plus(e.getDurata()), e.getnPersone() ,e.getDurata(),e.getTolleranza(), trovato,  EventType.TAVOLO_LIBERATO
						));
			} else {
					// nessun tavolo disponibile. Accettano il bancone?
					double bancone = Math.random() ;
					if(bancone<=e.getTolleranza()) {
						// sì, vado al bancone
						stat.addNumClientiSoddisfatti(e.getnPersone());
					} else {
						// no, vado a casa
						stat.addNumClientiInsoddisfatti(e.getnPersone());
					}
				}
				
			
			break; 
			
		case TAVOLO_LIBERATO: 
			
			e.getTavolo().setOccupato(false);
			
			
			
			break; 
		
		
		
		}
		
	}
	
	public Statistiche getStat() {
		return stat; 
	}

	private void aggiungiTavolo(int num, int nPosti) {
		
		for(int i =0; i<num; i++) {
			Tavolo t = new Tavolo(nPosti,false);
			this.tavoli.add(t);
		}
		
	}

}
