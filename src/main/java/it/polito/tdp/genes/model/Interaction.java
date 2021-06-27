package it.polito.tdp.genes.model;

public class Interaction {
	private String geneID1;
	private String geneID2;
	private double peso;
	public Interaction(String geneID1, String geneID2, double peso) {
		super();
		this.geneID1 = geneID1;
		this.geneID2 = geneID2;
		this.peso = peso;
	}
	public String getGeneID1() {
		return geneID1;
	}
	public void setGeneID1(String geneID1) {
		this.geneID1 = geneID1;
	}
	public String getGeneID2() {
		return geneID2;
	}
	public void setGeneID2(String geneID2) {
		this.geneID2 = geneID2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}

}

