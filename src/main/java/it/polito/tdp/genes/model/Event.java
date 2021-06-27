package it.polito.tdp.genes.model;

public class Event implements Comparable<Event> {

	private int T;
	private int nIng;
	
	public Event(int t, int nIng) {
		super();
		T = t;
		this.nIng = nIng;
	}

	@Override
	public int compareTo(Event arg0) {
		// TODO Auto-generated method stub
		return this.T-arg0.T;
	}

	public int getT() {
		return T;
	}

	public void setT(int t) {
		T = t;
	}

	public int getnIng() {
		return nIng;
	}

	public void setnIng(int nIng) {
		this.nIng = nIng;
	}

}
