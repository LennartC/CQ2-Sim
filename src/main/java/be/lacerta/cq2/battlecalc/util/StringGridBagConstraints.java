// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   StringGridBagConstraints.java

package be.lacerta.cq2.battlecalc.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.PrintWriter;

// Referenced classes of package samples.accessory:
//            StringInsets, Listable

public class StringGridBagConstraints extends GridBagConstraints
    implements Cloneable, Listable
{

    public StringGridBagConstraints()
    {
        reset();
    }

    public StringGridBagConstraints(GridBagConstraints gridbagconstraints)
    {
        copyFromOld(gridbagconstraints);
    }

    public StringGridBagConstraints(StringGridBagConstraints stringgridbagconstraints)
    {
        copyFromOld(stringgridbagconstraints);
    }

    public StringGridBagConstraints(StringGridBagConstraints stringgridbagconstraints, String s)
    {
        copyFromOld(stringgridbagconstraints);
        setValues(s);
    }

    public StringGridBagConstraints(String s)
    {
        this();
        setValues(s);
    }

    public String toString()
    {
        String s = getClass().getName();
        return s + "[" + paramString() + "]";
    }

    public String paramString()
    {
        return "gridx=" + super.gridx + ",gridy=" + super.gridy + ",gridwidth=" + super.gridwidth + ",gridheight=" + super.gridheight + ",weightx=" + super.weightx + ",weighty=" + super.weighty + ",anchor=" + convertAnchorToString(super.anchor) + ",fill=" + convertFillToString(super.fill) + ",ipadx=" + super.ipadx + ",ipady=" + super.ipady + ",insets=" + super.insets;
    }

    public String toParsableString()
    {
        String s = "";
        boolean flag = false;
        StringGridBagConstraints stringgridbagconstraints = new StringGridBagConstraints();
        if(super.gridx != ((GridBagConstraints) (stringgridbagconstraints)).gridx)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "gridx=" + super.gridx;
        }
        if(super.gridy != ((GridBagConstraints) (stringgridbagconstraints)).gridy)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "gridy=" + super.gridy;
        }
        if(super.gridwidth != ((GridBagConstraints) (stringgridbagconstraints)).gridwidth)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "gridwidth=" + super.gridwidth;
        }
        if(super.gridheight != ((GridBagConstraints) (stringgridbagconstraints)).gridheight)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "gridheight=" + super.gridheight;
        }
        if(super.weightx != ((GridBagConstraints) (stringgridbagconstraints)).weightx)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "weightx=" + super.weightx;
        }
        if(super.weighty != ((GridBagConstraints) (stringgridbagconstraints)).weighty)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "weighty=" + super.weighty;
        }
        if(super.anchor != ((GridBagConstraints) (stringgridbagconstraints)).anchor)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "anchor=" + convertAnchorToString(super.anchor);
        }
        if(super.fill != ((GridBagConstraints) (stringgridbagconstraints)).fill)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "fill=" + convertFillToString(super.fill);
        }
        if(super.ipadx != ((GridBagConstraints) (stringgridbagconstraints)).ipadx)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "ipadx=" + super.ipadx;
        }
        if(super.ipady != ((GridBagConstraints) (stringgridbagconstraints)).ipady)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + "ipady=" + super.ipady;
        }
        String s1 = null;
        if(super.insets != null && ((GridBagConstraints) (stringgridbagconstraints)).insets != null)
            s1 = "insets=[" + super.insets.top + "," + super.insets.right + "," + super.insets.bottom + "," + super.insets.left + "]";
        if(s1 != null)
        {
            if(flag)
                s = s + ",";
            flag = true;
            s = s + s1;
        }
        return s;
    }

    private String convertAnchorToString(int i)
    {
        String s = "" + i;
        switch(i)
        {
        case 10: // '\n'
            s = "CENTER";
            break;

        case 11: // '\013'
            s = "NORTH";
            break;

        case 12: // '\f'
            s = "NORTHEAST";
            break;

        case 13: // '\r'
            s = "EAST";
            break;

        case 14: // '\016'
            s = "SOUTHEAST";
            break;

        case 15: // '\017'
            s = "SOUTH";
            break;

        case 16: // '\020'
            s = "SOUTHWEST";
            break;

        case 17: // '\021'
            s = "WEST";
            break;

        case 18: // '\022'
            s = "NORTHWEST";
            break;
        }
        return s;
    }

    private String convertFillToString(int i)
    {
        String s = "" + i;
        switch(i)
        {
        case 0: // '\0'
            s = "NONE";
            break;

        case 1: // '\001'
            s = "BOTH";
            break;

        case 2: // '\002'
            s = "HORIZONTAL";
            break;

        case 3: // '\003'
            s = "VERTICAL";
            break;
        }
        return s;
    }

    private void copyFromOld(GridBagConstraints gridbagconstraints)
    {
        super.anchor = gridbagconstraints.anchor;
        super.fill = gridbagconstraints.fill;
        super.gridwidth = gridbagconstraints.gridwidth;
        super.gridheight = gridbagconstraints.gridheight;
        super.gridx = gridbagconstraints.gridx;
        super.gridy = gridbagconstraints.gridy;
        super.ipadx = gridbagconstraints.ipadx;
        super.ipady = gridbagconstraints.ipady;
        super.weightx = gridbagconstraints.weightx;
        super.weighty = gridbagconstraints.weighty;
        if(gridbagconstraints.insets == null)
            super.insets = null;
        else
            super.insets = (Insets)gridbagconstraints.insets.clone();
    }

    public Object clone()
    {
        StringGridBagConstraints stringgridbagconstraints = new StringGridBagConstraints(this);
        return stringgridbagconstraints;
    }

    public void reset()
    {
        super.anchor = 10;
        super.fill = 0;
        super.gridwidth = 1;
        super.gridheight = 1;
        super.gridx = -1;
        super.gridy = -1;
        super.ipadx = 0;
        super.ipady = 0;
        super.weightx = 0.001D;
        super.weighty = 0.001D;
        if(super.insets == null)
        {
            super.insets = new Insets(0, 0, 0, 0);
        } else
        {
            super.insets.top = 0;
            super.insets.left = 0;
            super.insets.bottom = 0;
            super.insets.right = 0;
        }
    }

    public void copyFrom(StringGridBagConstraints stringgridbagconstraints)
    {
        super.anchor = ((GridBagConstraints) (stringgridbagconstraints)).anchor;
        super.fill = ((GridBagConstraints) (stringgridbagconstraints)).fill;
        super.gridwidth = ((GridBagConstraints) (stringgridbagconstraints)).gridwidth;
        super.gridheight = ((GridBagConstraints) (stringgridbagconstraints)).gridheight;
        super.gridx = ((GridBagConstraints) (stringgridbagconstraints)).gridx;
        super.gridy = ((GridBagConstraints) (stringgridbagconstraints)).gridy;
        super.ipadx = ((GridBagConstraints) (stringgridbagconstraints)).ipadx;
        super.ipady = ((GridBagConstraints) (stringgridbagconstraints)).ipady;
        super.weightx = ((GridBagConstraints) (stringgridbagconstraints)).weightx;
        super.weighty = ((GridBagConstraints) (stringgridbagconstraints)).weighty;
        if(((GridBagConstraints) (stringgridbagconstraints)).insets == null)
            super.insets = null;
        else
        if(super.insets == null)
        {
            super.insets = (Insets)((GridBagConstraints) (stringgridbagconstraints)).insets.clone();
        } else
        {
            super.insets.top = ((GridBagConstraints) (stringgridbagconstraints)).insets.top;
            super.insets.left = ((GridBagConstraints) (stringgridbagconstraints)).insets.left;
            super.insets.bottom = ((GridBagConstraints) (stringgridbagconstraints)).insets.bottom;
            super.insets.right = ((GridBagConstraints) (stringgridbagconstraints)).insets.right;
        }
    }

    public void setValues(String s)
    {
        if(s == null)
            return;
        char ac[] = normalizeString(s);
        for(int i = 0; i < ac.length; i = parseOneParameter(ac, i));
    }

    private char[] normalizeString(String s)
    {
        boolean flag = false;
        int i = 0;
        char ac[] = s.toCharArray();
        boolean flag2 = false;
        boolean flag3 = false;
        for(int j = 0; j < ac.length; j++)
        {
            char c = ac[j];
            if(Character.isWhitespace(c))
            {
                flag = true;
                flag2 = true;
            } else
            {
                if(flag3 && flag2 && (isNameChar(c) || isValueChar(c)))
                    throw new IllegalArgumentException("StringGridBagConstraints: white space in name or value, after '" + s.substring(0, j - 1) + "'");
                flag3 = isNameChar(c) || isValueChar(c);
                ac[i++] = Character.toLowerCase(c);
                flag2 = false;
            }
        }

        if(flag)
        {
            char ac1[] = new char[i];
            System.arraycopy(ac, 0, ac1, 0, i);
            ac = ac1;
        }
        return ac;
    }

    private boolean isNameChar(char c)
    {
        return Character.isLetterOrDigit(c);
    }

    private boolean isValueChar(char c)
    {
        return Character.isLetterOrDigit(c) || c == '[' || c == ']';
    }

    protected int parseOneParameter(char ac[], int i)
    {
        int j = i;
        for(; i < ac.length && isNameChar(ac[i]); i++);
        int k = i - 1;
        if(j < 0 || k < 0 || j > k)
            return ac.length;
        String s = new String(ac, j, (k - j) + 1);
        if(i > ac.length || ac[i] != '=')
            throw new IllegalArgumentException("Could not find '=' after '" + s + "'");
        int l = ++i;
        if(s.equals("insets"))
        {
            i = findInsetsValueEnd(ac, i);
        } else
        {
            for(; i < ac.length && isValueChar(ac[i]); i++);
            i--;
        }
        int i1 = i;
        if(i1 >= ac.length || l > i1)
            throw new IllegalArgumentException("Could not find value for '" + s + "'");
        String s1 = new String(ac, l, (i1 - l) + 1);
        setOneParameter(s, s1);
        for(; i < ac.length && ac[i] != ','; i++);
        if(i < ac.length && ac[i] == ',')
            i++;
        return i;
    }

    protected void setOneParameter(String s, String s1)
    {
        if(s.equals("gridx"))
        {
            int i = -1;
            if(s1.equals("relative"))
                i = -1;
            else
                i = getIntegerValueFromString(s, s1);
            if(i != super.gridx)
                super.gridx = i;
        } else
        if(s.equals("gridy"))
        {
            int j = -1;
            if(s1.equals("relative"))
                j = -1;
            else
                j = getIntegerValueFromString(s, s1);
            if(j != super.gridy)
                super.gridy = j;
        } else
        if(s.equals("gridwidth"))
        {
            int k = 0;
            if(s1.equals("remainder"))
                k = 0;
            else
                k = getIntegerValueFromString(s, s1);
            if(k != super.gridwidth)
                super.gridwidth = k;
        } else
        if(s.equals("gridheight"))
        {
            int l = 0;
            if(s1.equals("remainder"))
                l = 0;
            else
                l = getIntegerValueFromString(s, s1);
            if(l != super.gridheight)
                super.gridheight = l;
        } else
        if(s.equals("weightx"))
        {
            double d = getDoubleValueFromString(s, s1);
            if(d != super.weightx)
                super.weightx = d;
        } else
        if(s.equals("weighty"))
        {
            double d1 = getDoubleValueFromString(s, s1);
            if(d1 != super.weighty)
                super.weighty = d1;
        } else
        if(s.equals("anchor"))
        {
            int i1 = getAnchorValueFromString(s, s1);
            if(i1 != super.anchor)
                super.anchor = i1;
        } else
        if(s.equals("fill"))
        {
            int j1 = getFillValueFromString(s, s1);
            if(j1 != super.fill)
                super.fill = j1;
        } else
        if(s.equals("insets"))
            getInsetsValueFromString(s, s1);
        else
        if(s.equals("ipadx"))
        {
            int k1 = getIntegerValueFromString(s, s1);
            if(k1 != super.ipadx)
                super.ipadx = k1;
        } else
        if(s.equals("ipady"))
        {
            int l1 = getIntegerValueFromString(s, s1);
            if(l1 != super.ipady)
                super.ipady = l1;
        } else
        {
            throw new IllegalArgumentException("Unknown parameter specified: '" + s + "'");
        }
    }

    private int findInsetsValueEnd(char ac[], int i)
    {
        if(ac[i] != '[')
            throw new IllegalArgumentException("StringGridBagConstraints: insets value must be in brackets '[]'");
        for(i++; i < ac.length && ac[i] != ']'; i++);
        return i;
    }

    private int getIntegerValueFromString(String s, String s1)
    {
        int i = 0;
        try
        {
            i = Integer.parseInt(s1);
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new IllegalArgumentException("Bad integer format for '" + s + "'");
        }
        return i;
    }

    private double getDoubleValueFromString(String s, String s1)
    {
        double d = 0.0D;
        try
        {
            d = Double.parseDouble(s1);
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new IllegalArgumentException("Bad double format for '" + s + "'");
        }
        return d;
    }

    private int getAnchorValueFromString(String s, String s1)
    {
        byte byte0 = 10;
        if(s1.equals("center"))
            byte0 = 10;
        else
        if(s1.equals("north"))
            byte0 = 11;
        else
        if(s1.equals("northeast"))
            byte0 = 12;
        else
        if(s1.equals("east"))
            byte0 = 13;
        else
        if(s1.equals("southeast"))
            byte0 = 14;
        else
        if(s1.equals("south"))
            byte0 = 15;
        else
        if(s1.equals("southwest"))
            byte0 = 16;
        else
        if(s1.equals("west"))
            byte0 = 17;
        else
        if(s1.equals("northwest"))
            byte0 = 18;
        else
            throw new IllegalArgumentException("Illegal value for '" + s + "'");
        return byte0;
    }

    private int getFillValueFromString(String s, String s1)
    {
        byte byte0 = 0;
        if(s1.equals("none"))
            byte0 = 0;
        else
        if(s1.equals("both"))
            byte0 = 1;
        else
        if(s1.equals("horizontal"))
            byte0 = 2;
        else
        if(s1.equals("vertical"))
            byte0 = 3;
        else
            throw new IllegalArgumentException("Illegal value for '" + s + "'");
        return byte0;
    }

    private void getInsetsValueFromString(String s, String s1)
    {
        if(super.insets == null)
            super.insets = new StringInsets();
        else
        if(!(super.insets instanceof StringInsets))
            super.insets = new StringInsets(super.insets);
        ((StringInsets)super.insets).setValues(s1);
    }

    public void list()
    {
        PrintWriter printwriter = new PrintWriter(System.out);
        list(printwriter);
    }

    public void list(PrintWriter printwriter)
    {
        list(printwriter, 0);
    }

    public void list(PrintWriter printwriter, int i)
    {
        String s = "";
        for(int j = 0; j < i; j++)
            s = s + "    ";

        String s1 = getClass().getName();
        printwriter.println(s + s1 + ":");
        printwriter.println(s + "gridx=" + super.gridx);
        printwriter.println(s + "gridy=" + super.gridy);
        printwriter.println(s + "gridwidth=" + super.gridwidth);
        printwriter.println(s + "gridheight=" + super.gridheight);
        printwriter.println(s + "weightx=" + super.weightx);
        printwriter.println(s + "weighty=" + super.weighty);
        printwriter.println(s + "anchor=" + convertAnchorToString(super.anchor));
        printwriter.println(s + "fill=" + convertFillToString(super.fill));
        printwriter.println(s + "ipadx=" + super.ipadx);
        printwriter.println(s + "ipady=" + super.ipady);
        printwriter.println(s + "insets=" + super.insets);
        printwriter.flush();
    }
}
