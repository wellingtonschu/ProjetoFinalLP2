package br.edu.ifcriodosul.conceitual;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Alex Manoel Coelho <alexma_coelho@hotmail.com>
 */

@Entity
public class Configuracao implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String nomePlantacao;
    
    private float umidadeDoSoloMin;
    
    private float umidadeDoSoloMax;
    
    private float umidadeDoArMin;
    
    private float umidadeDoArMax;
    
    private float temperaturaMin;
    
    private float temperaturaMax;
    
    @OneToOne
    private Localizacao localizacao;

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
     * @return the nomePlantacao
     */
    public String getNomePlantacao() {
        return nomePlantacao;
    }

    /**
     * @param nomePlantacao the nomePlantacao to set
     */
    public void setNomePlantacao(String nomePlantacao) {
        this.nomePlantacao = nomePlantacao;
    }

    /**
     * @return the umidadeDoSoloMin
     */
    public float getUmidadeDoSoloMin() {
        return umidadeDoSoloMin;
    }

    /**
     * @param umidadeDoSoloMin the umidadeDoSoloMin to set
     */
    public void setUmidadeDoSoloMin(float umidadeDoSoloMin) {
        this.umidadeDoSoloMin = umidadeDoSoloMin;
    }

    /**
     * @return the umidadeDoSoloMax
     */
    public float getUmidadeDoSoloMax() {
        return umidadeDoSoloMax;
    }

    /**
     * @param umidadeDoSoloMax the umidadeDoSoloMax to set
     */
    public void setUmidadeDoSoloMax(float umidadeDoSoloMax) {
        this.umidadeDoSoloMax = umidadeDoSoloMax;
    }

    /**
     * @return the umidadeDoArMin
     */
    public float getUmidadeDoArMin() {
        return umidadeDoArMin;
    }

    /**
     * @param umidadeDoArMin the umidadeDoArMin to set
     */
    public void setUmidadeDoArMin(float umidadeDoArMin) {
        this.umidadeDoArMin = umidadeDoArMin;
    }

    /**
     * @return the umidadeDoArMax
     */
    public float getUmidadeDoArMax() {
        return umidadeDoArMax;
    }

    /**
     * @param umidadeDoArMax the umidadeDoArMax to set
     */
    public void setUmidadeDoArMax(float umidadeDoArMax) {
        this.umidadeDoArMax = umidadeDoArMax;
    }

    /**
     * @return the temperaturaMin
     */
    public float getTemperaturaMin() {
        return temperaturaMin;
    }

    /**
     * @param temperaturaMin the temperaturaMin to set
     */
    public void setTemperaturaMin(float temperaturaMin) {
        this.temperaturaMin = temperaturaMin;
    }

    /**
     * @return the temperaturaMax
     */
    public float getTemperaturaMax() {
        return temperaturaMax;
    }

    /**
     * @param temperaturaMax the temperaturaMax to set
     */
    public void setTemperaturaMax(float temperaturaMax) {
        this.temperaturaMax = temperaturaMax;
    }

    /**
     * @return the localizacao
     */
    public Localizacao getLocalizacao() {
        return localizacao;
    }

    /**
     * @param localizacao the localizacao to set
     */
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }
    
    

}
