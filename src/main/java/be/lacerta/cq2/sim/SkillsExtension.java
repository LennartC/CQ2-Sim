package be.lacerta.cq2.sim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.lacerta.cq2.sim.hbn.Mage;
import be.lacerta.cq2.sim.hbn.MageSkill;
import be.lacerta.cq2.sim.hbn.Skill;
import be.lacerta.cq2.sim.hbn.User;

public class SkillsExtension extends AbstractSimExtension {

	public void run(String page) {
		if (post) {
			String action = getString("action");
			if (action.equals("search")) {
				if (!getString("skill").equals("")) {
					List<MageSkill> msList = MageSkill.loadBySkill(Skill.loadByText(getString("skill")));
					List<Mage> foundMages = new ArrayList<Mage	>();
					for (MageSkill ms : msList) foundMages.add(ms.getMage());
					request.setAttribute("skills_foundMages", foundMages);
					request.setAttribute("skills_resultTitle", "Mages found with skill: "+getString("skill"));
				}
			} else if (action.equals("add")) {
				if (!getString("mage").equals("")) {
					List<User> users = User.getUsersByMageName(getString("mage"));
					if (users != null && users.size() > 0) {
						request.setAttribute("message",getString("mage")+" is a member of our alliance. You are not allowed to update his/her skills.");
					} else {
						setSkills(getString("mage"),getString("skills"));
						request.setAttribute("message",getString("mage")+" updated.");
					}
				}
			}
		}
		
		setPath("/skills.jsp");
	}

	protected static void setSkills(String mage, String skills) {
		Set<Skill> oldSkills = MageSkill.getSkills(mage);
		Set<Skill> newSkills = new HashSet<Skill>();
		for (String strSkill : skills.split(",")) {
			try {
			newSkills.add((Skill)Skill.get(Skill.class, Integer.parseInt(strSkill)));
			} catch (NumberFormatException nfe) {}
		}
		
		// remove old skills not known anymore...
		for (Skill skill : oldSkills) {
			if (!newSkills.contains(skill)) {
				MageSkill.load(mage, skill).delete();
			}
		}
		
		// add new skills...
		for (Skill skill : newSkills) {
			if (!oldSkills.contains(skill)) {
				MageSkill ms = new MageSkill(Mage.getOrCreateMage(mage), skill);
				ms.saveOrUpdate();
			}
		}
	}
}
