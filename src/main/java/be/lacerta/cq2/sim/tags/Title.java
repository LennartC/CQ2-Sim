package be.lacerta.cq2.sim.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Title extends TagSupport {
	String title;
	String href;
	String reftext;
	String rel;
	String subtitle;
	
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.write("<table width=\"100%\">");
			out.write("<tr>");
			out.write("    <td class=\"titleline\" width=\"100%\"><font class=\"head\" size=\"-1\">"+title+"</font>");
			if (href!=null && reftext!=null) {
				out.write(" (<a href=\""+href+"\" ");
				if (rel!=null) out.write("rel=\""+rel+"\" ");
				out.write(">"+reftext+"</a>)");
			}
			out.write("</td>");
			out.write("</tr>");
			if (subtitle!=null) {
				out.write("<tr>");
				out.write("    <td class=\"titleline\" width=\"100%\"><font class=\"head\">"+subtitle+"</font></td>");
				out.write("</tr>");
			}
			out.write("</table>");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new JspException("IOException while writing data to page" + ioe.getMessage());
		}
		return SKIP_BODY;
	}

	public int doEndTag() {
		href=null;
		reftext=null;
		rel=null;
		subtitle=null;
		return EVAL_PAGE;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getReftext() {
		return reftext;
	}

	public void setReftext(String reftext) {
		this.reftext = reftext;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}
	
	
	
}
