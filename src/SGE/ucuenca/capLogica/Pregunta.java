/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SGE.ucuenca.capLogica;

/**
 *
 * @author Jhon
 */
public class Pregunta {
    private int cod_encuesta;
    private int cod_pregunta;
    private String descripcion;

    public Pregunta(int cod_encuesta, int cod_pregunta, String descripcion) {
        this.cod_encuesta = cod_encuesta;
        this.cod_pregunta = cod_pregunta;
        this.descripcion = descripcion;
    }

    /**
     * @return the cod_encuesta
     */
    public int getCod_encuesta() {
        return cod_encuesta;
    }

    /**
     * @param cod_encuesta the cod_encuesta to set
     */
    public void setCod_encuesta(int cod_encuesta) {
        this.cod_encuesta = cod_encuesta;
    }

    /**
     * @return the cod_pregunta
     */
    public int getCod_pregunta() {
        return cod_pregunta;
    }

    /**
     * @param cod_pregunta the cod_pregunta to set
     */
    public void setCod_pregunta(int cod_pregunta) {
        this.cod_pregunta = cod_pregunta;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
