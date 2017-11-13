/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author a1136844
 */
@Entity
@Table(name = "leitura")
@NamedQueries({
    @NamedQuery(name = "Leitura.findAll", query = "SELECT l FROM Leitura l")})
public class Leitura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "data_leitura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLeitura;
    @Basic(optional = false)
    @Column(name = "valor")
    private int valor;
    @JoinColumn(name = "sensor_id_sensor", referencedColumnName = "id_sensor")
    @ManyToOne(optional = false)
    private Sensor sensorIdSensor;

    public Leitura() {
    }

    public Leitura(Date dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public Leitura(Date dataLeitura, int valor) {
        this.dataLeitura = dataLeitura;
        this.valor = valor;
    }

    public Date getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(Date dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Sensor getSensorIdSensor() {
        return sensorIdSensor;
    }

    public void setSensorIdSensor(Sensor sensorIdSensor) {
        this.sensorIdSensor = sensorIdSensor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataLeitura != null ? dataLeitura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leitura)) {
            return false;
        }
        Leitura other = (Leitura) object;
        if ((this.dataLeitura == null && other.dataLeitura != null) || (this.dataLeitura != null && !this.dataLeitura.equals(other.dataLeitura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidade.Leitura[ dataLeitura=" + dataLeitura + " ]";
    }
    
}
