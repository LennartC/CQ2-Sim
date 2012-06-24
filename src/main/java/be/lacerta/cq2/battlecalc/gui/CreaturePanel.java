package be.lacerta.cq2.battlecalc.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.xml.sax.SAXException;

import be.lacerta.cq2.battlecalc.objects.Creature;
import be.lacerta.cq2.battlecalc.objects.Enchant;
import be.lacerta.cq2.battlecalc.objects.Item;
import be.lacerta.cq2.battlecalc.objects.Itherian;
import be.lacerta.cq2.battlecalc.util.CreatureLoader;
import be.lacerta.cq2.battlecalc.util.StringGridBagLayout;
import be.lacerta.cq2.battlecalc.util.StringUtils;

public class CreaturePanel extends MPanel {
  private JTextField critName;
  private JTextField damage;
  private JTextField health;
  private JTextField defence;
  private JTextField damageIth;
  private JTextField healthIth;
  private JTextField defenceIth;
  private JButton lookup;

  private JComboBox itemBox;
  private JComboBox curseBox;
  private JComboBox enchantBox;
  private JComboBox warEnchantTypeBox, warEnchantLevelBox;

  private CreaturePanel opponentsPanel = null;
  private String[] curses = {"           ", "Suffocation", "Metamorphosis"};
  private String[] enchants = {"           ","Ruby","Sapphire"};
  
  static Hashtable<String, Creature> critMap = null;

  public CreaturePanel(URL imageURL, Color bgcolor) {
    super(imageURL, bgcolor);
    setLayout(new StringGridBagLayout());
    add("gridx=0,gridy=0,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",new JLabel("Name: "));
    critName = new JTextField();
    add("gridx=1,gridy=0,gridwidth=2,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",critName);
    //JTextField tmpField = new JTextField();
    //add("gridx=2,gridy=0,fill=HORIZONTAL,insets=[1,0,0,0]",tmpField);
    lookup = new JButton("Get");
    JPanel tmpPanel = new JPanel();
    tmpPanel.add(lookup);
    tmpPanel.setOpaque(false);
    lookup.setPreferredSize(new java.awt.Dimension(55, 20));
    add("gridx=3,gridy=0,insets=[1,0,0,0]",tmpPanel);

    add("gridx=0,gridy=1,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",new JLabel("Damage: "));
    damage = new JTextField();
    add("gridx=1,gridy=1,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",damage);
    add("gridx=2,gridy=1,insets=[1,0,0,0]",new JLabel(" +(ith/item) "));
    damageIth = new JTextField();
    add("gridx=3,gridy=1,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",damageIth);
   
    add("gridx=0,gridy=2,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",new JLabel("Health: "));
    health = new JTextField();
    add("gridx=1,gridy=2,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",health);
    add("gridx=2,gridy=2,insets=[1,0,0,0]",new JLabel(" +(ith/item) "));
    healthIth = new JTextField();
    add("gridx=3,gridy=2,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",healthIth);

    add("gridx=0,gridy=3,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",new JLabel("Defence: "));
    defence = new JTextField();
    add("gridx=1,gridy=3,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",defence);
    add("gridx=2,gridy=3,insets=[1,0,0,0]",new JLabel(" +(ith/item) "));
    defenceIth = new JTextField();
    add("gridx=3,gridy=3,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",defenceIth);
   
    add("gridx=0,gridy=4,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",new JLabel("Curse: "));
    curseBox = new JComboBox(curses);
    add("gridx=1,gridy=4,gridwidth=2,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",curseBox);
    
    // warEnchantTypeBox, warEnchantLevelBox
    
    add("gridx=0,gridy=5,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",new JLabel("Enchant: "));
    warEnchantTypeBox = new JComboBox(enchants);
    add("gridx=1,gridy=5,gridwidth=2,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",warEnchantTypeBox);
    warEnchantLevelBox = createEnchantBox();
    add("gridx=3,gridy=5,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",warEnchantLevelBox);
    
    add("gridx=0,gridy=6,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",new JLabel("Item: "));
    itemBox = new JComboBox(Item.ITEMS);
    add("gridx=1,gridy=6,gridwidth=2,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",itemBox);
    enchantBox = new JComboBox(Item.ENCHANTS);
    add("gridx=3,gridy=6,fill=HORIZONTAL,weightx=1.0,insets=[1,0,0,0]",enchantBox);

    createHashMap();
    addListeners();
  }
  private JComboBox createEnchantBox() {
	  String[] items = new String[21];
	  items[0] = "";
	  for (int i=1; i<21; i++) {
		  items[i] = ""+(i);
	  }
	  return new JComboBox(items);
  }
  
