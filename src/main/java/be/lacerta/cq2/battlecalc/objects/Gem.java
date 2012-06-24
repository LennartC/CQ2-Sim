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

package be.lacerta.cq2.battlecalc.objects;


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
	public final static int RUBY = 1;
	public final static int SAPHIRE = 2;

	public final static int UGLY = 0;
	public final static int BEAUTIFUL = 1;
	public final static int MAGNIFICENT = 2;
	public final static int EXQUISITE = 3;
	
	private static HashMap<String, Double> gempower = null;
	
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
	
	public Gem(String s) throws InvalidParameterException {
		String[] gem = s.split(" ");
		if (gem.length < 4) throw new InvalidParameterException("Unable to parse gem: "+s);
		try {
			this.setQuality(gem[0]);
			this.setLevel(Integer.parseInt(gem[2]));
			this.setName(gem[3]);
		} catch (NumberFormatException nfe) {
			throw new InvalidParameterException("Unable to parse gem: "+s);
		}
	}

	private int parseEnchant(String enchant) {
		if (enchant.toUpperCase().equals("RUBY"))
			return Gem.RUBY;
		else if (enchant.toUpperCase().equals("SAPHIRE"))
			return Gem.SAPHIRE;
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
		if (level == 0 || enchant != SAPHIRE)
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
	
	public static boolean isValidSign(String s) {
		if (gempower == null) {
			initGempower();
		}
		return (gempower.containsKey(s));
	}
	
	private static void initGempower() {
		gempower = new HashMap<String,Double>();
		gempower.put("efis", new Double(28.0));
		gempower.put("aner", new Double(30.0));
		gempower.put("asih", new Double(33.6));
		gempower.put("qulah", new Double(38.8));
		gempower.put("nefar", new Double(45.6));
		gempower.put("zaneh", new Double(56.0));
		gempower.put("faerh", new Double(72.0));
		gempower.put("muena", new Double(100.8));
		gempower.put("urtae", new Double(168.0));
		gempower.put("qaera", new Double(252.0));
		gempower.put("qer", new Double(133.0));
		gempower.put("dun", new Double(64.0));
		gempower.put("eno", new Double(42.0));
		gempower.put("tur", new Double(43.2));
		gempower.put("zey", new Double(88.9));
		gempower.put("ime", new Double(106.6));
		gempower.put("uax", new Double(533.3));
		gempower.put("ruh", new Double(45.7));
		gempower.put("inu", new Double(41.0));
		gempower.put("kih", new Double(69.6));
		gempower.put("jih", new Double(84.2));
		gempower.put("var", new Double(50.0));
		gempower.put("yar", new Double(160.0));
		gempower.put("nis", new Double(40.0));
		gempower.put("aru", new Double(44.4));
		gempower.put("sah", new Double(48.6));
		gempower.put("fih", new Double(53.3));
		gempower.put("fen", new Double(57.1));
		gempower.put("eda", new Double(61.5));
		gempower.put("len", new Double(76.2));
		gempower.put("uza", new Double(94.2));
		gempower.put("zar", new Double(100.0));
		gempower.put("mul", new Double(114.3));
		gempower.put("mae", new Double(123.0));
		gempower.put("qah", new Double(145.5));
		gempower.put("xil", new Double(266.7));
		gempower.put("nax", new Double(533.3));
		gempower.put("lua", new Double(80.0));
		gempower.put("efu", new Double(55.2));
		gempower.put("chi", new Double(51.6));
		gempower.put("uxi", new Double(533.3));
		gempower.put("xio", new Double(533.3));
		gempower.put("xis", new Double(533.3));
		gempower.put("yun", new Double(177.8));
		gempower.put("nuy", new Double(200.0));
		gempower.put("fau", new Double(59.3));
		gempower.put("uyi", new Double(228.6));
		gempower.put("vae", new Double(72.7));
		gempower.put("uka", new Double(66.7));
		gempower.put("ner", new Double(47.1));
	}
	
	public static String getRandomSign() {
		if (gempower == null) {
			initGempower();
		}
		String sign = "";
		Random generator = new Random();
		int nr = generator.nextInt(49);
		int i=0;
		for (Iterator<String> it=gempower.keySet().iterator();it.hasNext();i++) {
			if (i==nr) {
				sign = ""+it.next();
				break;
			} else it.next();
		}
		return sign;
	}
	
	public static Gem getRandomGem() {
		if (gempower == null) {
			initGempower();
		}
		String sign = "";
		Random generator = new Random();
		int nr = generator.nextInt(49);
		int i=0;
		for (Iterator<String> it=gempower.keySet().iterator();it.hasNext();i++) {
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
		if (gempower == null) {
			initGempower();
		}
	    
	    double power = 0;
	    
	    if (gempower.get(gem.getName().toLowerCase()) != null) {
	    	power = ((Double)gempower.get(gem.getName().toLowerCase())).doubleValue();
	    }

	    if (gem.getQuality() == Gem.BEAUTIFUL) power *= 1.2;
	    else if (gem.getQuality() == Gem.MAGNIFICENT) power *= 1.4;
	    else if (gem.getQuality() == Gem.EXQUISITE) power *= 1.6;
	     
	    power *= gem.getLevel();
	    return (int)Math.floor(power);
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

	public int compareTo(Gem g) throws ClassCastException {
		return g.getPower()-this.getPower();
	}
    
}




