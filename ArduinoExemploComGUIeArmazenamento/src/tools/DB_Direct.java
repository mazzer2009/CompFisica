package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author radames
 */
public class DB_Direct {

    DB_Direct_Connection c;
    Connection connection;
    private String msgErro = "";

    public DB_Direct(String ArquivoTextoDaConexao) {
        c = new DB_Direct_Connection(ArquivoTextoDaConexao);
        connection = c.getConexao();
    }

    public DB_Direct(String serverName, String myDataBase, String userName, String password, String entidade) {
        c = new DB_Direct_Connection(serverName, myDataBase, userName, password, entidade);
        connection = c.getConexao();
    }

    public boolean getStatusDaConexao() {
        if (connection == null) {
            return false;
        } else {
            return true;
        }
    }

    public ResultSet executaSelect(String sql) {
        if (getStatusDaConexao()) {
            java.sql.Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                return rs;
            } catch (SQLException ex) {
                return null;
            }
        } else {
            //   System.out.println("Erro de conexão com o Banco de Dados");
            return null;
        }
    }

    public int executaAtualizacaoNoBD(String sql) {
        this.msgErro = "";
        if (getStatusDaConexao()) {
            java.sql.Statement statement;
            try {
                statement = connection.createStatement();
                return statement.executeUpdate(sql);

            } catch (SQLException ex) {
                // System.out.println(ex.getErrorCode());

                switch (ex.getErrorCode()) {
                    case 1062:
                        this.msgErro = "Erro. Este registro já está no BD (chave primária duplicada) "
                                + " Código do erro: " + ex.getErrorCode() + " [" + ex.getMessage() + "]";
                        break;
                    case 1406:
                        this.msgErro = "Erro. Campo com muitos caracteres [" + ex.getMessage() + "]";
                        break;
                    case 1452:
                        this.msgErro = "Erro. Chave estrangeira não foi preenchida corretamente. [" + ex.getMessage() + "]";
                        break;
                    default:
                        this.msgErro = "" + ex.getErrorCode() + " [" + ex.getMessage() + "]";
                        return -1;
                }

            }
        } else {
            this.msgErro = "Problema na conexão com o Banco de Dados";
            return -1;
        }
        return -1;
    }

    public static void main(String[] args) {
        //Rdms_BD_direct verificarConexao = new DB_Direct("src/sol01.txt");
        //System.out.println("status " + new DB_Direct("src/sol01.txt").getStatusDaConexao());
        System.out.println("status " + new DB_Direct(
                "localhost",
                "BDArduino",
                "radames",
                "lageado",
                "").getStatusDaConexao());
    }

    public String getMsgErro() {
        return msgErro;
    }
}
