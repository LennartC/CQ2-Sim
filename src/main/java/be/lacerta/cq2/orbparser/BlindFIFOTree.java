package be.lacerta.cq2.orbparser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import be.lacerta.cq2.objects.Gem;
import be.lacerta.cq2.objects.Orb;

public class BlindFIFOTree implements Tree {

  private boolean DEBUG = false;
  private List<Orb> allOrbs;
  private HashMap<String,List<Gem>> gems;
  private List<OrbNode> openNodes = new Vector<OrbNode>();
  private HashSet<OrbNode> closedNodes = new HashSet<OrbNode>();
  private List<OrbNode> endNodes = new Vector<OrbNode>();
  private int totalnodes = 0;

  public BlindFIFOTree(List<Orb> orbs, HashMap<String,List<Gem>> gems) {
	  this.allOrbs = orbs;
      this.gems = gems;
      closedNodes = new HashSet<OrbNode>();
      if (DEBUG) System.out.println("Created BreadthFirstSearch with "+allOrbs.size()+" orbs");
  }


  public OrbNode search() {
	  if (DEBUG) System.out.println("Searching for "+allOrbs.size()+" orbs");
    Vector<OrbNode> nodes = new Vector<OrbNode>();
    for (int i=0; i<allOrbs.size(); i++) {
    	//nodes.add(new OrbNode(i,gems,allOrbs));
    }
    
    addNodesToOpenNodes(nodes);
    if (DEBUG) System.out.println("nodes open: "+openNodes.size());
    OrbNode n = selectNode();
    while(n != null) {
      System.gc();
      if (DEBUG) System.out.print("Getting leafs for orb "+n.getId()+"... ");
      List<OrbNode> leafs = n.getLeafs(true, allOrbs, gems);
      if (DEBUG) System.out.println(leafs.size()+" found");
      if (leafs.size() == 0) {
    	  endNodes.add(n);
      } else {
    	  addNodesToOpenNodes(leafs);
      }
      n = selectNode();
    }
    if (DEBUG) System.out.println("nodes open: "+openNodes.size());
    return findBestEndNode();
  }

  
  private OrbNode findBestEndNode() {
	  
	  for (Iterator<OrbNode> i=endNodes.iterator(); i.hasNext();) {
		  System.out.println("===================================");
		  OrbNode n = i.next();
		 // int tp = n.getTreePower();
		  while (n!=null) {
			  System.out.println(n.getId()+": "+allOrbs.get(n.getId()).toString());
			  n = n.getParent();
		  }
		  //System.out.println("Total power: "+tp);
	  }
	  System.out.println("===================================");
	  
	  OrbNode n = null;
//	  int power=-1;
	  for (Iterator<OrbNode> i=endNodes.iterator(); i.hasNext();) {
		  OrbNode in = i.next();
		  if (DEBUG) System.out.println("finding best end node: "+in.getId());
//		  if (in.getTreePower() > power) {
//			  power = in.getTreePower();
//			  n = in;
//		  }
	  }
	  return n;
  }
  

  private void addNodesToOpenNodes(List<OrbNode> nodes) {
    OrbNode n;
    Iterator<OrbNode> itr = nodes.iterator();
    while(itr.hasNext()) {
      n = (OrbNode) itr.next();
      if(newNode(n)) {
    	if (DEBUG) System.out.println("Adding node to open node: "+n.getId());
        openNodes.add(n);
        totalnodes++;
      } //else {
    	//  if (DEBUG) System.out.println("Not added node to open node: "+n.getId());
      //}
    }
  }

  private OrbNode selectNode() {
		OrbNode n = null;

		do {
			if (openNodes.size() > 0) {
				n = (OrbNode) openNodes.get(0);
				openNodes.remove(0);
			} else {
				n = null;
				break;
			}
		} while (!newNode(n));

		if (n != null)	closedNodes.add(n);
		return n;
	}

  private boolean newNode(OrbNode n) {
    if (closedNodes.contains(n)) {
      return false;
    } else {
      return true;
    }
  }


public int getTotalnodes() {
	return totalnodes;
}



}

