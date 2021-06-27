package it.polito.tdp.genes.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model();
		System.out.println(m.creaGrafo());
		System.out.println(m.geniAdiacenti(m.idMapGeniEssenziali.get("G234194")));

	}

}
