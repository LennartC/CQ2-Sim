package be.lacerta.cq2.utils;

import java.util.Set;

import be.lacerta.cq2.sim.hbn.Item;
import be.lacerta.cq2.sim.hbn.RaidResult;
import be.lacerta.cq2.sim.hbn.Reveal;
import be.lacerta.cq2.sim.hbn.RevealCrit;

public class SimAjax {
	public String[] getRaidReport(String reportId, String divId) {
		String report = "";
		
		try {
			RaidResult rr = (RaidResult)RaidResult.get(RaidResult.class, Integer.parseInt(reportId));
			if (rr!=null) report = formatRaidReport(rr.getText());
		} catch (NumberFormatException nfe) {};
		String[] ret = {divId, report};
		return ret;
	}
	
	// TODO: change to divs
	public String[] getReveal(String revealId, String divId) {
		String report = "";
		try {
			Reveal r = (Reveal)Reveal.get(Reveal.class, Integer.parseInt(revealId));
			if (r!=null) {
				Set<RevealCrit> crits = r.getCreatures();
				
				report += "	<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n";
				report += "	    <tr>\n";
				report += "	        <td class=\"titleline\" width=\"100%\" colspan=\"6\"><font class=\"head\">Creature List ("+ crits.size() +")</font></td>\n";
				report += "	    </tr>\n";
				
				for (RevealCrit crit : crits) {
					report += "		<tr height=\"8\"> <td colspan=\"6\"></td></tr>\n";
					if (crit.getRace()==null) {
					report += "	    <tr> <td colspan=\"6\"><%= crit.getUnparsed() %></td></tr>\n";
					} else {
						report += "		<tr>\n";
						report += "			<td width=\"28%\" align=\"left\">\n";
						report += "			   <font class=\"head\">"+crit.getName()+"</font>\n";
						report += "			</td>\n";
						report += "			<td width=\"25%\" align=\"center\">\n";
						report += "			   "+crit.getRace()+"/"+crit.getCreatureClass(); if (crit.getType() != null) { report += "("+crit.getType()+")"; }
						report += "			</td>\n";
						report += "			<td width=\"5%\" align=\"center\">L"+crit.getLevel()+"</td>\n";
						if (crit.getStandardCreature()!=null) {
							report += "				<td width=\"6%\" align=\"center\">\n";
							report += "					<font "; if (crit.getStandardCreature().getDamage() != crit.getDamage()) { report += "class=\"alert\""; }
							report += "					>"+crit.getDamage()+"</font>/<font "; if (crit.getStandardCreature().getHealth() != crit.getHealth()) { report += "class=\"alert\""; }
							report += "					>"+crit.getHealth()+"</font>\n";
							report += "				</td>\n";
							report += "				<td width=\"20%\" align=\"center\">\n";
							report += "				    F<font "; if (crit.getStandardCreature().getForestDef() != crit.getForestDef()) { report += "class=\"alert\""; }
							report += "				    >"+crit.getForestDef()+"</font>/D<font "; if (crit.getStandardCreature().getDeathDef() != crit.getDeathDef()) { report += "class=\"alert\""; }
							report += "					>"+crit.getDeathDef()+"</font>/A<font "; if (crit.getStandardCreature().getAirDef() != crit.getAirDef()) { report += "class=\"alert\""; }
							report += "					>"+crit.getAirDef()+"</font>/E<font "; if (crit.getStandardCreature().getEarthDef() != crit.getEarthDef()) { report += "class=\"alert\""; }
							report += "					>"+crit.getEarthDef()+"</font>\n";
							report += "				</td>\n";
						} else {
							report += "				<td width=\"6%\" align=\"center\">\n";
							report += "				"+crit.getDamage()+"/"+crit.getHealth()+"</td>\n";
							report += "				<td width=\"20%\" align=\"center\">\n";
							report += "				    F<font>"+crit.getForestDef()+"</font>/D<font\n";
							report += "					>"+crit.getDeathDef()+"</font>/A<font \n";
							report += "					>"+crit.getAirDef()+"</font>/E<font\n";
							report += "					>"+crit.getEarthDef()+"</font>\n";
							report += "				</td>\n";
						}
						report += "				\n";
						report += "			<td width=\"16%\" align=\"center\">\n";
						if (crit.getItem() != null) {
							Item i = Item.getItem(crit.getItem());
							if (i == null) i = Item.getItemByShortName(crit.getItem(), crit.getRace());
						
							if (i != null) {
	
								report += "<a href=\"javascript:void(0);\"";
								report += " onmouseover=\"window.status = '"+ i.getDescr().replaceAll("'","\\\\'") +"'; return overlib('"+ i.getDescr().replaceAll("'","\\\\'") +"');\"";
								report += " onmouseout=\"return nd();\">"+ i.getName() +"</a>"; 
				            } else {
				            	report += crit.getItem();
				            } 
							
							if (crit.getEnchant() != null) {
								report += "* ("+crit.getEnchant()+")";
							}
						}
						report += "			</td>\n";
						report += "		</tr>\n";
						report += "		<tr>\n";
						report += "			<td colspan=\"6\">&nbsp;&nbsp;";
						if (crit.getCurse() != null) {
							report +="Cursed: "+crit.getCurse();
						}
						report += "			</td>\n";
						report += "		</tr>\n";
					}
				}				
				report += "	</table>\n";
			}
		} catch (NumberFormatException nfe) {
		} catch (NullPointerException npe) {
		};
		String[] ret = {divId, report};
		return ret;
	}
	

	private String formatRaidReport(String text) {
		String result=text;
		result = result.replaceAll("We attacked (.+)\\.", "We attacked <a href=\"?page=mage&mage=$1\">$1</a>.");
		result = result.replaceAll("General Information|Report", "<br/><font class=\"head\">$0</font>");
		result = result.replaceAll("Visible Creatures|Conclusion|Visible and Hidden Creatures|Hidden Creatures", "<br/><u>$0</u>");
		result = result.replaceAll("Your .+ killed your opponent's .{3,21} but died in the process\\.\\s+It gained \\d+ experience\\.", "<font class=\"top\"><i>$0</i></font>");
		result = result.replaceAll("Your .+ killed your opponent's .{3,21} because it was exhausted\\.\\s+It gained \\d+ experience\\.", "<font class=\"alert\"><i>$0</i></font>");
		result = result.replaceAll("Your .+ killed your opponent's .{3,21}\\.\\s+It gained \\d+ experience\\.|" +
				"Your (?!opponent's).+ was left undefended and could steal a lot of resources\\.?|" +
				"It managed to steal your opponent's .+\\.", "<font class=\"alert\"><i>$0</i></font>");
		result = result.replaceAll("Your .+ got killed by your opponent's .{3,21}\\.\\s+It gained \\d+ experience\\.|" +
				"Your opponent's .+ was left undefended and could steal a lot of resources\\.", "<font class=\"head\"><i>$0</i></font>");		
		//result = result.replaceAll("Your .+ was left undefended and could steal a lot of resources\\.?", "<font class=\"alert\"><i>$0</i></font>");
		result = result.replaceAll("\n", "\n<br/>");
		return result+"<br/>";
	}
}
