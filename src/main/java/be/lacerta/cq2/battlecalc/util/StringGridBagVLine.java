// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   StringGridBagLine.java

package be.lacerta.cq2.battlecalc.util;

import java.awt.*;

// Referenced classes of package samples.accessory:
//            StringGridBagLine

class StringGridBagVLine extends StringGridBagLine
{

    public StringGridBagVLine()
    {
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Rectangle rectangle = getBounds();
        int i = rectangle.width / 2 - super.lineWidth / 2;
        if(i < 0)
            i = 0;
        int j = 0;
        int k = (j + rectangle.height) - 1;
        java.awt.Color color = g.getColor();
        g.setColor(super.lineColor);
        g.drawLine(i, j, i, k);
        g.setColor(color);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(super.lineWidth, 10);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(1, 3);
    }
}
