package pacm.view;
import java.io.File;
public class TesteMp3 {
  public static void main(String[] args) {
    //String com o caminho do arquivo MP3 a ser tocado
    String path = "/Users/vitorbenedito/Documents/musicas/Audioslave/Audioslave-Be Yourself.mp3";
    //Instanciação de um objeto File com o arquivo MP3
    File mp3File = new File(path);
    //Instanciação do Objeto MP3, a qual criamos a classe.
    Mp3 musica = new Mp3(mp3File);
    //Finalmente a chamada do método que toca a música
    musica.run();
  }
}