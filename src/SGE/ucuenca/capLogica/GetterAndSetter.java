/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SGE.ucuenca.capLogica;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Jhon
 */
public class GetterAndSetter {
        
    /**
     *
     * @param obj
     * @param fieldName
     * @param value
     */
    public void callSetter(Object obj, String fieldName, Object value) {
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
            pd.getWriteMethod().invoke(obj, value);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    /**
     *
     * @param obj
     * @param fieldName
     * @param value
     */
    public Object callGetter(Object obj, String fieldName) {
        PropertyDescriptor pd;
        Object objValCampo = null;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
            //Obtiene el valor del Campo
            objValCampo = pd.getReadMethod().invoke(obj);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return objValCampo;
    }
    
}
