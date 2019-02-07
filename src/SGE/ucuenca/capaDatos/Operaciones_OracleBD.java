/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SGE.ucuenca.capaDatos;

import SGE.ucuenca.capLogica.GetterAndSetter;
import SGE.ucuenca.capLogica.Registro_Conexiones;
import SGE.ucuenca.capaGUI.jframe_GestorConsultas;
import static SGE.ucuenca.capaGUI.jframe_GestorConsultas.jTable_visualizarRegistros;
import SGE.ucuenca.capaGUI.jframe_STARTEncuesta;
import SGE.ucuenca.capaGUI.jframe_RealizarEncuesta;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jhon
 */
public class Operaciones_OracleBD {

    public Conexion_OracleBD con = new Conexion_OracleBD();

    public static Connection cn;
    PreparedStatement pst;
    //Consulta
    Statement sql;
    ResultSet rs;

    public static int contadorRegistros;
    public int indiceListaCampos;

    public static String selectStringQuery;

    //Mapa con las Tablas y los Campos de cada uno
    public static Map<String, ArrayList<String>> mapaTablas;
    private static ArrayList<String> nombreArrayList;
    private static Iterator it;

    //Uso de Metodos para Operar con los Objetos Recividos
    GetterAndSetter gs = new GetterAndSetter();

    //Funciones de OperacionesBD en SQL
    //============================================================================
    //Terminado
    public boolean insertarRegistros(Object objeto) throws ParseException {
        //Datos recibidos de la capa de negocios - Instanciado la clase

        //Obtener nombre de clase
        System.out.println("Nombre Clase: " + objeto.getClass().getSimpleName().toUpperCase());
//        cn = con.Conectar(jframe_GestorEncuesta.getjTextField_usuario(), jframe_GestorEncuesta.getjTextField_contraseña());    //Ingresar con los datos de INICIO SESION
        boolean valido = true;
        //NUEVO INSERT ========================================================
        //Pasar valores a Base de Datos
        if (cn == null) {
            System.out.println("ESTA NULL");
        } else {
            System.out.println("NO ESTA NULL");
            try {
                //Obtener Campos del Objeto
                Field[] campos = objeto.getClass().getDeclaredFields();
                String stringSignosPregunta = "";
                for (int i = 0; i < campos.length; i++) {
                    if (i == 0) {
                        stringSignosPregunta = stringSignosPregunta + "?";
                    } else {
                        stringSignosPregunta = stringSignosPregunta + ",?";
                    }
                }
                System.out.println("SIGNOS: " + stringSignosPregunta);
                System.out.println("\n======== VALORES A GUARDAR ========");

                pst = cn.prepareStatement("INSERT INTO ENCUESTA_DB." + objeto.getClass().getSimpleName().toUpperCase() + " VALUES (" + stringSignosPregunta + ") ");
                for (int i = 0; i < campos.length; i++) {
                    String fieldName = campos[i].getName();
                    System.out.println("Nombre Campo: " + fieldName.toUpperCase());

                    //Compara el tipo de dato del campo
                    if (campos[i].getType().toString().toUpperCase().contains("STRING")) {
                        //Obtener Valores de los Campos del Objeto
                        System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                        pst.setString(i + 1, (String) gs.callGetter(objeto, fieldName));
                    }
                    if (campos[i].getType().toString().toUpperCase().contains("INT")) {
                        //Obtener Valores de los Campos del Objeto
                        System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                        pst.setInt(i + 1, (Integer) gs.callGetter(objeto, fieldName));
                    }
                }
                pst.executeUpdate();
                System.out.println("========    GUARDAR ========");
                valido = true;
            } catch (SQLException ex) {
//                Logger.getLogger(Operaciones_OracleBD.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR: " + ex);
                int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex, "Alerta!", JOptionPane.OK_CANCEL_OPTION);
                if (result != 0) {
                    System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
                } else {
                    System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
                }
                valido = false;
            }
        }
        return valido;

