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
public class ClaseValidacion {
    
    public static int getNumero(String aux_valor){
        try {
            if (aux_valor.equalsIgnoreCase("")) {aux_valor = "0";}
            return Integer.parseInt(aux_valor);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: "+ e);
            return 0;
        }
    }
    
}
