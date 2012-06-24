

package be.lacerta.cq2.battlecalc.util;

import java.awt.*;
import java.io.PrintWriter;
import java.util.Enumeration;

public class StringGridBagLayout extends GridBagLayout
    implements Listable
{

    public StringGridBagLayout()
    {
        debug = false;
        clientDebug = false;
        debugColor = null;
        layoutContainerEntryLevel = 0;
        initDebugging();
        setDefaultConstraints(new StringGridBagConstraints());
    }

    public StringGridBagLayout(StringGridBagConstraints stringgridbagconstraints)
    {
        debug = false;
        clientDebug = false;
        debugColor = null;
        layoutContainerEntryLevel = 0;
        initDebugging();
        setDefaultConstraints(stringgridbagconstraints);
    }

    public StringGridBagLayout(String s)
    {
        debug = false;
        clientDebug = false;
        debugColor = null;
        layoutContainerEntryLevel = 0;
        initDebugging();
        StringGridBagConstraints stringgridbagconstraints = new StringGridBagConstraints(s);
        setDefaultConstraints(stringgridbagconstraints);
    }

    public void setDebugging(boolean flag)
    {
        if(flag != clientDebug)
            clientDebug = flag;
    }

    public boolean isDebugging()
    {
        return clientDebug;
    }

    private void initDebugging()
    {
        if(debugColors == null)
        {
            debugColors = (new Color[] {
                Color.black, Color.red, Color.blue, Color.white, Color.green
            });
        }
    }

    public static Color getNextDebugColor()
    {
        Color color = Color.black;
        if(debugColors != null)
        {
            color = debugColors[nextDebugColor];
            if(++nextDebugColor >= debugColors.length)
                nextDebugColor = 0;
        }
        return color;
    }

    public StringGridBagConstraints getDefaultConstraints()
    {
        StringGridBagConstraints stringgridbagconstraints = null;
        if(super.defaultConstraints == null)
            stringgridbagconstraints = new StringGridBagConstraints();
        else
        if(super.defaultConstraints instanceof StringGridBagConstraints)
            stringgridbagconstraints = (StringGridBagConstraints)super.defaultConstraints.clone();
        else
            stringgridbagconstraints = new StringGridBagConstraints(super.defaultConstraints);
        return stringgridbagconstraints;
    }

    public void setDefaultConstraints(StringGridBagConstraints stringgridbagconstraints)
    {
        if(stringgridbagconstraints == null)
            super.defaultConstraints = new GridBagConstraints();
        else
            super.defaultConstraints = (StringGridBagConstraints)stringgridbagconstraints.clone();
    }

    public void setDefaultConstraints(String s)
    {
        StringGridBagConstraints stringgridbagconstraints = new StringGridBagConstraints(getDefaultConstraints(), s);
        setDefaultConstraints(stringgridbagconstraints);
    }

    public void addLayoutComponent(Component component, Object obj)
    {
        if(obj instanceof String)
            addLayoutComponent((String)obj, component);
        else
            super.addLayoutComponent(component, obj);
    }

    public void addLayoutComponent(String s, Component component)
    {
        GridBagConstraints gridbagconstraints = getConstraints(component);
        StringGridBagConstraints stringgridbagconstraints = null;
        if(gridbagconstraints == null)
        {
            stringgridbagconstraints = getDefaultConstraints();
        } else
        if(gridbagconstraints instanceof StringGridBagConstraints)
        {
            stringgridbagconstraints = (StringGridBagConstraints)gridbagconstraints;
        } else
        {
            stringgridbagconstraints = new StringGridBagConstraints(gridbagconstraints);
        }
        stringgridbagconstraints.setValues(s);
        super.addLayoutComponent(component, stringgridbagconstraints);
    }

    public Dimension minimumLayoutSize(Container container)
    {
        if(clientDebug)
            ArrangeGrid(container);
        Dimension dimension = super.minimumLayoutSize(container);
        if(clientDebug)
        {
            Dimension dimension1 = getDebugSizeDelta();
            if(dimension1 != null)
            {
                dimension.width += dimension1.width;
                dimension.height += dimension1.height;
            }
        }
        return dimension;
    }

    public Dimension preferredLayoutSize(Container container)
    {
        if(clientDebug)
            ArrangeGrid(container);
        Dimension dimension = super.preferredLayoutSize(container);
        if(clientDebug)
        {
            Dimension dimension1 = getDebugSizeDelta();
            if(dimension1 != null)
            {
                dimension.width += dimension1.width;
                dimension.height += dimension1.height;
            }
        }
        return dimension;
    }

    private Dimension getDebugSizeDelta()
    {
        Dimension dimension = null;
        if(clientDebug)
        {
            dimension = new Dimension(0, 0);
            int ai[][] = getLayoutDimensions();
            int ai1[] = ai[1];
            int ai2[] = ai[0];
            if(ai != null)
            {
                dimension.width += 3 * (1 + ai2.length);
                dimension.height += 3 * (1 + ai1.length);
            }
        }
        return dimension;
    }

    public void layoutContainer(Container container)
    {
        layoutContainerEntryLevel++;
        Component acomponent[] = container.getComponents();
        Component acomponent1[] = new Component[acomponent.length];
        for(int i = 0; i < acomponent.length; i++)
            acomponent1[i] = acomponent[i];

        for(int j = 0; j < acomponent1.length; j++)
            if(acomponent1[j] instanceof StringGridBagLine)
                container.remove(acomponent1[j]);

        super.layoutContainer(container);
        if(clientDebug)
        {
            int ai[][] = getLayoutDimensions();
            int ai1[] = ai[0];
            int ai2[] = ai[1];
            if(debugColor == null)
                debugColor = getNextDebugColor();
            if(ai1 != null && ai2 != null)
            {
                Point point = getLayoutOrigin();
                int k1 = 0;
                int l1 = 0;
                Dimension dimension = getDebugSizeDelta();
                int i2 = 0;
                for(int k = 0; k < ai1.length; k++)
                    i2 += ai1[k];

                i2 += dimension.width;
                int j2 = 0;
                for(int l = 0; l < ai2.length; l++)
                    j2 += ai2[l];

                j2 += dimension.height;
                int k2 = point.x;
                int l2 = point.y;
                int k3 = l2 + 1;
                int l3 = j2 - 2;
                k1 = k2;
                for(int i1 = 0; i1 < ai1.length; i1++)
                {
                    if(i1 == 0)
                    {
                        StringGridBagVLine stringgridbagvline = new StringGridBagVLine();
                        stringgridbagvline.setLineColor(debugColor);
                        container.add(stringgridbagvline);
                        stringgridbagvline.setBounds(k1 + 1, k3, 1, l3);
                        shiftComponentsRightOfLine(k1, 3);
                        k1 += 3;
                    }
                    k1 += ai1[i1];
                    StringGridBagVLine stringgridbagvline1 = new StringGridBagVLine();
                    stringgridbagvline1.setLineColor(debugColor);
                    container.add(stringgridbagvline1);
                    stringgridbagvline1.setBounds(k1 + 1, k3, 1, l3);
                    shiftComponentsRightOfLine(k1, 3);
                    k1 += 3;
                }

                l1 = l2;
                int i4 = k2 + 1;
                l3 = i2 - 2;
                for(int j1 = 0; j1 < ai2.length; j1++)
                {
                    if(j1 == 0)
                    {
                        StringGridBagHLine stringgridbaghline = new StringGridBagHLine();
                        stringgridbaghline.setLineColor(debugColor);
                        container.add(stringgridbaghline);
                        stringgridbaghline.setBounds(i4, l1 + 1, l3, 1);
                        shiftComponentsBelowLine(l1, 3);
                        l1 += 3;
                    }
                    l1 += ai2[j1];
                    StringGridBagHLine stringgridbaghline1 = new StringGridBagHLine();
                    stringgridbaghline1.setLineColor(debugColor);
                    container.add(stringgridbaghline1);
                    stringgridbaghline1.setBounds(i4, l1 + 1, l3, 1);
                    shiftComponentsBelowLine(l1, 3);
                    l1 += 3;
                }

            }
        }
        layoutContainerEntryLevel--;
    }

    private void shiftComponentsBelowLine(int i, int j)
    {
        if(super.comptable != null)
        {
            for(Enumeration<Component> enumeration = super.comptable.keys(); enumeration.hasMoreElements();)
            {
            	Component component = enumeration.nextElement();
                Point point = component.getLocation();
                if(point.y >= i)
                     component.setLocation(point.x, point.y + j);
            }

        }
    }

    private void shiftComponentsRightOfLine(int i, int j)
    {
        if(super.comptable != null)
        {
            for(Enumeration<Component> enumeration = super.comptable.keys(); enumeration.hasMoreElements();)
            {
            	Component component = enumeration.nextElement();
            	Point point = component.getLocation();
            	if(point.x >= i)
            		component.setLocation(point.x + j, point.y);
            }

        }
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

        String s1 = s + "    ";
        printwriter.println(s + " ---------- StringGridBagLayout Contents (BEGIN) ----------");
        Enumeration<Component> enumeration = super.comptable.keys();
        int k = 0;
        while(enumeration.hasMoreElements())
        {
            k++;
            Component component = enumeration.nextElement();
            if(k > 1)
                printwriter.println();
            printwriter.println(s + component);
            GridBagConstraints gridbagconstraints = getConstraints(component);
            if(gridbagconstraints == null)
            {
                printwriter.println(s1 + "(NO CONSTRAINTS)");
            } else
            {
                StringGridBagConstraints stringgridbagconstraints;
                if(gridbagconstraints instanceof StringGridBagConstraints)
                    stringgridbagconstraints = (StringGridBagConstraints)gridbagconstraints;
                else
                    stringgridbagconstraints = new StringGridBagConstraints(gridbagconstraints);
                stringgridbagconstraints.list(printwriter, i + 1);
            }
        }
        printwriter.println(s + " ---------- StringGridBagLayout Contents (END) ------------");
        printwriter.flush();
    }

    boolean debug;
    boolean clientDebug;
    static Color debugColors[] = null;
    static int nextDebugColor = 0;
    Color debugColor;
    int layoutContainerEntryLevel;

}
