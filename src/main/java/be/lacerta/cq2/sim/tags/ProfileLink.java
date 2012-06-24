package be.lacerta.cq2.sim.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import be.lacerta.cq2.utils.CQ2Functions;

public class ProfileLink extends TagSupport {
	String mage;
	String kingdom;
	String link = "javascript:void(0);";
	String cssClass;
	String text;
	
	
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			if (mage!=null) {
				if (text==null) text=mage;
				out.print("<a href=\""+link+"\" ");
				if (cssClass!=null) out.print("class=\""+cssClass+"\" ");
				out.print("rel=\"cluetip\" title=\"#<a href='"+CQ2Functions.URL+"index.php?page=playerinfo&action=viewinfo&name="+mage+"' target='_blank'>CQ2</a> | <a href='?page=mage&mage="+mage+"'>Sim</a>\">"+text+"</a>");
			} else
			if (kingdom!=null) {
				if (text==null) text=kingdom;
				out.print("<a href=\""+link+"\" ");
				if (cssClass!=null) out.print("class=\""+cssClass+"\" ");
				out.print("rel=\"cluetip\" title=\"#<a href='"+CQ2Functions.URL+"index.php?page=kingdominfo&action=viewinfo&name="+kingdom+"' target='_blank'>CQ2</a> | <a href='?page=kingdom&kingdom="+kingdom+"'>Sim</a>\">"+text+"</a>");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new JspException("IOException while writing data to page" + ioe.getMessage());
		}
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		this.mage = null;
		this.kingdom = null;
		this.cssClass = null;
		this.text = null;
		this.link = "javascript:void(0);";
		return EVAL_PAGE;
	}
	
	public String getMage() {
		return mage;
	}

	public void setMage(String mage) {
		this.mage = mage;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
