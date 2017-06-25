package it.polito.tdp.artsmia.model;

import java.util.HashSet;
import java.util.Set;

public class Exhibition {
	private int exhibition_id;
	private String department;
	private String title;
	private int begin;
	private int end;
	
	private Set<ArtObject> opere;
	
	public Exhibition(int exhibition_id, String department, String title, int begin, int end) {
		super();
		this.exhibition_id = exhibition_id;
		this.department = department;
		this.title = title;
		this.begin = begin;
		this.end = end;
		this.opere=new HashSet<>();
	}
	
	public int getExhibition_id() {
		return exhibition_id;
	}
	public void setExhibition_id(int exhibition_id) {
		this.exhibition_id = exhibition_id;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + exhibition_id;
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
		Exhibition other = (Exhibition) obj;
		if (exhibition_id != other.exhibition_id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return title + " " + begin + "-" + end + "\n";
	}
	
	public Set<ArtObject> getOpere() {
		return opere;
	}
	public void setOpere(Set<ArtObject> opere2) {
		this.opere = opere2;
	}
	
	

}
