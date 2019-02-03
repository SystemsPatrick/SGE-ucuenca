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
public class Encuesta {
    private int cod_encuesta;
    private String nombre;
    private String descripcion;
    private int cod_tipo_encuesta;

    public Encuesta(int cod_encuesta, String nombre, String descripcion, int cod_tipo_encuesta) {
        this.cod_encuesta = cod_encuesta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cod_tipo_encuesta = cod_tipo_encuesta;
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
    
    
    
}