  public String getCritName() {
    return critName.getText().trim();
  }
  public int getDamage() {
    return StringUtils.parseInt(damage.getText());
  }
  public int getHealth() {
    return StringUtils.parseInt(health.getText());
  }
  public int getDefence() {
    return StringUtils.parseInt(defence.getText());
  }
  public int getDamageIth() {
    return StringUtils.parseInt(damageIth.getText());
  }
  public int getHealthIth() {
    return StringUtils.parseInt(healthIth.getText());
  }
  public int getDefenceIth() {
    return StringUtils.parseInt(defenceIth.getText());
  }
  public String getSelectedItem() {
    return itemBox.getSelectedItem().toString();
  }
  public String getSelectedEnchant() {
    return enchantBox.getSelectedItem().toString();
  }
  public String getSelectedCurse() {
    return curseBox.getSelectedItem().toString();
  }
  public int getSelectedWarEnchantType() {
	  int t = 0;
	  String type = warEnchantTypeBox.getSelectedItem().toString();
	  
	  if (type.toUpperCase().equals("RUBY")) t=Enchant.RUBY;
	  else if (type.toUpperCase().equals("SAPPHIRE")) t=Enchant.SAPPHIRE;
	  
	  return t;
  }
  public int getSelectedWarEnchantLevel() {
	  int l = 0;
	  String level = warEnchantLevelBox.getSelectedItem().toString();
	  try {
		  l = Integer.parseInt(level);
	  } catch (NumberFormatException nfe) { }
	  
	  return l;
  }
  public void clear() {
    critName.setText("");
    damage.setText("");
    health.setText("");
    defence.setText("");
    damageIth.setText("");
    healthIth.setText("");
    defenceIth.setText("");
    itemBox.setSelectedIndex(0);
    curseBox.setSelectedIndex(0);
    enchantBox.setSelectedIndex(0);
  }

  public void setCreature(Creature crit, Creature opponent, Itherian ith) {
    critName.setText(crit.getName());
    damage.setText(""+Math.round(crit.getDamage()));
    health.setText(""+crit.getHealth());
    if (opponent != null) {
        defence.setText(""+Math.round(crit.getDef(opponent.getCreatureClass())));
    } else {
        defence.setText(""+Math.round(crit.getForestDef()));
    }
    if (crit.isMetad()) {
        curseBox.setSelectedItem("Metamorphosis");
    } else if (crit.isSuffocated()) {
        curseBox.setSelectedItem("Suffocation");
    } else if (crit.isDoppeled()) {
        curseBox.setSelectedItem("Doppelganger");
    }
    itemBox.setSelectedItem(crit.getItem().getName());
    if (crit.getItem().isImOf()) {
      enchantBox.setSelectedItem("ImOf");
    } else if (crit.getItem().isMiBe()) {
      enchantBox.setSelectedItem("MiBe");
    } else if (crit.getItem().isMaBe()) {
      enchantBox.setSelectedItem("MaBe");
    }
    
    if (ith != null) {
    	damageIth.setText(""+Math.round(ith.getDamage()));
    	healthIth.setText(""+Math.round(ith.getHealth()));
    	defenceIth.setText(""+Math.round(ith.getDefence()));
    }
  }

  public void addListeners() {
    lookup.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        lookup();
      }
    });

    critName.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
          lookup();
        }
      }
    });

    lookup.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
          lookup();
        }
      }
    });


  }

  private void lookup() {
    if(!critName.getText().trim().equals("")) {
      try {
        Creature crit = (Creature)critMap.get(critName.getText().trim().toUpperCase());
        damage.setText(""+new Double(crit.getDamage()).intValue());
        health.setText(""+new Double(crit.getHealth()).intValue());

        if (opponentsPanel != null && !opponentsPanel.getCritName().equals("")) {
            Creature crit2 = (Creature)critMap.get(opponentsPanel.getCritName().trim().toUpperCase());

            if (crit2.getCreatureClass().toUpperCase().equals("FOREST"))
                defence.setText(""+new Double(crit.getForestDef()).intValue());
            if (crit2.getCreatureClass().toUpperCase().equals("DEATH"))
                defence.setText(""+new Double(crit.getDeathDef()).intValue());
            if (crit2.getCreatureClass().toUpperCase().equals("AIR"))
                defence.setText(""+new Double(crit.getAirDef()).intValue());
            if (crit2.getCreatureClass().toUpperCase().equals("EARTH"))
                defence.setText(""+new Double(crit.getEarthDef()).intValue());
        } else defence.requestFocus();
      } catch (NullPointerException ex) {
        damage.requestFocus();
      }
    }
  }

  private void createHashMap() {
  	if (critMap == null) {
  	  try {
  	  	CreatureLoader critLoader = new CreatureLoader();
  	  	XmlErrorHandler errorHandler = new XmlErrorHandler();
  	  	critLoader.setErrorHandler(errorHandler);
  	  	try {
  	  		critMap = critLoader.getCreatures();
  	  	} catch (SAXException e) {
  	  		errorHandler.fatalError(e);
  	  	}
  	  } catch (IllegalArgumentException exc) {
        JOptionPane.showMessageDialog(null,
                "Unable to load creature data.  Creature look up will not work, please report this error."+
				"\n\nError message:\n"+exc.getMessage(),
                "IllegalArgumentException",JOptionPane.ERROR_MESSAGE);
        critMap = new Hashtable<String, Creature>();
  	  }
  	}
  }

  public void setOpponentsPanel(CreaturePanel panel) {
      opponentsPanel = panel;
  }
  
  public Hashtable<String, Creature> getCreatureMap() {
      return critMap;
  }
}
