package be.lacerta.cq2.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.lacerta.cq2.objects.Enchant;
import be.lacerta.cq2.objects.Gem;
import be.lacerta.cq2.objects.Orb;
import be.lacerta.cq2.sim.hbn.HibernateUtil;
import be.lacerta.cq2.sim.hbn.Mage;


@SuppressWarnings("serial")
public class CQ2Functions {
	public static final String URL = "http://www.castlequest.be/";
	
	public static int getXP(int level) {
		double xp=0;
		for (int i=1; i<level; i++) {
			xp += 8000 * Math.pow(1.06, (i-1));
		}
		return new Double(Math.floor(xp+.5)).intValue();
	}
	
	public static int calcSummonChance(int mage, int creature) {
		return Math.max(0, Math.min(100, (mage*250/creature)-200));
	}
	
	public static int calcMinimumEnchant(int nr, int total) {
		Double minEnchant = Math.floor( (new Double(nr)/new Double(total))*1.9*100+.5 );
		minEnchant = Math.max(1, minEnchant);
		minEnchant = Math.min(20, minEnchant);
		return minEnchant.intValue();
	}
	
	public static String convertToShortItemName(String item) {
		String shortItem = "";
		StringTokenizer st = new StringTokenizer(item);
		while (st.hasMoreElements()) {
			String token = st.nextToken();
			if (token.length()>=2) shortItem += token.substring(0,2);
			else shortItem += token;
		}
		return shortItem;
	}
	
