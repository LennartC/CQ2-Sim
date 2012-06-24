package be.lacerta.cq2.sim.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import be.lacerta.cq2.utils.StringUtils;


public class AutoInput extends TagSupport {
	protected String id = null;
	protected String name = null;
	protected String value = null;
	protected String size = "25";
	protected String type = null;
	protected String extra = null;

	public int doStartTag() throws JspException {
		try {
			
			if (value==null) { 
				if (pageContext.getAttribute(name) != null && pageContext.getAttribute(name) instanceof String) {
					value=(String)pageContext.getAttribute(name);
				} else if (pageContext.getRequest().getParameter(name) != null) {
					value=pageContext.getRequest().getParameter(name);
				}
			}
			if (id==null) id=name;
			
			String input = "<input type=\"text\" class=\"input\" onkeyup=\"auto"+StringUtils.capitalize(type)+"Complete(event,this)\"";
			input += " name=\""+name+"\"";
			if (id != null) 	input += " id=\""+id+"\"";
			if (size != null) 	input += " size=\""+size+"\"";
			if (value != null) 	input += " value=\""+value+"\"";
			if (extra != null) 	input += " "+extra;

			input += "/>";
			
			JspWriter out = pageContext.getOut();
			out.print(input);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new JspException("IOException while writing data to page" + ioe.getMessage());
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		
		id = null;
		name = null;
		value = null;
		size = "25";
		type = null;
		extra = null;
		
		return EVAL_PAGE;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {		
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
