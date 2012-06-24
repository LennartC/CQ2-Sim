package be.lacerta.cq2.sim;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import be.lacerta.cq2.objects.Gem;
import be.lacerta.cq2.objects.Orb;
import be.lacerta.cq2.orbparser.BeamSearchTree2;
import be.lacerta.cq2.orbparser.HeuristicEfficiency;
import be.lacerta.cq2.orbparser.HeuristicGemPower;
import be.lacerta.cq2.orbparser.HeuristicNetherEffect;
import be.lacerta.cq2.orbparser.OrbHeuristicValue;
import be.lacerta.cq2.orbparser.OrbNode;
import be.lacerta.cq2.orbparser.Tree;
import be.lacerta.cq2.sim.hbn.Amulet;
import be.lacerta.cq2.sim.hbn.Creature;
import be.lacerta.cq2.utils.CQ2Functions;
import be.lacerta.cq2.utils.CreatureFinder;

public class OrbExtension extends AbstractSimExtension {
		
	List<Orb> orbs = new ArrayList<Orb>();
	HashMap<String,List<Gem>> gems = new HashMap<String,List<Gem>>();
	Integer baselevel;
	
	public void run(String page) {
		request.setAttribute("orb_fullOnly", getInt("fullOnly"));
		request.setAttribute("orb_highestpower", getString("orderBy").equals("highestpower"));
		request.setAttribute("orb_highesteffect", getString("orderBy").equals("highesteffect"));
		request.setAttribute("orb_highestefficiency", getString("orderBy").equals("highestefficiency"));
		request.setAttribute("orb_effectpergem", getInt("effectpergem"));
		request.setAttribute("orb_minEfficiency", getInt("minEfficiency"));
		request.setAttribute("orb_minSigns", getInt("minSigns"));
		
		if (!getString("creature").equals("")) {
			request.setAttribute("orb_creature", getString("creature"));
			Amulet amu = CreatureFinder.findAmulet(getString("creature"));
			if (amu instanceof Creature) {
				baselevel = CQ2Functions.calcBaseLevel(((Creature)amu).getSkill());
			}
		} else {
			baselevel = 59;
			request.setAttribute("orb_creature", "Abyss Banshee");
		}

		int minEfficiency = getInt("minEfficiency");
		int minSigns = getInt("minSigns");
		
		request.setAttribute("orb_baselevel", baselevel);
		
		if (post) {
			user.refresh();
			user.setGems(getString("gems"));
			user.setOrbs(getString("orbs"));
			user.update();

			List<Gem> g = parseGems(user.getGems());
			List<Orb> o = parseOrbs(user.getOrbs());
			
			orbs = new ArrayList<Orb>();
			gems = new HashMap<String,List<Gem>>();
			int uses = getInt("uses");
			
			for (Gem gem : g) addGem(gem);
			
	    	for (Orb orb : o) {
	    		if (orb.getGemsigns().size()>=minSigns) {
		    		for (int i=0; i<orb.getUses() && i<uses; i++) {
			    		boolean full=true;
			    		for (String sign : orb.getGemsigns()) {
			    			if (gems.get(sign) == null) {
			    				full=false;
			    				break;
			    			}
			    		}
			    		if ( (full || getInt("fullOnly")==0) &&
			    			 (minEfficiency <= 0 || CQ2Functions.calcNetherEfficiency(baselevel, orb) > minEfficiency)
			    			) {
			    			addOrb(new Orb(orb.getColour(),orb.getUses()-i,orb.getGemsigns()));
			    		}
		    		}
	    		}
	    	}
			
			if (getString("orderBy").equals("highestpower")) {
				parseOrbs(uses,new HeuristicGemPower());
			} else if (getString("orderBy").equals("highesteffect")) {
				parseOrbs(uses,new HeuristicNetherEffect(baselevel));
			} else if (getString("orderBy").equals("highestefficiency")) {
				parseOrbs(uses,new HeuristicEfficiency(baselevel));
			} else {
				parseOrbsSimple();
			}
			
		}
		setPath("/orb.jsp");
	}
	
	private List<Gem> parseGems(String s) {
		List<Gem> gems = new ArrayList<Gem>();
		for (String line : s.split("\n")) {
			try {
			gems.add(new Gem(line));
			} catch (InvalidParameterException ipe) {
				// ipe.printStackTrace();
			}
		}
		return gems;
	}
	