	public static String findMage(String mage) {
		
		if (mage == null || mage.equals("")) return mage;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			Mage m = (Mage) Mage.get(Mage.class, mage);
			if (m!=null) return m.getName();
			
			// if we're still here, do a did you mean search ...
			int distance = mage.length()*2/3;
			String fuzzy = null;

				// TODO: add levenshtein to the select part so we don't have to calculate it again ... has to be added to hbn mapping first, but don't know how.
				Query q = session.createQuery("select name from Mage as t where levenshtein(upper(t.name),:parameter1) < :parameter2 order by levenshtein(upper(t.name),:parameter1)");
				q.setString("parameter1", mage.toUpperCase());
				q.setInteger("parameter2", distance);
				q.setMaxResults(1);
//				for (Iterator<Object[]> it = q.iterate(); it.hasNext(); ) {
//					Object[] el = it.next();
//					fuzzy = (String)el[0];
//					distance = (Integer)el[1];
//				}
				String name = (String)q.uniqueResult();
				if (name!=null) {
					int newdist = StringUtils.getLevenshtein(name.toUpperCase(), mage.toUpperCase());
					if (newdist < distance) {
						fuzzy = name;
						distance = newdist;
					}
				}
			
			return fuzzy;
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	public final static double gdGemOrbMax = 10;
	public final static double gdNetherGemMaxEffectAmount = 6.5;

	public final static HashMap<String,Double> gdNetherBonusMultipliers =  new HashMap<String,Double>(){ {  
	        put("levels",130.0);  
	        put("forest",110.0);
	        put("death",110.0);
	        put("air",110.0);
	        put("earth",110.0);
	        put("stolen",600.0);
	        put("xp",15.0);
	     } };  
	     
	 public static double calcNetherEffect(int baselevel, int signs, Gem gem, int enchant) {
			double countmultiplier = signs/gdGemOrbMax;
			double effect = 0;
			double multiplier = countmultiplier * calcGemMultiplier(baselevel, gem) * Enchant.MULTIPLIER[enchant];
			effect += multiplier * Enchant.NETHER_BONUS[enchant];
			return effect;
	}
	 
	public final static double gdItherianGemMaxEffectAmount = 3;
	public final static double gdItherianMaxMultiplierBonus = 1.2;
	
	public static double calcItherianPotential(int baselevel, Orb orb) {
		double bonus = 0.0;
		double countmultiplier = orb.getGemsigns().size()/gdGemOrbMax;
		double maxmultiplier = 1 / gdItherianGemMaxEffectAmount;
		
		int gemlevels = 0;
		
		for (String sign : orb.getGemsigns()) {
			Gem gem = new Gem(sign,baselevel,Gem.UGLY);
			if (gem.isTravel()) gem.setQuality(Gem.EXQUISITE);
			
			gemlevels += Math.ceil(new Double(baselevel)*gdItherianMaxMultiplierBonus);
			
			double raritymultiplier = gem.isTravel() ? 
					Gem.MIN_TRAVEL_RARITY / gem.getRarity() * 0.7 :
					Gem.MIN_BIRTHSIGN_RARITY / gem.getRarity() * 1;
					
			double qualitymultiplier = Gem.QUALITY_MULTIPLIER[gem.getQuality()];
			
			double multiplier = countmultiplier * maxmultiplier * raritymultiplier * qualitymultiplier;
			bonus += multiplier;
		}
		
		double maxbindmultiplier = gdItherianMinCreatureMod + (baselevel - gdItherianMinCreatureLevel) / (gdItherianMaxCreatureLevel - gdItherianMinCreatureLevel) * (gdItherianMaxCreatureMod - gdItherianMinCreatureMod);

		double finalbonus = bonus * maxbindmultiplier * gdItherianMaxMultiplierBonus * gdItherianMaxMultiplierBonus * 1;
		return finalbonus;
	}

	public final static double gdItherianMinCreatureMod = 2;
	public final static double gdItherianMinCreatureLevel = 30;
	public final static double gdItherianMaxCreatureLevel = 60;
	public final static double gdItherianMaxCreatureMod = 4;
	
	public static double calcNetherEffect(int baselevel, Collection<Gem> gems, int enchant) {
		double countmultiplier = gems.size()/gdGemOrbMax;

		double effect = 0;
		for (Gem gem: gems) {
			if (gem!=null) {
				double multiplier = countmultiplier * calcGemMultiplier(baselevel, gem) * Enchant.MULTIPLIER[enchant];
				effect += multiplier * Enchant.NETHER_BONUS[enchant];
			}
		}
		return effect;
	}
	
	public static double calcNetherEfficiency(int baselevel, Orb orb) {
		List<Gem> gems = new ArrayList<Gem>();
		double power = 0.0;
		for (String sign : orb.getGemsigns()) {
			Gem gem = new Gem(sign,baselevel,Gem.UGLY);
			if (gem.isTravel()) gem.setQuality(Gem.EXQUISITE);
			gems.add(gem);
			if (!gem.isTravel()) power += gem.getPower();
		}
		power = Math.max(1000.0, power);
		double effect = CQ2Functions.calcNetherEffect(baselevel, gems, Enchant.EMERALD);
		return 1000*effect/power;
	}
	
	public static double calcRealNetherEfficiency(int baselevel, Orb orb) {
		double power = 0.0;
		for (Gem gem : orb.getGems()) {
			if (gem!=null) {
				if (!gem.isTravel()) power+=gem.getPower();
				else power+=(gem.getPower()/3);
			}
		}
		power = Math.max(1000.0, power);
		double effect = CQ2Functions.calcNetherEffect(baselevel, orb.getGems(), Enchant.EMERALD);
		return 1000*effect/power;
	}
	
	public static double calcGemMultiplier(int baselevel, Gem gem) {
		if (gem==null) return 0.0;
		double maxmultiplier = 1/gdNetherGemMaxEffectAmount;
		
		double levelmultiplier = new Double(gem.getLevel())/new Double(baselevel);
		if (levelmultiplier > 1) { levelmultiplier = 1; }
		
		double raritymultiplier = gem.isTravel() ? 
				Gem.MIN_TRAVEL_RARITY / gem.getRarity() * 0.7 :
				Gem.MIN_BIRTHSIGN_RARITY / gem.getRarity() * 1;
				
		double qualitymultiplier = Gem.QUALITY_MULTIPLIER[gem.getQuality()];
		
		double multiplier = maxmultiplier * levelmultiplier * raritymultiplier * qualitymultiplier;
		return multiplier;
	}
	
	public static int calcBaseLevel(int skill) {
		double gdStartingSkillPointsOnMain = 20;
		double gdSkillPointsPerLevel = 10;
		double gdSkillPointsOnMain = 0.5;
		return new Double(Math.floor( (new Double(skill) - gdStartingSkillPointsOnMain) / (gdSkillPointsPerLevel * 2 * gdSkillPointsOnMain) + 1 +.5 )).intValue();
	}
}
