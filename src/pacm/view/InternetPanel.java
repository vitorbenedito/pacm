/* 
 * Christopher Deckers (chrriis@nextencia.net) 
 * http://www.nextencia.net 
 * 
 * See the file "readme.txt" for information on usage and redistribution of 
 * this file, and for a DISCLAIMER OF ALL WARRANTIES. 
 */  
package pacm.view;  
  
import java.awt.BorderLayout;  
import java.awt.FlowLayout;  
import java.awt.event.ItemEvent;  
import java.awt.event.ItemListener;  
  
import javax.swing.BorderFactory;  
import javax.swing.JCheckBox;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  
import javax.swing.SwingUtilities;  
  
import chrriis.common.UIUtils;  
import chrriis.dj.nativeswing.swtimpl.NativeInterface;  
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;  
  
/** 
 * @author Christopher Deckers 
 */  
public class InternetPanel extends JPanel {  
  
  public InternetPanel() {  
    super(new BorderLayout());  
    JPanel webBrowserPanel = new JPanel(new BorderLayout());  
    webBrowserPanel.setBorder(BorderFactory.createTitledBorder("Native Web Browser component"));  
    final JWebBrowser webBrowser = new JWebBrowser();  
    webBrowser.navigate("http://www.google.com");  
    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);  
    add(webBrowserPanel, BorderLayout.CENTER);  
    
    webBrowser.setMenuBarVisible(false);
     
  }  
  
  /* Standard main method to try that test as a standalone application. */  
  public static void main(String[] args) {  
    UIUtils.setPreferredLookAndFeel();  
    NativeInterface.open();  
    SwingUtilities.invokeLater(new Runnable() {  
      public void run() {  
        JFrame frame = new JFrame("DJ Native Swing Test");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.getContentPane().add(new InternetPanel(), BorderLayout.CENTER);  
        frame.setSize(800, 600);  
        frame.setLocationByPlatform(true);  
        frame.setVisible(true);  
      }  
    });  
    NativeInterface.runEventPump();  
  }  
  
}  