package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {

	// coda degli eventi
	private PriorityQueue<Event> coda;

	// modello del mondo
	// 1)dato un ing dimmi su quale gene lavora
	private List<Genes> geneStudiato;

	// parametri di input
	private Genes startGene;
	private int nTotIng;
	private Graph<Genes, DefaultWeightedEdge> grafo;

	private int TMAX = 36; // numero di mesi di simulazione (3anni)
	private double probabilitaMantenereGene = 0.3;

	public Simulator(Genes start, int n, Graph<Genes, DefaultWeightedEdge> grafo) {
		this.startGene = start;
		this.nTotIng = n;
		this.grafo = grafo;

		if (this.grafo.degreeOf(startGene) == 0) {
			throw new IllegalArgumentException("VERTICE DI PARTENZA ISOLATO");
		}

		// inizializzo coda degli eventi
		this.coda = new PriorityQueue<>();
		for (int nIng = 0; nIng < this.nTotIng; nIng++) {
			this.coda.add(new Event(0, nIng));
		}
		// inizializzo il mondo creando un arraylist con nTotIng valori pari a start
		// gene
		this.geneStudiato = new ArrayList<>();
		for (int nIng = 0; nIng < this.nTotIng; nIng++) {
			this.geneStudiato.add(this.startGene);
		}
	}

	public void run() {
		while (!this.coda.isEmpty()) {
			Event ev = coda.poll();

			int T = ev.getT();
			int nIng = ev.getnIng();
			Genes g = this.geneStudiato.get(nIng);

			if (T < this.TMAX) {
				// cosa studiera nIng al mese T+1?
				if (Math.random() < this.probabilitaMantenereGene) {
					// mantieni
					this.coda.add(new Event(T + 1, nIng));
				} else {
					// cambia gene

					// calcola la somma dei pesi degli adiacenti, s
					double s = 0;
					for (DefaultWeightedEdge edge : this.grafo.edgesOf(g)) {
						s += this.grafo.getEdgeWeight(edge);
					}
					// estrai numero casuale R tra 0 e s
					double R = Math.random() * s;

					// confronta R con le somme parziali dei pesi
					Genes nuovo = null;
					double somma = 0.0;
					for (DefaultWeightedEdge edge : this.grafo.edgesOf(g)) {
						somma += this.grafo.getEdgeWeight(edge);
						if (somma > R) {
							nuovo = Graphs.getOppositeVertex(this.grafo, edge, g);
							break;
						}

					}

					this.geneStudiato.set(nIng, nuovo);
					this.coda.add(new Event(T + 1, nIng));
				}
			}
		}

	}

	public Map<Genes, Integer> getGeniStudiati() {
		Map<Genes, Integer> studiati = new HashMap<>();

		for (int nIng = 0; nIng < this.nTotIng; nIng++) {
			Genes g = this.geneStudiato.get(nIng);
			if (studiati.containsKey(g)) {
				studiati.put(g, studiati.get(g) + 1);
			} else {
				studiati.put(g, 1);
			}
		}
		return studiati;
	}
}
