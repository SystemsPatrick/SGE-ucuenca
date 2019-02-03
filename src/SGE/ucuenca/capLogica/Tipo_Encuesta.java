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
public class Tipo_Encuesta {
    private int cod_tipo_encuesta;
    private String nombre;

    public Tipo_Encuesta(int cod_tipo_encuesta, String nombre) {
        this.cod_tipo_encuesta = cod_tipo_encuesta;
        this.nombre = nombre;
    }

    /**
     * @return the cod_tipo_encuesta
     */
    public int getCod_tipo_encuesta() {
        return cod_tipo_encuesta;
    }

    /**
     * @param cod_tipo_encuesta the cod_tipo_encuesta to set
     */
    public void setCod_tipo_encuesta(int cod_tipo_encuesta) {
        this.cod_tipo_encuesta = cod_tipo_encuesta;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
