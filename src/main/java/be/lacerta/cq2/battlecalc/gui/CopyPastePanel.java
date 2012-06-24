/*
 * Copyright (c) 2005 Coopmans Lennart
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package be.lacerta.cq2.battlecalc.gui;

import java.awt.Color;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import be.lacerta.cq2.battlecalc.objects.Creature;
import be.lacerta.cq2.battlecalc.objects.Item;
import be.lacerta.cq2.battlecalc.objects.Itherian;

public class CopyPastePanel extends MPanel {

    private JTextArea crit1 = new JTextArea();
    private JTextArea crit2 = new JTextArea();

    private SpringLayout inputLayout = new SpringLayout();

    private Hashtable<String, Creature> critMap;
    
    Creature attackingCrit;
    Creature defendingCrit;
    
    Itherian attackingIth;
    Itherian defendingIth;
//                                    ....name.......race/class...,.......status.....................level.......damage/health........FD.......DD.......AD.......ED....item
//    private String compactRE =     "\\W*(.*)\\W+?(\\w*)/(\\w*).*,\\W+?(\\w*|kingdom defense)\\W+?L(\\d+?)\\W+?(\\d+?)/(\\d+?)\\W+?F(\\d+?)/D(\\d+?)/A(\\d+?)/E(\\d*)(\\D*)";

    /**
     * [^a-zA-Z]*([^\d\.\t]+)\W+([^\t]*)/(\w+),\W+(\w*|kingdom defense|kingdom offense)\s+L(\d+)\s+(\d+)/(\d+)\W+F(\d+)/D(\d+)/A(\d+)/E(\d+)(\D*)
     *                 1           2       3                      4                          5       6     7        8      9      10     11   12
     * Name: \1 || Race: \2 || class: \3 || status: \4 || level: \5 || dmg: \6 || health: \7 || def: \8|\9|\10|\11 || item: \12
     */