        //NUEVO INSERT ========================================================
        //Empleado
//        if (objeto.getClass().getSimpleName().equalsIgnoreCase("USUARIO")){
//            Usuario user = (Usuario) objeto;
//            
//            //Pasar valores a Base de Datos
//            if (cn == null) {
//                System.out.println("ESTA NULL");
//            } else {
//                System.out.println("NO ESTA NULL");
//                try {
//                    
//                    pst = cn.prepareStatement("INSERT INTO USUARIO VALUES (?,?,?,?) ");
//                    pst.setString(1, user.getCod_usuario());
//                    pst.setString(2, user.getNombre());
//                    pst.setString(3, user.getDireccion());
//                    pst.setString(4, user.getTelefono());
//                    pst.executeUpdate();
//                    System.out.println("========    GUARDAR ========");
//                    
//                } catch (SQLException ex) {
//                    Logger.getLogger(Operaciones_OracleBD.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//            
    }

    //Terminado
    public boolean eliminarRegistros(Object objeto) {
        boolean valido = true;
        //NUEVO DELETE ========================================================
        //Obtener nombre de clase
        System.out.println("Nombre Clase: " + objeto.getClass().getSimpleName().toUpperCase());
//        cn = con.Conectar(jframe_GestorEncuesta.getjTextField_usuario(), jframe_GestorEncuesta.getjTextField_contraseña());    //Ingresar con los datos de INICIO SECION

        if (cn == null) {
            System.out.println("ESTA NULL");
        } else {
            System.out.println("NO ESTA NULL");
            try {

                //Obtener Campos del Objeto
                Field[] campos = objeto.getClass().getDeclaredFields();

                String stringCamposSignos = "";
                for (int i = 0; i < campos.length; i++) {
                    //Compara para identificar el campo clave para modificar
                    if (campos[i].getName().toUpperCase().contains("COD")) {
                        System.out.println("Campo_NAME: " + campos[i].getName());
                        stringCamposSignos = stringCamposSignos + campos[i].getName().toUpperCase() + " = ? AND ";
                    }
                }

                stringCamposSignos = stringCamposSignos.substring(0, stringCamposSignos.length() - 5);
                System.out.println("CAMPOS: " + stringCamposSignos);
                System.out.println("\n======== VALORES A ELIMINAR ========");

                pst = cn.prepareStatement("DELETE FROM ENCUESTA_DB." + objeto.getClass().getSimpleName().toUpperCase() + " WHERE " + stringCamposSignos);
                System.out.println("DELETE FROM ENCUESTA_DB." + objeto.getClass().getSimpleName().toUpperCase() + " WHERE " + stringCamposSignos);
                Integer iteradorString = 0;
                for (int i = 0; i < campos.length; i++) {
                    //Compara para identificar el campo clave para modificar
                    if (campos[i].getName().toUpperCase().contains("COD")) {
                        iteradorString++;
                        String fieldName = campos[i].getName();
                        System.out.println("Nombre Campo: " + fieldName.toUpperCase());

                        //Compara el tipo de dato del campo
                        if (campos[i].getType().toString().toUpperCase().contains("STRING")) {
                            //Obtener Valores de los Campos del Objeto
                            System.out.println("-- ITERADOR : " + iteradorString);
                            System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                            pst.setString(iteradorString, (String) gs.callGetter(objeto, fieldName));
                        }
                        if (campos[i].getType().toString().toUpperCase().contains("INT")) {
                            //Obtener Valores de los Campos del Objeto
                            System.out.println("-- ITERADOR : " + iteradorString);
                            System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                            pst.setInt(iteradorString, (Integer) gs.callGetter(objeto, fieldName));
                        }
                    }
                }
                pst.executeUpdate();
                System.out.println("========    DELETE ========");
                valido = true;
            } catch (SQLException ex) {
//                Logger.getLogger(Operaciones_OracleBD.class.getName()).log(Level.SEVERE, null, ex);
                int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex, "Alerta!", JOptionPane.OK_CANCEL_OPTION);
                if (result != 0) {
                    System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
                } else {
                    System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
                }
                valido = false;
            }
        }
        return valido;

        
    }

    //Terminado
    public boolean modificarRegistros(Object objeto) {

        //Obtener nombre de clase
        System.out.println("Nombre Clase: " + objeto.getClass().getSimpleName().toUpperCase());
//        cn = con.Conectar(jframe_GestorEncuesta.getjTextField_usuario(), jframe_GestorEncuesta.getjTextField_contraseña());    //Ingresar con los datos de INICIO SECION
        boolean valido = true;
        //NUEVO UPDATE ========================================================
        if (cn == null) {
            System.out.println("ESTA NULL");
        } else {
            System.out.println("NO ESTA NULL");
            try {
                //Obtener Campos del Objeto
                Field[] campos = objeto.getClass().getDeclaredFields();

                String stringCamposSignos = "";
                String stringCamposSignosCondicion = "";
                Integer iteradorString = 0;
                for (int i = 0; i < campos.length; i++) {
                    //Compara para identificar el campo clave para modificar
                    if (!campos[i].getName().toUpperCase().contains("COD")) {
                        iteradorString++;
//                System.out.println("Campo_NAME: "+ campos[i].getName());
                        stringCamposSignos = stringCamposSignos + campos[i].getName().toUpperCase() + " = ?, ";
                    } else {
//                System.out.println("Campo_NAME+: "+ campos[i].getName());
                        stringCamposSignosCondicion = stringCamposSignosCondicion + campos[i].getName().toUpperCase() + " = ? AND ";
                    }
                }
                Integer iteradorStringCondicion = iteradorString;

                stringCamposSignos = stringCamposSignos.substring(0, stringCamposSignos.length() - 2);
                stringCamposSignosCondicion = stringCamposSignosCondicion.substring(0, stringCamposSignosCondicion.length() - 5);
//                System.out.println("CAMPOS: " + stringCamposSignos);
//                System.out.println("CONDICION: " + stringCamposSignosCondicion);
                System.out.println("\n======== VALORES A ACTUALIZAR ========");

                pst = cn.prepareStatement("UPDATE ENCUESTA_DB." + objeto.getClass().getSimpleName().toUpperCase() + " SET " + stringCamposSignos + " WHERE " + stringCamposSignosCondicion);
                System.out.println("UPDATE ENCUESTA_DB." + objeto.getClass().getSimpleName().toUpperCase() + " SET " + stringCamposSignos + " WHERE " + stringCamposSignosCondicion);
                iteradorString = 0;
                for (int i = 0; i < campos.length; i++) {
                    //Compara para identificar el campo clave para modificar
                    if (!campos[i].getName().toUpperCase().contains("COD")) {
                        iteradorString++;
                        String fieldName = campos[i].getName();
                        System.out.println("Nombre Campo: " + fieldName.toUpperCase());

                        //Compara el tipo de dato del campo
                        if (campos[i].getType().toString().toUpperCase().contains("STRING")) {
                            //Obtener Valores de los Campos del Objeto
                            System.out.println("-- ITERADOR : " + iteradorString);
                            System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                            pst.setString(iteradorString, (String) gs.callGetter(objeto, fieldName));
                        }
                        if (campos[i].getType().toString().toUpperCase().contains("INT")) {
                            //Obtener Valores de los Campos del Objeto
                            System.out.println("-- ITERADOR : " + iteradorString);
                            System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                            pst.setInt(iteradorString, (Integer) gs.callGetter(objeto, fieldName));
                        }
                    } else {
                        iteradorStringCondicion++;
                        String fieldName = campos[i].getName();
                        System.out.println("Nombre Campo: " + fieldName.toUpperCase());

                        //Compara el tipo de dato del campo
                        if (campos[i].getType().toString().toUpperCase().contains("STRING")) {
                            //Obtener Valores de los Campos del Objeto
                            System.out.println("-- ITERADOR : " + iteradorStringCondicion);
                            System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                            pst.setString(iteradorStringCondicion, (String) gs.callGetter(objeto, fieldName));
                        }
                        if (campos[i].getType().toString().toUpperCase().contains("INT")) {
                            //Obtener Valores de los Campos del Objeto
                            System.out.println("-- ITERADOR : " + iteradorStringCondicion);
                            System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                            pst.setInt(iteradorStringCondicion, (Integer) gs.callGetter(objeto, fieldName));
                        }
                    }
                }
                pst.executeUpdate();
                System.out.println("========    ACTUALIZADO ========");
                valido = true;
            } catch (SQLException ex) {
//                Logger.getLogger(Operaciones_OracleBD.class.getName()).log(Level.SEVERE, null, ex);
                int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex, "Alerta!", JOptionPane.OK_CANCEL_OPTION);
                if (result != 0) {
                    System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
                } else {
                    System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
                }
                valido = false;
            }
        }
        return valido;

        
    }

    //============================================================================
    //Terminado VER HR y Clave
    public List<String> metaDatoTablas() {
        List<String> aux_lista = null;
        String query = "SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER = 'ENCUESTA_DB'";
        // Colocar en el Mapa (Inicializo)
        mapaTablas = new HashMap<String, ArrayList<String>>();
        //Inicio de Metadato - Tablas
        cn = con.Conectar("ENCUESTA_DB", "12345");
        try {
            aux_lista = new ArrayList<String>();
            //Crea el objeto Statement
            Statement stmt = cn.createStatement();
            //Ejectuta la query
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                mapaTablas.put((String) rs.getObject(1), null);
                aux_lista.add((String) rs.getObject(1));
            }
        } catch (SQLException ex) {
//            System.out.println("ERROR: "+ ex.getMessage());
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex, "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }
        return aux_lista;
    }

    //Terminado VER HR y Clave
    public static ObservableList<Object> metaDatoCamposTabla(String aux_tablaSelect) {

        nombreArrayList = new ArrayList<String>();
        ObservableList<Object> datos = FXCollections.observableArrayList();
        //Inicio de Metadato - Campos
//        cn = con.Conectar(jframe_inicioEncuesta.getjTextField_usuario(), jframe_inicioEncuesta.getjTextField_contraseña()); // "HR", "1234"
        try {
//Crea el objeto Statement
            String query = "SELECT * FROM ENCUESTA_DB." + aux_tablaSelect.toUpperCase();
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // The column count starts from 1
            for (int i = 1; i <= columnCount; i++) {
                String name = rsmd.getColumnName(i);
                // Do stuff with name
                nombreArrayList.add(name);
                mapaTablas.put(aux_tablaSelect, nombreArrayList);
                datos.add(aux_tablaSelect.toUpperCase() + "." + name);
            }
        } catch (SQLException ex) {
//            System.out.println("ERROR: "+ ex.getMessage());
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex, "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }
        return datos;
    }

    //Muestra solo el Mapa con Tablas_Campos
    public static void mostrarMapa_Tablas_Campos() {
        // MOSTRAR en Consola el Mapa =========================================
        // Imprimimos el Map con un Iterador y Lista
        System.out.println("\n======== TABLAS - CAMPOS ========\n");
        it = mapaTablas.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            System.out.println("Nom_Tablas (Clave): " + key);

            if (mapaTablas.get(key) != null) {
                for (int i = 0; i < mapaTablas.get(key).size(); i++) {
                    System.out.println("-- Campos_Tablas (Valor): " + mapaTablas.get(key).get(i));
                }
            } else {
                System.out.println("\tCampos_Tablas (Valor): NULL");
            }
        }
    }

    //============================================================================
    //Terminado
    public void consultarRegistros(String aux_campos, String aux_tabla, String aux_stringCodicion) throws SQLException {   //Retornaria un Objeto

        selectStringQuery = "";
        sql = cn.createStatement();
        contadorRegistros = 0;

        selectStringQuery = "SELECT " + aux_campos + " FROM " + aux_tabla + " " + aux_stringCodicion;
        rs = sql.executeQuery(selectStringQuery);

        //if para todos los registros ( * )
        if (aux_campos.equals("*")) {
            funcion_mostrarTodosRegistros(mapaTablas.get(aux_tabla));
        } else {
            funcion_mostrarRegistrosCiertosCampos(mapaTablas.get(aux_tabla));
        }

    }

    //Terminado
    public void funcion_mostrarTodosRegistros(ArrayList<String> aux_campoTablas) throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel();
        Object[] varMostrarConsulta;
        int crearCabecera = 0;

        while (rs.next()) {
            varMostrarConsulta = new Object[50];
            indiceListaCampos = 0;
            for (int i = 0; i < aux_campoTablas.size(); i++) {
                varMostrarConsulta[i] = rs.getString(aux_campoTablas.get(indiceListaCampos));

                if (crearCabecera < aux_campoTablas.size()) {
                    //Agrega las columnas en jtable
                    modelo.addColumn(aux_campoTablas.get(indiceListaCampos));
                    crearCabecera++;
                }

                System.out.println("Registro: " + (indiceListaCampos + 1) + ":  " + rs.getString(aux_campoTablas.get(indiceListaCampos)));
                indiceListaCampos++;
            }
            //Agrga el registro a jtable
            modelo.addRow(varMostrarConsulta);
            contadorRegistros++;
        }
        //Modificar la tabla
        jTable_visualizarRegistros.setModel(modelo);
        System.out.println("Numero de Registros: " + contadorRegistros);

    }

    //Terminado
    public void funcion_mostrarRegistrosCiertosCampos(List<String> aux_campoTablas) throws SQLException {
        DefaultTableModel modelo = null;
        modelo = new DefaultTableModel();
        // (aux_camposAgregados) estan los campos obtenidos en la Interfaz
        List aux_camposAgregados = jframe_GestorConsultas.camposAgregados;
        Object[] varMostrarConsulta = null;

        //Pasar de Object a String para tener el tamaño del vactor con valores
//        String aux_camposAgregadoString = (String)aux_camposAgregados;
        int crearCabecera = 0;
        while (rs.next()) {
            varMostrarConsulta = new Object[50];
            indiceListaCampos = 0;
            for (int i = 0; i < aux_camposAgregados.size(); i++) {
                varMostrarConsulta[i] = rs.getString(aux_camposAgregados.get(indiceListaCampos).toString());

                if (crearCabecera < aux_camposAgregados.size()) {
                    //Agrega las columnas en jtable
                    modelo.addColumn(aux_camposAgregados.get(indiceListaCampos).toString());
                    crearCabecera++;
                }

                System.out.println("Registro: " + (indiceListaCampos + 1) + ":  " + rs.getString(aux_camposAgregados.get(indiceListaCampos).toString()));
                indiceListaCampos++;
            }
            //Agregas el registro (Fila) a jtable - Fila de OBJECT
            modelo.addRow(varMostrarConsulta);
            contadorRegistros++;
        }
        //Modificar la tabla
        jTable_visualizarRegistros.setModel(modelo);
        System.out.println("Numero de Registros: " + contadorRegistros);

    }

    //REALIZAR CONSULTA
    //============================================================================
    //Terminado VER HR y Clave
    public void metaDatoTablasJtable() {
        // Colocar en el Mapa (Inicializo)
        mapaTablas = new HashMap<String, ArrayList<String>>();
        //Inicio de Metadato - Tablas
//        cn = con.Conectar("usuario_jhon", "1234"); // "HR", "1234" //"usuario_jhon", "1234"
        DatabaseMetaData metaDatos;
        try {
            metaDatos = cn.getMetaData();
            ResultSet rs = metaDatos.getTables(null, "ENCUESTA_DB"/*PONER MAYUSCULA*/, "%", null); // null, "HR"/*PONER MAYUSCULA*/, "%", null // null, "USUARIO_JHON"/*PONER MAYUSCULA*/, "%", null

            while (rs.next()) {
                // 3 = Nombre de Tabla y 4 = tipo de tabla
                String nombre_tabla = rs.getString(3);
                String tipo_tabla = rs.getString(4);

                if (tipo_tabla.equalsIgnoreCase("TABLE")) {
                    // Colocar en el Mapa
                    mapaTablas.put(nombre_tabla, null);
                }
            }
        } catch (SQLException ex) {
//            System.out.println("ERROR: "+ ex.getMessage());
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex, "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }

    }

    //Terminado VER HR y Clave
    public void metaDatoCamposTablaJtable(String aux_tablaSelect) {

        nombreArrayList = new ArrayList<String>();

        //Inicio de Metadato - Campos
//        cn = con.Conectar("usuario_jhon", "1234"); // "HR", "1234"
        DatabaseMetaData metaDatos;
        try {
            metaDatos = cn.getMetaData();
            ResultSet rs1 = metaDatos.getColumns("ENCUESTA_DB"/*PONER MAYUSCULAS*/, null, aux_tablaSelect, null); // "HR"/*PONER MAYUSCULAS*/, null, aux_tablaSelect, null
            while (rs1.next()) {
                // 3 = Nombre de Tabla y 4 = Campos, 5 = tamaño campo, 6 = tipo de dato SQL
//                String tipoDato_tabla = rs1.getString(6);
                String nombre_campo = rs1.getString(4);
                // Agrega los campos a la clave de la tabla seleccionada
                nombreArrayList.add(nombre_campo);
                mapaTablas.put(aux_tablaSelect, nombreArrayList);

            }
        } catch (SQLException ex) {
//            System.out.println("ERROR: "+ ex.getMessage());
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex, "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }

    }

    //============================================================================
    //Terminado
    public DefaultTableModel consultarRegistrosJTable(String aux_campos, String aux_tabla, String aux_stringCodicion) throws SQLException {   //Retornaria un Objeto

        selectStringQuery = "";
        sql = cn.createStatement();
        contadorRegistros = 0;

        selectStringQuery = "SELECT " + aux_campos + " FROM " + aux_tabla + " " + aux_stringCodicion;
        rs = sql.executeQuery(selectStringQuery);

        DefaultTableModel modelo = new DefaultTableModel();
        //if para todos los registros ( * )
        if (aux_campos.equals("*")) {
            modelo = funcion_mostrarTodosRegistrosJtabble(mapaTablas.get(aux_tabla));
        } else {
            modelo = funcion_mostrarRegistrosCiertosCamposJtable();
        }
        return modelo;
    }

    //Terminado
    public DefaultTableModel funcion_mostrarTodosRegistrosJtabble(ArrayList<String> aux_campoTablas) throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel();
        Object[] varMostrarConsulta;
        int crearCabecera = 0;

        while (rs.next()) {
            varMostrarConsulta = new Object[50];
            indiceListaCampos = 0;
            for (int i = 0; i < aux_campoTablas.size(); i++) {
                varMostrarConsulta[i] = rs.getString(aux_campoTablas.get(indiceListaCampos));

                if (crearCabecera < aux_campoTablas.size()) {
                    //Agrega las columnas en jtable
                    modelo.addColumn(aux_campoTablas.get(indiceListaCampos));
                    crearCabecera++;
                }

                System.out.println("Registro: " + (indiceListaCampos + 1) + ":  " + rs.getString(aux_campoTablas.get(indiceListaCampos)));
                indiceListaCampos++;
            }
            //Agrga el registro a jtable
            modelo.addRow(varMostrarConsulta);
            contadorRegistros++;
        }
        //Modificar la tabla
//        jTable_visualizarRegistros.setModel(modelo);
        System.out.println("Numero de Registros: " + contadorRegistros);
        return modelo;
    }
//metodo creado por patrick 

    public ObservableList<Object[]> registros(String consulta, int numcol) {
        ObservableList<Object[]> datos = FXCollections.observableArrayList();
        try {
            sql = cn.createStatement();
            rs = sql.executeQuery(consulta);
            while (rs.next()) {
                Object[] aux = new Object[numcol];
                for (int i = 0; i < numcol; i++) {
                    aux[i] = (rs.getString(i + 1));
                }
                datos.add(aux);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return datos;
    }

    //Terminado
    public DefaultTableModel funcion_mostrarRegistrosCiertosCamposJtable() throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel();
        // (aux_camposAgregados) estan los campos obtenidos en la Interfaz
        List aux_camposAgregados = jframe_RealizarEncuesta.camposEnJTable;
        Object[] varMostrarConsulta = null;

        //Pasar de Object a String para tener el tamaño del vactor con valores
//        String aux_camposAgregadoString = (String)aux_camposAgregados;
        int crearCabecera = 0;
        while (rs.next()) {
            varMostrarConsulta = new Object[50];
            indiceListaCampos = 0;
            for (int i = 0; i < aux_camposAgregados.size(); i++) {
                varMostrarConsulta[i] = rs.getString(aux_camposAgregados.get(indiceListaCampos).toString());

                if (crearCabecera < aux_camposAgregados.size()) {
                    //Agrega las columnas en jtable
                    modelo.addColumn(aux_camposAgregados.get(indiceListaCampos).toString());
                    crearCabecera++;
                }

                System.out.println("Registro: " + (indiceListaCampos + 1) + ":  " + rs.getString(aux_camposAgregados.get(indiceListaCampos).toString()));
                indiceListaCampos++;
            }
            //Agregas el registro (Fila) a jtable - Fila de OBJECT
            modelo.addRow(varMostrarConsulta);
            contadorRegistros++;
        }
        //Modificar la tabla
//        jTable_visualizarRegistros.setModel(modelo);
        System.out.println("Numero de Registros: " + contadorRegistros);
        return modelo;
    }

    //Manejo de Commit y Rollback
    public void startCommitRollback(boolean aux_valor) throws SQLException {
//        cn = con.Conectar(jframe_inicioEncuesta.getjTextField_usuario(), jframe_inicioEncuesta.getjTextField_contraseña());    //Ingresar con los datos de INICIO SESION
        if (aux_valor) {    // Si es true COMMIT
            cn.commit();
        } else {            // Si es false ROLLBACK
            cn.rollback();
        }
    }

    public ObservableList<Object[]> reg() {
        return null;
    }

    //=========================================================================
    //  MANEJO DE USUARIOS
    //=========================================================================
    //Creacion de Usuarios
    public boolean crearUsuariosSQL(String nuevoUsuario, String nuevaContraseña, int valorSelectRol) {
        boolean valido = true;
        try {
//            cn = con.Conectar(jframe_administrarUsuarios.getUsuario(), jframe_administrarUsuarios.getContraseña());
            sql = cn.createStatement();
            //Creacion de usuario
            String crearUsuario = "CREATE USER " + nuevoUsuario + " IDENTIFIED BY " + nuevaContraseña + " DEFAULT TABLESPACE MyTablespace TEMPORARY TABLESPACE TEMP ACCOUNT UNLOCK";
            System.out.println("Sentencia CREATE USER:  " + crearUsuario);
            rs = sql.executeQuery(crearUsuario);

            //Asignar permisos de Conexion
            String crearRolConnect = "GRANT CONNECT TO " + nuevoUsuario;
            String crearRolResource = "GRANT RESOURCE TO " + nuevoUsuario;
            System.out.println("Sentencia GRANT CONNECT:  " + crearRolConnect);
            System.out.println("Sentencia GRANT RESOURCE:  " + crearRolResource);
            rs = sql.executeQuery(crearRolConnect);
            rs = sql.executeQuery(crearRolResource);

            //Condicion para asignar ROLES Administradores o Encuestadores
            // 0 = Admin, 1 = Encuestador
            if (valorSelectRol == 0) {
                //GRANT dba TO rol_administrador;
                System.out.println("\tROL DE ADMIN");
                String crearRolDBA = "GRANT rol_administrador TO " + nuevoUsuario;
                rs = sql.executeQuery(crearRolDBA);
            }
            if (valorSelectRol == 1) {
                //GRANT create session, delete any table, insert any table, select any table, update any table TO rol_encuestador;
                System.out.println("\tROL DE ENCUESTADOR - Privilegios de Objetos");
                String rolToTablas = "GRANT execute any PROCEDURE TO " + nuevoUsuario;
                rs = sql.executeQuery(rolToTablas);
                rolToTablas = "GRANT insert, delete, update, select ON USUARIO TO " + nuevoUsuario;
                rs = sql.executeQuery(rolToTablas);
                rolToTablas = "GRANT insert, delete, update, select ON TIPO_ENCUESTA TO " + nuevoUsuario;
                rs = sql.executeQuery(rolToTablas);
                rolToTablas = "GRANT insert, delete, update, select ON ENCUESTA TO " + nuevoUsuario;
                rs = sql.executeQuery(rolToTablas);
                rolToTablas = "GRANT insert, delete, update, select ON PREGUNTA TO " + nuevoUsuario;
                rs = sql.executeQuery(rolToTablas);
                rolToTablas = "GRANT insert, delete, update, select ON P_OPMULTIPLE TO " + nuevoUsuario;
                rs = sql.executeQuery(rolToTablas);
                //ROL DE VISTAS
//                rolToTablas = "GRANT insert, delete, update, select ON P_OPMULTIPLE TO " + nuevoUsuario;
//                rs = sql.executeQuery(rolToTablas);
            }
            if (valorSelectRol == 2) {
                //GRANT create session, insert, select TO rol_cliente;
                System.out.println("\tROL DE CLIENTE - Privilegios de Objetos");
                String rolToTablas = "GRANT execute any PROCEDURE TO " + nuevoUsuario;
                rs = sql.executeQuery(rolToTablas);
                rolToTablas = "GRANT insert, select ON RESPUESTA_TEXTO TO " + nuevoUsuario;
                rs = sql.executeQuery(rolToTablas);
                rolToTablas = "GRANT insert, select ON RESPUESTA_OPCION TO " + nuevoUsuario;
                rs = sql.executeQuery(rolToTablas);

            }

            System.out.println("========    GUARDAR ========");
            valido = true;
            //Condicion para asignar ROLES Administradores o Encuestadores
        } catch (SQLException ex) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
            valido = false;
        }
        return valido;
    }

    //Eliminar Usuario
    public boolean eliminarUsuariosSQL(String nuevoUsuario) {
        boolean valido = true;
        try {
//            cn = con.Conectar(jframe_administrarUsuarios.getUsuario(), jframe_administrarUsuarios.getContraseña());
            sql = cn.createStatement();
            //Creacion de usuario
            String eliminarUsuario = "DROP USER " + nuevoUsuario + " CASCADE";
            System.out.println("Sentencia DROP USER:  " + eliminarUsuario);
            rs = sql.executeQuery(eliminarUsuario);
            System.out.println("========    ELIMINADO ========");
            valido = true;
        } catch (SQLException ex) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
            valido = false;
        }
        return valido;
    }

    //Obtener para el Jlist Usuarios
    public List<String> agregarUsuariosJlist() {
        List<String> aux_lista = null;
        try {
//            cn = con.Conectar(jframe_administrarUsuarios.getUsuario(), jframe_administrarUsuarios.getContraseña());
            aux_lista = new ArrayList<String>();
            String todosUsuarios;
            sql = cn.createStatement();
            selectStringQuery = "SELECT * FROM ALL_USERS";
            rs = sql.executeQuery(selectStringQuery);

            while (rs.next()) {
                todosUsuarios = rs.getString("USERNAME");
                aux_lista.add(todosUsuarios);
            }

        } catch (SQLException ex) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }
        return aux_lista;
    }

    //Obtener para el Jlist Privilegios
    public List<String> agregarPrivilegiosJlist(String aux_usuarioSeleccionado) {
        List<String> aux_lista = null;
        try {
            aux_lista = new ArrayList<String>();
//            cn = con.Conectar(jframe_administrarUsuarios.getUsuario(), jframe_administrarUsuarios.getContraseña());
            String todosPrivilegios;
            sql = cn.createStatement();
            selectStringQuery = "SELECT * FROM DBA_SYS_PRIVS WHERE GRANTEE = " + aux_usuarioSeleccionado.toUpperCase();
            System.out.println("PRIVILEGIOS:    " + selectStringQuery);
            rs = sql.executeQuery(selectStringQuery);

            while (rs.next()) {
                todosPrivilegios = rs.getString("PRIVILEGE");
                aux_lista.add(todosPrivilegios);
            }

        } catch (SQLException ex) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }
        return aux_lista;
    }

    //Agregar Privolegios
    public void agregarPrivilegios(String aux_privilegios, String aux_usuarioSelec) {
        try {
            selectStringQuery = "";
//            cn = con.Conectar(jframe_administrarUsuarios.getUsuario(), jframe_administrarUsuarios.getContraseña());
            sql = cn.createStatement();
            selectStringQuery = "GRANT " + aux_privilegios + " TO " + aux_usuarioSelec;
            System.out.println("Asisgar privilegios: " + selectStringQuery);
            rs = sql.executeQuery(selectStringQuery);
        } catch (SQLException ex) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }
    }

    //Quitar Privilegios
    public void quitarPrivilegios(String aux_privilegios, String aux_usuarioSelec) {
        try {
            selectStringQuery = "";
            sql = cn.createStatement();
            selectStringQuery = "REVOKE " + aux_privilegios + " FROM " + aux_usuarioSelec;
            System.out.println("Asisgar privilegios: " + selectStringQuery);
            rs = sql.executeQuery(selectStringQuery);
            selectStringQuery = "";
        } catch (SQLException ex) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }
    }

    //SIN USAR
    //Obtener para el Jlist (Privilegios del Rol)
    public List<String> agregarRolesJlist(String aux_tipoRoles) {
        List<String> aux_lista = null;
        try {
            aux_lista = new ArrayList<String>();
//            cn = con.Conectar(jframe_administrarUsuarios.getUsuario(), jframe_administrarUsuarios.getContraseña());
            String todosPrivilegios;
            sql = cn.createStatement();
            selectStringQuery = "SELECT * FROM DBA_SYS_PRIVS WHERE GRANTEE = " + aux_tipoRoles.toUpperCase() + " ORDER BY GRANTEE";
            System.out.println("PRIVILEGIOS:    " + selectStringQuery);
            rs = sql.executeQuery(selectStringQuery);

            while (rs.next()) {
                todosPrivilegios = rs.getString("PRIVILEGE");
                aux_lista.add(todosPrivilegios);
            }

        } catch (SQLException ex) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }
        return aux_lista;
    }

    //Obtener para el Jlist Roles de Usuario Seleccionado
    public List<String> agregarRolesUsuarioSelect(String aux_nombreUsuario) {
        List<String> aux_lista = null;
        try {
            aux_lista = new ArrayList<String>();
//            cn = con.Conectar(jframe_administrarUsuarios.getUsuario(), jframe_administrarUsuarios.getContraseña());
            String todosRoles;
            sql = cn.createStatement();
            selectStringQuery = "SELECT * FROM DBA_ROLE_PRIVS WHERE GRANTEE = " + aux_nombreUsuario.toUpperCase();
            System.out.println("PRIVILEGIOS:    " + selectStringQuery);
            rs = sql.executeQuery(selectStringQuery);

            while (rs.next()) {
                todosRoles = rs.getString("GRANTED_ROLE");
                aux_lista.add(todosRoles);
            }

        } catch (SQLException ex) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }
        return aux_lista;
    }
    
    //=========================================================================
    //  MANEJO DE TRIGGERS y PROCEDIMIENTOS
    //=========================================================================
    //Obtener Registros de Conexiones de ENCUESTA_DB
    public Object[] recuperarRegistros() {
        List<String> aux_listaUsuario = null;
        List<String> aux_listaAction = null;
        List<String> aux_listaCodigo = null;
        List<String> aux_listaTimeStandExt = null;
        Object[] aux_arrayList = null;
        
        try {
            aux_listaUsuario = new ArrayList<String>();
            aux_listaAction = new ArrayList<String>();
            aux_listaTimeStandExt = new ArrayList<String>();
            aux_listaCodigo = new ArrayList<String>();
            aux_arrayList = new Object[4];
            
            cn = con.Conectar("ENCUESTA_DB", "12345");
            String usuariosRec;
            String actionRec;
            String extendTimeRec;
            String codigoRec;
            sql = cn.createStatement();
            selectStringQuery = "SELECT USERNAME, ACTION_NAME, EXTENDED_TIMESTAMP, RETURNCODE FROM DBA_AUDIT_SESSION";
            System.out.println("PRIVILEGIOS:    " + selectStringQuery);
            rs = sql.executeQuery(selectStringQuery);

            while (rs.next()) {
                usuariosRec = rs.getString("USERNAME");
                aux_listaUsuario.add(usuariosRec);
                actionRec = rs.getString("ACTION_NAME");
                aux_listaAction.add(actionRec);
                extendTimeRec = rs.getString("EXTENDED_TIMESTAMP");
                aux_listaTimeStandExt.add(extendTimeRec);
                codigoRec = rs.getString("RETURNCODE");
                aux_listaCodigo.add(codigoRec);
            }
            
            aux_arrayList[0] = aux_listaUsuario;
            aux_arrayList[1] = aux_listaAction;
            aux_arrayList[2] = aux_listaTimeStandExt;
            aux_arrayList[3] = aux_listaCodigo;

        } catch (SQLException ex) {
            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
            if (result != 0) {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            } else {
                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
            }
        }
        return aux_arrayList;
    }
    
    //Insertar Registros Conexiones
    public boolean insertar(Object objeto) throws ParseException {
        //Datos recibidos de la capa de negocios - Instanciado la clase

        //Obtener nombre de clase
//        System.out.println("Nombre Clase: " + objeto.getClass().getSimpleName().toUpperCase());
//        cn = con.Conectar(jframe_GestorEncuesta.getjTextField_usuario(), jframe_GestorEncuesta.getjTextField_contraseña());    //Ingresar con los datos de INICIO SESION
        boolean valido = true;
        //NUEVO INSERT ========================================================
        //Pasar valores a Base de Datos
        if (cn == null) {
            System.out.println("ESTA NULL");
        } else {
//            System.out.println("NO ESTA NULL");
            try {
                //Obtener Campos del Objeto
                Field[] campos = objeto.getClass().getDeclaredFields();
                String stringSignosPregunta = "";
                for (int i = 0; i < campos.length; i++) {
                    if (i == 0) {
                        stringSignosPregunta = stringSignosPregunta + "?";
                    } else {
                        stringSignosPregunta = stringSignosPregunta + ",?";
                    }
                }
//                System.out.println("SIGNOS: " + stringSignosPregunta);
//                System.out.println("\n======== VALORES A GUARDAR ========");

                pst = cn.prepareStatement("INSERT INTO ENCUESTA_DB." + objeto.getClass().getSimpleName().toUpperCase() + " VALUES (" + stringSignosPregunta + ") ");
                for (int i = 0; i < campos.length; i++) {
                    String fieldName = campos[i].getName();
//                    System.out.println("Nombre Campo: " + fieldName.toUpperCase());

                    //Compara el tipo de dato del campo
                    if (campos[i].getType().toString().toUpperCase().contains("STRING")) {
                        //Obtener Valores de los Campos del Objeto
//                        System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                        pst.setString(i + 1, (String) gs.callGetter(objeto, fieldName));
                    }
                    if (campos[i].getType().toString().toUpperCase().contains("INT")) {
                        //Obtener Valores de los Campos del Objeto
//                        System.out.println("Valor Campo: " + gs.callGetter(objeto, fieldName));
                        pst.setInt(i + 1, (Integer) gs.callGetter(objeto, fieldName));
                    }
                }
                pst.executeUpdate();
//                System.out.println("========    GUARDAR ========");
                valido = true;
            } catch (SQLException ex) {
                System.out.println("NOTA: " + ex.getMessage());
                valido = false;
            }
        }
        return valido;   
    }

    //Procedimiento para Contraseña
    public boolean ejecutar_procPassword(String aux_password) throws ParseException {
        
        boolean valido = true;
        if (cn == null) {
//            cn = con.Conectar("ENCUESTA_DB", "12345");
            System.out.println("ESTA NULL");
            try {
                pst = cn.prepareStatement("CALL ENCUESTA_DB.PROCEDURE_PASSWORD(?)");
                pst.setString(1, aux_password);
                pst.executeUpdate();
                System.out.println("========    ejecutar_procPassword ========");
                valido = true;
                
                //Consulta para NumRegistros
                sql = cn.createStatement();
                selectStringQuery = "select * from intentos_password";
                rs = sql.executeQuery(selectStringQuery);
                int contador_procPassword = 0;
                while (rs.next()) {
                    contador_procPassword++;
                }
                metodo_interno();
                //Actualizar la CLave
                pst = cn.prepareStatement("UPDATE Intentos_Password SET cod_intentosPasw = ? where cod_intentosPasw = ?");
                pst.setInt(1, contador_procPassword);
                pst.setInt(2, 0);
                pst.executeUpdate();
                
            } catch (SQLException ex) {
                System.out.println("NOTA PROC::: " + ex.getMessage());
                valido = false;
            }
        } else {
            System.out.println("NO ESTA NULL: PROC_NO");
        }
        return valido;   
    }
    
    //Uso de insertar() para Registros Conexiones
    public void metodo_interno(){
        Object[] vector = recuperarRegistros();
        System.out.println("--->"+vector);
        List<String> aux_lista1 = (List<String>) vector[0];
        List<String> aux_lista2 = (List<String>) vector[1];
        List<String> aux_lista3 = (List<String>) vector[2];
        List<String> aux_lista4 = (List<String>) vector[3];
        
        Registro_Conexiones obj;
        for (int i = aux_lista1.size()-4; i < aux_lista1.size(); i++) {
//            System.out.println(aux_lista1.get(i) +"  :::  "+ aux_lista2.get(i)+"  :::  "+ aux_lista3.get(i));
            obj = new Registro_Conexiones(i+1,aux_lista1.get(i), aux_lista2.get(i), aux_lista3.get(i), aux_lista4.get(i));
            try {
                insertar(obj);
            } catch (ParseException ex) {
                System.out.println("ERROR: "+ ex.getMessage());
            }
        }
        //Commit
        try {
            // TODO add your handling code here:
            startCommitRollback(true);
        } catch (SQLException ex) {
            System.out.println("ERROR: "+ ex);
        }
    }
    
    //Procedimiento para Validar la Cedula
    public int proced_validacionCedula(String aux_cedula) {
        
        int valorRetorno = 0;
//        cn = con.Conectar("ENCUESTA_DB", "12345");
        if (cn == null) {
            System.out.println("ESTA NULL");
        } else {
            System.out.println("NO ESTA NULL: PROC_NO");
            try {
                CallableStatement cStmt = cn.prepareCall("{call ENCUESTA_DB.validarCedulaUsuario(?, ?)}");
                cStmt.setString(1, aux_cedula);
                cStmt.registerOutParameter(2, Types.INTEGER);
                cStmt.execute();
                
                valorRetorno = cStmt.getInt(2);
                System.out.println("RETORNO_VALIDACION: ======== : "+ valorRetorno);
                cStmt.close();
                return valorRetorno;
                
            } catch (SQLException ex) {
                System.out.println("ERROR PROC VALIDACION::: " + ex);
                return 0;
            }
        }
        return valorRetorno;   
    }
    
    //Procedimiento para Validar la Cedula
    public int proced_add_datos_usuario_encuesta(String aux_cod_usuario, String aux_cod_encuesta) {
        
        int valorRetorno = 0;
//        cn = con.Conectar("ENCUESTA_DB", "12345");
        if (cn == null) {
            System.out.println("ESTA NULL");
        } else {
            System.out.println("NO ESTA NULL: PROC_");
            try {
                
                CallableStatement cStmt = cn.prepareCall("{call ENCUESTA_DB.add_datos_usuario_encuesta(?,?,?)}");
                cStmt.setString(1, aux_cod_usuario);
                cStmt.setString(2, aux_cod_encuesta);
                cStmt.registerOutParameter(3, Types.INTEGER);
                cStmt.execute();
                
                valorRetorno = cStmt.getInt(3);
                System.out.println("RETORNO_ADD_DATOS: ======== : "+ valorRetorno);
                cStmt.close();
                
                return valorRetorno;
            } catch (SQLException ex) {
                System.out.println("NOTA PROC ADD_USER_ENCUESTA::: " + ex.getMessage());
                return 0;
            }
        }
        return valorRetorno;   
    }
    
    //Procedimiento para Validar Respuesta Numerica
    public void proced_validaRespuesta(String aux_cod_encuesta, String aux_cod_pregunta, String aux_cod_usuario, String aux_respuesta) {
        
//        cn = con.Conectar("ENCUESTA_DB", "12345");
        if (cn == null) {
            System.out.println("ESTA NULL");
        } else {
            System.out.println("NO ESTA NULL: PROC_validaRespuesta");
            try {
                
                CallableStatement cStmt = cn.prepareCall("{call ENCUESTA_DB.VALIDARRESPUESTA(?,?,?,?)}");
                cStmt.setString(1, aux_cod_encuesta);
                cStmt.setString(2, aux_cod_pregunta);
                cStmt.setString(3, aux_cod_usuario);
                cStmt.setString(4, aux_respuesta);
                cStmt.execute();
                cStmt.close();
                
            } catch (SQLException ex) {
                System.out.println("ERROR: PROC validaRespuesta::: " + ex.getMessage());
            }
        }  
    }
    
    //Obtener registros para Tabla en VISTAS
