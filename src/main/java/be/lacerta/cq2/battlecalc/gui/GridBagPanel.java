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

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.net.URL;

public class GridBagPanel extends MPanel {

  private GridBagLayout layout;
  private GridBagConstraints constraints;

  public GridBagPanel() {
  super();
  layout = new GridBagLayout();
  constraints = new GridBagConstraints();
  super.setLayout(layout);
  constraints.fill = GridBagConstraints.BOTH;
}


  public GridBagPanel(URL imageURL, Color bgcolor) {
    super(imageURL, bgcolor);
    layout = new GridBagLayout();
    constraints = new GridBagConstraints();
    super.setLayout(layout);
    constraints.fill = GridBagConstraints.BOTH;
  }

  public void weight(int x, int y) {
    constraints.weightx = x; // mag mee vergroten horizontaal
    constraints.weighty = y; // mag niet mee vergroten vertikaal
  }

  public void add(Component component, int row, int column, int width, int height) {
    constraints.gridx = column;
    constraints.gridy = row;
    constraints.gridwidth = width;
    constraints.gridheight = height;

    layout.setConstraints(component, constraints);
    add(component);
  }

}