//    private String compactRE = "[^a-zA-Z]*([^\\d\\.\\t]+?)\\W+?([^\\t]*)/(\\w+?),\\W+?(\\w*|kingdom defense|kingdom offense)\\s+?L(\\d+?)\\s+?(\\d+?)/(\\d+?)\\W+?F(\\d+?)/D(\\d+?)/A(\\d+?)/E(\\d*)(\\D*)";


    /** Creates new OK/Cancel Dialog
     *
     * @param mg mainGui: the parent frame.
     */
    public CopyPastePanel(URL imageURL, Color bgcolor, Hashtable<String, Creature>  critMap) {
    	this(new URL[]{ imageURL },bgcolor,critMap);
    }

    public CopyPastePanel(URL[] imageURL, Color bgcolor, Hashtable<String, Creature>  critMap) {
        super(imageURL, bgcolor);
        this.critMap = critMap;
        initGUI();
    }
    
    public void setAttackerText(String text) {
    	crit1.setText(text);
    }

    public void setDefenderText(String text) {
    	crit2.setText(text);
    }

    /** This method is called from within the constructor to initialize the dialog. */
    private void initGUI() {
        //setLayout(contentPaneLayout);
 

        setLayout(inputLayout);
        add(new JLabel("Attacking creature: "));
        add(new JScrollPane(crit1));
        crit1.setLineWrap(true);
        add(new JLabel("Defending creature: "));
        add(new JScrollPane(crit2));
        crit2.setLineWrap(true);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        SpringUtilities.makeCompactGrid(this, 4, 1, 5, 5, 5, 5);

        //disclaimer = new JTextArea();
        //disclaimer.setText(" COMPACT MODE only! Experimental, so report bugs please!");
        //disclaimer.setEditable(false); disclaimer.setBackground(getBackground());
        //add(disclaimer, BorderLayout.NORTH);
        //add(inputPanel, BorderLayout.CENTER);
        //add(btnPanel, BorderLayout.SOUTH);
    }

    /** parse the input fields, make creatures and items out of it, fill in and return to the parent frame */
    public void parseInputs() {
      String input1 = crit1.getText().trim().replaceAll("  ","\t");
      String input2 = crit2.getText().trim().replaceAll("  ","\t");
      
      attackingCrit = new Creature();
      defendingCrit = new Creature();
      
      attackingIth = new Itherian();
      defendingIth = new Itherian();

      // 1. check for race and class
      findRaceAndClass(input1,attackingCrit);

      findRaceAndClass(input2,defendingCrit);
      
      // 2. get damage/health
      findDamageAndHealth(input1,attackingCrit);

      findDamageAndHealth(input2,defendingCrit);

      // 3. get def
      findDef(input1,attackingCrit);

      findDef(input2,defendingCrit);
      
      // 4. get item
      findItem(input1,attackingCrit);
      findItem(input2,defendingCrit);
      
      // 5. get enchant
      findEnchant(input1,attackingCrit,defendingCrit,attackingIth);
      findEnchant(input2,defendingCrit,attackingCrit,defendingIth);

//      if (false) {
//      System.out.println("race: "+attackingCrit.getRace());
//      System.out.println("class: "+attackingCrit.getCreatureClass());
//      System.out.println("name: "+attackingCrit.getName());
//      System.out.println("damage: "+attackingCrit.getDamage());
//      System.out.println("health: "+attackingCrit.getHealth());
//      System.out.println("AD: "+attackingCrit.getAirDef());
//      System.out.println("DD: "+attackingCrit.getDeathDef());
//      System.out.println("FD: "+attackingCrit.getForestDef());
//      System.out.println("ED: "+attackingCrit.getEarthDef()); 
//      System.out.println("item: "+attackingCrit.getItem().getName());      
//      System.out.println("MB? "+attackingCrit.getItem().isMaBe());
//      System.out.println("ith dmg: "+attackingIth.getDamage());
//      System.out.println("ith hlt: "+attackingIth.getHealth());
//      System.out.println("ith def: "+attackingIth.getDefence());
//      System.out.println("=====================================");
//      System.out.println("race: "+defendingCrit.getRace());
//      System.out.println("class: "+defendingCrit.getCreatureClass());
//      System.out.println("name: "+defendingCrit.getName());
//      System.out.println("damage: "+defendingCrit.getDamage());
//      System.out.println("health: "+defendingCrit.getHealth());
//      System.out.println("AD: "+defendingCrit.getAirDef());
//      System.out.println("DD: "+defendingCrit.getDeathDef());
//      System.out.println("FD: "+defendingCrit.getForestDef());
//      System.out.println("ED: "+defendingCrit.getEarthDef()); 
//      System.out.println("item: "+defendingCrit.getItem().getName());      
//      System.out.println("MB? "+defendingCrit.getItem().isMaBe());
//      System.out.println("ith dmg: "+attackingIth.getDamage());
//      System.out.println("ith hlt: "+attackingIth.getHealth());
//      System.out.println("ith def: "+attackingIth.getDefence());
//      }
    }
    
    private boolean findEnchant(String input, Creature crit, Creature opp, Itherian ith) {
    	input = input.toUpperCase();
    	
    	       if (input.matches(".*\\s+\\(MABE "+opp.getRace().toUpperCase()+"\\).*")) {
        	crit.getItem().setEnchant("MaBe");
        } else if (input.matches(".*\\s+\\(IMOF "+opp.getRace().toUpperCase()+"\\).*")) {
        	crit.getItem().setEnchant("ImOf");
        } else if (input.matches(".*\\s+\\(MIBE "+opp.getRace().toUpperCase()+"\\).*")) {
        	crit.getItem().setEnchant("MiBe");
        } else if (input.matches(".*\\s+\\(MAIM "+opp.getRace().toUpperCase()+"\\).*")) {
        	if (opp.getCreatureClass().equals("air")) crit.setAirDef("100");
        	if (opp.getCreatureClass().equals("forest")) crit.setForestDef("100");
        	if (opp.getCreatureClass().equals("death")) crit.setDeathDef("100");
        	if (opp.getCreatureClass().equals("earth")) crit.setEarthDef("100");
        } else {
        	Pattern pattern = Pattern.compile(".*\\s+\\(\\+(\\d+)[%]? (DAMAGE|HEALTH|DEFENSES)\\).*");
        	Matcher match = pattern.matcher(input);
        	if (match.find()) {
        		int amount = Integer.parseInt(match.group(1));
        		String type = match.group(2);
        		
        		if (type.equals("DAMAGE")) ith.setDamage(amount);
        		if (type.equals("HEALTH")) ith.setHealth(amount);
        		if (type.equals("DEFENSES")) ith.setDefence(amount);
        	}
        }
        

    	//(MaBe Banshee)
    	return false;
    }
    
    private boolean findItem(String input, Creature crit) {
    	boolean found = false;
    	Item item = new Item("","");
    	for (String name : Item.ITEMS) {
            if (input.matches(".*\\s+"+name+".*")) {
            	item = new Item(name,"");
                found = true;
                break;
            }
    	}
    	

    	for (String name : Item.ITEMS) {
            if (input.matches(".*\\s+"+convertInCompactItem(name)+".*")) {
            	item = new Item(name,"");
                found = true;
                break;
            }
    	}
    	
    	crit.setItem(item);
    	
    	return found;
    }
    
    private String convertInCompactItem(String name) {
    	String[] tokens = name.split(" ");
    	String ret = "";
    	for (String token : tokens) {
    		ret += token.substring(0,2);
    	}
    	return ret;
    }
    
    private boolean findDef(String input, Creature crit) {
    	boolean found = false;
    	Pattern pattern = Pattern.compile(".*F(\\d+)/D(\\d+)/A(\\d+)/E(\\d+)\\s+.*");
    	Matcher match = pattern.matcher(input);
    	if (match.find()) {
    		found = true;
    		crit.setForestDef(match.group(1));
    		crit.setDeathDef(match.group(2));
    		crit.setAirDef(match.group(3));
    		crit.setEarthDef(match.group(4));
    	}
    	return found;
    }
    
    private boolean findDamageAndHealth(String input, Creature crit) {
    	boolean found = false;
    	Pattern pattern = Pattern.compile(".*L(\\d+)\\s+(\\d+)/(\\d+)\\W+.*");
    	Matcher match = pattern.matcher(input);
    	if (match.find()) {
    		found = true;
    		int level = Integer.parseInt(match.group(1));
    		crit.setDamage(match.group(2), level, level);
    		crit.setHealth(match.group(3), level, level);
    	}
    	
    	return found;
    }

    private boolean findRaceAndClass(String input, Creature crit) {
        boolean found = false;
        input = input.toUpperCase();
        
        // check by race/class
    	Iterator<String> iter = critMap.keySet().iterator();
        while(!found && iter.hasNext()) {
            String key = iter.next();
            Creature iCrit = critMap.get(key);
            if (input.matches(".*"+iCrit.getName().toUpperCase()+".*"+iCrit.getRace().toUpperCase()+"/"+iCrit.getCreatureClass().toUpperCase()+".*")) {
                crit.setName(iCrit.getName());
                crit.setCreatureClass(iCrit.getCreatureClass());
                crit.setRace(iCrit.getRace());
                found = true;
            }
        }
        
        // check by name/class
        iter = critMap.keySet().iterator();
        while(!found && iter.hasNext()) {
            Object key = iter.next();
            Creature iCrit = (Creature)critMap.get(key);
            if (input.matches(".*"+iCrit.getName().toUpperCase()+"/"+iCrit.getCreatureClass().toUpperCase()+".*")) {
                crit.setName(iCrit.getName());
                crit.setCreatureClass(iCrit.getCreatureClass());
                crit.setRace(iCrit.getRace());
                found = true;
            }
        }
    	return found;
    }
  /**
   * check if the enchant matches the race
   *
   * @param enchant String
   * @param race String
   * @return boolean
   */
  @SuppressWarnings("unused")
