package be.lacerta.cq2.sim.tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import be.lacerta.cq2.sim.hbn.Like;
import be.lacerta.cq2.sim.hbn.RaidResult;
import be.lacerta.cq2.sim.hbn.RaidResultNote;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.utils.StringUtils;

public class RaidReport extends TagSupport {

	protected String raid = null;
	protected String user = null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm");
	
	public int doStartTag() throws JspException {
		try {
			
			Enumeration<String> parameters = pageContext.getRequest().getParameterNames();
			String args = "";
			while (parameters.hasMoreElements()) {
				String parameter = parameters.nextElement();
				if (!parameter.equals("action") && !parameter.equals("id") && !parameter.equals("note") && !parameter.equals("text")) {
					if (!args.equals("")) args+= "&amp;";
					args += parameter+"="+pageContext.getRequest().getParameter(parameter);
				}
			}

			RaidResult raid = (RaidResult)RaidResult.get(RaidResult.class, Integer.parseInt(this.raid));
			User user = (User)User.get(User.class, Integer.parseInt(this.user));
			
			JspWriter out = pageContext.getOut();
			
			out.println("<div class=\"raidreport\">");
			
			// SHORT
			out.println("	<div class=\"rr_short\">");
			out.println("   <span class=\"rr_date\">"+StringUtils.formatDate(raid.getDate())+"</span>");
			out.println("		"+StringUtils.getUserLink(raid.getUser())+" <span class=\"rr_userlevel\">(Level "+raid.getUser().getLevel()+"&nbsp;"+raid.getUser().getCq2class()+" mage)</span>");
			
			if (raid.isIncoming()) {
				if (raid.getTotalpb() != null) {
					out.println("         	got raided by <a href=\"?page=mage&mage="+ raid.getMage() +"\">"+ raid.getMage() +"</a> and lost "+ raid.getTotalpb() +"% power balance.");
				} else if (raid.getTotalgem() != null && !raid.getTotalgem().equals("")){
					out.println("         	got raided by <a href=\"?page=mage&mage="+ raid.getMage() +"\">"+ raid.getMage() +"</a> and lost a "+ raid.getTotalgem() +" Gem.");
				} else if (raid.getTotalworkers() != null){
					out.println("         	got raided by <a href=\"?page=mage&mage="+ raid.getMage() +"\">"+ raid.getMage() +"</a> and lost "+ raid.getTotalworkers() +" workers.");
				} else {
					out.println("         	got raided by <a href=\"?page=mage&mage="+ raid.getMage() +"\">"+ raid.getMage() +"</a> and lost "+ raid.getTotalres() +" resources and "+ raid.getTotalpwr() +" power.");
				}
			} else {
				if (raid.getTotalpb() != null) {
					out.println("         	raided <a href=\"?page=mage&mage="+ raid.getMage() +"\">"+ raid.getMage() +"</a> and won "+ raid.getTotalpb() +"% power balance.");
				} else if (raid.getTotalgem() != null && !raid.getTotalgem().equals("")){
					out.println("         	raided <a href=\"?page=mage&mage="+ raid.getMage() +"\">"+ raid.getMage() +"</a> and won a "+ raid.getTotalgem() +" Gem.");
				} else if (raid.getTotalworkers() != null){
					out.println("         	raided <a href=\"?page=mage&mage="+ raid.getMage() +"\">"+ raid.getMage() +"</a> and killed "+ raid.getTotalworkers() +" workers.");
				} else {
					out.println("           raided <a href=\"?page=mage&mage="+ raid.getMage() +"\">"+ raid.getMage() +"</a> and won "+ raid.getTotalres() +" resources and "+ raid.getTotalpwr() +" power.");
				}
			};

			out.println("	</div>");
			
			// REPORT
			out.println("	<div class=\"rr_report\">");
			out.println("		<div class=\"rr_links\"><a href=\"#\" onclick=\"showreport('"+ raid.getId() +"','report"+ raid.getId() +"'); return false;\">report</a></div>");
			out.println("		<div style=\"display: none;\" id=\"report"+ raid.getId() +"\">");
			out.println("		</div>");
			out.println("	</div>");
			out.println("	");
			
			// COMMENTS
			out.println("	<div class=\"rr_comments\">");
			int size = 0;
			if (raid.getNotes() != null) size=raid.getNotes().size();
			
			out.println("		<div class=\"rr_links\"><a href=\"#\" onclick=\"showcommentform('commentfrm"+ raid.getId() +"'); return false;\">comments ("+size+")</a></div>");
			out.println("		<div style=\"display: none;\" id=\"commentfrm"+ raid.getId() +"\">");

			if (raid.getNotes() != null) {
				Set<RaidResultNote> rns = raid.getNotes();
				for (RaidResultNote rn : rns) {
			out.println("							<div class=\"rr_comment\">");
			out.println("							 <div class=\"rr_commenttitle\">"+ rn.getNote().getUser().getUsername() +" @ "+ rn.getNote().getDate() +"</div>");
			out.println("							 <div class=\"rr_commenttext\">");
			out.println("							 "+rn.getNote().getNote()+"");
			out.println("							 </div>");
			out.println("							</div>");
				}
			}
			out.println("			<form action=\"?"+args+"\" method=\"POST\">");
			out.println("			  <input type=\"hidden\" name=\"action\" value=\"addnote\"/>");
			out.println("			  <input type=\"hidden\" name=\"id\" value=\""+ raid.getId() +"\"/>");
			out.println("			  <textarea class=\"input\" name=\"note\" style=\"width: 50%\"></textarea><br/>");
			out.println("			  <input class=\"input\" type=\"submit\" value=\"Add Note\"/>(max 1024 chars)");
			out.println("			</form>");
			out.println("		</div>");
			out.println("	</div>");
			
			// LIKE
			out.println("	<div class=\"rr_likes\">");
			
			Like l = Like.load(user, raid.getId(), Like.TYPE_RAIDRESULT);
			if (l==null)
				out.println("		<div class=\"rr_links\"><a href=\"?"+args+"&amp;action=like&amp;id="+ raid.getId() +"\">like</a></div>");
			else
				out.println("		<div class=\"rr_links\"><a href=\"?"+args+"&amp;action=like&amp;id="+ raid.getId() +"\">unlike</a></div>");
			
			List<User> likes = Like.getLikes(raid.getId(), Like.TYPE_RAIDRESULT);
			if (likes != null && likes.size() > 0) {
				if (likes.size() == 1) {
					if (likes.contains(user)) {
						out.println("		<div class=\"rr_links\">You like this</div>");
					} else {
						out.println("		<div class=\"rr_links\">"+likes.get(0).getUsername()+" likes this</div>");
					}
				} else {
					if (likes.contains(user)) {
						out.println("		<div class=\"rr_links\">You and <a href=\"#\" onclick=\"showlikes('like"+ raid.getId() +"'); return false;\">"+(likes.size()-1)+" others</a> like this</div>");
					} else {
						out.println("		<div class=\"rr_links\"><a href=\"#\" onclick=\"showlikes('like"+ raid.getId() +"'); return false;\">"+likes.size()+" people</a> like this</div>");
					}
					out.println("		<div class=\"rr_like\" style=\"display: none;\" id=\"like"+ raid.getId() +"\">");
					out.println("		<img src=\"/zod/images/thumb.gif\" class=\"rr_like\" alt=\"likes\"/>");
					for (User u : likes) {
						out.println("<span class=\"rr_like\">"+StringUtils.getUserLink(u)+"</span>");
					}
					out.println("		</div>");
				}
			}
			
			out.println("	</div>");
			
			out.println("</div>");
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new JspException("IOException while writing data to page" + ioe.getMessage());
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		raid = null;
		
		return EVAL_PAGE;
	}

	
	
	public String getRaid() {
		return raid;
	}

	public void setRaid(String raid) {
		this.raid = raid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	

}
