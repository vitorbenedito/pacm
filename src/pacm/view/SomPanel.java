package pacm.view;

    import java.awt.BorderLayout;  
    import java.awt.Graphics;  
    import java.awt.GridLayout;  
    import java.awt.Image;  
    import java.awt.Toolkit;  
    import java.awt.event.ActionEvent;  
    import java.awt.event.ActionListener;  
    import java.io.File;  
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
      
    import javax.swing.JButton;  
    import javax.swing.JFileChooser;  
    import javax.swing.JPanel;  
      
    /* 
     *  
     * Por William Antônio Siqueira 
     *  
     * FALTA: Buferizar as sons aberta.... 
     * */  
      
      
    public class SomPanel extends JPanel {  
      
       private static final long serialVersionUID = 1L;  
       private JButton btnAvancar, btnVoltar, btnAbrir;  
       private JPanel pnlBotoes; 
       private int indice = 0;  
       private String sons[]; 
       private Mp3 mp3;
      
       public SomPanel(String sonsUrls[]) {  
          
           
          this.setBackground(new java.awt.Color(0, 51, 102));
           
          setLayout(new BorderLayout(5, 10));  
          setSize(450, 300);  
      
          btnAvancar = new JButton(">>");  
      
          btnAbrir = new JButton("Abrir");  
      
          btnVoltar = new JButton("<<");  
      
          pnlBotoes = new JPanel(new GridLayout(1, 3, 30, 5));  
          pnlBotoes.add(btnVoltar);  
          pnlBotoes.add(btnAbrir);  
          pnlBotoes.add(btnAvancar);  
          pnlBotoes.setBackground(new java.awt.Color(0, 51, 102));
          add(pnlBotoes, BorderLayout.NORTH);  
      
          if (sonsUrls == null || sonsUrls.length == 0) {  
//             String imgs[] = escolhersons();  
//             if (imgs == null || imgs.length == 0) {  
//                System.exit(0);  
//             } else {  
//                setsons(imgs);  
//             }  
          } else {  
             setsons(sonsUrls);  
          }  
      
          btnAvancar.addActionListener(new ActionListener() {  
      
             @Override  
             public void actionPerformed(ActionEvent e) {  
                if (indice < sons.length - 1)  
                   indice++;  
                else  
                   indice = 0;  
                
                //pnlImagem.setImg(sons[indice]);  
                tocar();
             }  
          });  
      
          btnVoltar.addActionListener(new ActionListener() {  
      
             @Override  
             public void actionPerformed(ActionEvent e) {  
                if (indice > 0)  
                   indice--;  
                else  
                   indice = sons.length - 1;  
                
                //pnlImagem.setImg(sons[indice]);  
                tocar();
                
             }  
          });  
          btnAbrir.addActionListener(new ActionListener() {  
      
             @Override  
             public void actionPerformed(ActionEvent e) {  
                setsons(escolhersons());  
             }  
          });  
      
       }
       
       public void tocar(){
           if(mp3 != null){
               mp3.stopMusic();
           }
           mp3 = new Mp3(new File(sons[indice]));
           mp3.start();
       }
      
       public String[] escolhersons() throws NullPointerException {  
          String sons[];  
          JFileChooser fc = new JFileChooser();  
          fc.setMultiSelectionEnabled(true);  
      
          fc.showOpenDialog(this);  
      
          File selFile[] = fc.getSelectedFiles();  
          sons = new String[selFile.length];  
      
          for (int i = 0; i < selFile.length; i++) {  
             sons[i] = selFile[i].getAbsolutePath();  
          }  
          return sons;  
       }  
      
       public void setsons(String sons[]) {  
          indice = 0;  
          //pnlImagem.setImg(sons[0]);  
          this.sons = sons;  
          tocar();
                 
       }
      
       public static void main(String[] args) {  
          new ImagemPanel(args).setVisible(true);  
       }  
    }  