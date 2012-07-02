
    package pacm.view;   
       
    import java.awt.BorderLayout;   
    import java.awt.Dimension;   
    import java.awt.FlowLayout;   
    import java.awt.Font;   
    import java.awt.Toolkit;   
    import java.awt.datatransfer.Clipboard;   
    import java.awt.datatransfer.DataFlavor;   
    import java.awt.datatransfer.StringSelection;   
    import java.awt.datatransfer.Transferable;   
    import java.awt.datatransfer.UnsupportedFlavorException;   
    import java.awt.event.ActionEvent;   
    import java.awt.event.ActionListener;   
    import java.awt.event.KeyEvent;   
    import java.awt.event.KeyListener;   
    import java.awt.event.MouseEvent;   
    import java.awt.event.MouseListener;   
    import java.io.BufferedReader;   
    import java.io.BufferedWriter;   
    import java.io.File;   
    import java.io.FileInputStream;   
    import java.io.FileNotFoundException;   
    import java.io.FileReader;   
    import java.io.FileWriter;   
    import java.io.IOException;   
    import java.util.Locale;   
    import java.util.Properties;   
       
    import javax.print.Doc;   
    import javax.print.DocFlavor;   
    import javax.print.DocPrintJob;   
    import javax.print.PrintException;   
    import javax.print.PrintService;   
    import javax.print.PrintServiceLookup;   
    import javax.print.SimpleDoc;   
    import javax.print.attribute.HashPrintRequestAttributeSet;   
    import javax.print.attribute.PrintRequestAttributeSet;   
    import javax.print.attribute.standard.Copies;   
    import javax.print.attribute.standard.MediaSize;   
    import javax.print.attribute.standard.MediaSizeName;   
    import javax.print.attribute.standard.Sides;   
    import javax.swing.Box;   
    import javax.swing.JFileChooser;   
    import javax.swing.JFrame;   
    import javax.swing.JLabel;   
    import javax.swing.JMenu;   
    import javax.swing.JMenuBar;   
    import javax.swing.JMenuItem;   
    import javax.swing.JPanel;   
    import javax.swing.JPopupMenu;   
    import javax.swing.JScrollPane;   
    import javax.swing.JSeparator;   
    import javax.swing.JTextArea;   
    import javax.swing.JToolBar;   
    import javax.swing.border.EtchedBorder;   
       
       
    /**  
     * @author K1ndred  
     *  
     * Editor de texto em swing  
     * Classe que implementa um editor de texto em swing  
     * Falta adicionar os eventos do mouse para vincular interface  
     */   
    public class EditorTextoPanel extends JPanel {   
           
        private File arquivo_atual = null;   
           
        private JMenuBar barra = null;   
           
        private JTextArea texto = null;   
           
        private JToolBar ferramentas = null;   
           
        private JMenuBar barra_status = null;   
           
        private JLabel arquivo_status = null;   
           
        private JScrollPane scroll = null;   
           
       
           
        private Clipboard transferencia = null;   
           
        private Toolkit toolkit = null;   
           
        private JPopupMenu popup = null;   
           
        private Properties propriedades = null;   
       
        private JLabel insert_status = null;   
       
        private JLabel capslock_status = null;   
       
           
        public EditorTextoPanel(){   
               
            
            texto = new JTextArea();   
            inicializarComponentes();   
               
        }   
           
        public EditorTextoPanel(String caminho) {   
            
            texto = new JTextArea();   
            File arquivo = new File(caminho);   
            inicializarComponentes();   
            System.out.println(caminho);   
            abrir_documento(arquivo);   
        }   
           
        private void inicializarComponentes() {   
               
            setLayout( new BorderLayout());   
               
            //this.getContentPane().add(painel);   
               
            // inicializa o toolkit singleton da maquina virtual   
            toolkit = Toolkit.getDefaultToolkit();   
               
            // pega a instancia do clipboard singleton do sistema operacional    
            transferencia = toolkit.getSystemClipboard();   
               
            // pega as propriedades do sistema   
            propriedades = System.getProperties();   
               
               
            // Barra de status   
               
                       
               
               
               
            barra_status = new JMenuBar();   
            barra_status.setLayout(new BorderLayout());   
            barra_status.setBorder(new EtchedBorder());   
            barra_status.setBorderPainted(true);   
               
           
            arquivo_status = new JLabel(" ");   
            arquivo_status.setBorder(new EtchedBorder());   
               
       
               
               
               
            insert_status  = new JLabel(" ");   
            insert_status.setPreferredSize((new Dimension(26,20)));   
            insert_status.setBorder(new EtchedBorder());   
            capslock_status = new JLabel(" ");   
            capslock_status.setPreferredSize(new Dimension(36,20));   
            capslock_status.setBorder(new EtchedBorder());   
               
            // Ajustando o estado das barras de status   
            // naoi funciona, sugestões aceitas   
            System.out.println(KeyEvent.VK_CAPS_LOCK);   
            boolean b = toolkit.getLockingKeyState(20);   
            if (b) {   
                capslock_status.setText("CAPS");   
            }   
               
            // adiciona componentes   
            // macetao pra fazer o fill com os componentes   
            // Assim os componentes preenchem a barra dinamicamente   
            JPanel status2 = new JPanel(new BorderLayout());   
            JPanel status3 = new JPanel(new BorderLayout());   
            status3.add(insert_status,BorderLayout.EAST);   
            status3.add(status2,BorderLayout.CENTER);   
            status2.add(capslock_status,BorderLayout.EAST);   
            status2.add(arquivo_status,BorderLayout.CENTER);   
            //barra_status.add(Box.createHorizontalGlue());   
            barra_status.add(status3);   
            //barra_status.setPreferredSize(new Dimension(400,32));   
               
               
            // Menu Arquivo   
               
            JMenu arquivo = new JMenu("Arquivo");   
            arquivo.setMnemonic('A');   
               
            final JMenuItem novo = new JMenuItem("Novo");   
            novo.setMnemonic('N');   
            novo.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    novo();   
                       
                }   
                   
            });   
               
            JMenuItem abrir = new JMenuItem("Abrir");   
            abrir.setMnemonic('A');   
            abrir.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    abrir();   
                       
                }   
                   
            });   
               
               
            JMenuItem salvar = new JMenuItem("Salvar");   
            salvar.setMnemonic('S');   
            salvar.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    salvar();   
                       
                }   
                   
            });   
               
               
            JMenuItem salvar_como = new JMenuItem("Salvar Como");   
            salvar_como.setMnemonic('C');   
            salvar_como.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    salvar_como();   
                       
                }   
                   
            });   
               
            JMenuItem imprimir = new JMenuItem("Imprimir");   
            imprimir.setMnemonic('P');   
            imprimir.addActionListener(new ActionListener() {   
       
                public void actionPerformed(ActionEvent e) {   
       
                    if (arquivo_atual != null) {   
                        imprimir();   
                    }   
       
                }   
            })  ;   
               
               
            JMenuItem sair = new JMenuItem("Sair");   
            sair.setMnemonic('R');   
            sair.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    System.exit(0);   
                       
                }   
                   
            });   
               
               
            arquivo.add(novo);   
            arquivo.add(abrir);   
            arquivo.add(new JSeparator());   
            arquivo.add(salvar);   
            arquivo.add(salvar_como);   
            arquivo.add(new JSeparator());   
            // removido por falta de testes   
            //arquivo.add(imprimir);   
            //arquivo.add(new JSeparator());   
            arquivo.add(sair);   
               
            // Menu Editar   
            JMenuItem recortar = new JMenuItem("Recortar");   
            recortar.setMnemonic('R');   
            recortar.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    recortar();   
                       
                }   
                   
            });   
               
            JMenuItem copiar = new JMenuItem("Copiar");   
            copiar.setMnemonic('C');   
            copiar.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    copiar();   
                       
                }   
                   
            });   
               
            JMenuItem colar = new JMenuItem("Colar");   
            colar.setMnemonic('L');   
            colar.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    colar();   
                       
                }   
                   
            });   
               
               
            JMenu editar = new JMenu("Editar");   
            editar.setMnemonic('E');   
               
            editar.add(recortar);   
            editar.add(copiar);   
            editar.add(colar);   
               
            // Menu Ajuda          
            JMenuItem sobre = new JMenuItem("Sobre");   
            sobre.setMnemonic('S');   
            sobre.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                       
                    sobre();   
                       
                }   
            });   
               
            JMenu ajuda = new JMenu("Ajuda");   
            ajuda.setMnemonic('J');   
            ajuda.add(sobre);   
               
            // popup menu   
               
            // menuitem novos   
            JMenuItem recortar_popup = new JMenuItem("Recortar");   
            recortar_popup.setMnemonic('R');   
            recortar_popup.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    recortar();   
                       
                }   
                   
            });   
               
            JMenuItem copiar_popup = new JMenuItem("Copiar");   
            copiar_popup.setMnemonic('C');   
            copiar_popup.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    copiar();   
                       
                }   
                   
            });   
               
            JMenuItem colar_popup = new JMenuItem("Colar");   
            colar_popup.setMnemonic('L');   
            colar_popup.addActionListener(new ActionListener() {   
                   
                public void actionPerformed(ActionEvent e) {   
                    colar();   
                       
                }   
                   
            });   
               
               
            popup = new JPopupMenu();   
            popup.add(recortar_popup);   
            popup.add(copiar_popup);   
            popup.add(colar_popup);   
               
               
            // Barra de menus   
            barra = new JMenuBar();   
            barra.add(arquivo);   
            barra.add(editar);   
            barra.add(Box.createHorizontalGlue());   
            barra.add(ajuda);   
               
               
            scroll = new JScrollPane(texto);   
               
            // Montando a Janela   
            // para o menu superior, utiliza-se um borderlayout por razoes estéticas   
            // o resultado é mais agradável a vista   
               
               
            JPanel superior = new JPanel(new BorderLayout(0,0));   
            superior.add(barra,BorderLayout.NORTH);   
            // a ser adicionado posteriormente por fins educativos   
            //superior.add(ferramentas,BorderLayout.SOUTH);   
               
            add(BorderLayout.NORTH,superior);   
            add(BorderLayout.CENTER,scroll);   
            add(BorderLayout.SOUTH,barra_status);   
               
               
            // Ajustando o popupMenu   
            texto.add(popup);   
               
            texto.addMouseListener(new MouseListener() {   
                   
                public void mouseClicked(MouseEvent e) {   
                       
                       
                       
                }   
                   
                public void mouseEntered(MouseEvent e) {   
                       
                       
                }   
                   
                public void mouseExited(MouseEvent e) {   
                       
                       
                }   
                   
                public void mousePressed(MouseEvent e) {   
                       
                    if (e.isPopupTrigger()) {   
                        popup.show(e.getComponent(),   
                                e.getX(), e.getY());   
                    }   
                       
                }   
                   
                public void mouseReleased(MouseEvent e) {   
                       
                    if (e.isPopupTrigger()) {   
                        popup.show(e.getComponent(),   
                                e.getX(), e.getY());   
                    }   
                       
                }   
            });   
               
            texto.addKeyListener(new KeyListener() {   
       
                public void keyPressed(KeyEvent e) {   
       
                    // TODO Auto-generated method stub   
                    switch (e.getKeyCode()) {   
                        case 155: {   
                            if (insert_status.getText().equals(" ")) {   
                                insert_status.setText("INS");   
                            }   
                            else {   
                                insert_status.setText(" ");   
                            }   
                            break;   
                        }   
                        case 20: {   
                            if (capslock_status.getText().equals(" ")) {   
                                capslock_status.setText("CAPS");   
                            }   
                            else {   
                                capslock_status.setText(" ");   
                            }   
                            break;   
       
                        }   
                    }   
                       
       
                }   
       
                public void keyReleased(KeyEvent e) {   
       
                    // TODO Auto-generated method stub   
       
                }   
       
                public void keyTyped(KeyEvent e) {   
       
                    // TODO Auto-generated method stub   
       
                }   
            });   
               
            texto.setLocale(Locale.getDefault());   
               
            texto.setEditable(true);   
               
            // Ajustes Locais   
            // ajustar_interface(propriedades.getProperty("os.name"));   
            // Fonte   
            texto.setFont(new Font("Dialog",Font.PLAIN,13));   
       
       
            // Mostra a Janela   
            this.setSize(new Dimension(500,500));   
               
            this.show();   
               
               
        }   
           
        /*private void ajustar_interface(String OS) {  
            if (OS.equalsIgnoreCase("Linux")) {  
                System.out.println("Ajuste da interface para Linux");  
                texto.setFont(new Font("Dialog",Font.PLAIN,13));  
          
            }  
            if (OS.equalsIgnoreCase("Windows")) {  
                System.out.println("Ajuste da interface para windows");  
                  
            }  
            if (OS.equalsIgnoreCase("Unix")) {  
                System.out.println("Ajuste da interface para unix");  
                  
            }  
            if (OS.equalsIgnoreCase("MacOs")) {  
                System.out.println("Ajuste da interface para MacOs");  
                  
            }  
      
      
      
        }*/   
           
           
        private void novo() {   
               
            arquivo_atual = null;   
            texto.setText("");   
            arquivo_status.setText(" ");   
               
        }   
           
        private void abrir() {   
               
            JFileChooser janela = new JFileChooser();   
            janela.setApproveButtonText("Abrir");   
            janela.setApproveButtonToolTipText("Abre o arquivo selecionado");   
            janela.setDialogTitle("Abrir...");   
            int returnVal = janela.showOpenDialog(this);   
            if(returnVal == JFileChooser.APPROVE_OPTION) {   
                novo();   
                arquivo_atual = janela.getSelectedFile();   
                abrir_documento(arquivo_atual);   
            }   
        }   
           
        // Método que abre um documento internamente   
           
        private void abrir_documento(File arquivo) {   
               
            if (arquivo.exists()) {   
                arquivo_atual = arquivo;   
                try {   
                    BufferedReader leitor;   
                    System.out.println(arquivo_atual.getCanonicalPath());   
                       
                    leitor = new BufferedReader(new FileReader(arquivo_atual));   
                       
                    String linha = null;   
                    while( (linha = leitor.readLine() ) != null ) {   
                        texto.append(linha + '\n');   
                    }   
                       
                    arquivo_status.setText("Arquivo atual: " + arquivo_atual.getCanonicalPath());   
                       
                }   
                catch (FileNotFoundException e) {   
                }   
                catch (IOException e) {   
                }   
            }   
            else {   
                arquivo_status.setText(" ");   
            }   
        }   
           
        private void salvar() {   
               
            if (arquivo_atual == null) {   
                salvar_como();         
            }   
            else {   
                try {   
                    BufferedWriter bw =  new BufferedWriter(new FileWriter(arquivo_atual));   
                    bw.write(texto.getText());   
                    bw.close();   
                } catch (IOException io) {   
                }   
            }   
        }   
           
        private void salvar_como() {   
            JFileChooser janela = new JFileChooser();   
            janela.setApproveButtonText("Salvar");   
            janela.setApproveButtonToolTipText("Salva no arquivo selecionado.");   
            janela.setDialogTitle("Salvar Como...");   
            // adicionar acessorio que verifica se o arquivo já existe   
            // janela.setAccessory()   
            janela.setMultiSelectionEnabled(false);   
               
            int returnVal = janela.showOpenDialog(this);   
            if(returnVal == JFileChooser.APPROVE_OPTION) {   
                try {   
                    arquivo_atual = janela.getSelectedFile();   
                    BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo_atual));   
                    escritor.write(texto.getText());   
                    escritor.close();   
                       
                } catch (IOException e) {   
                }   
                   
            }   
               
        }   
           
        // Imprimir   
        // Necessita testes   
        private void imprimir() {   
            // Cria um stream de texto puro   
            FileInputStream txtStream = null;   
            try {   
                txtStream = new FileInputStream(arquivo_atual);   
            }   
            catch (FileNotFoundException ffne) {   
            }   
            if (txtStream == null) {   
                return;   
            }   
               
            // Configura o documento a ser impresso   
            DocFlavor txtFormatado = DocFlavor.INPUT_STREAM.TEXT_PLAIN_HOST;   
            Doc meuDocumento = new SimpleDoc(txtStream, txtFormatado, null);   
            PrintRequestAttributeSet atributos = new HashPrintRequestAttributeSet();   
               
            // Padrao 1 cópia tamanho A4 somente face   
            atributos.add(new Copies(1));   
            atributos.add(MediaSize.getMediaSizeForName(MediaSizeName.ISO_A4));   
            atributos.add(Sides.ONE_SIDED);   
               
            // Adquire as informaçoes do serviço de impressao   
            PrintService[] servicos = PrintServiceLookup.lookupPrintServices(txtFormatado, atributos);   
            // se exite um serviço   
            if (servicos.length > 0) {   
                // entao usa o primeiro serviço encontrado para imprimir   
                DocPrintJob trabalho = servicos[0].createPrintJob();   
                try {   
                    trabalho.print(meuDocumento, atributos);   
                }   
                catch (PrintException pe) {   
                    System.out.println("Nao foi possível imprimir");   
                }   
            }    
               
        }   
           
        private void sair() {   
            System.exit(0);   
        }   
           
        private void recortar() {   
            String recorte = texto.getSelectedText();   
            if (recorte != null) {   
                //recorta o texto original   
                texto.replaceSelection(null);   
                // envia para o clipboard   
                StringSelection auxiliar = new StringSelection(recorte);   
                transferencia.setContents(auxiliar,new StringSelection("self"));   
            }   
               
        }   
        private void copiar() {   
            String recorte = texto.getSelectedText();   
            if (recorte != null) {   
                // envia o texto para o clipboard   
                StringSelection auxiliar = new StringSelection(recorte);   
                transferencia.setContents(auxiliar,new StringSelection("self"));   
            }   
               
        }   
           
        private void colar() {   
               
            String recorte = null;   
            Transferable auxiliar = null;   
            auxiliar = transferencia.getContents(null);   
            if (auxiliar.isDataFlavorSupported(DataFlavor.stringFlavor)) {   
                try {   
                    recorte = (String) auxiliar.getTransferData(DataFlavor.stringFlavor);   
                       
                }   
                catch (UnsupportedFlavorException e) {   
                    System.out.println("Tipo mime não suportado");;   
                    recorte = null;   
                }   
                catch (IOException e) {   
                    System.out.println("Erro de IO");   
                }   
            }   
            if (recorte != null) {   
                texto.replaceSelection(recorte);   
            }   
        }   
           
        private void sobre() {   
            JFrame janela_sobre = new JFrame();   
            janela_sobre.setTitle("Sobre...");   
            janela_sobre.setSize(new Dimension(150,150));   
            janela_sobre.getContentPane().setLayout(new FlowLayout());   
            janela_sobre.setLocation(this.getX(),this.getY());   
            janela_sobre.setResizable(false);   
            janela_sobre.setUndecorated(false);   
            janela_sobre.setVisible(true);   
            janela_sobre.getContentPane().add(new JLabel("JavaTextEditor"));   
            janela_sobre.getContentPane().add(new JLabel("Autor: K1NDRED"));   
            janela_sobre.getContentPane().add(new JLabel("lasanha@gmail.com"));   
               
                           
        }   
           
        public static  void main(String[] args) {   
               
            if (args.length > 0) {   
                for (int i = 0; i < args.length; i ++) {   
                    System.out.println(args[i]);   
                    new EditorTextoPanel(args[i]);   
                }   
            }   
            else {   
                new EditorTextoPanel();   
            }   
        }   
       
           
       
    }   