	private List<Orb> parseOrbs(String s) {
		List<Orb> orbs = new ArrayList<Orb>();
		for (String line : s.split("\n")) {
			try {
				line = line.trim();
				Orb orb = new Orb(line);
				orbs.add(orb);
				} catch (InvalidParameterException ipe) {
					// ipe.printStackTrace();
				}
		}
		return orbs;
	}
	
	private void parseOrbsSimple() {
		List<Orb> orblist = new ArrayList<Orb>();

		for (Orb o : orbs) {

			Orb orb = new Orb(o.getColour(),o.getUses(),o.getGemsigns());

			for (String sign : orb.getGemsigns()) {

				List<Gem> l = gems.get(sign);
				if (l != null && l.size() > 0) {
					List<Gem> nl = new Vector<Gem>();
					for (Gem gem : l) {
						nl.add(gem);
					}
					try {
						orb.setGem(nl.get(0));
						nl.remove(0);
						gems.put(sign, nl);
					} catch (InvalidParameterException ipe) {
						ipe.printStackTrace();
					}
				}
			}

			orblist.add(orb);
		}
		
		prepareForDisplay(orblist);

	}
	
	private void parseOrbs(int uses, final OrbHeuristicValue heuristic) { 	
//		System.out.println("base level: "+baselevel);
    	// sort gems on potential quality
		for (String sign : gems.keySet()) {
			List<Gem> gemlist = gems.get(sign);
			Collections.sort(gemlist,new Comparator<Gem>() {
				public int compare(Gem g1, Gem g2) {
					List<Gem> g1l = new ArrayList<Gem>();
					g1l.add(g1);
					List<Gem> g2l = new ArrayList<Gem>();
					g2l.add(g2);
					double do1 = heuristic.getValue(new Orb(g1l)).doubleValue();
					double do2 = heuristic.getValue(new Orb(g2l)).doubleValue();
					
					// if same effect, take lowest level
					if (do1==do2) {
						return g1.getLevel()-g2.getLevel();
					}
					return new Double((do2-do1)*1000).intValue();
				}
			});
//			System.out.print("sign: "+sign+" --> ");
//			for (Gem gem : gemlist) {
//				List<Gem> g1l = new ArrayList<Gem>();
//				g1l.add(gem);
//				System.out.print(gem.toCompactString() +"("+heuristic.getValue(new Orb(g1l)).doubleValue()+")");
//			}
//			System.out.println("");
			gems.put(sign, gemlist);
		}

    	Tree t = new BeamSearchTree2(orbs,gems,heuristic,(getInt("fullOnly") == 1));
    	
    	
    	OrbNode n = t.search();

    	List<OrbNode> nodes = new ArrayList<OrbNode>();
		while (n != null) {
			nodes.add(n);
			n = n.getParent();
		}
		Collections.sort(nodes);
		
		List<Orb> orblistTemp = new ArrayList<Orb>();
		
		// let's first use the best gem for each orb
		// sort, then only use existing gems, and sort again
		for (OrbNode node : nodes) {
			Orb orb = node.getOrb();
			for (String sign : orb.getGemsigns()) {
				List<Gem> l = gems.get(sign);
				if (l != null && l.size() > 0) {
					try {
						orb.setGem(l.get(0));
					} catch (InvalidParameterException ipe) {
						//ipe.printStackTrace();
					}
				} else {
					orb.removeGem(sign);
				}
			}
			orblistTemp.add(orb);
		}

		Collections.sort(orblistTemp,new Comparator<Orb>() {
			public int compare(Orb o1, Orb o2) {
				double do1 = heuristic.getValue(o1).doubleValue();
				double do2 = heuristic.getValue(o2).doubleValue();
				return new Double((do2-do1)*1000).intValue();
			}
		});
		
		List<Orb> orblistFinal = new ArrayList<Orb>();
		for (Orb orb : orblistTemp) {
			for (String sign : orb.getGemsigns()) {
				List<Gem> l = gems.get(sign);
				if (l != null && l.size() > 0) {
					List<Gem> nl = new ArrayList<Gem>();
					for (Gem gem : l) nl.add(gem);

					try {
						orb.setGem(nl.get(0));
					} catch (InvalidParameterException ipe) {
						//ipe.printStackTrace();
					}
					nl.remove(0);
					if (nl.size()==0) {
						gems.remove(sign);
					} else {
						gems.put(sign, nl);
					}
					
				} else {
					orb.removeGem(sign);
				}
			}
			orblistFinal.add(orb);
		}
		
		Collections.sort(orblistTemp,new Comparator<Orb>() {
			public int compare(Orb o1, Orb o2) {
				double do1 = heuristic.getValue(o1).doubleValue();
				double do2 = heuristic.getValue(o2).doubleValue();
				return new Double((do2-do1)*1000).intValue();
			}
		});
		
		prepareForDisplay(orblistFinal);
    	
	}
	
