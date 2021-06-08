package it.polito.tdp.rivers.model;
 

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Map <Integer,River> riversMap;
	private Simulatore sim;
	
	public Model() {
		dao = new RiversDAO();
		riversMap=new TreeMap <Integer,River>();
		dao.getAllRivers(riversMap);
		
	}
	
	public River getRiver (String nome) {
		
		for(River ri: riversMap.values()) {
			if(ri.getName().equals(nome)) {
				return ri;
			}
		}
			return null;
	}


	public List <String> getRivers() {
		List<String> rivers = new ArrayList<String>();
		
		for(River r: riversMap.values())
			rivers.add(r.getName());
		
		return rivers;
	}
	
	
	
	public void addFlows() {
		for(Integer id: riversMap.keySet()) {
			riversMap.get(id).setFlows(this.dao.getFlows(id));
		}
	}

	
	/*
	 * 0. Prima data
	 * 1. Ultima data
	 * 2. N misurazioni
	 * 3. flusso medio
	 * @param scelto
	 * @return
	 */
	public String[] getDates(String scelto) {
		River r = new River(0,null);
		
		for(River ri:riversMap.values())
			if(ri.getName().equals(scelto))
				r = ri;
		
		String []dati = new String [4];
		//riempi i dati
		dati[0]=r.firstDate().toString();
		dati[1]=r.lastDate().toString();
		dati[2]=""+r.N_flows();
		dati[3]=""+r.avg_flows();
		
		return dati;
	}
	
	
	public StringBuilder simula (Double k, River river) {
		sim = new Simulatore();
		
			sim.init(k, river);
			sim.run();
		
			StringBuilder sb = new StringBuilder();
			sb.append("Il numero di giorni in cui non si è potuta garantire l'erogazione minima è: "+sim.getGGNoErogazione()+"\n");
			sb.append("L'occupazione media del bacino è: "+sim.getC_med()+" m3");
			return sb;
		
	}
	
	

}