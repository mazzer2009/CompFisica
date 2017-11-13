package tools;

// @author Radames
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DB_Direct_Connection {

    private String serverName = "";    //caminho do servidor do BD  
    private String myDataBase = "";        //nome do seu banco de dados  
    private String url = "" + serverName + "/" + myDataBase;
    private String userName = "";        //nome de um usuário de seu BD        
    private String password = "";      //sua senha de acesso  
    private String entidade = "";

    public DB_Direct_Connection(String serverName, String myDataBase, String userName, String password, String entidade) {
        this.serverName = serverName;    //caminho do servidor do BD  
        this.myDataBase = myDataBase;        //nome do seu banco de dados  
        this.url = "jdbc:mysql://" + serverName + "/" + myDataBase;
        this.userName = userName;        //nome de um usuário de seu BD        
        this.password = password;      //sua senha de acesso  
        this.entidade = entidade;
    }

    public DB_Direct_Connection(String caminho) {
        ManipulaArquivo arq = new ManipulaArquivo();

     //   System.out.println("caminho " + caminho);
        List<String> bd = arq.abrirArquivo(caminho);

        //System.out.println("aaa " + bd.size());
        if (bd.size() > 0) {
            this.serverName = bd.get(0);    //caminho do servidor do BD  
            this.myDataBase = bd.get(1);        //nome do seu banco de dados  
            this.url = "jdbc:mysql://" + serverName + "/" + myDataBase;
            this.userName = bd.get(2);        //nome de um usuário de seu BD        
            this.password = bd.get(3);;      //sua senha de acesso  
        }
    }

    public Connection getConexao() {
        try {
            // Carregando o JDBC Driver padrão  
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            // Configurando a nossa conexão com um banco de dados//  

            Connection connection = DriverManager.getConnection(url, userName, password);

            return connection;
        } catch (ClassNotFoundException e) {  //Driver não encontrado  
            System.out.println("O driver expecificado nao foi encontrado, adicione a biblioteca Mysql Jdbc Driver no projeto.");
            return null;

        } catch (SQLException e) {
            //Não conseguindo se conectar ao banco  
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            return null;
        }
    }

    public String getServerName() {
        return serverName;
    }

    public String getMyDataBase() {
        return myDataBase;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }
}
