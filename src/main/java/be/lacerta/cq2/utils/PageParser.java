package be.lacerta.cq2.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.lacerta.cq2.objects.Creature;
import be.lacerta.cq2.objects.Item;
import be.lacerta.cq2.objects.Itherian;
import be.lacerta.cq2.sim.hbn.CritCurses;
import be.lacerta.cq2.sim.hbn.CursedMage;
import be.lacerta.cq2.sim.hbn.Kingdom;
import be.lacerta.cq2.sim.hbn.Mage;
import be.lacerta.cq2.sim.hbn.RaidResult;
import be.lacerta.cq2.sim.hbn.RevealCrit;
import be.lacerta.cq2.sim.hbn.Skill;
import be.lacerta.cq2.sim.hbn.Support;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.sim.hbn.XPLog;

public class PageParser {
	public final static int DONATION_NOT_FOUND = 0;
	public final static int DONATION_FAILED = 1;
	public final static int DONATION_SUCCESS = 2;
	
	public final static int RAID_NOT_FOUND = 0;
	public final static int RAID_GENERAL_INFO_NOT_FOUND = 1;
	public final static int RAID_CONCLUSION_NOT_FOUND = 2;
	public final static int RAID_SUCCESS = 2;
	
	
	
	public final static String REGEXP_PLAYERINFO_REQUIRED = "(\\S*) lives in (\\S*).*"+
															"Class:\\s*(\\S*)\\s*Mage\\s*" +
															"Level:\\s*(\\d+)\\s*";
	public final static String REGEXP_PLAYERINFO_SKILLS = 	"Forest Magic:\\s*(\\d+)\\s+.*" +
															"Air Magic:\\s*(\\d+)\\s+.*" +
															"Death Magic:\\s*(\\d+)\\s+.*" +
															"Earth Magic:\\s*(\\d+)\\s+";
	
	public final static String REGEXP_CHARACTER_REQUIRED = 	"Class:\\s*(\\S*)\\s*Mage\\s*" +
															"Level:\\s*(\\d+)\\s*" +
															"Experience:\\s*(\\d*)\\s*/.*";
	public final static String REGEXP_CHARACTER_SKILLS = 	"Forest Magic:\\s*(\\d+)\\s+.*" +
															"Death Magic:\\s*(\\d+)\\s+.*" +
															"Air Magic:\\s*(\\d+)\\s+.*" +
															"Earth Magic:\\s*(\\d+)\\s+";
	public final static String REGEXP_CREATURE_CURSES = "(Metamorphosis|Doppelganger|Suffocation|Jinx|Surathil's Blessing|Metamorphosis Dispell|Doppelganger Dispell|Suffocation Dispell|Jinx Dispell|Surathil's Blessing Dispell)";

	public static String parseSpellbook(String page, User user) {
		if (user.getMage()==null) return "Please fill in your mage name before you add your curses.";
		
		// remove skills
		List<CritCurses> skills = CritCurses.getCursesForMage(user.getMage());
		for (CritCurses cc : skills) {
			cc.delete();
		}
		
		// find new ones
		int curses = 0;
		be.lacerta.cq2.sim.hbn.Creature crit = null;
		Skill skill = null;
		String level = null;
		
		Pattern pattern = Pattern.compile(
				"(.*) "+REGEXP_CREATURE_CURSES+" (Apprentice|Expert|Grandmaster): .*"
				);
		Matcher match = pattern.matcher(page);
		while (match.find()) {
			crit = be.lacerta.cq2.sim.hbn.Creature.getCreature(match.group(1));
			skill = Skill.loadByText(match.group(2));
			level = match.group(3);
			if(crit!=null && skill!=null && level!=null) {
				CritCurses cc = new CritCurses(user.getMage(),crit,skill,level);
				cc.save();
				curses++;
			}
		}
		return curses+" curses set for "+user.getMage();
	}
 
