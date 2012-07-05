package be.lacerta.cq2.utils;


import java.math.BigInteger;
import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Locale;


import be.lacerta.cq2.sim.hbn.User;


public class StringUtils {
	
	public static String convertTimeCount(long seconds) {
		String result = "";
		int i = 0;
		while (seconds >= 86400) {
			i++;
			seconds -= 86400;
		}
		if (i > 0) result += i+" days ";
		
		i=0;
		while (seconds >= 3600) {
			i++;
			seconds -= 3600;
		}
		if (i > 0) result += i+" hours ";
	
		i=0;
		while (seconds >= 60) {
			i++;
			seconds -= 60;
		}
		if (i > 0) result += i+" minutes ";
		result += seconds+" seconds";
		return result;
	}
	
	public static String encrypt(String s) {
		if (s == null)
			return null;
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
			return ""+(new BigInteger(1,m.digest()).toString(16));
		} catch (NoSuchAlgorithmException e) {
			return "ce114e4501d2f4e2dcea3e17b546f339";
		}
		
		
	}

	public static String getUserLink(User u) {
		if (u == null) return "";
		String link = "<a href=\"?page=info&id="+u.getId()+"\"";
		if (u.isOnline())  link += " class=\"top\"";
		else  link += " class=\"head\"";
		link += ">";

		link += u.getUsername();
		link += "</a>";
		return link;
	}
	
	public static String getProfileLinks(String mage) {
		if (mage == null) return "";
		
		//String link = "<a href=\""+CQ2Functions.URL+"index.php?page=playerinfo&action=viewinfo&name="+u.getMage()+"\"";
		//link += " class=\"head\"";
		//link += ">";
		//http://cq2.lacerta.be/zod/sim?page=mage&mage=naTas
		
		String link = "<sup>";
		link += "(<a href=\'"+CQ2Functions.URL+"index.php?page=playerinfo&action=viewinfo&name="+mage+"' target='_blank'>CQ2</a>";
		link += "|<a href=\'?page=mage&mage="+mage+"'>Sim</a>)";
		link += "</sup>";
		return link;
	}
	
	public static String getCQ2Link(User u) {
		if (u == null) return "";
		String link = "";
		//String link = "<a href=\""+CQ2Functions.URL+"index.php?page=playerinfo&action=viewinfo&name="+u.getMage()+"\"";
		//link += " class=\"head\"";
		//link += ">";
		
		link += "(";
		if(u.getMage().getName().length()>0) link += u.getMage();
		else link += u.getUsername();
		link += ", L";
		link += u.getLevel();
		link += ", ";
		link += u.getCq2class();
		link += ")";
		//link += "</a>";
		return link;
	}
	
	public static String stripHTML(String s) {
		if (s == null)
			return null;
		
		StringBuffer buffer = new StringBuffer(s);
		try {
			int TEXT = 0;
			int COMMENT = 1;
			int TAG = 2;
			int SCRIPT = 3;
			int type = TEXT;
			int start = 0;
			for (int i = 0; i < buffer.length(); i++) {
				char c = buffer.charAt(i);
				if (c == '<') {
					if (type == COMMENT || type == SCRIPT)
						continue;
					if (substringStartsWith(buffer, i, "<!--"))
						type = COMMENT;
					else if (substringStartsWith(buffer, i, "<script"))
						type = SCRIPT;
					else
						type = TAG;
					start = i;
					continue;
				}
				if (c == '>' && type != TEXT) {
					String text = buffer.substring(start + 1, i).trim() + " ";
					text = text.toLowerCase();
					if (type == COMMENT && !text.endsWith("-- "))
						continue;
					if (type == SCRIPT && !text.endsWith("/script "))
						continue;
					String replace = " ";
					if (text.startsWith("p ") || text.startsWith("br "))
						replace = "\n\n";

					if (!replace.equals(" ")) {
						buffer.replace(start, i + 1, replace);
						i = start - 1;
					} else {
						i = start + buffer.length();
					}
					type = TEXT;
					continue;
				}
				if (type == TEXT
						&& c == '&'
						&& (buffer.charAt(i + 1) == '#' || Character
								.isUnicodeIdentifierStart(buffer.charAt(i + 1)))) {
					for (int j = i + 1; j < buffer.length(); j++) {
						char cc = buffer.charAt(j);
						if (cc == ';') {
							String text = buffer.substring(i + 1, j);
							String replace = "";
							if (text.equals("nbsp"))
								replace = " ";
							if (text.equals("gt"))
								replace = ">";
							if (text.equals("lt"))
								replace = "<";
							if (text.equals("amp"))
								replace = "&";
							if (text.equals("auml"))
								replace = "ä";
							if (text.equals("ouml"))
								replace = "ö";
							if (text.equals("uuml"))
								replace = "ü";
							if (text.equals("Auml"))
								replace = "Ä";
							if (text.equals("Ouml"))
								replace = "Ö";
							if (text.equals("Uuml"))
								replace = "Ü";
							if (text.equals("szlig"))
								replace = "ß";
							if (text.equals("#10"))
								replace = "\n";
							if (text.equals("#13"))
								replace = "\r";
							if (text.equals("#9"))
								replace = "\t";
							buffer.replace(i, j + 1, replace);
							break;
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buffer.toString()
				.replaceAll("  ", " ")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	public static String convertBBCode(String s) {
		if (s == null) return null;
		s = s.replaceAll(">", "&gt;");
		s = s.replaceAll("<", "&lt;");
		StringBuffer buffer = new StringBuffer(s);
		
		HashMap<Tag, Tag> tags = new HashMap<Tag, Tag>();
		tags.put(new Tag("[b]","<b>"), new Tag("[/b]","</b>"));
		tags.put(new Tag("[i]","<i>"), new Tag("[/i]","</i>"));
		tags.put(new Tag("[u]","<u>"), new Tag("[/u]","</u>"));
		tags.put(new Tag("[pre]","<pre>"), new Tag("[/pre]","</pre>"));
		tags.put(new Tag("[strike]","<strike>"), new Tag("[/strike]","</strike>"));
		tags.put(new Tag("[center]","<center>"), new Tag("[/center]","</center>"));
		tags.put(new Tag("[red]","<font color=\"red\">"), new Tag("[/red]","</font>"));
		tags.put(new Tag("[blue]","<font color=\"blue\">"), new Tag("[/blue]","</font>"));
		tags.put(new Tag("[green]","<font color=\"#00FF00\">"), new Tag("[/green]","</font>"));
		tags.put(new Tag("[purple]","<font color=\"purple\">"), new Tag("[/purple]","</font>"));
		tags.put(new Tag("[sea]","<font color=\"#008080\">"), new Tag("[/sea]","</font>"));
		tags.put(new Tag("[cyan]","<font color=\"#00FFFF\">"), new Tag("[/cyan]","</font>"));
		tags.put(new Tag("[yellow]","<font color=\"#FF00FF\">"), new Tag("[/yellow]","</font>"));
		tags.put(new Tag("[quote]",
						 "<table width=\"90%\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td><hr size=\"1\" class=\"line\"></td></tr><tr><td><i>"),
				 new Tag("[/quote]",
						 "</i></td></tr><tr><td><hr size=\"1\" class=\"line\"></td></tr></table>"));

		for (Iterator<Tag> it = tags.keySet().iterator(); it.hasNext(); ) {
			Tag tag = it.next();
			int start=buffer.indexOf(tag.getOriginal(), 0);
			while (start >= 0) {
				int end=buffer.indexOf(tags.get(tag).getOriginal(), start+1);
				if (end>=0) {
					buffer.replace(start, 
							start + tag.getOriginal().length(),
							tag.getReplacer());
					end=buffer.indexOf(tags.get(tag).getOriginal(), start+1);
					buffer.replace(end, 
							end	+ tags.get(tag).getOriginal().length(),
							tags.get(tag).getReplacer());
				} else {
					buffer.replace(start, 
							start + tag.getOriginal().length(),
							"");
				}
				start=buffer.indexOf(tag.getOriginal(), start+1);
			}
			int end=buffer.indexOf(tags.get(tag).getOriginal(), 0);
			if (end >= 0) {
				buffer.replace(end, 
						end	+ tags.get(tag).getOriginal().length(),
						tags.get(tag).getReplacer());	
			}
		}
		
		s = buffer.toString().replaceAll("\n", "<br/>");
		
		HashMap<String, String> codes = new HashMap<String, String>();

		codes.put("\\[img\\](\\S*)\\[\\/img\\]", "<img src=\"$1\" />");
		codes.put("\\[url\\](\\S*)\\[\\/url\\]", "<a href=\"$1\" />$1</a>");
		codes.put("\\[url=(\\S*)\\](\\S*)\\[\\/url\\]", "<a href=\"$1\" />$2</a>");

		for (Iterator<String> it = codes.keySet().iterator(); it.hasNext(); ) {
			String key = it.next();
			Pattern pat = Pattern.compile(key,Pattern.DOTALL);
			Matcher mat = pat.matcher(s);
			while (mat.find()) {
				String replacement = codes.get(key);
				for (int i=1; i<=mat.groupCount();i++) {
					replacement.replaceAll("$"+i, mat.group(i));
				}
				s = s.replaceFirst(key, replacement);
			}
		}

		return s;
	}
	
	private static boolean substringStartsWith(StringBuffer buffer, int index,
			String str) {
		if (index + str.length() > buffer.length())
			return false;
		String s = buffer.substring(index, index + str.length()).toLowerCase();
		return s.equals(str);
	}
    
    public static boolean isInteger(String s) {
    	if (s==null) return false;
    	try {
    		Integer.parseInt(s);
    		return true;
    	} catch (NumberFormatException nfe) {
    		return false;
    	}
    }
    
    public static int getLevenshtein(String s1, String s2) {
		int m[][];
		int cost;

		int n1 = s1.length();
		int n2 = s2.length();
		
		if (n1 == 0) return n2;
		if (n2 == 0) return n1;
		
		m = new int[n1 + 1][n2 + 1];

		for (int i = 0; i <= n1; i++) m[i][0] = i;
		for (int j = 0; j <= n2; j++) m[0][j] = j;

		for (int i = 1; i <= n1; i++) {
			char c1 = s1.charAt(i - 1);

			for (int j = 1; j <= n2; j++) {
				char c2 = s2.charAt(j - 1);
				
				if (c1 == c2) cost = 0;
				else cost = 1;

				m[i][j] = Math.min(m[i - 1][j] + 1, Math.min(m[i][j - 1] + 1,
						m[i - 1][j - 1] + cost));
			}
		}

		return m[n1][n2];
	}
    
    public static String capitalize(String input) {
        if (input.length() == 0) return input;

        char ch = input.charAt(0);

        if (Character.isUpperCase(ch)) return input;

        return String.valueOf(Character.toUpperCase(ch)) + input.substring(1);
    }
    
    public static void main (String[] args) {
    	
    	String test ="pim <pam> pom";
    	System.out.println(StringUtils.stripHTML(test));
    }
    
 
    public static String formatDate(Date date) {
    	SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy HH:mm:ss",Locale.ENGLISH);
    	return format.format(date);
    }
    
	public static int getDaysOld(Date date) {
		if(date==null) return Integer.MAX_VALUE;
		return (int)((System.currentTimeMillis()-date.getTime())/(1000*60*60*24));
	}
}
