package codigoFonte;

/**
 *
 * @author radames
 */
import Arduino.AcessaArduino;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import tools.DB_Direct;

public class GUI extends JFrame implements Observer {

    Container cp;

    JLabel lbResposta = new JLabel("0");
    JLabel lbPorta = new JLabel("");
    JLabel lbAvisos = new JLabel("");

    JLabel lbSensorTensao = new JLabel("Temperatura");
    JLabel lbSensorTensaoOriginal = new JLabel("0");
    JTextField tfSensorTensaoCalibragem = new JTextField(10);
    JLabel lbSensorTensaoCorrigido = new JLabel("0");
    JLabel lbSensorCorrente = new JLabel("Umidade");
    JLabel lbSensorCorrenteOriginal = new JLabel("0");
    JTextField tfSensorCorrenteCalibragem = new JTextField(10);
    JLabel lbSensorCorrenteCorrigido = new JLabel("0");

    JTextArea taTexto = new JTextArea();

    JButton btAmarelo = new JButton("LED Amarelo");
    JButton btVermelho = new JButton("LED Vermelho");

    AcessaArduino acessaArduino;
    JPanel painelResposta = new JPanel();
    JPanel painelNorte = new JPanel();
    JPanel painelSul = new JPanel();
    JPanel painelDados = new JPanel();
    JPanel painelCentro = new JPanel();
    JPanel painelGrafTensao = new JPanel();
    JPanel painelGrafCorrente = new JPanel();

    ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
    List<String> calib;
    List<Integer> dadosTensao = new ArrayList<>();
    List<Integer> dadosCorrente = new ArrayList<>();

    Locale ptBR = new Locale("pt", "BR");
    NumberFormat numberFormat = NumberFormat.getNumberInstance(ptBR); //para números
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    DB_Direct db = new DB_Direct("src/tools/local.txt");

    int max = 10;

    String ss = "";
    String sql = "";

