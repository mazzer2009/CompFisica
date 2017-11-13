package codigoFonte;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * @author radames
 */
public class GetDiretorioDaAplicacao {

    private String diretorioDaAplicacao;

    public String getDiretorioDaAplicacao() {
        return System.getProperty("user.dir");
    }

    public String getDiretorioDoUsuario() {
        return System.getProperty("user.home");
    }

    public static void main(String[] args) {
        GetDiretorioDaAplicacao getDiretorioCorrente = new GetDiretorioDaAplicacao();
        System.out.println("Diretorio " + getDiretorioCorrente.getDiretorioDaAplicacao());
    }

    public String getApplicationPath() {
        String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath();
        File dir = new File(url).getParentFile();
        String path = null;

        if (dir.getPath().contains(".jar")) {
            path = findJarParentPath(dir);
            System.out.println("jar path" + path);
        } else {
            path = dir.getPath();
        }

        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return path.replace("%20", " ");
        }
    }

    //  retorna o caminho quando a aplicao est dentro de um  arquivo .jar 
    private String findJarParentPath(File jarFile) {
        while (jarFile.getPath().contains(".jar")) {
            jarFile = jarFile.getParentFile();
        }

        return jarFile.getPath().substring(6);
    }
}
