/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estufa;

import Arduino.AcessaArduino;
import DAOs.DAOLeitura;
import DAOs.DAOSensor;
import Entidade.Leitura;
import Entidade.Sensor;
import java.util.Date;

/**
 *
 * @author a1136844
 */
public class Estufa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        temp.setIdSensor(2);
//        temp.setNomeSensor("Temperatura");
//        
//        Sensor temp;
//        Leitura leitura = new Leitura();
//        DAOSensor daoSensor = new DAOSensor();
//        temp = daoSensor.obter(1);
//        if (!(temp==null)) {
//        leitura.setSensorIdSensor(temp);
//        leitura.setDataLeitura(new Date());
//        leitura.setValor(100);
//        }
//        DAOLeitura daoLeitura = new DAOLeitura();
//        daoLeitura.inserir(leitura);
////     List<Sensor> arrayList = new ArrayList<Sensor>();
////        arrayList=(daoSensor.listInOrderId());
//        System.out.println("");
//        


        AcessaArduino acessaArduino = new AcessaArduino();
        while (true){
            System.out.println(acessaArduino.getDadosArduino());
            
        }
        
    }
    
}
