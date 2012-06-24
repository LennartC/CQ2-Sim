package be.lacerta.cq2.sim;

import java.util.HashMap;

import be.lacerta.cq2.sim.hbn.Creature;

@SuppressWarnings("serial")
public class ItherianExtension extends AbstractSimExtension {

	public final static HashMap<String,Integer> ITH_ITEMS =  new HashMap<String,Integer>(){ {  
        put("Ahn Havoc Axe",8);  
        put("Enim Rift",400);
        put("Irantha Rift",500);
        put("Mungus Harbinger",45);
        put("Narklin Dream",200);
        put("Naxor Harbinger",30);
        put("Nilanu Rift",300);
        put("Quahn Havoc Axe",10);
        put("Sadilim Dream",250);
        put("Siphlin Dream",150);
        put("Urdox Harbinger",60);
        put("Urh Havoc Axe",6);
     } };  
     
	public void run(String page) {
		request.setAttribute("itherian_creatures", Creature.getItherians());
		request.setAttribute("itherian_items", ITH_ITEMS);
		request.setAttribute("itherian_gems", getString("gems"));
		
		if (post) {
			
		}
		
		setPath("/itherian.jsp");
	}

}
