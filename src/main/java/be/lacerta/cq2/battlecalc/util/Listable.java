// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Listable.java

package be.lacerta.cq2.battlecalc.util;

import java.io.PrintWriter;

public interface Listable
{

    public abstract void list();

    public abstract void list(PrintWriter printwriter);

    public abstract void list(PrintWriter printwriter, int i);
}
