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
public class Respuesta_Opcion {
    private int cod_encuesta;
    private int cod_pregunta;
    private int cod_opc_multiple;
    private int cod_usuario;
    private String respuesta;

    public Respuesta_Opcion(int cod_encuesta, int cod_pregunta, int cod_opc_multiple, int cod_usuario, String respuesta) {
        this.cod_encuesta = cod_encuesta;
        this.cod_pregunta = cod_pregunta;
        this.cod_opc_multiple = cod_opc_multiple;
        this.cod_usuario = cod_usuario;
        this.respuesta = respuesta;
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
     * @return the cod_opc_multiple
     */
    public int getCod_opc_multiple() {
        return cod_opc_multiple;
    }

    /**
     * @param cod_opc_multiple the cod_opc_multiple to set
     */
    public void setCod_opc_multiple(int cod_opc_multiple) {
        this.cod_opc_multiple = cod_opc_multiple;
    }

    /**
     * @return the cod_usuario
     */
    public int getCod_usuario() {
        return cod_usuario;
    }

    /**
     * @param cod_usuario the cod_usuario to set
     */
    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    /**
     * @return the respuesta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    
}
