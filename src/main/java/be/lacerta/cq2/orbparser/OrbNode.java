package be.lacerta.cq2.orbparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.lacerta.cq2.objects.Gem;
import be.lacerta.cq2.objects.Orb;


public class OrbNode implements Comparable<OrbNode> {
	OrbNode parent;
	HashMap<String, List<Gem>> unusedGems;
	boolean complete;
	int id;

	private Orb orb;

	OrbHeuristicValue heuristic;
	
	
	HashSet<Integer> treeCache = null;

	public OrbNode(int id, HashMap<String, List<Gem>> gems, List<Orb> allOrbs, OrbHeuristicValue heuristic) {
		this(id, gems, null, allOrbs, heuristic);
	}


	@SuppressWarnings("unchecked")
	public OrbNode(int id, HashMap<String, List<Gem>> gems, OrbNode parent,
			List<Orb> allOrbs, OrbHeuristicValue heuristic) {
		this.id = id;
		this.parent = parent;
		this.heuristic = heuristic;
		unusedGems = (HashMap<String, List<Gem>>) gems.clone();
		complete = true;
		treeCache = parent != null ? 
				(HashSet<Integer>) parent.getTreeCache().clone() : 
				new HashSet<Integer>();
		treeCache.add(id);

		orb = allOrbs.get(id);
		for (String sign : orb.getGemsigns()) {
			List<Gem> gemList = unusedGems.get(sign);
			if (gemList != null && gemList.size() > 0) {
				List<Gem> newGemList = new ArrayList<Gem>();
				newGemList.addAll(gemList);
				orb.setGem(newGemList.get(0));
				newGemList.remove(0);
				unusedGems.put(sign,newGemList);
			} else {
				complete = false;
			}
		}
		
		heuristic.setOrb(orb);
	}


	public List<OrbNode> getLeafs(boolean complete, List<Orb> allOrbs,
			HashMap<String, List<Gem>> gems) {
		List<OrbNode> leafs = new ArrayList<OrbNode>();

		for (int i = 0; i < allOrbs.size(); i++) {

			if (!inTree(i)) {
				OrbNode n = new OrbNode(i, this.unusedGems, this, allOrbs, heuristic.createChild());
				if (!complete || n.isComplete()) {
					leafs.add(n);
				}
			}
		}

		return leafs;
	}

	private boolean inTree(int id) {
		if (id==this.id) return true;
		if (treeCache != null) return treeCache.contains(id);
		OrbNode parent = this.parent;
		while (parent != null) {
			if (id == parent.getId())
				return true;
			parent = parent.getParent();
		}
		return false;
	}

	/**
	 * get the parent node of this node
	 * @return Node
	 */
	public OrbNode getParent() {
		return parent;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OrbNode))
			return false;
		OrbNode n = (OrbNode) other;
		return (n.getTreeSet().equals(this.getTreeSet()));
	}

	public Set<Integer> getTreeSet() {
		if (treeCache!=null) return treeCache;
		
		Set<Integer> s = new HashSet<Integer>();
		s.add(id);
		OrbNode parent = this.parent;
		while (parent != null) {
			s.add(parent.getId());
			parent = parent.getParent();
		}
		return s;
	}

	public int hashCode() {
		return this.getTreeSet().hashCode();
	}

	public HashMap<String, List<Gem>> getUnusedGems() {
		return unusedGems;
	}

	public void setUnusedGems(HashMap<String, List<Gem>> unusedGems) {
		this.unusedGems = unusedGems;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}


	public HashSet<Integer> getTreeCache() {
		return treeCache;
	}

	public OrbHeuristicValue getHeuristic() {
		return heuristic;
	}
	
	public int compareTo(OrbNode n) throws ClassCastException {
		if (n==null) return -1;
		Double nNr = n.getHeuristic()==null?0.0:n.getHeuristic().getValue().doubleValue();
		Double tNr = this.getHeuristic()==null?0.0:this.getHeuristic().getValue().doubleValue();
		return new Double((nNr-tNr)*10000).intValue();
	}


	public void setOrb(Orb orb) {
		this.orb = orb;
	}


	public Orb getOrb() {
		return orb;
	}
}