private boolean enchMatch(String enchant, String race) {
    if (enchant.matches(".*"+race+".*")) { System.out.println(enchant+ " matches " +race); return true;}
    else if (enchant.equals("Storm") && (race.equals("Cyclone") || race.equals("Hurricane"))) return true;
    else if (enchant.equals("Diabolic Horde") && (race.matches(".*Diabolic.*"))) return true;
    else if (enchant.equals("Doomguard") && (race.matches(".*Guard.*"))) return true;
    else if (enchant.equals("Pit Worm") && (race.equals("Aardvark's Pit Maggot"))) return true;
    else if (enchant.equals("Undead") && (race.matches(".*Zombie.*"))) return true;
    else if (enchant.equals("Smith") && ((race.matches(".*Crafter.*") || race.equals("Hammer Lord")))) return true;
    else if (enchant.equals("Cleric") && (race.matches(".*Sorcere.*"))) return true;
    else if (enchant.equals("Imp") && (race.matches(".*Carver.*"))) return true;
    return false;
  }

public Creature getAttackingCrit() {
	return attackingCrit;
}

public Creature getDefendingCrit() {
	return defendingCrit;
}

public Itherian getAttackingIth() {
	return attackingIth;
}

public Itherian getDefendingIth() {
	return defendingIth;
}

}
