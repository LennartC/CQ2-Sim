/*
 * Copyright (c) 2005 Coopmans Lennart
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package be.lacerta.cq2.battlecalc.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import be.lacerta.cq2.battlecalc.*;
import be.lacerta.cq2.battlecalc.objects.Creature;
import be.lacerta.cq2.battlecalc.objects.Enchant;
import be.lacerta.cq2.battlecalc.objects.Item;
import be.lacerta.cq2.battlecalc.objects.Itherian;
import be.lacerta.cq2.battlecalc.util.BrowserLauncher;
import be.lacerta.cq2.battlecalc.util.CalcParser;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;

public class MainFrame extends JFrame {

  private Container container;

  private Color bgcolor = new Color(235,244,249);

  private CreaturePanel attackerPanel, defenderPanel;
  private CopyPastePanel cpPanel;
  private JTextArea battleArea;
  private JTabbedPane tabbedPane;
  private JButton fight, proCalc, update, clear1, clear2;

  private Fight myFight;
  private CpWindow myCpWindow;

  private Version version = new Version();

  private Creature attackingCrit, defendingCrit;
  private Itherian attackingIth, defendingIth;
  private Enchant attackingEnchant, defendingEnchant;

  private CalcParser calcParser = new CalcParser();

  public MainFrame() {
    super( "CQ2 Battle Calculator - Age of Resurgence" );

    container = getContentPane();
    container.setLayout( new BorderLayout() );

    container.add(createTopPane(670,185),BorderLayout.NORTH);
    container.add(createFightPanel(670,294),BorderLayout.CENTER);
    container.add(createStatusPanel(670,21),BorderLayout.SOUTH);

    addListeners();


    setIconImage( (new ImageIcon(MainFrame.class.getResource("img/icon.jpg"))).getImage());
    setSize( 640, 470 );
    setVisible( true );
  }

  private void addListeners() {
    addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e ) {
                        if (e.getButton() == 3) pasteCrits();
                    }
    });

    fight.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e ) {
               checkCopyPasteTab();
            	
               if (getCreatures()) {
            	   attackingCrit.setIth(attackingIth);
            	   defendingCrit.setIth(defendingIth);
                   myFight = new Fight(attackingCrit, attackingEnchant,
                                       defendingCrit, defendingEnchant);
                   myFight.calc(1);
                   battleArea.setText(myFight.getFight());
                }
            }
        });

   proCalc.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e ) {
            	checkCopyPasteTab();
            	
               if (getCreatures()) {
            	   attackingCrit.setIth(attackingIth);
            	   defendingCrit.setIth(defendingIth);
                  myFight = new Fight(attackingCrit, attackingEnchant,
                                      defendingCrit, defendingEnchant);
                  myFight.calc(10000);
                  battleArea.setText("Based on 10000 calculations:\n"+
                                   "\nAttacker wins: "+myFight.getWin()+ "%"+
                                   "\nDefender wins: "+myFight.getLost()+ "%"+
                                   "\nBoth creatures die: "+myFight.getDip()+ "%");
                 }
            }
        });

   clear1.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e ) {
              attackerPanel.clear();
              cpPanel.setAttackerText("");
            }
        });
   clear2.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e ) {
              defenderPanel.clear();
              cpPanel.setDefenderText("");
            }
        });
  }

  private boolean checkCopyPasteTab() {
	  if (tabbedPane.getSelectedIndex() == 1) {
		  cpPanel.parseInputs();
		  setCrits(cpPanel.getAttackingCrit(),cpPanel.getDefendingCrit(),cpPanel.getAttackingIth(),cpPanel.getDefendingIth());
		  tabbedPane.setSelectedIndex(0);
	  }
	  return false;
  }
  
  private JTabbedPane createTopPane(int width, int height) {
	tabbedPane = new JTabbedPane();
	tabbedPane.setSize(width,height);
	ImageIcon icon1 = new ImageIcon(MainFrame.class.getResource("img/Edit16.gif"));
	ImageIcon icon2 = new ImageIcon(MainFrame.class.getResource("img/Paste16.gif"));
	tabbedPane.addTab("Manual", icon1, createManualPanel(width,height));
	tabbedPane.addTab("Copy/Paste", icon2, createCopyPastePanel(width,height),"COMPACT MODE! (Experimental)");
	tabbedPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	
	return tabbedPane;  
  }
  private JPanel createCopyPastePanel(int width, int height) {
	  cpPanel = new CopyPastePanel(
			  new URL[]{MainFrame.class.getResource("img/img001.jpg"),MainFrame.class.getResource("img/img002.jpg")},
			  new Color(95, 159, 207),
			  attackerPanel.getCreatureMap() );
	  return cpPanel;
  }
  private JPanel createManualPanel(int width, int height) {
    JPanel panel = new JPanel();
    panel.setSize(width,height);
    panel.setLayout(new GridLayout(1,2));

    attackerPanel = new CreaturePanel(MainFrame.class.getResource("img/img001.jpg"),
                                                       new Color(95, 159, 207));
    attackerPanel.setBorder(BorderFactory.createTitledBorder(
           BorderFactory.createLineBorder(Color.black), "Attacking Crit"));
    defenderPanel = new CreaturePanel(MainFrame.class.getResource("img/img002.jpg"),
                                                       new Color(95, 159, 207));
    defenderPanel.setBorder(BorderFactory.createTitledBorder(
           BorderFactory.createLineBorder(Color.black), "Defending Crit"));

    attackerPanel.setOpponentsPanel(defenderPanel);
    defenderPanel.setOpponentsPanel(attackerPanel);

    panel.add(attackerPanel);
    panel.add(defenderPanel);
    return panel;
  }

  private JPanel createFightPanel(int width, int height) {
    JPanel panel = new JPanel();
    panel.setSize(width,height);
    panel.setBackground(bgcolor);
    panel.setLayout(new BorderLayout());
    panel.add(createButtonPanel(),BorderLayout.NORTH);


    battleArea = new JTextArea() {
      public void paintComponent(Graphics g) {
      g.setColor(bgcolor);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      ImageIcon imageIcon = new ImageIcon(MainFrame.class.getResource("img/img004.jpg"));
      Image image = imageIcon.getImage(); { setOpaque(false); }

      //int x = (this.getWidth()-imageIcon.getIconWidth())/2;
      //int y = (this.getHeight()-imageIcon.getIconHeight())/2;
      if (image != null) {
        g.drawImage(image, 0, this.getHeight()-imageIcon.getIconHeight(), this.getWidth(),  imageIcon.getIconHeight(), this);
      }
      super.paintComponent(g);
      }
    };

    battleArea.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            	calcBattleAreaInput();
            }
          }
        });
    //battleArea.setOpaque(false);
    JScrollPane battlePane = new  JScrollPane(battleArea);
    battlePane.setOpaque(false);
    panel.add(battlePane,BorderLayout.CENTER);

    return panel;
  }

  private JPanel createButtonPanel() {
    JPanel panel = new MPanel(MainFrame.class.getResource("img/img003.jpg"),
                                                       new Color(95, 159, 207));
    MPanel buttonPanelWest = new MPanel(MainFrame.class.getResource("img/img003.jpg"),
                                                       new Color(95, 159, 207));
    MPanel buttonPanelCenter = new MPanel(MainFrame.class.getResource("img/img003.jpg"),
                                                       new Color(95, 159, 207));
    MPanel buttonPanelEast = new MPanel(MainFrame.class.getResource("img/img003.jpg"),
                                                       new Color(95, 159, 207));
    clear1 = new JButton("clear");
    clear1.setOpaque(false);
    fight = new JButton("fight!");
    fight.setOpaque(false);
    proCalc = new JButton("Calc %");
    proCalc.setOpaque(false);
    clear2 = new JButton("clear");
    clear2.setOpaque(false);
    panel.setLayout(new BorderLayout());
    buttonPanelWest.setLayout(new FlowLayout());
    buttonPanelCenter.setLayout(new FlowLayout());
    buttonPanelEast.setLayout(new FlowLayout());
    clear1.setPreferredSize(new java.awt.Dimension(73, 20));
    fight.setPreferredSize(new java.awt.Dimension(73, 20));
    proCalc.setPreferredSize(new java.awt.Dimension(73, 20));
    clear2.setPreferredSize(new java.awt.Dimension(73, 20));
    buttonPanelWest.add(clear1);
    buttonPanelCenter.add(fight);
    buttonPanelCenter.add(proCalc);
    buttonPanelEast.add(clear2);
    panel.add(buttonPanelEast, BorderLayout.EAST);
    panel.add(buttonPanelCenter, BorderLayout.CENTER);
    panel.add(buttonPanelWest, BorderLayout.WEST);
    return panel;
  }

  private JPanel createStatusPanel(int width, int height) {
    JPanel panel = new MPanel(MainFrame.class.getResource("img/statusbg.jpg"),
                              new Color(95, 159, 207));
    panel.setSize(width,height);
    JLabel cpr = new JLabel("          CQ2 BattleCalc v"+version.getVersion()+" by mortician");
    cpr.setForeground(new Color(255, 255, 255));
    cpr.setFont(new java.awt.Font("Helvetica", Font.BOLD, 9));
    panel.setLayout(new BorderLayout());
    update = new JButton("cq2.lacerta.be");
    update.setOpaque(false);
    update.setFont(new java.awt.Font("Verdana", Font.BOLD, 9));
    update.setPreferredSize(new java.awt.Dimension(110, 19));
    update.setToolTipText("Check for new version...");
    update.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
            BrowserLauncher.openURL("http://cq2.lacerta.be");
        } catch (IOException ex) {
        }
      }
    });
    panel.add(cpr,BorderLayout.WEST);
    panel.add(update, BorderLayout.EAST);

    return panel;
  }

  private boolean getCreatures() {
  	Item attackingItem = new Item(attackerPanel.getSelectedItem(),
            attackerPanel.getSelectedEnchant());
  	if (!checkItemDependencies(attackingItem, "attacking")) { return false; }
    attackingCrit = new Creature(attackerPanel.getDamage(),
                                 attackerPanel.getHealth(),
                                 attackerPanel.getDefence(),
                                 attackingItem,
                                 attackerPanel.getSelectedCurse());
    attackingCrit.setName(attackerPanel.getCritName().equals("") ?
                          "creature" : attackerPanel.getCritName());
    attackingIth = new Itherian(attackerPanel.getDamageIth(),
                                attackerPanel.getHealthIth(),
                                attackerPanel.getDefenceIth());
    attackingEnchant = new Enchant(attackerPanel.getSelectedWarEnchantType(),
    							   attackerPanel.getSelectedWarEnchantLevel());

    Item defendingItem = new Item(defenderPanel.getSelectedItem(),
            defenderPanel.getSelectedEnchant());
    if (!checkItemDependencies(defendingItem, "defending")) { return false;	}
    defendingCrit = new Creature(defenderPanel.getDamage(),
                                 defenderPanel.getHealth(),
                                 defenderPanel.getDefence(),
                                 defendingItem,
                                 defenderPanel.getSelectedCurse());
    defendingCrit.setName(defenderPanel.getCritName().equals("") ?
                          "creature" : defenderPanel.getCritName());
    defendingIth = new Itherian(defenderPanel.getDamageIth(),
                                defenderPanel.getHealthIth(),
                                defenderPanel.getDefenceIth());
    defendingEnchant = new Enchant(defenderPanel.getSelectedWarEnchantType(),
    							   defenderPanel.getSelectedWarEnchantLevel());
    return true;
  }


private boolean checkItemDependencies(Item item, String string) {
  	if (item.needNumberOfSame()) {
  	    String nrSame = JOptionPane.showInputDialog(null,"The impact of the "+string+" creature's item depends\non the creatures that accompany that create.\n\n# same:");
  	    try {
  	    	item.setSameCrits(Integer.parseInt(nrSame));
  	    } catch (NumberFormatException e) {e.printStackTrace(); return false;}
  	}
	if (item.needNumberOfDiff()) {
  	    	String nrDiff = JOptionPane.showInputDialog(null,"The impact of the "+string+" creature's item depends\non the creatures that accompany that create.\n\n# different:");
  	    	try {
  	    		item.setDiffCrits(Integer.parseInt(nrDiff));
  	    	} catch (NumberFormatException e) {e.printStackTrace(); return false;}
	}
	return true;
}

private void pasteCrits() {
    myCpWindow = new CpWindow(this,attackerPanel.getCreatureMap());
    myCpWindow.setSize(415,260);
    myCpWindow.setVisible(true);
    addWindowFocusListener(
                new java.awt.event.WindowAdapter() {
                    public void windowGainedFocus(WindowEvent evt) {
                      myCpWindow.toFront();
                    }
                });
  }

  /**
   * Insert creatures details in the fields
   *
   * @param attackingCrit your creature's damage
   * @param defendingCrit your creature's health
   */
  public void setCrits(Creature attackingCrit, Creature defendingCrit, Itherian attackingIth, Itherian defendingIth) {
    attackerPanel.setCreature(attackingCrit, defendingCrit, attackingIth);
    defenderPanel.setCreature(defendingCrit, attackingCrit, defendingIth);
  }
  
  private void calcBattleAreaInput() {
  	String[] lines = battleArea.getText().split("\n");
  	if (lines.length >= 1) {
	  	try {
	  		BigDecimal value = BigDecimal.valueOf(calcParser.calc(lines[(lines.length-1)]));
	  		value = value.stripTrailingZeros();
	  		battleArea.append(value.toPlainString()+"\n");
	  	} catch (IllegalArgumentException e) {}
  	}
  }


}


