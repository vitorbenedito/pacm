package pacm.view;

    import java.awt.BorderLayout;  
    import java.awt.Graphics;  
    import java.awt.GridLayout;  
    import java.awt.Image;  
    import java.awt.Toolkit;  
    import java.awt.event.ActionEvent;  
    import java.awt.event.ActionListener;  
    import java.io.File;  
      
    import javax.swing.JButton;  
    import javax.swing.JFileChooser;  
    import javax.swing.JPanel;  
      
    /* 
     *  
     * Por William Antônio Siqueira 
     *  
     * FALTA: Buferizar as imagens aberta.... 
     * */  
      
      
    public class ImagemPanel extends JPanel {  
      
       private static final long serialVersionUID = 1L;  
       private JButton btnAvancar, btnVoltar, btnAbrir;  
       private JPanel pnlBotoes;  
       private VisualizaImgem pnlImagem;  
       private int indice = 0;  
       private String imagens[];  
      
       public ImagemPanel(String imagensUrls[]) {  
          
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
      
          pnlImagem = new VisualizaImgem();  
          add(pnlImagem);  
      
          if (imagensUrls == null || imagensUrls.length == 0) {  
             String imgs[] = escolherImagens();  
             if (imgs == null || imgs.length == 0) {  
                System.exit(0);  
             } else {  
                setImagens(imgs);  
             }  
          } else {  
             setImagens(imagensUrls);  
          }  
      
          btnAvancar.addActionListener(new ActionListener() {  
      
             @Override  
             public void actionPerformed(ActionEvent e) {  
                if (indice < imagens.length - 1)  
                   indice++;  
                else  
                   indice = 0;  
                
                pnlImagem.setImg(imagens[indice]);  
             }  
          });  
      
          btnVoltar.addActionListener(new ActionListener() {  
      
             @Override  
             public void actionPerformed(ActionEvent e) {  
                if (indice > 0)  
                   indice--;  
                else  
                   indice = imagens.length - 1;  
                
                pnlImagem.setImg(imagens[indice]);  
             }  
          });  
          btnAbrir.addActionListener(new ActionListener() {  
      
             @Override  
             public void actionPerformed(ActionEvent e) {  
                setImagens(escolherImagens());  
             }  
          });  
      
       }  
      
       public String[] escolherImagens() throws NullPointerException {  
          String imagens[];  
          JFileChooser fc = new JFileChooser();  
          fc.setMultiSelectionEnabled(true);  
      
          fc.showOpenDialog(this);  
      
          File selFile[] = fc.getSelectedFiles();  
          imagens = new String[selFile.length];  
      
          for (int i = 0; i < selFile.length; i++) {  
             imagens[i] = selFile[i].getAbsolutePath();  
          }  
          return imagens;  
       }  
      
       public void setImagens(String imagens[]) {  
          indice = 0;  
          pnlImagem.setImg(imagens[0]);  
          this.imagens = imagens;  
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