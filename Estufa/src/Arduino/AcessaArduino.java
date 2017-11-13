package Arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.TooManyListenersException;

public class AcessaArduino extends Observable implements SerialPortEventListener {

    private String portaSelecionada;
    private String tempString = "";
    SerialPort serialPort;
    /**
     * The port we're normally going to use.
     */
    private static final String PORT_NAMES[] = {
        // Mac OS X
        "serial-A9007UX1",
        //linux
        "/dev/tty/usb", "/dev/ttyUSB0", "/dev/ttyUSB1", "/dev/ttyUSB2", "/dev/ttyACM0", "/dev/ttyACM1",
        "/dev/ttyACM2", "/dev/ttyACM3", "/dev/ttyACM4", "/dev/ttyACM5", "/dev/ttyACM6",
        // windows
        "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "COM10", "COM11", "COM12", "COM13"
    };
    /**
     * Buffered input stream from the port
     */
    private BufferedReader input;
    /**
     * The output stream to the port
     */
    private OutputStream output;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;

    public AcessaArduino(Observer observador) {
        addObserver(observador);
        initialize();
    }

    public AcessaArduino() {
        initialize();
    }

    public String getDadosArduino() {
        return tempString;
    }

    public String getPortaSelecionada() {
        return portaSelecionada;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    private void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        portEnum = CommPortIdentifier.getPortIdentifiers();
        // iterate through, looking for the port
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    portaSelecionada = currPortId.getName();
                    // System.out.println("Porta selecionada=>" + currPortId.getName());
                    break;
                }
            }
        }

        if (portId == null) {
            System.out.println("Não encontrou a porta USB destinada ao Arduino");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

        } catch (PortInUseException | UnsupportedCommOperationException | IOException | TooManyListenersException e) {
            System.err.println(e.toString());
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    public void setDataToArduino(SerialPort portaDeComunicacao, String valor) {
        try {
            output.write(valor.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendSingleByte(byte myByte) {
        try {
            output.write(myByte);
            output.flush();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
  
    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                tempString = inputLine;
               // System.out.println("tempString "+tempString);
                notifyObservers(tempString);
                setChanged();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }

}
