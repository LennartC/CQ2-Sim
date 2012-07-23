package be.lacerta.cq2.sim;

import java.util.HashMap;
import java.util.List;

import be.lacerta.cq2.sim.hbn.Creature;
import be.lacerta.cq2.sim.hbn.Itherian;
import be.lacerta.cq2.sim.hbn.Sacrifices;
import be.lacerta.cq2.sim.hbn.User;

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
		if("addSacs".equals(getString("action"))) //&& post)
		{
			try{
				 for (int i=0; getString("ammy"+i) != ""; i++) 
				 { 
					 Sacrifices s = new Sacrifices();
					 s.setCrit(Creature.getCreature(getString("ammy"+i)));
					 s.setItemID(Itherian.getItherian(getInt("ownItem")));
					 s.save();
				 }
		
			}catch (Exception e){ e.printStackTrace();}
				request.setAttribute("itherian_creatures", Creature.getItherians());
				request.setAttribute("add_itherian_item", ITH_ITEMS);
				request.setAttribute("itherian_gems", getString("gems"));
				request.setAttribute("itherian_items_user", Itherian.getItherians(user));
			 
		}else if ("deleteSacs".equals(getString("action")))
		{
			Sacrifices.getSacrifice(getInt("getSac"));
			request.setAttribute("itherian_creatures", Creature.getItherians());
			request.setAttribute("add_itherian_item", ITH_ITEMS);
			request.setAttribute("itherian_gems", getString("gems"));
			request.setAttribute("itherian_items_user", Itherian.getItherians(user));
			
		}
		else if ("addIth".equals(getString("action")))
		{
			Itherian i = new Itherian();
			i.setCq2ID(getInt("cq2ID"));
			i.setUser(user);
			i.setItemname(getString("item"));
			i.save();
	
			request.setAttribute("itherian_creatures", Creature.getItherians());
			request.setAttribute("add_itherian_item", ITH_ITEMS);
			request.setAttribute("itherian_gems", getString("gems"));
			request.setAttribute("itherian_items_user", Itherian.getItherians(user));
		}
		else if ("deleteIth".equals(getString("action")))
		{
			Itherian.getItherian(getInt("ownItem2")).delete();
			request.setAttribute("itherian_creatures", Creature.getItherians());
			request.setAttribute("add_itherian_item", ITH_ITEMS);
			request.setAttribute("itherian_gems", getString("gems"));
			request.setAttribute("itherian_items_user", Itherian.getItherians(user));
		}
		else
		{
			request.setAttribute("itherian_creatures", Creature.getItherians());
			request.setAttribute("add_itherian_item", ITH_ITEMS);
			request.setAttribute("itherian_gems", getString("gems"));
			
			
			
			request.setAttribute("itherian_items_user", Itherian.getItherians(user));
			
		}
		
		
		setPath("/itherian.jsp");
	}

}
