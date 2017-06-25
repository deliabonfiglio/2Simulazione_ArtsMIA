package it.polito.tdp.artsmia.db;

import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class TestArtsmiaDAO {

	public static void main(String[] args) {
		
		ArtsmiaDAO dao = new ArtsmiaDAO() ;
		
		List<ArtObject> objects = dao.listObject() ;
		System.out.println(objects.size());

		
		List<Exhibition> l1= dao.getExhibitionsFromYear(2010);
		System.out.println(l1.toString());
		
		Exhibition ex= dao.getExhibitionWithMoreObjects(2010);
		System.out.println(ex.toString());
	}

}
