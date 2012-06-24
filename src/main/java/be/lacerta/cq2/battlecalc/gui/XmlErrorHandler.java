package be.lacerta.cq2.battlecalc.gui;

import javax.swing.JOptionPane;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author lennart
 */
public class XmlErrorHandler implements ErrorHandler {

	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
	 */
	public void warning(SAXParseException exception) throws SAXException {
		JOptionPane.showMessageDialog(null, 
				"Warning when loading creature xml:\n\n"+exception.getMessage(),
				"Creature data warning",
				JOptionPane.WARNING_MESSAGE);
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
	 */
	public void error(SAXParseException exception) throws SAXException {
		JOptionPane.showMessageDialog(null, 
				"Error when loading creature xml:\n\n"+exception.getMessage(),
				"Creature data error",
				JOptionPane.ERROR_MESSAGE);
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
	 */
	public void fatalError(SAXParseException exception) throws SAXException {
		JOptionPane.showMessageDialog(null, 
				"Fatal error when loading creature xml:\n\n"+exception.getMessage(),
				"Creature data fatal error",
				JOptionPane.ERROR_MESSAGE);
	}
	
	public void fatalError(SAXException exception) {
		JOptionPane.showMessageDialog(null, 
				"Fatal error when loading creature xml:\n\n"+exception.getMessage(),
				"Creature data fatal error",
				JOptionPane.ERROR_MESSAGE);
	}

}
