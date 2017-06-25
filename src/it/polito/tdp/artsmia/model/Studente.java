package it.polito.tdp.artsmia.model;

import java.util.HashSet;
import java.util.Set;

public class Studente implements Comparable<Studente>{
	private int id;
	private Set<ArtObject> opere;
	
	public Studente(int id) {
		super();
		this.id = id;
		this.opere= new HashSet<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<ArtObject> getOpere() {
		return opere;
	}

	public void addOpere(Set<ArtObject> opere) {
		this.opere.addAll(opere);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Studente other = (Studente) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(Studente arg0) {
		return -(this.getOpere().size()-arg0.getOpere().size());
	}
	
	public String toString(){
		return "Studente "+id+" numero opere: "+this.getOpere().size()+"\n";
	}

}