// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   StringGridBagLine.java

package be.lacerta.cq2.battlecalc.util;

import java.awt.*;

// Referenced classes of package samples.accessory:
//            StringGridBagLine

class StringGridBagHLine extends StringGridBagLine
{

    public StringGridBagHLine()
    {
    }

    public void paint(Graphics g)
    {
        if(super.debug)
            System.out.println("StringGridBagHLine.paint(), bounds = " + getBounds());
        super.paint(g);
        Rectangle rectangle = getBounds();
        int i = rectangle.height / 2 - super.lineWidth / 2;
        if(i < 0)
            i = 0;
        int j = 0;
        int k = (j + rectangle.width) - 1;
        java.awt.Color color = g.getColor();
        g.setColor(super.lineColor);
        g.drawLine(j, i, k, i);
        g.setColor(color);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(10, super.lineWidth);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(3, 1);
    }
}
