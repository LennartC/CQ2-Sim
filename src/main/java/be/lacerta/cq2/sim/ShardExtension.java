package be.lacerta.cq2.sim;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.lacerta.cq2.sim.hbn.Mage;
import be.lacerta.cq2.sim.hbn.ShardDonation;

public class ShardExtension extends AbstractSimExtension {

	public void run(String page) {
		request.setAttribute("shard_action", request.getParameter("action"));
		request.setAttribute("shard_donation", getString("donation"));
		
		if (post) {
			if (getString("action").equals("add")) {
				
				String[] split = getString("donation").split("\n");
				String mage = "";
				String time = "";
				int shards = 0;
				for (String s : split) {
					if (s.trim().matches(".*\\S+ donated the following things to you.*")) {
						mage = s.trim().split(" ")[0];
					} else if (s.trim().matches(".*General: donation from \\S+ .*@.*")) {
						Pattern timePattern = Pattern.compile(".*General: donation from \\S+ .*(\\d{4}-\\d{2}-\\d{2} @ \\d{2}:\\d{2}).*");
						Matcher match = timePattern.matcher(s);
						if (match.find()) {
							time = match.group(1);
						} else {
							time = s.trim().split(" ")[4]+" @ "+s.trim().split(" ")[7];
						}
					} else if (s.trim().matches(".*\\S+ Shard.*")) {
						ShardDonation sd = new ShardDonation();
						sd.setMage(Mage.getOrCreateMage(mage));
						try {
							sd.setTime(time);
						} catch (ParseException e) {
							e.printStackTrace();
							sd.setTime(new Date());
						}
						sd.setShard(s.trim());
						sd.setToUserId(user.getId());
						sd.save();
						shards++;
					} 
				}

				request.setAttribute("shard_message", shards+" shards added for "+mage);
			}
		}
		if (getString("action").equals("showdonations")) {
			request.setAttribute("shard_donations",ShardDonation.getShardDonations(100));
		} else {
			request.setAttribute("shard_shardspermage",ShardDonation.getShardsPerMage());
			request.setAttribute("shard_stock",ShardDonation.getStock());
		}
		
		setPath("/shards.jsp");
	}
	
}
