package be.lacerta.cq2.sim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.lacerta.cq2.sim.hbn.Configuration;
import be.lacerta.cq2.sim.hbn.Kingdom;
import be.lacerta.cq2.sim.hbn.Mage;
import be.lacerta.cq2.sim.hbn.Reveal;
import be.lacerta.cq2.sim.service.NewsService;
import be.lacerta.cq2.sim.service.RevealService;
import be.lacerta.cq2.utils.Condition;
import be.lacerta.cq2.utils.Pair;
import be.lacerta.cq2.utils.SimConstants;

public class RevealDBExtension extends AbstractSimExtension {

	public void run(String page) {
		request.setAttribute("reveal_action", getString("action"));
		request.setAttribute("reveal_unparsed", getString("reveal"));
		request.setAttribute("reveal_mageClass", getString("mageClass"));
		request.setAttribute("reveal_mage", getString("mage"));
		request.setAttribute("reveal_kingdom", getString("kingdom"));
		request.setAttribute("reveal_level", getString("level"));
		request.setAttribute("reveal_general", getString("general"));
		
		if (post) {
			if (getString("action").equals("add")) {
				try {
					Reveal r = RevealService.INSTANCE.addReveal(user, getString("general"), getString("reveal"));
				
					if (r!=null) {
						NewsService.INSTANCE.addNews(
								"?page=reveal&mage="+r.getName(),
								r.getName()+" (L"+r.getLevel()+" "+r.getMageClass()+" mage)",
								"Reveal",
								user
							);
						
						if (new Boolean(Configuration.getValue(Configuration.PROPAGETE))) {
							RevealService.INSTANCE.propagate(r);
						}
					}
				
					publishReavel(r);
					
					request.setAttribute("reveal_message", "Reveal added");
					request.setAttribute("reveal_action",null);
					
				} catch (SimException se) {
					request.setAttribute("reveal_message", "Unable to add reveal: "+se.getMessage());
				}

			} else if (getString("action").equals("addnote")) {
				Reveal r = RevealService.INSTANCE.addNote(user, getInt("id"), getString("note"));
				publishReavel(r);
			} else if (getString("action").equals("search")) {
				
				
				Map<Pair<String,Condition>,Object> conditions = new HashMap<Pair<String,Condition>,Object>();
				
				for (int i=0; i<6 && request.getParameter("condField"+i) != null; i++) {
					Object value = null;
					
					// lame way of finding out what type to use :/
					if (getString("condField"+i).matches("level") || getString("condField"+i).matches(".*Skill")) {
						value = getInt("condValue"+i);
					} else {
						System.out.println(getString("condField"+i)+" didn't match");
						value = getString("condValue"+i);
					}

					conditions.put(
							new Pair<String,Condition>(
									getString("condField"+i),
									Condition.fromString(getString("condition"+i))
							),
							value
					);
				}
				
				List<Reveal> reveals = RevealService.INSTANCE.find(conditions);
				request.setAttribute("reveal_latest", reveals);
				
			} else {
				publishReavel(null);
			}
		} else if (getString("action").equals("reparse") && user.hasAccess(SimConstants.RIGHTS_SITEADMIN) && getInt("id")>0) {
			Reveal r = RevealService.INSTANCE.reparse(user, getInt("id"));
			publishReavel(r);
		} else if (getString("action").equals("viewold")) {
			request.setAttribute("reveal_allformage",  RevealService.INSTANCE.findByMage(getString("mage")));
		} else if (getString("action").equals("propagate") && user.hasAccess(SimConstants.RIGHTS_SUPERADMIN)) {
			RevealService.INSTANCE.propagate(getInt("id"));
		} else if (getString("mage").length()>0 || getInt("id")>0) {
			if (getString("action").equals("add")) {
				Reveal r = Reveal.getRevealByMage(getString("mage"));
				request.setAttribute("reveal_mage", getString("mage"));
				if (r!=null) {
					request.setAttribute("reveal_unparsed", r.getUnparsed());
					request.setAttribute("reveal_mageClass", r.getMageClass());
					request.setAttribute("reveal_kingdom", r.getKingdom());
					request.setAttribute("reveal_level", r.getLevel());	
					request.setAttribute("reveal_forestSkill", r.getForestSkill());	
					request.setAttribute("reveal_deathSkill", r.getDeathSkill());	
					request.setAttribute("reveal_airSkill", r.getAirSkill());	
					request.setAttribute("reveal_earthSkill", r.getEarthSkill());	
				}
			} else {
				publishReavel(null);
			}
		} else if (getString("action").equals("new")) {
			request.setAttribute("reveal_action","add");
		} else {
			request.setAttribute("reveal_latest", Reveal.getLatest(14));
		}
		setPath("/reveal.jsp");
	}
	
	private void publishReavel(Reveal r) {
		if (r==null) {
			if (getInt("id") > 0) {
				r = (Reveal)Reveal.get(Reveal.class, getInt("id"));
			} else {
				r = Reveal.getRevealByMage(getString("mage"));
			}
		}
			
		if (r!=null) {
			request.setAttribute("reveal_reveal", r);
		} else {
			request.setAttribute("reveal_action","add");
			request.setAttribute("reveal_message", "no reveal found");
		}
	}
	
	public static void updateMageByReveal(Reveal r) {
		Kingdom kd = Kingdom.loadByName(r.getKingdom());
		if (kd==null) {
			kd = new Kingdom();
			kd.setName(r.getKingdom());
			kd.saveOrUpdate();
		}
		Mage m = r.getMage();
		m.setCq2class(r.getMageClass());
		m.setLevel(r.getLevel());
		m.setKingdom(kd);
		m.saveOrUpdate();
	}
}
