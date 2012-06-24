package be.lacerta.cq2.sim.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import be.lacerta.cq2.sim.hbn.Creature;
import be.lacerta.cq2.sim.hbn.Item;
import be.lacerta.cq2.sim.hbn.Reveal;
import be.lacerta.cq2.utils.StringUtils;

public class AutoCompleteJS implements Tag {
	public static String TYPE_CREATURE = "creature";
	public static String TYPE_ITEM = "item";
	public static String TYPE_AMULET = "amulet";
	public static String TYPE_POWERSPELL = "powerspell";
	public static String TYPE_REVEAL = "reveal";
	
	
	protected String type = null;

	private PageContext pageContext;
	private Tag parent;

	public AutoCompleteJS() {
		super();
	}

	public int doStartTag() throws JspException {
		try {
			if (type==null) type=TYPE_CREATURE;
			
			JspWriter out = pageContext.getOut();
			out.println("<script type=\"text/javascript\">");
			out.println("function auto"+StringUtils.capitalize(type)+"Complete (event,field) {");
			out.println("	var creatures=new Array();");

			try {

				int i = 0;
				
				if (type.equals(TYPE_CREATURE) || type.equals(TYPE_AMULET)) {
					List<Creature> cl = Creature.getCreatures("name");
					for (Creature cr : cl) {
						out.println("creatures[" + i++ + "] = \"" + cr.getName()
								+ "\";");
					}
				}
				if (type.equals(TYPE_ITEM) || type.equals(TYPE_AMULET)) {
					List<Item> cl = Item.getItems("name");
					for (Item cr : cl) {
						out.println("creatures[" + i++ + "] = \"" + cr.getName()
								+ "\";");
					}
				} 
				if (type.equals(TYPE_POWERSPELL)) {
					// TODO: add autocomplete powerspells
				} 
				
				if (type.equals(TYPE_REVEAL)) {
					List<Reveal> cl = Reveal.getReveals("name");
					for (Reveal rv : cl) {
						out.println("reveal[" + i++ + "] = \"" + rv.getName()
								+ "\";");
					}
				} 			

			} catch (NullPointerException npe) {
			} catch (ClassCastException cce) {
			}

			out.println("	var found = false;");
			out.println("	for ( var i=0; i<creatures.length; i++){");
			out.println("		if (creatures[i].toUpperCase().indexOf(field.value.toUpperCase()) == 0) {");
			out.println("			found=true; break;");
			out.println("		}");
			out.println("	}");
			out.println("	var cursorKeys =\"8;46;37;38;39;40;33;34;35;36;45;\";");
			out.println("	if (cursorKeys.indexOf(event.keyCode+\";\") == -1) {");
			out.println("		var oldValue = field.value;");
			out.println("		var newValue = found ? creatures[i] : oldValue;");
			out.println("		if (newValue != field.value) {");
			out.println("			if (field.createTextRange) {");
			out.println("				field.value = newValue;");
			out.println("				var rNew = field.createTextRange();");
			out.println("				rNew.moveStart('character', oldValue.length) ;");
			out.println("				rNew.select();");
			out.println("			} else if (field.setSelectionRange) {");
			out.println("				field.value = newValue;");
			out.println("        		field.setSelectionRange(oldValue.length, newValue.length);");
			out.println("			}");
			out.println("		}");
			out.println("	}");
			out.println("}");
			out.println("</script>");
		} catch (IOException ioe) {
			throw new JspException("Error: IOException while writing to client"
					+ ioe.getMessage());
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public void release() {
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public void setParent(Tag parent) {
		this.parent = parent;
	}

	public Tag getParent() {
		return parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