	private void prepareForDisplay(List<Orb> parsedOrbs) {
		List<Gem> neededRegular = new ArrayList<Gem>();
		List<Gem> neededCommon = new ArrayList<Gem>();
		List<Gem> neededUncommon = new ArrayList<Gem>();
		List<Gem> neededScarce = new ArrayList<Gem>();
		List<Gem> neededRare = new ArrayList<Gem>();
		List<Gem> neededExceptional = new ArrayList<Gem>();
		List<Gem> neededSingular = new ArrayList<Gem>();
		List<Gem> neededTravel = new ArrayList<Gem>();
		List<Gem> remainingBs = new ArrayList<Gem>();
		
		// put everything in remaining to start
    	for(String sign : gems.keySet()) {
    		if (sign.length() == 3) {
    			List<Gem> t = gems.get(sign);
    			if (t != null) remainingBs.addAll(t);
    		}
    	}
		
		for (Orb o : parsedOrbs) {
			for (String sign : o.getGemsigns()) {
				Gem g = o.getGem(sign);
				if (g==null) {
					Gem ng = new Gem(sign,1,"ugly");
					if (ng.isTravel()) {
						neededTravel.add(ng);
					} else if (ng.isRegular()) {
						neededRegular.add(ng);
					} else if (ng.isCommon()) {
						neededCommon.add(ng);
					} else if (ng.isUncommon()) {
						neededUncommon.add(ng);
					} else if (ng.isScarce()) {
						neededScarce.add(ng);
					} else if (ng.isRare()) {
						neededRare.add(ng);
					} else if (ng.isExceptional()) {
						neededExceptional.add(ng);
					} else if (ng.isSingular()) {
						neededSingular.add(ng);
					}
				} else {
					remainingBs.remove(g);
				}
			}
		}
		
    	Collections.sort(neededRegular,Collections.reverseOrder());
    	Collections.sort(neededCommon,Collections.reverseOrder());
    	Collections.sort(neededUncommon,Collections.reverseOrder());
    	Collections.sort(neededScarce,Collections.reverseOrder());
    	Collections.sort(neededRare,Collections.reverseOrder());
    	Collections.sort(neededExceptional,Collections.reverseOrder());
    	Collections.sort(neededSingular,Collections.reverseOrder());
    	Collections.sort(neededTravel,Collections.reverseOrder());
    	Collections.sort(remainingBs);
		
		request.setAttribute("orb_orblist", parsedOrbs);
		
		request.setAttribute("orb_needed", "1");
		request.setAttribute("orb_neededRegular", neededRegular);
		request.setAttribute("orb_neededCommon", neededCommon);
		request.setAttribute("orb_neededUncommon", neededUncommon);
		request.setAttribute("orb_neededScarce", neededScarce);
		request.setAttribute("orb_neededRare", neededRare);
		request.setAttribute("orb_neededExceptional", neededExceptional);
		request.setAttribute("orb_neededSingular", neededSingular);
		request.setAttribute("orb_neededTravel", neededTravel);
		
		request.setAttribute("orb_remaining", remainingBs);
	}
	
	private void addGem(Gem gem) {
		List<Gem> l;
		if (gems.get(gem.getName()) == null) {
			l = new ArrayList<Gem>();
		} else {
			l = gems.get(gem.getName());
		}
		l.add(gem);
		Collections.sort(l);
		gems.put(gem.getName(), l);
	}
	
	private void addOrb(Orb orb) {
		orbs.add(orb);
	}
}