//    public Object[] recuperarRegistrosVISTAS() {
//        Object[] aux_arrayList = null;
//        
//        try {
//            aux_arrayList = new Object[15];
//            cn = con.Conectar("ENCUESTA_DB", "12345");
//            sql = cn.createStatement();
//            selectStringQuery = "SELECT USERNAME, ACTION_NAME, EXTENDED_TIMESTAMP, RETURNCODE FROM DBA_AUDIT_SESSION";
//            System.out.println("PRIVILEGIOS:    " + selectStringQuery);
//            rs = sql.executeQuery(selectStringQuery);
//
//            int i = 0;
//            while (rs.next()) {
//                aux_arrayList[0] = rs.getString("USERNAME");
//                actionRec = rs.getString("ACTION_NAME");
//                extendTimeRec = rs.getString("EXTENDED_TIMESTAMP");
//                codigoRec = rs.getString("RETURNCODE");
//            }
//
//        } catch (SQLException ex) {
//            int result = JOptionPane.showConfirmDialog(null, "MENSAJE: \n\n" + ex.getMessage(), "Alerta!", JOptionPane.OK_CANCEL_OPTION);
//            if (result != 0) {
//                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
//            } else {
//                System.out.println("\n=====================\n\nERROR: " + ex + "\n=====================\n");
//            }
//        }
//        return aux_arrayList;
//    }
    
}
