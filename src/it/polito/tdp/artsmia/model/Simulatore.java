package it.polito.tdp.artsmia.model;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.artsmia.model.Evento.EventType;

import java.util.*;

public class Simulatore {
	//parametri simulazione
	private int K;
	private int annoPartenza;
	
	private SimpleDirectedGraph<Exhibition, DefaultEdge> grafo;
	
	private List<Exhibition> mostre;
	private Map<Integer, Studente> map;
	
	private PriorityQueue< Evento> queue;

	public Simulatore(int anno, Model model, int kstudenti) {
		this.K=kstudenti;
		this.annoPartenza= anno;
		this.grafo= model.getGrafo();
		this.mostre= new ArrayList<>();
		this.queue= new PriorityQueue<>();
	}

	public void caricaMostre() {
		for(Exhibition m: grafo.vertexSet()){
			if(m.getBegin()== annoPartenza){
				mostre.add(m);
			}
		}
	}

	public void caricaCoda() {
		map = new HashMap<>();
		//scelta stessa mostra a cui partecipare in modo casuale
		Exhibition mostra = mostre.get((int) (Math.random()*mostre.size()));
		
		for(int i=1; i<=K; i++){
			//i è l'id dello studente
			Studente s = new Studente(i);
			s.addOpere(mostra.getOpere());
			map.put(s.getId(), s);
			
			queue.add(new Evento(s, mostra, EventType.SCELTA_MOSTRA));
		}
		
	}

	public void run() {
		while(!queue.isEmpty()){
			Evento e = queue.poll();
			System.out.println(e.toString());
			
			switch (e.getTipo()) {
			case SCELTA_MOSTRA:
				 processSceltaMostraEvent(e); 
				break;

			default:
				break;
			}
		}
		
	}

	private void processSceltaMostraEvent(Evento e) {
		Exhibition mostra = e.getMostra();
		
		List<Exhibition> vicine = Graphs.neighborListOf(grafo, mostra);
		
		//System.out.println("mostre raggiungibili "+vicine.toString());
		
		if(vicine.size()!=0){
			Exhibition nuovaMostra = vicine.get((int) (Math.random()*vicine.size()));
			
			Studente s = map.get(e.getStudente().getId());
			
			s.addOpere(nuovaMostra.getOpere());
			
			queue.add(new Evento(s, nuovaMostra, EventType.SCELTA_MOSTRA));		
		} else {
			System.out.println("No mostre raggiungibili\n");
		}
	}

	public List<Integer> getResults() {
		
		List<Integer> studenti = new ArrayList<>();
		
		for(int i=1; i<=K; i++){
			studenti.add(map.get(i).getOpere().size());
		}
		
		Collections.sort(studenti);
		return studenti;
	}

}
