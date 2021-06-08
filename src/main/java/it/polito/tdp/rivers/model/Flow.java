package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Flow {
	private LocalDate day;
	private double flow; //m3 al sec
	private int riverID;

	public Flow(LocalDate day, double flow, int riverID) {
		this.day = day;
		this.flow = flow;
		this.riverID = riverID;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
	}

	@Override
	public String toString() {
		return "Flow [day=" + day + ", flow=" + flow + ", riverID=" + riverID + "]";
	}

	
}