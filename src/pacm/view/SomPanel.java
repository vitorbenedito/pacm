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
       private SomPanel.VisualizaImgem pnlImagem;  
       private int indice = 0;  
       private String sons[];  
      
       public SomPanel(String sonsUrls[]) {  
          
          setLayout(new BorderLayout(5, 10));  
          setSize(450, 300);  
      
          btnAvancar = new JButton(">>");  
      
          btnAbrir = new JButton("Abrir");  
      
          btnVoltar = new JButton("<<");  
      
          pnlBotoes = new JPanel(new GridLayout(1, 3, 30, 5));  
          pnlBotoes.add(btnVoltar);  
          pnlBotoes.add(btnAbrir);  
          pnlBotoes.add(btnAvancar);  
          add(pnlBotoes, BorderLayout.NORTH);  
      
          pnlImagem = new SomPanel.VisualizaImgem();  
          add(pnlImagem);  
      
          if (sonsUrls == null || sonsUrls.length == 0) {  
             String imgs[] = escolhersons();  
             if (imgs == null || imgs.length == 0) {  
                System.exit(0);  
             } else {  
                setsons(imgs);  
             }  
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
                tocarSom(sons[indice]);
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
                tocarSom(sons[indice]);
                
             }  
          });  
          btnAbrir.addActionListener(new ActionListener() {  
      
             @Override  
             public void actionPerformed(ActionEvent e) {  
                setsons(escolhersons());  
             }  
          });  
      
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
          tocarSom(sons[0]);
          this.sons = sons;  
       }  
       
       public void tocarSom(String path)
       {
           ThreadPoolExecutor pool = new ThreadPoolExecutor(1, // core threads
                1, // max threads
                1, // timeout
                TimeUnit.MINUTES, // timeout units
                new LinkedBlockingQueue() // work queue
        );
           
           File mp3File = new File(path);
           Thread t = new Thread( new Mp3(mp3File) );
           
           pool.submit(t);
           
       }
      
       public class VisualizaImgem extends JPanel {  
          private static final long serialVersionUID = 1L;  
          private Image img;  
      
          public VisualizaImgem() {  
          }  
      
          public VisualizaImgem(Image img) {  
             setImg(img);  
          }  
      
          public VisualizaImgem(String url) {  
             setImg(img);  
          }  
      
          protected void paintComponent(final Graphics g) {  
             super.paintComponent(g);  
             g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
          }  
      
          public void setImg(Image img) {  
             this.img = img;  
             this.repaint();  
          }  
      
          public void setImg(String url) {  
             setImg(Toolkit.getDefaultToolkit().createImage(url));  
          }  
       }  
      
       public static void main(String[] args) {  
          new ImagemPanel(args).setVisible(true);  
       }  
    }  