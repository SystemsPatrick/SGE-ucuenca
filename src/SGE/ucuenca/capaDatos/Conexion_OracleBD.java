/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SGE.ucuenca.capaDatos;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Jhon
 */
public class Conexion_OracleBD {

    private Connection conexion;
    private String Usuario;
    private String password;
   

    public String getUsuario() {
        return Usuario;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Connection Conectar(String getUsuario, String getContraseña) {
        System.out.println("Usuario ingresado: " + getUsuario);
        System.out.println("Contraseña ingresada: " + getContraseña);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:XE";
            conexion = DriverManager.getConnection(BaseDeDatos, getUsuario, getContraseña);
            if (conexion != null) {
                System.out.println("Conexion exitosa a esquema " + getUsuario);
            } else {
                System.out.println("Conexion fallida");
            }
        } catch (Exception e) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + e, "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result == 0) {
                System.out.println("\n=====================\n\nERROR: " + e + "\n=====================\n");
                System.exit(0);
            } else {
                System.out.println("\n=====================\n\nERROR: " + e + "\n=====================\n");
                System.exit(0);
            }
        }

        return this.conexion;
    }

}
