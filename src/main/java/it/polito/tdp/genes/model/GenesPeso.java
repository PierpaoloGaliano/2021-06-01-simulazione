package it.polito.tdp.genes.model;

public class GenesPeso implements Comparable<GenesPeso>{
	private Genes g;
	private Double peso;
	public GenesPeso(Genes g, Double peso) {
		super();
		this.g = g;
		this.peso = peso;
	}
	public Genes getG() {
		return g;
	}
	public void setG(Genes g) {
		this.g = g;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "g=" + g + ", peso=" + peso ;
	}
	@Override
	public int compareTo(GenesPeso arg0) {

		return arg0.getPeso().compareTo(this.peso);
	}
	
}
