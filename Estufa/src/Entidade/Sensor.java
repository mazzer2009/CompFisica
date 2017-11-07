/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author a1136844
 */
@Entity
@Table(name = "sensor")
@NamedQueries({
    @NamedQuery(name = "Sensor.findAll", query = "SELECT s FROM Sensor s")})
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_sensor")
    private Integer idSensor;
    @Basic(optional = false)
    @Column(name = "nome_sensor")
    private String nomeSensor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sensorIdSensor")
    private List<Leitura> leituraList;

    public Sensor() {
    }

    public Sensor(Integer idSensor) {
        this.idSensor = idSensor;
    }

    public Sensor(Integer idSensor, String nomeSensor) {
        this.idSensor = idSensor;
        this.nomeSensor = nomeSensor;
    }

    public Integer getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Integer idSensor) {
        this.idSensor = idSensor;
    }

    public String getNomeSensor() {
        return nomeSensor;
    }

    public void setNomeSensor(String nomeSensor) {
        this.nomeSensor = nomeSensor;
    }

    public List<Leitura> getLeituraList() {
        return leituraList;
    }

    public void setLeituraList(List<Leitura> leituraList) {
        this.leituraList = leituraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSensor != null ? idSensor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sensor)) {
            return false;
        }
        Sensor other = (Sensor) object;
        if ((this.idSensor == null && other.idSensor != null) || (this.idSensor != null && !this.idSensor.equals(other.idSensor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidade.Sensor[ idSensor=" + idSensor + " ]";
    }
    
}
