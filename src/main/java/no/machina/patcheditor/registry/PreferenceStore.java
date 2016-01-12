/*
 * PreferenceStore.java
 *
 * Created on 18. juni 2004, 10:01
 */

package no.machina.patcheditor.registry;

/**
 *
 * @author  narve
 */
public interface PreferenceStore {

//    public void storeProperties( Properties properties ); 

    public Object getProperty( String key, Object def ); 
    
    public void setProperty( String key, Object value ); 
        
    public String getString( String key ); 

    public String getString( String key, String def ); 

    public String[] getStringArray( String key, String[] def ); 
    
}
