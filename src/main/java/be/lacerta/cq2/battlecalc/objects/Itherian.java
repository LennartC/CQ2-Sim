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


package be.lacerta.cq2.battlecalc.objects;

public class Itherian {
  public int damage = 0, defence = 0;
  public int health = 0;
  
  public Itherian() {
	  this(0,0,0);
  }
  
  /**
   * itherian
   * @param dmg damage added by the itherian
   * @param hlt health added by the itherian
   * @param def defence added by the itherian
   */
  public Itherian(int dmg, int hlt, int def) {
    damage = dmg;
    health = hlt;
    defence = def;
  }
  /** @return damage added by the itherian */
  public int getDamage() { return damage; }
  /** @return health added by the itherian */
  public int getHealth() { return health; }
  /** @return defence added by the itherian */
  public int getDefence() { return defence; }
  /** @param dmg damage added by the itherian */
  public void setDamage(int dmg) { damage = dmg; }
  /** @param hlt health added by the itherian */
  public void setHealth(int hlt) { health = hlt; }
  /** @param def defence added by the itherian */
  public void setDefence(int def) { defence = def; }

}
