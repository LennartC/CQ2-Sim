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


public class About extends JWindow {

private Container container;
private JPanel panel;
private JLabel name, author, line1, line2, line3, site;

public About() {
   container = getContentPane();
   this.setSize(new Dimension(394,314));

   final ImageIcon imageIcon = new ImageIcon("img/about.png");
   panel = new JPanel() {
            Image image = imageIcon.getImage();
            {setOpaque(false);}
            public void paintComponent (Graphics g) {
                    g.setColor(new Color(124, 124, 124));
                    g.fillRect(0, 0, this.getWidth(), this.getHeight());
                    g.drawImage(image, 1, 1, (this.getWidth()-2), (this.getHeight()-2), this);
                    super.paintComponent(g);
            }
    };
    panel.setLayout(null);
    name = new JLabel("CQ2 BattleCalc v1.3");
    name.setFont(new java.awt.Font("Helvetica", Font.BOLD, 16));
    name.setBounds(new Rectangle(20, 130, 255, 28));
    author = new JLabel("by mortician <mortician@lacerta.be>");
    author.setFont(new java.awt.Font("SansSerif", 0, 12));
    author.setBounds(new Rectangle(20, 157, 255, 15));
    line1 = new JLabel("This program is distributed in the hope that it will be useful,");
    line2 = new JLabel("but WITHOUT ANY WARRANTY; without even the implied warranty of");
    line3 = new JLabel("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.");
    line1.setFont(new java.awt.Font("SansSerif", 0, 11));
    line2.setFont(new java.awt.Font("SansSerif", 0, 11));
    line3.setFont(new java.awt.Font("SansSerif", 0, 11));
    line1.setBounds(new Rectangle(20,165,400,80));
    line2.setBounds(new Rectangle(20,185,400,80));
    line3.setBounds(new Rectangle(20,205,400,80));
    site = new JLabel("www.lacerta.be");
    site.setFont(new java.awt.Font("SansSerif", 1, 11));
    site.setBounds(new Rectangle(20,230,400,80));
    panel.add(name, null);
    panel.add(author, null);
    panel.add(line1, null);
    panel.add(line2, null);
    panel.add(line3, null);
    panel.add(site, null);

    container.add(panel);

    this.addMouseListener(
            new MouseAdapter() {
                public void mouseClicked(MouseEvent e ) {
                    dispose();
                }
    });

}

}