	public static List<CursedMage> parseActiveCurses(String page, User user) {
		
		List<CursedMage> updatedMages = null;
		Set<String> mages = new HashSet<String>();
		
		Pattern pattern = Pattern.compile(
				"Surathli's Anger on (\\S*): .* \\((\\d+) active shards left, (\\d+) days, (\\d+) hours, (\\d+) minutes left"
				);
		Matcher match = pattern.matcher(page);
		while (match.find()) {
			if (updatedMages == null) updatedMages = new ArrayList<CursedMage>();

			String mage = match.group(1);
			int shards = Integer.parseInt(match.group(2));
			int days = Integer.parseInt(match.group(3));
			int hours = Integer.parseInt(match.group(4));
			int minutes = Integer.parseInt(match.group(5));
			
			long end = new Date().getTime();
			end += ((days*24+hours)*60+minutes)*60*1000;
			
			mages.add(mage);
			
			CursedMage cm = CursedMage.getByMageName(mage);
			if (cm == null) {
				cm = new CursedMage();
				cm.setMage(Mage.getOrCreateMage(mage));
				cm.setUser(user);
				cm.setEndTime(new Date(end));
				cm.setShards(shards);
				cm.save();
				updatedMages.add(cm);
			} else if (cm.getEndTime().getTime() < (end-10*60*1000) || cm.getEndTime().getTime() > (end+10*60*1000)) {
				cm.delete();
				cm = new CursedMage();
				cm.setMage(Mage.getOrCreateMage(mage));
				cm.setUser(user);
				cm.setEndTime(new Date(end));
				cm.setShards(shards);
				cm.save();
				updatedMages.add(cm);
			}
		}
		
		List<CursedMage> curses = CursedMage.getByUser(user);
		for (CursedMage cm : curses) {
			if (cm.getMage() == null || !mages.contains(cm.getMage().getName())) {
				cm.delete();
			}
		}
		
		return updatedMages;
	}
	public static boolean parseCharacterPage(String page, User user) {
		Pattern pattern = Pattern.compile(
				REGEXP_CHARACTER_REQUIRED +
				REGEXP_CHARACTER_SKILLS,
				Pattern.DOTALL
				);
		Matcher match = pattern.matcher(page);
		if (match.find()) {
			user.setCq2class(match.group(1));
			user.setLevel(Integer.parseInt(match.group(2)));
			user.setForestSkill(Integer.parseInt(match.group(4)));
			user.setDeathSkill(Integer.parseInt(match.group(5)));
			user.setAirSkill(Integer.parseInt(match.group(6)));
			user.setEarthSkill(Integer.parseInt(match.group(7)));
			user.update();
			
			XPLog xl = new XPLog(user.getId(), Integer.parseInt(match.group(3)));
			xl.save();
			
			return true;
		}
		return false;
	}
	
	public static String parseKingdom(String page) {
		boolean found = false;
		
		Pattern p;
		Matcher m;
		
		String kdName = "Stonehenge";
		int kdLevel = 1;
		// find kd: IaC (no faction) is ruled by king Sagi_Zod
		p = Pattern.compile("(\\S*)\\s*\\((.*)\\) is ruled by (king|queen)");
		m = p.matcher(page);	
		if (m.find()) {
			kdName = m.group(1);
			found = true;
		}
		p = Pattern.compile("You are living in (\\S*)\\s*\\((.*)\\)\\.");
		m = p.matcher(page);	
		if (m.find()) {
			kdName = m.group(1);
			found = true;
		}
		
		// find kd level
		p = Pattern.compile("It is a level (\\d*) kingdom");
		m = p.matcher(page);
		if (m.find()) {
			kdLevel = Integer.parseInt(m.group(1));
		}
	
		if (found) {
			Kingdom kingdom = Kingdom.loadByName(kdName);

			if (kingdom == null) {
				kingdom = new Kingdom();
				kingdom.setName(kdName);
			}
			
			kingdom.setLevel(kdLevel);
			kingdom.saveOrUpdate();
			
			// find inhabitants
			p = Pattern.compile("(\\S*)\\s*\\((king, |warlord, |queen, |)L(\\d*), (Forest|Death|Air|Earth)\\)");
			m = p.matcher(page);
			
			List<Mage> parsedInhabitants = new ArrayList<Mage>();
			
			while (m.find()) {
				//String mage = m.group(1);
				Mage mage = Mage.getOrCreateMage(m.group(1));
				mage.setLevel(Integer.parseInt(m.group(3)));
				mage.setCq2class(m.group(4));
				mage.setKingdom(kingdom);
				mage.saveOrUpdate();
				kingdom.addInhabitant(mage);
				parsedInhabitants.add(mage);
			}
			
			List<Mage> toRemove = new ArrayList<Mage>();
			for (Mage mage : kingdom.getInhabitants()) {
				if (!parsedInhabitants.contains(mage)) toRemove.add(mage);
			}
			
			for (Mage mage : toRemove) {
				kingdom.removeInhabitant(mage);
			}
			
			kingdom.saveOrUpdate();
			
			List<User> users = User.getUserList();
			for (User u : users) {
				if (parsedInhabitants.contains(u.getMage())) {

					int xp = CQ2Functions.getXP(u.getMage().getLevel());
					
					try {
						XPLog l = XPLog.getXPLogs(u.getId(), 1).get(0);
						if (xp > l.getXp()) {
							new XPLog(u.getId(), xp).save();
						}
					} catch (NullPointerException npe) {
						new XPLog(u.getId(), xp).save();
					} catch (IndexOutOfBoundsException npe) {
						new XPLog(u.getId(), xp).save();
					}
					u.update();
				}
			}
		}
		return kdName;
	}
	
