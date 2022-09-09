package it.polito.tdp.PremierLeague.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	List<Match> vertici;
	List<Team> teams;
	Graph<Match, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao= new PremierLeagueDAO();
	}
	
	public String creaGrafo() {
		this.grafo= new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		vertici= dao.listAllMatches();
		teams= dao.listAllTeams();
		
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		
		for(Match m1: vertici) {
					if(m1.resultOfTeamHome.compareTo(-1)==0) {
						m1.pesoOut+=3;
					}if(m1.resultOfTeamHome.compareTo(1)==0) {
						m1.pesoIn+=3;
					}if(m1.resultOfTeamHome.compareTo(0)==0) {
						m1.pesoIn+=1;
						m1.pesoOut+=1;
					}
		}
				
	for(Match m1: vertici) {
		for(Match m2: vertici) {
			if(m1!=m2) {
				 if(m1.getPesoIn()<m2.getPesoOut() && (!grafo.containsEdge(m2,m1))) {
					 Graphs.addEdgeWithVertices(this.grafo, m1, m2, m2.getPesoOut()-m1.getPesoIn());
				 }
				 if(m1.getPesoIn()>m2.getPesoOut() && (!grafo.containsEdge(m1,m2)) ) {
					 Graphs.addEdgeWithVertices(this.grafo, m2, m1, m1.getPesoIn()-m2.getPesoOut());
				 }
				}
			}
		}
	
		
		
		
		return "Grafo creato!\n# Vertici:"+grafo.vertexSet().size()+ "\n# Archi: "+grafo.edgeSet().size();	
	}
	
	
	
}
