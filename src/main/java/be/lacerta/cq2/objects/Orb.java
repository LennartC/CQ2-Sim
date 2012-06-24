package be.lacerta.cq2.objects;

import java.security.InvalidParameterException;
import java.util.*;

import be.lacerta.cq2.utils.CQ2Functions;
import be.lacerta.cq2.utils.StringUtils;

public class Orb implements Comparable<Orb> {
	String colour;
	int uses;
	SortedMap<String,Gem> gems;
	
	public Orb(String s) throws InvalidParameterException {
		try {
			String[] split = s.trim().split(" ");
			this.setColour(split[0]);
			this.setUses(Integer.parseInt(split[3]));
			int length = split.length-8;
			String[] gemsigns = new String[length];
			System.arraycopy(split,8,gemsigns,0,length);
			this.setGems(gemsigns);
		} catch (NumberFormatException nfe) {
			throw new InvalidParameterException("Unable to parse orb: "+s);
		} catch (ArrayIndexOutOfBoundsException aie) {
			throw new InvalidParameterException("Unable to parse orb: "+s);
		}
	}
	
	public Orb(Collection<Gem> gems) {
		setColour("black");
		setUses(1);
		this.gems = new TreeMap<String,Gem>();
		for (Gem gem : gems) {
			if (gem!=null) this.gems.put(gem.getName(), gem);
		}
		
	}
	
	public Orb(String colour, int uses, Collection<String> gems) {
		setColour(colour);
		setUses(uses);
		setGemSigns(gems);
	}
	
	public Orb() {
		this.setColour("black");
		this.setUses(1);
		Random generator = new Random();
		List<String> gems = new Vector<String>();
		for (int i=0; i<(1+generator.nextInt(8)); i++) {
			gems.add(Gem.getRandomSign());
		}
		this.setGemSigns(gems);
	}
	
	public String toString() {
		String s = "";
		s+= StringUtils.capitalize(colour)+" orb ";
		if (uses>1) s+="with "+uses+" uses remaining. ";
		else s+="with "+uses+" use remaining. ";
		s+="Gem signs: ";
		for (String sign : gems.keySet()) {
			s+=sign+", ";
		}
		s = s.substring(0,s.length()-2);
		return s;
	}
	
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public int getUses() {
		return uses;
	}
	public void setUses(int uses) {
		this.uses = uses;
	}
	public Collection<Gem> getGems() {
		return gems.values();
	}
	public Set<String> getGemsigns() {
		return gems.keySet();
	}
	
	public Gem getGem(String sign) {
		return gems.get(sign.toLowerCase());
	}
	
	public void setGemSigns(Collection<String> gems) {
		this.gems = new TreeMap<String,Gem>();

		for (String sign : gems) {
			sign = sign.trim().toLowerCase();
			if (Gem.isValidSign(fixSign(sign))) {
				this.gems.put(fixSign(sign), null);
			} else {
				throw new InvalidParameterException("Invalid gems found: "+fixSign(sign));
			}
			if (sign.charAt(sign.length()-1)=='.') {
				break;
			}
		}
	}
	
	private String fixSign(String s) {
		return s.replace(",", "").replace(".", "");
	}
	
	public void setGems(String[] gems) throws InvalidParameterException {
		setGemSigns(Arrays.asList(gems));
	}
	
	public void setGem(Gem gem) throws InvalidParameterException {
		if (!gems.containsKey(gem.getName().toLowerCase())) {
			throw new InvalidParameterException("Invalid gem for this orb: "+gem.toCompactString());
		} else {
		    gems.put(gem.getName().toLowerCase(), gem);	
		}
	}
	
	public void removeGem(String sign) {
		gems.put(sign, null);
	}
	
	public int getPower() {
		int total = 0;
    	for(String sign : gems.keySet()) {
    		Gem g = gems.get(sign);
    		if (g != null) total += g.getPower();
    	}
		return total;
	}
	
	public int getCost(int base) {
		int total = 0;
    	for(String sign : gems.keySet() ) {
    		Gem g = gems.get(sign);
    		if (g != null) total += g.getLevel()*base;
    	}
		return total;
	}

	public double getEffect(int baselevel, int enchant) {
    	List<Gem> gemlist = new ArrayList<Gem>();
		for(String sign : gems.keySet()) {
    		gemlist.add(gems.get(sign));
    	}
		
		return CQ2Functions.calcNetherEffect(baselevel,gemlist,enchant);
	}
	
	public int getPotentialPower() {
		int power = 0;
		for (String sign : gems.keySet()) {
			Gem g = new Gem(sign,40,"Ugly");
			if (g.isTravel()) {
				g.setLevel(55);
				g.setQuality("Exceptional");
			}
			power += g.getPower();
		}
		return power;
	}
	
	public double getPotentialEffect(int baselevel) {
		double effect = 0;
		for (String sign : gems.keySet()) {
			Gem g = new Gem(sign,baselevel,"Ugly");
			if (g.isTravel()) g.setQuality("Exceptional");
			effect += CQ2Functions.calcNetherEffect(baselevel, gems.keySet().size(), g, Enchant.EMERALD);
		}
		return effect;
	}

	public int compareTo(Orb o) {
		return  (int)Math.round( (o.getEffect(30,Enchant.EMERALD)-this.getEffect(30,Enchant.EMERALD))*1000 );
	}

}
