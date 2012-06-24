package be.lacerta.cq2.orbparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import be.lacerta.cq2.objects.Gem;
import be.lacerta.cq2.objects.Orb;

public class BeamSearchTree2 implements Tree {

	//private boolean DEBUG = false;
	private List<Orb> allOrbs;
	private HashMap<String,List<Gem>> gems;
	private List<OrbNode> openNodes = new ArrayList<OrbNode>();
	private SortedSet<OrbNode> tempNodes = new TreeSet<OrbNode>();
	private HashSet<OrbNode> closedNodes = new HashSet<OrbNode>();
	private List<OrbNode> endNodes = new ArrayList<OrbNode>();
	private HashSet<Set<Integer>> calcedTrees;
	private int totalnodes = 0;
	private int bmax=10;
	private int factor=750;
	private boolean fullOnly;
	private OrbHeuristicValue heuristic;
	
	private boolean alluses = false;

	public BeamSearchTree2(List<Orb> orbs, HashMap<String,List<Gem>> gems, OrbHeuristicValue heuristic, boolean fullOnly) {
		this.allOrbs = orbs;
		this.gems = gems;
		this.heuristic = heuristic;
		this.fullOnly = fullOnly;
		closedNodes = new HashSet<OrbNode>();
		calcedTrees = new HashSet<Set<Integer>>();
		//if (DEBUG) System.out.println("Created BreadthFirstSearch with "+allOrbs.size()+" orbs");
	}


	public OrbNode search() {
		// if (DEBUG) System.out.println("Searching for "+allOrbs.size()+" orbs");
		List<OrbNode> nodes = new ArrayList<OrbNode>();
		for (int i=0; i<allOrbs.size(); i++) {
			nodes.add(new OrbNode(i,gems,allOrbs,heuristic));
		}
		addNodesToOpenNodes(nodes);
		//if (DEBUG) System.out.println("nodes open: "+openNodes.size());
		OrbNode n = selectNode();
		while(n != null) {
			//System.gc();
			//if (DEBUG) System.out.print("Getting leafs for orb "+n.getId()+"... ");
			List<OrbNode> leafs = n.getLeafs(fullOnly, allOrbs, gems);
			//if (DEBUG) System.out.println(leafs.size()+" found");
			if (leafs.size() == 0) {
				endNodes.add(n);
			} else {
				addNodesToOpenNodes(leafs);
			}
			n = selectNode();
		}
		//if (DEBUG) System.out.println("nodes open: "+openNodes.size());
		return findBestEndNode();
	}

	/*
	 * TODO: don't put end nodes in list, keep one node .. if new end node 
	 * check if it's better. If better update endnode.
	 */
	private OrbNode findBestEndNode() {

		OrbNode n = null;
		double val=-1.0;
		for (OrbNode in : endNodes) {
			//if (DEBUG) System.out.println("finding best end node: "+in.getId());
			if (in.getHeuristic().getValue().doubleValue() > val) {
				val = in.getHeuristic().getValue().doubleValue();
				n = in;
			}
		}
		return n;
	}


	private void addNodesToOpenNodes(List<OrbNode> nodes) {

		for (OrbNode n : nodes) {
			if(newNode(n)) {
				//if (DEBUG) System.out.println("Adding node to open node: "+n.getId());
				tempNodes.add(n);
				totalnodes++;
			}    	
		}

	}

	private OrbNode selectNode() {
		OrbNode n = null;
		try {
			do {
				n = (OrbNode) openNodes.get(0);
				openNodes.remove(0);
			} while (!newNode(n));

			closedNodes.add(n);
			calcedTrees.add(n.getTreeSet());
			return n;
		}
		catch (IndexOutOfBoundsException e) {
			if (tempNodes.isEmpty()) return null;
			int i = 0;
			while (i < getBmax() && !tempNodes.isEmpty()) {
				n = (OrbNode) tempNodes.first();
				tempNodes.remove(n);
				if (newNode(n)) {
					openNodes.add(n);
					i++;
				}
			}
			//ignoredNodes.addAll(tempNodes);
			tempNodes = new TreeSet<OrbNode>();
			return selectNode();
		}
	}

	private int getBmax() {
		return (factor/totalnodes+bmax);
	}

	private boolean newNode(OrbNode n) {
		if (closedNodes.contains(n)) {
			return false;
		} else if (calcedTrees.contains(n.getTreeSet())) {
			return false;
		} else {
			return true;
		}
	}


	public int getTotalnodes() {
		return totalnodes;
	}


	public boolean isAlluses() {
		return alluses;
	}


	public void setAlluses(boolean alluses) {
		this.alluses = alluses;
	}



}

