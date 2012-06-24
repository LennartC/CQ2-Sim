package be.lacerta.cq2.sim.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import be.lacerta.cq2.sim.hbn.MageSkill;
import be.lacerta.cq2.sim.hbn.Skill;


public class SkillTree extends TagSupport {

	protected String name = null;
	protected String cq2class = null;
	protected String cq2user = null;

	public int doStartTag() throws JspException {
		try {
			
			JspWriter out = pageContext.getOut();
			
			out.println("<div id=\"skilltree_"+name+"\"></div>");
			out.println("<script>");
			out.println("  "+name+"=new dhtmlXTreeObject(\"skilltree_"+name+"\",\"100%\",\"100%\",0);");
			out.println("  "+name+".setImagePath(\"/zod/images/tree/\");");
			out.println("  "+name+".enableCheckBoxes(1);");
			out.println("  "+name+".enableTreeImages(0);");
			out.println("  "+name+".enableThreeStateCheckboxes(true);");
			out.println("  "+name+".loadXMLString('"+Skill.getXMLSkillTree(cq2user,cq2class).replaceAll("'","\\\\'").replaceAll("\n","")+"');");
			
			for (Skill skill : MageSkill.getSkills(cq2user)) {
				out.println("  "+name+".setCheck("+ skill.getId() +",true);");
				out.println("  "+name+".openItem("+ skill.getId() +",true);");
			}
			
			out.println("</script>");

		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new JspException("IOException while writing data to page" + ioe.getMessage());
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		name = null;
		cq2class = null;
		cq2user = null;
		
		return EVAL_PAGE;
	}

	
	public String getCq2class() {
		return cq2class;
	}

	public void setCq2class(String cq2class) {
		this.cq2class = cq2class;
	}

	public String getCq2user() {
		return cq2user;
	}

	public void setCq2user(String cq2user) {
		this.cq2user = cq2user;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
