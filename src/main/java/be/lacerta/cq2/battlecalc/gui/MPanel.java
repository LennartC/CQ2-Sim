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

import java.io.IOException;
import java.net.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MPanel extends JPanel {

  private ImageIcon imageIcon;
  private Image image = null;
  private Color bgcolor;

  public MPanel(URL imageURL, Color bgcolor) {
    imageIcon = new ImageIcon(imageURL);
    image = imageIcon.getImage(); { setOpaque(false); }
    this.bgcolor = bgcolor;
  }
  
  public MPanel(URL[] imageURLs, Color bgcolor) {
	try {
		int width = 0;
		int height = 0;
		BufferedImage[] images = new BufferedImage[imageURLs.length];
	  	for (int i = 0; i < imageURLs.length; i++) {
			images[i] = ImageIO.read(imageURLs[i]);
			width += images[i].getWidth();
			height = Math.max(height, images[i].getHeight());
	  	}
	  	BufferedImage total = new BufferedImage(width,height,images[0].getType());
	  	Graphics2D g = total.createGraphics();
	  	int w = 0;
	  	for (int i=0; i<images.length; i++) {
	  		g.drawImage(images[i], w, 0, null);
	  		w += images[i].getWidth();
	  	}
	  	g.dispose();
	  	image = total; { setOpaque(false); }
	  	
	} catch (IOException e) {
		e.printStackTrace();
	}
	this.bgcolor = bgcolor;
  }
  
  public MPanel() {
    super();
    this.bgcolor = super.getBackground();
  }

  public void paintComponent(Graphics g) {
    g.setColor(bgcolor);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    if (image != null) {
      g.drawImage(image, 0, 0, (this.getWidth()), (this.getHeight()), this);
    }
    super.paintComponent(g);
  }


}
