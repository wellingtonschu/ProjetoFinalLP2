package br.edu.ifcriodosul.conceitual;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Alex Manoel Coelho <alexma_coelho@hotmail.com>
 */

@Entity
public class Leitura implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private float dadosLeitura;

    @ManyToOne
    private Sensor sensor;
    
    @ManyToOne
    private TipoLeitura tipoLeitura;
    
    @ManyToOne
    private Log log;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the dadosLeitura
     */
    public float getDadosLeitura() {
        return dadosLeitura;
    }

    /**
     * @param dadosLeitura the dadosLeitura to set
     */
    public void setDadosLeitura(float dadosLeitura) {
        this.dadosLeitura = dadosLeitura;
    }

    /**
     * @return the sensor
     */
    public Sensor getSensor() {
        return sensor;
    }

    /**
     * @param sensor the sensor to set
     */
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    /**
     * @return the tipoLeitura
     */
    public TipoLeitura getTipoLeitura() {
        return tipoLeitura;
    }

    /**
     * @param tipoLeitura the tipoLeitura to set
     */
    public void setTipoLeitura(TipoLeitura tipoLeitura) {
        this.tipoLeitura = tipoLeitura;
    }

    /**
     * @return the log
     */
    public Log getLog() {
        return log;
    }

    /**
     * @param log the log to set
     */
    public void setLog(Log log) {
        this.log = log;
    }
    
    
}
