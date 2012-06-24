package be.lacerta.cq2.sim;

import java.util.Date;
import java.util.List;

import be.lacerta.cq2.sim.hbn.Mage;
import be.lacerta.cq2.sim.hbn.Support;
import be.lacerta.cq2.sim.hbn.User;
import be.lacerta.cq2.sim.hbn.UserImage;
import be.lacerta.cq2.utils.PageParser;
import be.lacerta.cq2.utils.SimConstants;

public class UserExtension extends AbstractSimExtension {

	public void run(String page) {
		if (page.equals("memberadd")) {
			doMemberAdd();
		} else if (page.equals("options")) {
			doOptions();
		} else if (page.equals("info")) {
			doInfo();
		} else if (page.equals("members")) {
			doMembers();
		}
	}

	private void doInfo() {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			User user = (User)User.get(User.class, id);
			request.setAttribute("info_user", user);
			setPath("/info.jsp");
		} catch (NumberFormatException nfe) {
			setPath("/main.jsp");
		}
	}
	
	private void doMembers() {
		String action = getString("action");

		if (post && action.equals("parsekd")) {
			PageParser.parseKingdom(getString("kdpage"));
		}
		
		List<User> users = User.getUserList();
		
		request.setAttribute("members_userlist", users);
		setPath("/members.jsp");
	}
	
	private void doMemberAdd() {
		if (user.hasAccess(SimConstants.RIGHTS_USERADMIN)) {
			
			request.setAttribute("memberadd_class", "");
			request.setAttribute("memberadd_kingdom", "");
			request.setAttribute("memberadd_username", "");
			request.setAttribute("memberadd_cq2name", "");

			if (post) {
				boolean success = false;
				if (User.createFromDatabase(request.getParameter("username")) == null) {
					User u = new User();
					u.setCq2class(request.getParameter("class"));
					if (request.getParameter("cq2name") != null)
						u.setMage(Mage.getOrCreateMage(request.getParameter("cq2name")));
					u.setKingdom(request.getParameter("kingdom"));
					u.setUsername(request.getParameter("username"));
					u.setPlainPassword(request.getParameter("password"));
					u.setPassExpired(true);
					u.setUlvl(1);
					if (u.save()) {
						success = true;
					}
				}
				
				if (success) {
					request.setAttribute("memberadd_added", "success");
				} else {
					request.setAttribute("memberadd_added", "failed");
					request.setAttribute("memberadd_class", request.getParameter("class"));
					request.setAttribute("memberadd_cq2name", request.getParameter("cq2name"));
					request.setAttribute("memberadd_kingdom", request.getParameter("kingdom"));
					request.setAttribute("memberadd_username", request.getParameter("username"));
				}
			}
			setPath("/memberadd.jsp");
		} else {
			setPath("/main.jsp");
		}
	}

	private void doOptions() {
		User u = null;
		if (request.getParameter("id") != null 
				&& user.hasAccess(SimConstants.RIGHTS_USERADMIN)
				&& Integer.parseInt(request.getParameter("id")) != user.getId()) {
			u = (User)User.get(User.class, getInt("id"));
			if (user.getUlvl()<u.getUlvl()) {
				setPath("/gfas.jsp");
				return;
			}
		} else {
			user.refresh();
			u = user;
		}
		
		if (post) {
			if (getString("action").equals("changepassword")) {
				if (!request.getParameter("newpassword").equals("")) {
					if (request.getParameter("newpassword").equals(request.getParameter("newpassword2"))) {
						u.setPlainPassword(request.getParameter("newpassword"));
						u.setPassExpired(false);
						u.update();
					} else {
						request.setAttribute("message", "You haven't entered the same new password 2 times.");
					}
				}
			} else if (getString("action").equals("changegeneral")) {
				u.setKingdom(getString("kingdom"));
				u.setLevel(getInt("level"));
				u.setImagepac(getString("imagepack"));
				u.setCq2class(getString("class"));
				if (request.getParameter("cq2name") != null)
					u.setMage(Mage.getOrCreateMage(getString("cq2name")));
				u.setEmail(getString("email"));
				u.setPhone(getString("phone"));
				u.setQauth(getString("qauth"));
				u.setForestSkill(getInt("forestSkill"));
				u.setAirSkill(getInt("airSkill"));
				u.setDeathSkill(getInt("deathSkill"));
				u.setEarthSkill(getInt("earthSkill"));
				u.setBirthday(getDate("birthday"));
				u.setLocation(getString("location"));
				if (request.getParameter("disabled") != null) {
					u.setDisabled(true);
				}
				if ((user.hasAccess(SimConstants.RIGHTS_SUPERADMIN) &&
						!u.hasAccess(SimConstants.RIGHTS_SUPERADMIN) &&
						user.getUlvl() > u.getUlvl()) || user.getId() == 1) {
					long ulvl = 1;
					if (request.getParameter("zodonly") != null)
						ulvl *= SimConstants.RIGHTS_ZODONLY;
					if (request.getParameter("pubmod") != null)
						ulvl *= SimConstants.RIGHTS_PUBMOD;
					if (request.getParameter("admin") != null)
						ulvl *= SimConstants.RIGHTS_USERADMIN;
					if (request.getParameter("support") != null)
						ulvl *= SimConstants.RIGHTS_SUPPORTADMIN;
					if (request.getParameter("nextage") != null)
						ulvl *= SimConstants.RIGHTS_NEXT_AGE;
					if (request.getParameter("tek") != null)
						ulvl *= SimConstants.RIGHTS_TEK;
					if (request.getParameter("superadmin") != null 
							&& user.getId() == 1) {
						ulvl = SimConstants.RIGHTS_SUPERADMIN;
					}
					u.setUlvl(ulvl);
				}
				u.getMage().saveOrUpdate();
				u.update();
			} else if (getString("action").equals("fromcharpage")) {
				String charpage = getString("charpage");
				PageParser.parseCharacterPage(charpage, u);
			} else if (getString("action").equals("skills")) {
				if (u.getMage() != null && !u.getMage().equals("")) {
					SkillsExtension.setSkills(u.getMage().getName(), getString("skills"));
				}
			} else if (getString("action").equals("updateimage")) {
				u.refresh();
				UserImage i = u.getUserImage();
				
				if (i==null) {
					i = new UserImage(u,getString("imagetext"));
					u.setUserImage(i);
					i.save();
				}
				else {
					i.setText(getString("imagetext"));
					i.update();
				}
				
			}
		} else {
			if (u.isPassExpired()) {
				request.setAttribute("message", "Your password is expired. Please set a new password!");
			}
		}
		request.setAttribute("options_user", u);

		setPath("/options.jsp");
	}

}
