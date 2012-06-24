package be.lacerta.cq2.battlecalc.util;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import be.lacerta.cq2.battlecalc.objects.Creature;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

public class CreatureLoader {
	private ErrorHandler errorHandler = null;
	private Hashtable<String, Creature>  creatures = null;
	private Creature creature = null;

    public Hashtable<String, Creature>  getCreatures() throws SAXException {
        //SAXParserFactory factory = SAXParserFactory.newInstance();
        URL xmlURL = CreatureLoader.class.getResource("xml/crits.xml");
        URL schemaURL = CreatureLoader.class.getResource("xml/crits.xsd");
            
        parse(xmlURL, schemaURL);
        return creatures;
    }
    
    public void setErrorHandler(ErrorHandler errorHandler) {
    	this.errorHandler = errorHandler;
    }
    
    private boolean parse(URL xmlURL, URL schemaURL) throws SAXException {
    	
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        try {
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
        
        } catch (IllegalArgumentException ex) {
          ex.printStackTrace();
          factory.setNamespaceAware(false);
          factory.setValidating(false);	
        }
        
        DocumentBuilder builder;
		try {
			
			builder = factory.newDocumentBuilder();
			builder.setEntityResolver(new MyResolver(schemaURL));
			builder.setErrorHandler(errorHandler);

			Document document = builder.parse(xmlURL.openStream());
        
			Node rootNode  = document.getFirstChild();
			parseNodes(rootNode.getChildNodes());
 
		} catch (ParserConfigurationException e) {
			if (errorHandler != null) errorHandler.fatalError(
					new SAXParseException(e.getClass().getName()+": "+e.getMessage(),null));
		} catch (SAXException e) {
			if (errorHandler != null) errorHandler.fatalError(
					new SAXParseException(e.getClass().getName()+": "+e.getMessage(),null));
		} catch (IOException e) {
			if (errorHandler != null) errorHandler.fatalError(
					new SAXParseException(e.getClass().getName()+": "+e.getMessage(),null));
		}
    	
    	return true;
    }

    
    private void parseNodes(NodeList nodes) {
    	String nodeName;
    	String nodeValue;
    	
    	creatures = new Hashtable<String, Creature> ();
    	
    	for (int i = 0; i < nodes.getLength(); i++) {
    	    if (nodes.item(i).getNodeName().equals("creature")) {
    		  creature = new Creature();
    		  NodeList subnodes = nodes.item(i).getChildNodes();
    		  for (int j = 0; j < subnodes.getLength(); j++) {
    		  	nodeName = subnodes.item(j).getNodeName();
    		  	nodeValue = subnodes.item(j).getNodeValue();
    	        
    		  	if (nodeName.equals("name"))		creature.setName(nodeValue);
    	        else if (nodeName.equals("class"))	creature.setCreatureClass(nodeValue);
    	        else if (nodeName.equals("race"))	creature.setRace(nodeValue);
    	        else if (nodeName.equals("damage"))	creature.setDamage(nodeValue,1,20);
    	        else if (nodeName.equals("health"))	creature.setHealth(nodeValue,1,20);
    	        else if (nodeName.equals("skill"))	creature.setSkill(nodeValue);
    	        else if (nodeName.equals("FD"))		creature.setForestDef(nodeValue);
    	        else if (nodeName.equals("DD"))		creature.setDeathDef(nodeValue);
    	        else if (nodeName.equals("AD"))		creature.setAirDef(nodeValue);
    	        else if (nodeName.equals("ED"))		creature.setEarthDef(nodeValue);
    		  }
    		  
    		  creatures.put(creature.getName().toUpperCase(),creature.clone());
    	    }
    	}
    }
    
    
    class MyResolver implements EntityResolver {
    	URL schemaURL;
    	
    	public MyResolver (URL schemaURL) {
    		this.schemaURL = schemaURL;
    	}
    	
    	public InputSource resolveEntity (String publicId, String systemId) throws SAXException{ 
    	   	try {
				return new InputSource(schemaURL.openStream());
			} catch (IOException e) {
				if (errorHandler != null) errorHandler.error(
						new SAXParseException(e.getClass().getName()+": "+e.getMessage(),null));
			}
			return null;
    	}
    }

    
}
