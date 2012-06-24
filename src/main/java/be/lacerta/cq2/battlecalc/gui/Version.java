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

import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Version {
  private int version = 304;
  private boolean checking = false;

  public Version() {

  }

  public void checkUpdate() {
    checkVersion cv = new checkVersion();
    cv.start();
  }

  public void display(boolean err, String msg) {
    if (err)
      JOptionPane.showMessageDialog(null,
        "Unable to get version information.\n\nError message:\n"+msg,
                                    "Unable to check version",JOptionPane.ERROR_MESSAGE);
    else
      JOptionPane.showMessageDialog(null,msg);
  }

  class checkVersion extends Thread {
    @SuppressWarnings("deprecation")
	public void run() {
      if (checking) this.stop();
        checking = true;
        try {
          URL url = new URL("http://cq2.lacerta.be/version");

          StringBuffer result = new StringBuffer();
          BufferedReader reader = null;
          reader = new BufferedReader(new InputStreamReader(url.openStream()));
          String line = null;
          while ( (line = reader.readLine()) != null) {
            result.append(line);
          }
          int newVersion = Integer.parseInt(result.toString());
          if (newVersion(newVersion))
            display(false,
                "Newer version available for download.\nhttp://cq2.lacerta.be");
          else
            display(false, "No newer version available.");
        }
        catch (MalformedURLException ex) { display(true, ex.toString()); }
        catch (IOException ex) { display(true, ex.toString()); }
        catch (NumberFormatException ex) { display(true, ex.toString()); }
        checking = false;
      this.stop();
    }
  }

  private boolean newVersion(int version) {
    return (version > this.version);
  }

  public String getVersion() {
    int main = version / 100;
    int sub = version - (main*100);
    return main+"."+sub;
  }

}
