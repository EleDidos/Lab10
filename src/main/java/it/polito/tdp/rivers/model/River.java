package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class River {
	private int id;
	private String name;
	private double flowAvg;
	private List<Flow> flows;
	private int n_flows;
	
	public River(int id) {
		flows=new ArrayList <Flow>();
		this.id = id;
		n_flows=0;
	}

	
	public River(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	public LocalDate firstDate() {
		LocalDate firstDate= LocalDate.of(2030, 10, 10);
		
		if(n_flows>0) {
			for(Flow f:flows) {
				if(f.getDay().isBefore(firstDate))
					firstDate=f.getDay();
			}	
		}
		return firstDate;
	}
	
	
	public LocalDate lastDate() {
		LocalDate lastDate= LocalDate.of(1030, 10, 10);
		
		if(n_flows>0) {
			for(Flow f:flows) {
				if(f.getDay().isAfter(lastDate))
					lastDate=f.getDay();
			}	
		}
		return lastDate;
	}
	
	
	public int N_flows() {
		return this.n_flows;
	}
	
	
	public double avg_flows() {
		if(n_flows==0) 
			return 0.0;
		return this.flowAvg;
	}

	
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getFlowAvg() {
		return flowAvg;
	}

	public void setFlowAvg(double flowAvg) {
		this.flowAvg = flowAvg;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
		n_flows=this.flows.size();
		
		double tot = 0.0;
		for(Flow fi: this.flows)
			tot+=fi.getFlow();
		this.flowAvg=tot/n_flows;
	}

	public List<Flow> getFlows() {
		if (flows == null)
			flows = new ArrayList<Flow>();
		return flows;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		River other = (River) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
