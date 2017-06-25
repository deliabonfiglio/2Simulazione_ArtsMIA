package it.polito.tdp.artsmia.model;

public class Evento implements Comparable<Evento>{
	public enum EventType{SCELTA_MOSTRA}; 
	
	private Studente studente;
	private Exhibition mostra;
	private EventType tipo;
	
	public Evento(Studente studente, Exhibition mostra, EventType tipo) {
		super();
		this.studente = studente;
		this.mostra = mostra;
		this.tipo = tipo;
	}
	public Studente getStudente() {
		return studente;
	}
	public void setStudente(Studente studente) {
		this.studente = studente;
	}
	public Exhibition getMostra() {
		return mostra;
	}
	public void setMostra(Exhibition mostra) {
		this.mostra = mostra;
	}
	public EventType getTipo() {
		return tipo;
	}
	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int compareTo(Evento altro) {
		return this.mostra.getBegin()-altro.mostra.getBegin();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((studente == null) ? 0 : studente.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (studente == null) {
			if (other.studente != null)
				return false;
		} else if (!studente.equals(other.studente))
			return false;
		return true;
	}
	
	
	public String toString(){
		return studente+" "+mostra+" "+mostra.getOpere().size()+"\n";
	}

}
