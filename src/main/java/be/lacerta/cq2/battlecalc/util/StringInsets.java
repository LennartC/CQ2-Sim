// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   StringInsets.java

package be.lacerta.cq2.battlecalc.util;

import java.awt.Insets;

public class StringInsets extends Insets
{
    protected class ParseContext
    {

        protected boolean done;
        protected char chars[];
        protected int index;
        protected boolean namedValueSeen;
        protected boolean unnamedValueSeen;
        protected boolean topSeen;
        protected boolean leftSeen;
        protected boolean bottomSeen;
        protected boolean rightSeen;

        ParseContext(char ac[])
        {
            done = false;
            chars = null;
            index = 0;
            namedValueSeen = false;
            unnamedValueSeen = false;
            topSeen = false;
            leftSeen = false;
            bottomSeen = false;
            rightSeen = false;
            chars = ac;
        }
    }


    public StringInsets()
    {
        super(0, 0, 0, 0);
    }

    public StringInsets(Insets insets)
    {
        super(insets.top, insets.left, insets.bottom, insets.right);
    }

    public StringInsets(String s)
    {
        super(0, 0, 0, 0);
        setValues(s);
    }

    public void setValues(String s)
    {
        if(s == null)
            return;
        char ac[] = normalizeString(s);
        ParseContext parsecontext;
        for(parsecontext = new ParseContext(ac); !parsecontext.done;)
            parseOneValue(parsecontext);

        if(!parsecontext.namedValueSeen && !parsecontext.unnamedValueSeen)
            throw new IllegalArgumentException("Insets: No values specified");
        if(!parsecontext.namedValueSeen && (!parsecontext.topSeen || !parsecontext.leftSeen || !parsecontext.bottomSeen || !parsecontext.rightSeen))
            throw new IllegalArgumentException("Insets: When using unnamed values, four values must be specified");
        else
            return;
    }

    private char[] normalizeString(String s)
    {
        boolean flag = false;
        int i = 0;
        char ac[] = s.toCharArray();
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        for(int j = 0; j < ac.length; j++)
        {
            char c = ac[j];
            if(Character.isWhitespace(c))
                flag = true;
            else
            if(c == '[')
            {
                if(flag3)
                    throw new IllegalArgumentException("Insets: Only one '[' allowed");
                flag = true;
                flag3 = true;
                if(flag2)
                    throw new IllegalArgumentException("Insets: Illegal character(s) before '['");
            } else
            if(c == ']')
            {
                if(!flag3)
                    throw new IllegalArgumentException("Insets: Mismatched brackets: '[]'");
                if(flag4)
                    throw new IllegalArgumentException("Insets: Only one ']' allowed");
                flag = true;
                flag4 = true;
            } else
            {
                if(flag4)
                    throw new IllegalArgumentException("Insets: Illegal character(s) after ']'");
                flag2 = true;
                ac[i++] = Character.toLowerCase(c);
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

    protected void parseOneValue(ParseContext parsecontext)
    {
        char ac[] = parsecontext.chars;
        int i = ac.length;
        char c = ac[parsecontext.index];
        if(Character.isDigit(c))
        {
            if(parsecontext.namedValueSeen)
                throw new IllegalArgumentException("Insets: Mixed named an unnamed values");
            parsecontext.unnamedValueSeen = true;
            int k = parsecontext.index;
            for(; parsecontext.index < i && Character.isDigit(ac[parsecontext.index]); parsecontext.index++);
            int l = parsecontext.index - 1;
            int j = Integer.parseInt(new String(ac, k, (l - k) + 1));
            if(!parsecontext.topSeen)
            {
                parsecontext.topSeen = true;
                super.top = j;
            } else
            if(!parsecontext.leftSeen)
            {
                parsecontext.leftSeen = true;
                super.left = j;
            } else
            if(!parsecontext.bottomSeen)
            {
                parsecontext.bottomSeen = true;
                super.bottom = j;
            } else
            if(!parsecontext.rightSeen)
            {
                parsecontext.rightSeen = true;
                super.right = j;
            }
        } else
        if(parsecontext.unnamedValueSeen)
        {
            throw new IllegalArgumentException("Insets: Mixed named an unnamed values");
        } else
        {
            parsecontext.namedValueSeen = true;
            throw new RuntimeException("named inset values not supported");
        }
        for(; parsecontext.index < i && ac[parsecontext.index] != ','; parsecontext.index++);
        parsecontext.index++;
        if(parsecontext.index >= i)
            parsecontext.done = true;
    }
}
