package be.lacerta.cq2.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.lacerta.cq2.objects.Creature;
import be.lacerta.cq2.objects.Enchant;
import be.lacerta.cq2.objects.Itherian;
import be.lacerta.cq2.sim.hbn.Amulet;


public class CreatureFinder {

	private List<Creature> critList;
	
	public CreatureFinder() {
		createCreatureList();
	}
	
	public Creature[] parseInput(String input1, String input2) {
		try {
		
			String[] input1perline = input1.split("\r\n|\r|\n");
			String[] input2perline = input2.split("\r\n|\r|\n");

			createCreatureList();
	
			Creature[] crits = new Creature[2];
			List<Creature> critList = PageParser.parseCreatureList(input1);
			if (critList.size()>0) crits[0] = critList.get(0);
			
			critList = PageParser.parseCreatureList(input2);
			if (critList.size()>0) crits[1] = critList.get(0);

			// 5. get enchant
			findEnchant(input1perline[0],crits[0],crits[1]);
			findEnchant(input2perline[0],crits[1],crits[0]);
			
			return crits;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Creature[][] parseWave(String input) {
		try {
			Creature[] attackers = new Creature[15];
			Creature[] defenders = new Creature[15];
			
			String[] inputperline = input.split("\r\n|\r|\n");

			createCreatureList();
			int i = -1;
			boolean attacker = true;

			for (String line : inputperline) {
				if (line.matches(".*kingdom offense.*")) {
					i++;
					attacker = true;
					List<Creature> critList = PageParser.parseCreatureList(line);
					if (critList.size()>0) attackers[i] = critList.get(0);
					
					findEnchant(line,attackers[i]);
				} else if (line.matches(".*kingdom defense.*")) {
					attacker = false;
					List<Creature> critList = PageParser.parseCreatureList(line);
					if (critList.size()>0) defenders[i] = critList.get(0);
					findEnchant(line,defenders[i]);
				} else if (line.matches("Cursed.*")) {
					if (attacker) findCurse(line,attackers[i]);
					else findCurse(line,defenders[i]);
				}
			}

			Creature[][] crits = {attackers, defenders};
			return crits;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Enchant[] parseWaveEnchant(String input) {
		Enchant[] enchants = new Enchant[2];
		String[] inputperline = input.split("\r\n|\r|\n");
		int begin=0;
		for (String line : inputperline) {
			if (line.matches(".* have cast a level .*")) {
				begin = line.indexOf("level ")+6;
				int level = Integer.parseInt(line.substring(begin,line.indexOf(' ',  begin+1)));
				int type = getWaveEnchantType(line);
				begin = line.indexOf("wave enchant on the ")+20;
				int end = line.indexOf(" creatures",begin);
				String creature = line.substring(begin,end);
				if (line.matches(".*They have.*"))
					enchants[0] = new Enchant(type,level,creature);
				else {
					enchants[1] = new Enchant(type,level,creature);
					break;
				}
			}
		}
		return enchants;
	}
	
	private int getWaveEnchantType(String line) {
		if (line.toLowerCase().matches(".* sapphire .*")) {
			return Enchant.SAPPHIRE;
		} else if (line.toLowerCase().matches(".* ruby .*")) {
			return Enchant.RUBY;
		} else if (line.toLowerCase().matches(".* opal .*")) {
			return Enchant.OPAL;
		} else if (line.toLowerCase().matches(".* carnelian .*")) {
			return Enchant.CARNELIAN;
		}
		return 0;
	}
	
	public void findCurse(String input, Creature crit) {
		if (input.matches(".*"+Creature.METAMORPHOSIS+".*")) {
			crit.setMetad(true);
		} else if (input.matches(".*"+Creature.DOPPELGANGER+".*")) {
			crit.setDoppeled(true);
		} else if (input.matches(".*"+Creature.SUFFOCATION+".*")) {
			crit.setSuffocated(true);
		} else if (input.matches(".*"+Creature.JINX+".*")) {
			crit.setJinxed(true);
		}
	}

    private boolean findEnchant(String input, Creature crit, Creature opp) {
    	input = input.toUpperCase();
    	
    	       if (input.matches(".*\\s+\\(MABE "+opp.getRace().toUpperCase()+"\\).*")) {
        	crit.getItem().setEnchant("MaBe");
        } else if (input.matches(".*\\s+\\(IMOF "+opp.getRace().toUpperCase()+"\\).*")) {
        	crit.getItem().setEnchant("ImOf");
        } else if (input.matches(".*\\s+\\(MIBE "+opp.getRace().toUpperCase()+"\\).*")) {
        	crit.getItem().setEnchant("MiBe");
        } else if (input.matches(".*\\s+\\(MAIM "+opp.getRace().toUpperCase()+"\\).*")) {
        	if (opp.getCreatureClass().equals("air")) crit.setAirDef(100);
        	if (opp.getCreatureClass().equals("forest")) crit.setForestDef(100);
        	if (opp.getCreatureClass().equals("death")) crit.setDeathDef(100);
        	if (opp.getCreatureClass().equals("earth")) crit.setEarthDef(100);
        } else {
        	Pattern ithre = Pattern.compile(".*\\s+\\(\\+(\\d+)[%]? (DAMAGE|HEALTH|DEFENSES)\\).*");
        	Matcher m = ithre.matcher(input);
        	if (m.find() && crit instanceof Itherian) {
        		int amount = Integer.parseInt(m.group(1));
        		String type = m.group(2);
        		
        		if (type.equals("DAMAGE")) 	((Itherian)crit).setIthDamage(amount);
        		if (type.equals("HEALTH")) 	((Itherian)crit).setIthHealth(amount);
        		if (type.equals("DEFENSES"))((Itherian)crit).setIthDefence(amount);
        	}
        }
        

    	//(MaBe Banshee)
    	return false;
    }
    
    private boolean findEnchant(String input, Creature crit) {
    	String inputuc = input.toUpperCase();
    	
    	if (inputuc.matches(".*\\s+\\(MABE .*\\).*")) {
        	crit.getItem().setEnchant("MaBe",input.substring(input.lastIndexOf('(')+6,input.lastIndexOf(')')));
        } else if (inputuc.matches(".*\\s+\\(IMOF .*\\).*")) {
        	crit.getItem().setEnchant("ImOf",input.substring(input.lastIndexOf('(')+6,input.lastIndexOf(')')));
        } else if (inputuc.matches(".*\\s+\\(MIBE .*\\).*")) {
        	crit.getItem().setEnchant("MiBe",input.substring(input.lastIndexOf('(')+6,input.lastIndexOf(')')));
        } else if (inputuc.matches(".*\\s+\\(MAIM .*\\).*")) {
        	crit.getItem().setEnchant("MaIm",input.substring(input.lastIndexOf('(')+6,input.lastIndexOf(')')));
        } else {
        	Pattern ithre = Pattern.compile(".*\\s+\\(\\+(\\d+)[%]? (DAMAGE|HEALTH|DEFENSES)\\).*");
        	Matcher m = ithre.matcher(input);
        	if (m.find() && crit instanceof Itherian) {
        		int amount = Integer.parseInt(m.group(1));
        		String type = m.group(2);
        		
        		if (type.equals("DAMAGE")) 	((Itherian)crit).setIthDamage(amount);
        		if (type.equals("HEALTH")) 	((Itherian)crit).setIthHealth(amount);
        		if (type.equals("DEFENSES"))((Itherian)crit).setIthDefence(amount);
        	}
        }
        
    	return true;
    }

    public Creature findRaceAndClass(String input) {
    	Creature crit = null;     
        input = input.trim().toUpperCase();
        
        boolean found = false;
        //check if ith..
        if (input.matches("(I),")) {
        	crit = new Itherian();
        } else {
        	crit = new Creature();
        }
        
        //System.out.println(input);
        // check by race/class
        for (Creature iCrit : critList) {
        	//System.out.println(".*"+iCrit.getName().toUpperCase()+".*"+iCrit.getRace().toUpperCase()+"/"+iCrit.getCreatureClass().toUpperCase()+".*");
            if (input.matches(".*"+iCrit.getName().toUpperCase()+".*"+iCrit.getRace().toUpperCase()+"/"+iCrit.getCreatureClass().toUpperCase()+".*")) {
                //System.out.println("found");
            	crit.setName(iCrit.getName());
                crit.setCreatureClass(iCrit.getCreatureClass());
                crit.setRace(iCrit.getRace());
                crit.setDamage(Creature.convertStrength(new Double(""+iCrit.getDamage()).intValue(), 1, 1));
                crit.setHealth(Creature.convertStrength(new Double(""+iCrit.getHealth()).intValue(), 1, 1));
                found=true;
                break;
            }
        }
        
        if (!found) for (Creature iCrit : critList) {
        	//System.out.println(".*"+iCrit.getName().toUpperCase()+"/"+iCrit.getCreatureClass().toUpperCase()+".*");
            if (input.matches(".*"+iCrit.getName().toUpperCase()+"/"+iCrit.getCreatureClass().toUpperCase()+".*")) {
            	//System.out.println("found");
            	crit.setName(iCrit.getName());
                crit.setCreatureClass(iCrit.getCreatureClass());
                crit.setRace(iCrit.getRace());
                crit.setDamage(Creature.convertStrength(new Double(""+iCrit.getDamage()).intValue(), 1, 1));
                crit.setHealth(Creature.convertStrength(new Double(""+iCrit.getHealth()).intValue(), 1, 1));
                found=true;
                break;
            }
        }  
        // check by name/class
        if (!found) return null;
    	return crit;
    }

    private void createCreatureList() {
    	critList = SessionListener.getCreatureList();
		Collections.sort(critList,new CritLengthComparator());
    }
    
    public Creature[] selectInput (String attackerFieldName, String attackerFieldLevel, String defenderFieldName, String defenderFieldLevel) {
    	Creature[] crits = new Creature[2];
		crits[0] = selectCreature(attackerFieldName, attackerFieldLevel);
		crits[1] = selectCreature(defenderFieldName, defenderFieldLevel);
    	return crits;
    }
    
    public Creature selectCreature(String fieldName, String fieldLevel) {
		Creature crit = new Creature();
		
    	if (fieldName != null && !fieldName.equals("")) {
    		Amulet amu = CreatureFinder.findAmulet(fieldName);
    		int level = 20;
    		try {
    			level = Integer.parseInt(fieldLevel);
    		} catch (NumberFormatException nfe) { }
    		if (amu instanceof be.lacerta.cq2.sim.hbn.Creature) {
    			crit = convertDatabaseCreature((be.lacerta.cq2.sim.hbn.Creature)amu,level);
    		}
    	}
    	return crit;
    }
    
    public static Creature convertDatabaseCreature(be.lacerta.cq2.sim.hbn.Creature creature, int desiredLevel) {
    	Creature crit = new Creature();
    	crit.setName(creature.getName());
    	crit.setCreatureClass(creature.getRace().getCreatureClass());
		crit.setDamage(Creature.convertStrength(creature.getDamage(), 1, desiredLevel));
		crit.setHealth(Creature.convertStrength(creature.getHealth(), 1, desiredLevel));
		crit.setLevel(desiredLevel);
		crit.setAirDef(creature.getRace().getAD());
		crit.setDeathDef(creature.getRace().getDD());
		crit.setEarthDef(creature.getRace().getED());
		crit.setForestDef(creature.getRace().getFD());
		crit.setRace(creature.getRace().getName());
		crit.setSkill(creature.getSkill());
		return crit;
    }
    
	public static Amulet findAmulet(String name) {
		name = name.trim();
		Amulet amu = null;
		amu = be.lacerta.cq2.sim.hbn.Creature.getCreature(name);
		if (amu==null) amu = be.lacerta.cq2.sim.hbn.Item.getItem(name);
		if (amu==null) amu = be.lacerta.cq2.sim.hbn.Creature.getCreature(name+"%");
		if (amu==null) amu = be.lacerta.cq2.sim.hbn.Item.getItem(name+"%");

		int dist = Integer.MAX_VALUE;
		if (amu==null) {
			List<be.lacerta.cq2.sim.hbn.Creature> creatures = be.lacerta.cq2.sim.hbn.Creature.getCreatures();
			for (Iterator<be.lacerta.cq2.sim.hbn.Creature> i=creatures.iterator(); i.hasNext();) {
				be.lacerta.cq2.sim.hbn.Creature c = i.next();
				int l = StringUtils.getLevenshtein(name, c.getName());
				if (l < 10 && l < dist) {
					dist = l;
					amu = c;
				}
			}
			
			if (amu==null || dist>3) {
				List<be.lacerta.cq2.sim.hbn.Item> items = be.lacerta.cq2.sim.hbn.Item.getItems();
				for (Iterator<be.lacerta.cq2.sim.hbn.Item> i=items.iterator(); i.hasNext();) {
					be.lacerta.cq2.sim.hbn.Item c = i.next();
					int l = StringUtils.getLevenshtein(name, c.getName());
					if (l < 10 && l < dist) {
						dist = l;
						amu = c;
					}
				}
			}
		}
		
		return amu;
	}

}
