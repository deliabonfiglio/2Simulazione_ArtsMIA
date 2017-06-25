package it.polito.tdp.artsmia.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private List<Integer> anniMostre;
	private SimpleDirectedGraph<Exhibition, DefaultEdge> graph;
	private Simulatore sim;
	
	public List<Integer> getAnniMostre(){
		if(anniMostre==null){
			ArtsmiaDAO dao = new ArtsmiaDAO();
			anniMostre= dao.getAnni();
		}
		return anniMostre;
	}
	
	public List<Exhibition> getExhibitionsFromYear(int anno){
		ArtsmiaDAO dao = new ArtsmiaDAO();
		List<Exhibition> exhi= dao.getExhibitionsFromYear(anno);
		
		for(Exhibition e : exhi){
			dao.getObjectsForExhibition(e);
		}
		
		return exhi;
	}
	
	public void createGraph(Integer anno){
		this.graph= new SimpleDirectedGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(graph, this.getExhibitionsFromYear(anno));
		
		for(Exhibition e1: graph.vertexSet()){
			for(Exhibition e2: graph.vertexSet()){
				if(!e1.equals(e2)){
					if(e2.getBegin()<=e1.getEnd() && e2.getBegin()>e1.getBegin()){
						graph.addEdge(e1, e2);
					}
				}
			}
		}
		
		System.out.println(graph.toString());
	}

	public boolean isGraphConnected(){
		ConnectivityInspector<Exhibition, DefaultEdge> ci = new ConnectivityInspector<>(graph);
		
		return ci.isGraphConnected();
	}
	
	public Exhibition getExhibitionWithMoreObject(int anno){
		ArtsmiaDAO dao = new ArtsmiaDAO();
		return dao.getExhibitionWithMoreObjects(anno);
	}
	
	public List<Integer> avviaSimulazione(int anno, int kstudenti){
		sim= new Simulatore( anno, this, kstudenti);
		
		sim.caricaMostre();
		sim.caricaCoda();
		sim.run();
		
		return sim.getResults();
	}

	public SimpleDirectedGraph<Exhibition, DefaultEdge> getGrafo() {
		return this.graph;
	}
}
