package codigoFonte;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author radames
 */
public class JanelaNoCentroDoMaiorMonitor {

    Point p;
    Dimension d;
    JFrame f;
    List<Point> listaDeMonitores = new ArrayList();

    public JanelaNoCentroDoMaiorMonitor(JFrame f) {
        this.f = f;

        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = g.getScreenDevices();

       // System.out.println("total"+Toolkit.getDefaultToolkit().getScreenSize());
        
        for (int i = 0; i < devices.length; i++) {
//            System.out.println("Monitor " + i);
//            System.out.println("Width:" + devices[i].getDisplayMode().getWidth());
//            System.out.println("Height:" + devices[i].getDisplayMode().getHeight());
            listaDeMonitores.add(new Point(devices[i].getDisplayMode().getWidth(), devices[i].getDisplayMode().getHeight()));
        }
    }

    public Point getCentroMonitorMaior() {
        int g = 0;
        int maior = listaDeMonitores.get(0).x * listaDeMonitores.get(g).y;
        int somaX = 0;
        for (int i = g + 1; i < listaDeMonitores.size(); i++) {
            int m = listaDeMonitores.get(i).x * listaDeMonitores.get(i).y;
            somaX += listaDeMonitores.get(i).x;
            if (m > maior) {
                maior = m;
                g = i;
            }
        }
        p = new Point(
                (int) (listaDeMonitores.get(g).x / 2 - this.f.getWidth() / 2)+somaX, 
                (int) (listaDeMonitores.get(g).y / 2 - this.f.getHeight() / 2));
        return p;
    }

}
