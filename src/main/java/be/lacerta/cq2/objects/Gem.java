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

package be.lacerta.cq2.objects;


import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import be.lacerta.cq2.battlecalc.util.StringUtils;

/**
 * @author COOPMANSL
 *
 */
public class Gem implements Comparable<Gem> {
	public final static int TOPAZ = 15;
	public final static int QUARTZ = 20;
	public final static int OPAL = 25;
	public final static int CARNELIAN = 30;
	public final static int DIAMOND = 35;
	public final static int SAPPHIRE = 40;
	public final static int EMERALD = 45;
	public final static int RUBY = 50;
	
	public final static int UGLY = 0;
	public final static int BEAUTIFUL = 1;
	public final static int MAGNIFICENT = 2;
	public final static int EXQUISITE = 3;
	public final static double[] QUALITY_MULTIPLIER = {1, 1.2, 1.4, 1.6};
	
	public final static double MIN_REGULAR = 3875;
	public final static double MIN_COMMON = 3250;
	public final static double MIN_UNCOMMON = 2625;
	public final static double MIN_SCARCE = 2000;
	public final static double MIN_RARE = 1375;
	public final static double MIN_EXCEPTIONAL = 750;
	public final static double MIN_SINGULAR = 375;
	
	public final static int COST_TOPAZ = 1008;
	public final static int COST_QUARTZ = 1008;
	public final static int COST_OPAL = 1176;
	public final static int COST_CARNELIAN = 1344;
	public final static int COST_DIAMOND = 1680;
	public final static int COST_SAPPHIRE = 1680;
	public final static int COST_EMERALD = 2016;
	public final static int COST_RUBY = 2016;
	
	public final static double MIN_TRAVEL_RARITY = 2;
	public final static double MAX_TRAVEL_RARITY = 18;
	public final static double MIN_BIRTHSIGN_RARITY = 375;
	public final static double MAX_BIRTHSIGN_RARITY = 5000;
	public final static double RUDOLF_POWER_MULTIPLIER = 40;
	
	public final static HashMap<String,Integer> gemrarity = new HashMap<String,Integer>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		// TRAVEL
		put("efis", 18); 	// 28.0
		put("aner", 17); 	// 29.647
		put("asih", 15); 	// 33.6
		put("qulah", 13); // 38.7695
		put("nefar", 11); 	// 45.818
		put("zaneh", 9);	// 56.0
		put("faerh", 7);	// 72.0
		put("muena", 5);	// 100.8
		put("urtae", 3);	// 168
		put("qaera", 2);	// 252
		