    public GUI() {
        //buscar parâmetros salvos
        calib = manipulaArquivo.abrirArquivo("Calibragem.txt");

        if (calib.size() > 0) {
            tfSensorTensaoCalibragem.setText(calib.get(0));
            tfSensorCorrenteCalibragem.setText(calib.get(1));
        }

        lbAvisos.setOpaque(true); // para que as cores funcionem
        try {
            acessaArduino = new AcessaArduino(this); //passa como parâmetro a classe GUI (this) para informar quem é o Observador
            lbPorta.setText("Porta detectada: " + acessaArduino.getPortaSelecionada());
            lbAvisos.setBackground(Color.GREEN);
            painelSul.setBackground(Color.green);

        } catch (Exception e) {
            lbAvisos.setText("Erro ao acionar o Arduino");
            lbAvisos.setBackground(Color.red);
            painelSul.setBackground(Color.red);

        }

        //System.out.println(ArduinoSerialPortListener.serial_port);
        setSize(800, 400);
        setTitle("Monitoramento");
        setLocation(new JanelaNoCentroDoMaiorMonitor(this).getCentroMonitorMaior());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        painelNorte.add(lbPorta);
        // painelNorte.add(btAmarelo);
        // painelNorte.add(btVermelho);

        painelDados.setLayout(new GridLayout(2, 4));
        painelDados.add(lbSensorTensao);
        painelDados.add(lbSensorTensaoOriginal);
        painelDados.add(tfSensorTensaoCalibragem);
        painelDados.add(lbSensorTensaoCorrigido);

        painelDados.add(lbSensorCorrente);
        painelDados.add(lbSensorCorrenteOriginal);
        painelDados.add(tfSensorCorrenteCalibragem);
        painelDados.add(lbSensorCorrenteCorrigido);

        painelCentro.setLayout(new GridLayout(3, 1));
        painelCentro.add(painelDados);

        painelGrafTensao.setBackground(Color.cyan);
        painelCentro.add(painelGrafTensao);
        painelCentro.add(painelGrafCorrente);

        painelResposta.add(lbResposta);
        painelResposta.setLayout(new FlowLayout(FlowLayout.CENTER));

        painelSul.add(taTexto);
        cp.add(painelNorte, BorderLayout.NORTH);
        cp.add(painelCentro, BorderLayout.CENTER);
        cp.add(painelSul, BorderLayout.SOUTH);

        btAmarelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btVermelho.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //acessaArduino.setDataToArduino(acessaArduino.getSerialPort(), "v");
            }
        });

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //antes de sair do sistema, grava os dados da lista em disco
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                calib.clear();
                calib.add(tfSensorTensaoCalibragem.getText());
                calib.add(tfSensorCorrenteCalibragem.getText());
                manipulaArquivo.salvarArquivo("Calibragem.txt", calib);
                System.exit(0);
            }
        });

        setVisible(true);

    }

    public void paint(Graphics g) {
        super.paint(g);
        desenharGrafico(painelGrafTensao, dadosTensao, max);
        desenharGrafico(painelGrafCorrente, dadosCorrente, max);

    }

    public void desenharGrafico(JPanel painel, List<Integer> dados, int max) {
        int tam = painel.getWidth() / max;
        try {
            Graphics p1 = painel.getGraphics();
            int x1 = 0;
            int y1 = painel.getHeight() - 15;
            Point l1 = new Point(x1, y1);;
            Point l2;
            for (int i = 0; i < dados.size(); i++) {
                x1 += tam;
                p1.drawLine(x1, y1, x1, y1 - dados.get(i));
                l2 = new Point(x1, y1 - dados.get(i));
                p1.drawLine(l1.x, l1.y, l2.x, l2.y);
                p1.drawString(String.valueOf(dados.get(i)), l2.x, l2.y - 5);
                p1.drawString(simpleDateFormat.format(new Date()), l1.x - 5, y1 + 15);
                l1 = l2;
            }
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        String aux = acessaArduino.getDadosArduino().trim();

        //lbSensorTensaoOriginal.setText(aux);
        double vc;
        if (!aux.equals("")) {
            String[] s = aux.split("\\|");
            for (int i = 0; i < s.length; i++) {
                aux = s[i];
                String[] a = aux.split(";");
                int sensor = Integer.valueOf(a[0].trim());
                // System.out.println("sensor "+ sensor+ " valor "+a[1].trim());
                double v;
                switch (sensor) {
                    case 1:
                        lbSensorCorrenteOriginal.setText(a[1]);
                        try {
                            v = Double.valueOf(tfSensorCorrenteCalibragem.getText());
                            tfSensorCorrenteCalibragem.setBackground(Color.white);
                        } catch (Exception e) {
                            tfSensorCorrenteCalibragem.setBackground(Color.yellow);
                            v = 0;
                        }
                        vc = Double.valueOf(a[1]) + v;
                        vc = Math.abs(vc);
                        lbSensorCorrenteCorrigido.setText(String.valueOf(numberFormat.format(vc)) + " A");

                        //grafico
                        if (dadosCorrente.size() >= max - 1) {
                            dadosCorrente.remove(0);
                        }

                        int c = (int) vc;
                        dadosCorrente.add(c);
                        repaint();
                        ss = "'" + dtf.format(new Date()) + "',"
                                + "1,"
                                + "'" + String.valueOf(numberFormat.format(vc)) + "'";
                        sql = "INSERT INTO `sistemasensores`.`coleta_dados` "
                                + "(`datahora_coleta_dados`, `sensor_id_sensor`, `dado_coleta_dados`) "
                                + " VALUES (" + ss + ");";
                        //System.out.println("sql " + sql);
                        db.executaAtualizacaoNoBD(sql);
                        break;
                    case 2:
                        lbSensorTensaoOriginal.setText(a[1]);
                        try {
                            v = Double.valueOf(tfSensorTensaoCalibragem.getText());
                            tfSensorTensaoCalibragem.setBackground(Color.white);
                        } catch (Exception e) {
                            tfSensorTensaoCalibragem.setBackground(Color.yellow);
                            v = 0;
                        }
                        vc = Double.valueOf(a[1]) + v;
                        vc = Math.abs(vc);
                        lbSensorTensaoCorrigido.setText(String.valueOf(numberFormat.format(vc)) + " V");

                        if (dadosTensao.size() >= max - 1) {
                            dadosTensao.remove(0);
                        }

                        int t = (int) vc;
                        dadosTensao.add(t);
                        repaint();
                        ss = "'" + dtf.format(new Date()) + "',"
                                + "2,"
                                + "'" + String.valueOf(numberFormat.format(vc)) + "'";
                        sql = "INSERT INTO `sistemasensores`.`coleta_dados` "
                                + "(`datahora_coleta_dados`, `sensor_id_sensor`, `dado_coleta_dados`) "
                                + " VALUES (" + ss + ");";
                        //System.out.println("sql " + sql);
                        db.executaAtualizacaoNoBD(sql);
                        break;

                    default:
                        throw new AssertionError();
                }
            }
            // lbData.setText(dateFormat.format(new Date()));
            // lbHora.setText(timeFormat.format(new Date()));
        }
    }
}
