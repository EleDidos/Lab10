package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import it.polito.tdp.rivers.model.Evento.EventType;;

public class Simulatore {
	
		
	
	private PriorityQueue <Evento> queue;
	private River r;
	private List <Double> livelliC;
	
	//PARAMETRI SIMULAZIONE PARTENZA //////////

	//[m3] contenibili da bacino per una frazione k del mese 
	// a flusso medio prima di riempirsi			
	double Q; // m3 al gg
	//flusso out minimo
	double f_out_min;
	double f_med;
	
	
	//PARAMETRI OUTPUT ////////////////
	int gg; // gg in cui NON si garantisce f_out_min
	double C_med; //occupazione media del bacino
	
	//STATO DEL MONDO ////////////////
	double f_in;
	double f_out;
	double C; // [m3] QNT ACQUA nel bacino
			//da aggiornare gg per gg
	
					// scala      
	public void init (Double k, River r) {
		this.r=r;
		livelliC= new LinkedList<Double>();
		this.f_med=r.getFlowAvg()*86400;	
							//m3 al secondo --> m3 all'ora
		Q=k* f_med *30;
		C= Q/2; //m3 iniziali
		livelliC.add(C);
		f_out_min=(0.8*f_med);
		
		this.queue=new PriorityQueue <Evento>();
		//riempio coda con tutti gli arrivi del fiume scelto
		for(Flow f: r.getFlows())
			queue.add(new Evento(f.getDay(),EventType.ARRIVAL));
		
	}
	
	
	public void run() {
		
		Evento e;
		while ((e=this.queue.poll())!=null) {
			System.out.println(e);
			f_in=this.getFlowAtThatTime(e.getTime());
			
			//TRACIMAZIONE: esce l'eccesso + f_out
			if(C+f_in>Q) {
				double eccesso=Q-(C+f_in);
				C=C+f_in-eccesso-f_out;
				livelliC.add(C);
			} else {
				C=C+f_in; //entrata
				
				//EROGAZIONE SUPER
				double probability = Math.random(); //numero tra 0.0 e 1.0
				if(probability<=0.05) {
					if(C-(10*f_out_min)>0)
						C=C-(10*f_out_min);
					else
						C=0; //acqua esce tutta
					livelliC.add(C);
				} else {
					//EROGAZIONE MINIMA non garantita
					//il bacino si svuota comunque
					if(C<f_out_min) {
						C=0;
						gg++;
						livelliC.add(C);
					}else {
						//EROGAZIONE MINIMA garantita
						C=C-f_out_min; //uscita
						livelliC.add(C);
					}
					
				}
			}
		}//while	
	}//run

	
	/**
	 * Restituisce FLUSSO del fiume
	 * in quella data
	 * @param ld
	 * @return
	 */
	public double getFlowAtThatTime (LocalDate ld) {
		double f_med=0;
		for(Flow fi: r.getFlows())
			if(fi.getDay().equals(ld))
				f_med=fi.getFlow()*86400; //il flusso è in m3 al sec, f_med in m3 all'h
		return f_med;
		
	}
	
	
	public int getGGNoErogazione() {
		return gg;
	}
	
	public double getC_med() {
		double C_tot = 0.0;
		for(double Ci: livelliC)
			C_tot+=Ci;
		return C_tot/livelliC.size();
	}
	
				
		
	

}
