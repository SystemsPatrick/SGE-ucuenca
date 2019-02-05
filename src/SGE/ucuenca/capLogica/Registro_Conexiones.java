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
public class Registro_Conexiones {
    private int cod_registro;
    private String usuario; 
    private String accion; 
    private String fecha_hora_zona; 
    private String codigo;

    public Registro_Conexiones(int cod_registro, String usuario, String accion, String fecha_hora_zona, String codigo) {
        this.cod_registro = cod_registro;
        this.usuario = usuario;
        this.accion = accion;
        this.fecha_hora_zona = fecha_hora_zona;
        this.codigo = codigo;
    }

    /**
     * @return the cod_registro
     */
    public int getCod_registro() {
        return cod_registro;
    }

    /**
     * @param cod_registro the cod_registro to set
     */
    public void setCod_registro(int cod_registro) {
        this.cod_registro = cod_registro;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the accion
     */
    public String getAccion() {
        return accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    /**
     * @return the fecha_hora_zona
     */
    public String getFecha_hora_zona() {
        return fecha_hora_zona;
    }

    /**
     * @param fecha_hora_zona the fecha_hora_zona to set
     */
    public void setFecha_hora_zona(String fecha_hora_zona) {
        this.fecha_hora_zona = fecha_hora_zona;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
    
}
