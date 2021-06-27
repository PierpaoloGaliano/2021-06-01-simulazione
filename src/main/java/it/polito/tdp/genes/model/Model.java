package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	Map<String, Genes> idMapGeniEssenziali;
	List<Genes> geniEssenziali;
	Graph<Genes, DefaultWeightedEdge> grafo;
	GenesDao dao = new GenesDao();

	public String creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMapGeniEssenziali = new HashMap<>();
		this.geniEssenziali = new ArrayList<Genes>();
		for (Genes g : dao.getAllGenes()) {
			if (g.getEssential().equals("Essential")) {
				grafo.addVertex(g);
				this.idMapGeniEssenziali.put(g.getGeneId(), g);
				geniEssenziali.add(g);
			}
		}
		for (Interaction i : dao.getInteraction()) {
			if (idMapGeniEssenziali.containsKey(i.getGeneID1()) && idMapGeniEssenziali.containsKey(i.getGeneID2())) {
				if (idMapGeniEssenziali.get(i.getGeneID1()).getChromosome() == idMapGeniEssenziali.get(i.getGeneID2())
						.getChromosome()) {
					Graphs.addEdgeWithVertices(this.grafo, idMapGeniEssenziali.get(i.getGeneID1()),
							idMapGeniEssenziali.get(i.getGeneID2()), i.getPeso() * 2);
				}
				if (idMapGeniEssenziali.get(i.getGeneID1()).getChromosome() != idMapGeniEssenziali.get(i.getGeneID2())
						.getChromosome()) {
					Graphs.addEdgeWithVertices(this.grafo, idMapGeniEssenziali.get(i.getGeneID1()),
							idMapGeniEssenziali.get(i.getGeneID2()), i.getPeso());
				}
			}
		}

		return "GRAFO CREATO CON: " + this.grafo.vertexSet().size() + " VERTICI" + " e " + this.grafo.edgeSet().size()
				+ " ARCHI";
	}
	public List<GenesPeso> geniAdiacenti(Genes g){
		List<GenesPeso> result = new ArrayList<>();
		List<Genes> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, g));
		for(Genes gene : vicini) {
			GenesPeso genePeso = new GenesPeso(gene, this.grafo.getEdgeWeight(this.grafo.getEdge(gene, g)));
			result.add(genePeso);
		}
		Collections.sort(result);
		return result;
	}
	public List<Genes> getGeniEssenziali() {
		return geniEssenziali;
	}
	public Map<Genes, Integer> simulaIngegneri(Genes start, int n){
		try {
			Simulator sim = new Simulator(start, n, this.grafo);
			sim.run();
			return sim.getGeniStudiati();
		}catch (IllegalArgumentException ex) {
			return null;
		}
	}
	

}