		// REGULAR
		put("nis", 5000);
		put("inu", 4875);
		put("eno", 4750);
		put("tur", 4625);
		put("aru", 4500);
		put("ruh", 4375);
		put("ner", 4250);
		put("sah", 4125);
		put("var", 4000);
		put("chi", 3875);
		// COMMON
		put("fih", 3750);
		put("efu", 3625);
		put("fen", 3500);
		put("fau", 3375);
		put("eda", 3250);
		// UNCOMMON
		put("dun", 3125);
		put("uka", 3000);
		put("kih", 2875);
		put("vae", 2750);
		put("len", 2625);
		// SCARCE
		put("lua", 2500);
		put("jih", 2375);
		put("zey", 2250);
		put("uza", 2125);
		put("zar", 2000);
		// RARE
		put("ime", 1875);
		put("mul", 1750);
		put("mae", 1625);
		put("qer", 1500);
		put("qah", 1375);
		// EXCEPTIONAL
		put("yar", 1250);
		put("yun", 1125);
		put("nuy", 1000);
		put("uyi", 875);
		put("xil", 750);
		// SINGULAR
		put("uax", 375);
		put("nax", 375);
		put("uxi", 375);
		put("xio", 375);
		put("xis", 375);
	}};
	
	
	String name;

	private int quality = 0, enchant = 0, level = 0;

	public Gem(String quality, String enchant, String level) {
		this.quality = parseQuality(quality);
		this.enchant = parseEnchant(enchant);
		if (StringUtils.isInteger(level))
			this.level = Integer.parseInt(level);
	}

	public Gem(String name, int level, String quality) {
		this.name = name;
		this.level = level;
		this.quality = parseQuality(quality);
	}
	

	public Gem(String name, int level, int quality) {
		this.name = name;
		this.level = level;
		this.quality = quality;
	}
	
	public Gem(String s) throws InvalidParameterException {
		String[] gem = s.trim().split(" ");
		if (gem.length < 4) throw new InvalidParameterException("Unable to parse gem: "+s);
		try {
			this.setQuality(gem[0]);
			this.setLevel(Integer.parseInt(gem[2]));
			this.setName(gem[3].toLowerCase());
		} catch (NumberFormatException nfe) {
			throw new InvalidParameterException("Unable to parse gem: "+s);
		}
	}

	private int parseEnchant(String enchant) {
		if (enchant.toUpperCase().equals("RUBY"))
			return Gem.RUBY;
		else if (enchant.toUpperCase().equals("SAPHIRE"))
			return Gem.SAPPHIRE;
		else
			return 0;
	}

	private int parseQuality(String quality) {
		if (quality.toUpperCase().equals("BEAUTIFUL"))
			return Gem.BEAUTIFUL;
		else if (quality.toUpperCase().equals("MAGNIFICENT"))
			return Gem.MAGNIFICENT;
		else if (quality.toUpperCase().equals("EXQUISITE"))
			return Gem.EXQUISITE;
		else
			return Gem.UGLY;
	}

	/**
	 * getDamage
	 *
	 * @param myDmg double
	 * @return double
	 */
	public double getDamage(double myDmg) {
		if (level == 0 || enchant != RUBY)
			return myDmg;
		double gemEffect = 1.20 + (level * .30);
		if (quality == BEAUTIFUL)
			gemEffect *= 1.1;
		else if (quality == MAGNIFICENT)
			gemEffect *= 1.2;
		gemEffect = Math.floor(gemEffect * 100 + .5) / 100;
		return Math.floor((myDmg * gemEffect) * 100 + .5) / 100;
	}

	/**
	 * getDefence
	 *
	 * @param myDef double
	 * @return double
	 */
	public double getDefence(double myDef) {
		if (level == 0 || enchant != SAPPHIRE)
			return myDef;
		double gemEffect = 20 + (level * 5);
		if (quality == BEAUTIFUL)
			gemEffect = Math.floor(gemEffect * 1.1 + .5);
		else if (quality == MAGNIFICENT)
			gemEffect = Math.floor(gemEffect * 1.2 + .5);
		return Math.min(140, myDef + gemEffect);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = parseQuality(quality);
	}
	
	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getEnchant() {
		return enchant;
	}

	public void setEnchant(String enchant) {
		this.enchant = parseEnchant(enchant);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPower() {
		return Gem.calcPower(this);
	}
	
	public int getRarity() {
		if (name == null) return 1;
		return gemrarity.get(this.name.toLowerCase());
	}
	
	public static boolean isValidSign(String s) {
		return (gemrarity.containsKey(s));
	}
	
	public static String getRandomSign() {
		String sign = "";
		Random generator = new Random();
		int nr = generator.nextInt(49);
		int i=0;
		for (Iterator<String> it=gemrarity.keySet().iterator();it.hasNext();i++) {
			if (i==nr) {
				sign = ""+it.next();
				break;
			} else it.next();
		}
		return sign;
	}
	
	public static Gem getRandomGem() {
		String sign = "";
		Random generator = new Random();
		int nr = generator.nextInt(49);
		int i=0;
		for (Iterator<String> it=gemrarity.keySet().iterator();it.hasNext();i++) {
			if (i==nr) {
				sign = ""+it.next();
				break;
			} else it.next();
		}
		String quality = "";
		i=generator.nextInt(4);
		if (i==0) quality = "UGLY";
		else if (i==1) quality = "BEAUTIFUL";
		else if (i==2) quality = "MAGNIFICENT";
		else if (i==3) quality = "EXQUISITE";

		return new Gem(sign,20+generator.nextInt(40),quality);
	}
	
	public static int calcPower(Gem gem) {  
	    double power = 0;
	    
		// $raritymultiplier = gGetRarityMultiplier($row["signtype"], $row["rarity"]) * $gdGemTypeMultiplier[$row["signtype"]];
		// $row["power"] = round($row["glevel"] * gdRudolfPowerMultiplier * $gdGemQuality[$row["quality"]]["value"] * $raritymultiplier);
		
	    /*
	     * ($maxRarity[$type] / $rarity) * 1 => bs
	     * ($maxRarity[$type] / $rarity) * 0.7 => travel
	     */
	    double raritymultiplier = 0;
	    if (gem.isTravel()) {
	    	raritymultiplier = (MAX_TRAVEL_RARITY/gemrarity.get(gem.getName().toLowerCase()))*0.7;
	    } else {
	    	raritymultiplier = (MAX_BIRTHSIGN_RARITY/gemrarity.get(gem.getName().toLowerCase()))*1;
	    }
	    
	    power = gem.getLevel() * RUDOLF_POWER_MULTIPLIER * raritymultiplier * QUALITY_MULTIPLIER[gem.getQuality()];

	    return (int)Math.floor(power+.5);
	}
	
    public static void main(String[] args) {
//
//    	List l = new Vector();
//    	l.add(new Gem("Uka", 56, "Ugly"));
//    	l.add(new Gem("Zar", 56, "Ugly"));
//    	l.add(new Gem("Uxi", 56, "Ugly"));
//    	l.add(new Gem("Uxi", 55, "Ugly"));
//    	l.add(new Gem("Uxi", 54, "Ugly"));
//    	
//    	for (Iterator i = l.iterator(); i.hasNext();) {
//    		Gem g = (Gem)i.next();
//    		System.out.println("unsorted: "+g.toString());
//    	}
//    	
//    	Collections.sort(l);
//    	
//    	for (Iterator i = l.iterator(); i.hasNext();) {
//    		Gem g = (Gem)i.next();
//    		System.out.println("sorted: "+g.toString());
//    	}
    	String s = "black orb with 1 use remaining. gem signs: eno qer qulah sah tur var";
		String[] split = s.split(" ");
		int length = split.length-8;
		String[] gemsigns = new String[length];
		System.arraycopy(split,8,gemsigns,0,length);
		
		for (int i=0; i<gemsigns.length; i++) {
			System.out.println("sign: "+gemsigns[i]);
		}
     }
    
    public String toCompactString() {
    	String s = "";
    	if (quality == Gem.BEAUTIFUL) s+="Beautiful";
    	else if (quality == Gem.MAGNIFICENT) s+="Magnificent";
    	else if (quality == Gem.EXQUISITE) s+="Exquisite";
    	else s+="Ugly";
    	return s+" Level "+level+" "+name+" Gem";
    }
	
	public boolean isTravel() {
		return (getName().length() != 3);
	}
	
	public boolean isRegular() {
		return (
				!isTravel() &&
				gemrarity.get(getName()) >= Gem.MIN_REGULAR
		);
	}
	
	public boolean isCommon() {
		return (
				!isTravel() &&
				gemrarity.get(getName()) < Gem.MIN_REGULAR && 
				gemrarity.get(getName()) >= Gem.MIN_COMMON
		);
	}
	
	public boolean isUncommon() {
		return (
				!isTravel() &&
				gemrarity.get(getName()) < Gem.MIN_COMMON && 
				gemrarity.get(getName()) >= Gem.MIN_UNCOMMON
		);
	}
	
	public boolean isScarce() {
		return (
				!isTravel() &&
				gemrarity.get(getName()) < Gem.MIN_UNCOMMON && 
				gemrarity.get(getName()) >= Gem.MIN_SCARCE
		);
	}
	
	public boolean isRare() {
		return (
				!isTravel() &&
				gemrarity.get(getName()) < Gem.MIN_SCARCE && 
				gemrarity.get(getName()) >= Gem.MIN_RARE
		);
	}
	
	public boolean isExceptional() {
		return (
				!isTravel() &&
				gemrarity.get(getName()) < Gem.MIN_RARE && 
				gemrarity.get(getName()) >= Gem.MIN_EXCEPTIONAL
		);
	}
	
	public boolean isSingular() {
		return (
				!isTravel() &&
				gemrarity.get(getName()) < Gem.MIN_EXCEPTIONAL
		);
	}

	public int compareTo(Gem g) {
		if (g.getPower() == this.getPower())
			if (g.getName().equals(this.getName()))
				return g.getLevel()-this.getLevel();
			else 
				return g.getName().compareTo(this.getName());
		else
			return g.getPower()-this.getPower();
	}
    
}