	public static int parseDonation(String page, String reason, User user) {
		Date time = new Date();
		
		Pattern timePattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2}) @ (\\d{2}):(\\d{2})");
		Pattern toPattern = Pattern.compile("You donated the following things to (\\S*):");
		Pattern brimPattern = Pattern.compile("\\s+(\\d+)\\s+brimstone\\.");
		Pattern crysPattern = Pattern.compile("\\s+(\\d+)\\s+crystal\\.");
		Pattern essePattern = Pattern.compile("\\s+(\\d+)\\s+essence\\.");
		Pattern granPattern = Pattern.compile("\\s+(\\d+)\\s+granite\\.");
		
		Matcher m = timePattern.matcher(page);
		if (m.find()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd @ HH:mm");
			try {
				time = sdf.parse(m.group());
			} catch (ParseException e) {}
		}
		
		m = toPattern.matcher(page);
		if (m.find()) { 
			String to = m.group(1);
			int amount = 0;

			m = brimPattern.matcher(page);
			if (m.find()) amount += Integer.parseInt(m.group(1));				
			m = crysPattern.matcher(page);
			if (m.find()) amount += Integer.parseInt(m.group(1));				
			m = essePattern.matcher(page);
			if (m.find()) amount += Integer.parseInt(m.group(1));				
			m = granPattern.matcher(page);
			if (m.find()) amount += Integer.parseInt(m.group(1));
			
			if (!Support.addDonation(time, user, to, amount, reason)) {
				return DONATION_FAILED;
			} else {
				return DONATION_SUCCESS;
			}
		}
		return DONATION_NOT_FOUND;
	}
	
	
	public static List<RevealCrit> parseReveal(String page) {
		List<RevealCrit> list = new ArrayList<RevealCrit>();
		
		String[] lines = page.split("\n");
		
		CreatureFinder cf = new CreatureFinder();
		
		Pattern critPattern = Pattern.compile("(.*)/(air|earth|forest|death).*L(\\d+)\\s+(\\d+)/(\\d+)\\s+F(\\d+)/D(\\d+)/A(\\d+)/E(\\d+)(.*)");
		Pattern cursePattern = Pattern.compile("Cursed:\\s+(Metamorphosis|Doppelganger|Suffocation|Jinx|Surathil's Blessing)");
		
//		Mountain Priest   Priest/forest L20 427/936 F112/D57/A62/E12
//		Mountain Priest (cast curse) 	Priest/forest, unknown 	L20 	427/936 	F114/D59/A64/E14 	ShRo 	
//		Danny Ocean (sacrifice, curse)  Illusion Wolf/forest (N), defense  L150  7887/16210  F131/D108/A134/E89  MyCl* (MaBe Priest) 
//		Seraph Channeler (sacrifice, curse)  Seraph/forest (I), passive  L20  120/336  F110/D55/A55/E0  SaDr (+1500 Damage)  
//		Terror Hound (sacrifice, curse)  Hound/forest (N), passive  L41  1602/2169  F100/D70/A25/E0  CoofEx 
		
		int i=0;
		for (String line : lines) {
			Matcher m = critPattern.matcher(line);
			if (m.find()) {
				RevealCrit rc = new RevealCrit();
				rc.setUnparsed(line);
				rc.setSortid(i++);
				// standard stuff we can regexp
				rc.setCreatureClass(m.group(2));
				rc.setLevel(Integer.parseInt(m.group(3)));
				rc.setDamage(Integer.parseInt(m.group(4)));
				rc.setHealth(Integer.parseInt(m.group(5)));
				rc.setForestDef(Integer.parseInt(m.group(6)));
				rc.setDeathDef(Integer.parseInt(m.group(7)));
				rc.setAirDef(Integer.parseInt(m.group(8)));
				rc.setEarthDef(Integer.parseInt(m.group(9)));
				
				if (line.indexOf("(N)") > 0) {
					rc.setType("N");
				} else if (line.indexOf("(I)") > 0) {
					rc.setType("I");
				}
				
				// find race and class
				Creature crit = cf.findRaceAndClass(line);
				
				if (crit!=null) {
					rc.setName(crit.getName());
					rc.setRace(crit.getRace());
				} else { // try split ...
					String[] s = m.group(1).split("\\s{2,}");
					rc.setName(s[0]);
					if (s.length > 1) rc.setRace(s[1]);
				}
				

				// find item and enchant
				if (m.group(10).length() > 0) {
					String item = m.group(10).trim();
					if (item.indexOf('*')>0) {
						int start = item.indexOf('(')+1;
						int end = item.indexOf(')',start);
						if (start > 1 && end > 1) {
							rc.setItem(item.substring(0,start-3));
							rc.setEnchant(item.substring(start,end));
						} else {
							rc.setItem(item.substring(0,item.length()-1));
							rc.setEnchant("?");
						}
					} else if (("I").equals(rc.getType()) && item.indexOf('+') > 0) {
						int start = item.indexOf('+')+1;
						int end = item.indexOf(' ', start);
						if(item.indexOf("%")>-1) end--;
						rc.setIth(Integer.parseInt(item.substring(start,end)));
						rc.setItem(item);
					} else {
						rc.setItem(item);
					}
				}
				list.add(rc);
			} else {
				m = cursePattern.matcher(line);
				if (m.find()) {
					RevealCrit rc = list.get(list.size()-1);
					rc.setCurse(m.group(1));
				}
			}
		}

		return list;
	}
	
	public static List<Creature> parseCreatureList(String input) {
		List<Creature> list = new ArrayList<Creature>();
		
		String[] lines = input.split("\n");
		
		CreatureFinder cf = new CreatureFinder();
		
		Pattern critPattern = Pattern.compile("(.*)/(air|earth|forest|death).*L(\\d+)\\s+(\\d+)/(\\d+)\\s+F(\\d+)/D(\\d+)/A(\\d+)/E(\\d+)(.*)");
		Pattern cursePattern = Pattern.compile("Cursed:\\s+(Metamorphosis|Doppelganger|Suffocation|Jinx|Surathli's Blessing)");
		
//		Mountain Priest   Priest/forest L20 427/936 F112/D57/A62/E12
//		Mountain Priest (cast curse) 	Priest/forest, unknown 	L20 	427/936 	F114/D59/A64/E14 	ShRo 	
//		Danny Ocean (sacrifice, curse)  Illusion Wolf/forest (N), defense  L150  7887/16210  F131/D108/A134/E89  MyCl* (MaBe Priest) 
//		Seraph Channeler (sacrifice, curse)  Seraph/forest (I), passive  L20  120/336  F110/D55/A55/E0  SaDr (+1500 Damage)  
//		Terror Hound (sacrifice, curse)  Hound/forest (N), passive  L41  1602/2169  F100/D70/A25/E0  CoofEx 

		for (String line : lines) {
			Matcher m = critPattern.matcher(line);
			if (m.find()) {
				Creature creature;
		        //check if ith..
		        if (line.matches("(I),")) {
		        	creature = new Itherian();
		        } else {
		        	creature = new Creature();
		        }

				// standard stuff we can regexp
		        creature.setCreatureClass(m.group(2));
		        creature.setLevel(Integer.parseInt(m.group(3)));
		        creature.setDamage(Integer.parseInt(m.group(4)));
		        creature.setHealth(Integer.parseInt(m.group(5)));
		        creature.setForestDef(Integer.parseInt(m.group(6)));
		        creature.setDeathDef(Integer.parseInt(m.group(7)));
		        creature.setAirDef(Integer.parseInt(m.group(8)));
				creature.setEarthDef(Integer.parseInt(m.group(9)));
				
				if (line.indexOf("(N)") > 0) {
					creature.setNether(true);
				}
				
				// find race and class
				Creature crit = cf.findRaceAndClass(line);
				
				if (crit!=null) {
					creature.setName(crit.getName());
					creature.setRace(crit.getRace());
				} else { // try split ...
					String[] s = m.group(1).split("\\s{2,}");
					creature.setName(s[0]);
					if (s.length > 1) creature.setRace(s[1]);
				}
				

				// find item and enchant
				if (m.group(10).length() > 0) {
					Item item = new Item();
					creature.setItem(item);
					String itemstr = m.group(10).trim();
					if (itemstr.indexOf('*')>0) {
						int start = itemstr.indexOf('(')+1;
						int end = itemstr.indexOf(')',start);
						if (start > 1 && end > 1) {
							item.setName(itemstr.substring(0,start-3), true);
							item.setEnchant(itemstr.substring(start,start+4),itemstr.substring(start+4, end));
						} else {
							item.setName(itemstr.substring(0,itemstr.length()-1),true);
							item.setUnknownEnchant(true);
						}
					} else if (creature instanceof Itherian && itemstr.indexOf('+') > 0) {
						Pattern p = Pattern.compile(".*\\s+\\(\\+(\\d+)[%]? (DAMAGE|HEALTH|DEFENSES)\\).*");
						Matcher match = p.matcher(itemstr);
						if (match.find()) {
							String type=match.group(2);
			        		if (type.equals("DAMAGE")) 	((Itherian)creature).setIthDamage(Integer.parseInt(match.group(1)));
			        		if (type.equals("HEALTH")) 	((Itherian)creature).setIthHealth(Integer.parseInt(match.group(1)));
			        		if (type.equals("DEFENSES"))((Itherian)creature).setIthDefence(Integer.parseInt(match.group(1)));
						}
					} else {
						item.setName(itemstr, true);
					}
					fixBaseStats(creature);
				}
				list.add(creature);
			} else {
				m = cursePattern.matcher(line);
				if (m.find()) {
					Creature rc = list.get(list.size()-1);
					rc.setCurse(m.group(1));
				}
			}
		}

		return list;
	}
	
	// TODO: maybe better to get base stats from db?
	private static void fixBaseStats(Creature crit) {
		Item item = crit.getItem();
		if (item.getDmgIncr()>0) crit.setDamage(crit.getDamage()-item.getDmgIncr());
		if (item.getDmgIncrPerc()>0) crit.setDamage(new Double(crit.getDamage()/item.getDmgIncrPerc()).intValue());
		if (item.getHealthIncr()>0) crit.setHealth(crit.getHealth()-item.getHealthIncr());
		if (item.getHealthIncrPerc()>0) crit.setHealth(new Double(crit.getHealth()/item.getHealthIncrPerc()).intValue());
		if (item.getDefVsAirIncr()>0) crit.setAirDef(crit.getAirDef()-item.getDefVsAirIncr());
		if (item.getDefVsForestIncr()>0) crit.setForestDef(crit.getForestDef()-item.getDefVsForestIncr());
		if (item.getDefVsDeathIncr()>0) crit.setDeathDef(crit.getDeathDef()-item.getDefVsDeathIncr());
		if (item.getDefVsEarthIncr()>0) crit.setEarthDef(crit.getEarthDef()-item.getDefVsEarthIncr());
	}
	
	public static RaidResult parseRaid(String text, User user) {
		Pattern pattern;
		Matcher match;
		RaidResult rr = new RaidResult();
		
		rr.setUser(user);
		
		int idx = text.indexOf("General Information");
		if (idx > 200) text = text.substring(idx);
		
		idx = text.indexOf("Page generated in ");
		if (idx > 0) text = text.substring(0,idx-1);
		
		rr.setText(text);
		
		// find mage
		pattern = Pattern.compile("We were attacked by (\\S*)[.]");
		match = pattern.matcher(text);
		if (match.find()) {
			rr.setIncoming(true);
			rr.setMage(Mage.getOrCreateMage(match.group(1)));
		} else {
			pattern = Pattern.compile("We attacked (\\S*)[.]");
			match = pattern.matcher(text);
			if (match.find()) {
				rr.setIncoming(false);
				rr.setMage(Mage.getOrCreateMage(match.group(1)));
			} else {
				return null;
			}
		}
		
		// find date
		pattern = Pattern.compile("Date of battle: (\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2})[.]");
		match = pattern.matcher(text);
		if (match.find()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				rr.setDate(sdf.parse(match.group(1)+" "+match.group(2)));
			} catch (ParseException e) { rr.setDate(new Date()); }
		} else {
			rr.setDate(new Date());
		}
		
		// find gained/lost
		/*
		 * You gained 0 experience and lost 3% power balance.
		 * You gained 0 experience and 8% power balance. Your opponent lost 5% power balance.
		 * You managed to steal 100% from the arcane chamber of your opponent and convert it into a Ugly level 30 Yar Gem.
		 * You gained/lost 48 brimstone, 48 crystal, 48 essence, 48 granite and 190 power.
		 */
		
		
		pattern = Pattern.compile(" (\\d*) brimstone, (\\d*) crystal, (\\d*) essence, (\\d*) granite and (\\d*) p");
		match = pattern.matcher(text);
		if (match.find()) {
			rr.setTotalres(	Integer.parseInt(match.group(1))+
							Integer.parseInt(match.group(2))+
							Integer.parseInt(match.group(3))+
							Integer.parseInt(match.group(4))
						);
			rr.setTotalpwr(Integer.parseInt(match.group(5)));
			
			user.refresh();
			if (rr.isIncoming() && rr.getTotalres() > user.getRaidloss()) {
				user.setRaidloss(rr.getTotalres());
				user.update();
			} else if (rr.getTotalres() > user.getRaidgain()) {
				user.setRaidgain(rr.getTotalres());
				user.update();
			}	
		} else {
			rr.setTotalpwr(0);
			rr.setTotalres(0);

			boolean found = false;
			
			if (rr.isIncoming()) {
				pattern = Pattern.compile("You gained (\\d*) experience and lost (\\d*)% power bal");
				match = pattern.matcher(text);
				if (match.find()) {
					found = true;
					rr.setTotalpb(Integer.parseInt(match.group(2)));
				}

				// Your opponent managed to steal 132% from your arcane chamber and convert it into a Beautiful level 20 Chi Gem.
				if (!found) {
					pattern = Pattern.compile("Your opponent managed to steal (\\d*)% from your arcane chamber and convert it into a (.*) Gem");
					//                         Your opponent managed to steal 12    % from your arcane chamber and convert it into a Ugly level 4 Xil Gem.
					match = pattern.matcher(text);
					if (match.find()) {
						found = true;
						rr.setTotalgem(match.group(2));
					}
				}
				
				// Your opponent killed 2 of your workers, which is 1% of your total worker population.
				if (!found) {
					pattern = Pattern.compile("Your opponent killed (\\d*) of your workers, which is (\\d*)% of your total worker");
					match = pattern.matcher(text);
					if (match.find()) {
						found = true;
						rr.setTotalworkers(Integer.parseInt(match.group(1)));
					}
				}
			} else {
				/*
				 * You gained 0 experience and 8% power balance. Your opponent lost 5% power balance.
				 * You managed to steal 100% from the arcane chamber of your opponent and convert it into a Ugly level 30 Yar Gem.
				 * You killed 25 workers of your opponent, which is 16% of his total worker population.
				 */

				pattern = Pattern.compile("You gained (\\d*) experience and (\\d*)% power balance. Your opponent lost (\\d*)% p");
				match = pattern.matcher(text);
				if (match.find()) {
					found = true;
					rr.setTotalpb(Integer.parseInt(match.group(3)));
				}

				if (!found) {
					pattern = Pattern.compile("You managed to steal (\\d*)% from the arcane chamber of your opponent and convert it into a (.*)[.]");
					match = pattern.matcher(text);
					if (match.find()) {
						found = true;
						rr.setTotalgem(match.group(2));
					}
				}
				
				if (!found) {
					pattern = Pattern.compile("You killed (\\d+) workers of your opponent, which is (\\d+)% of his total worker population");
					match = pattern.matcher(text);
					if (match.find()) {
						found = true;
						rr.setTotalworkers(Integer.parseInt(match.group(1)));
					}
				}
				
			}
			
		}
		
		rr.save();
		
		return rr;
	}
	
}
