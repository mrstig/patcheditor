/*
 * FileSystemPreferenceStore.java
 *
 * Created on 18. juni 2004, 10:01
 */

package no.machina.patcheditor.registry;

import java.io.*;
import java.util.*;

/**
 *
 * @author  narve
 */
public abstract class FileSystemPreferenceStore implements PreferenceStore {
    
    protected Map<String, Object> values = new HashMap<String, Object>();
    
    protected final String fileName;
    
    public FileSystemPreferenceStore( String fname ) {
        this.fileName = fname;
        try {
            File f = new File( fileName );
            if( !f.exists() ) {
                store();
            }
            load();
        } catch( IOException ioe ) {
            throw new RuntimeException( "Error loading preferences!", ioe ); 
        }
    }
    
    public Object getProperty(String key, Object def) {
        Object val = values.get( key );
        return val != null ? val : def;
    }
    
    public void setProperty(String key, Object value) {
        values.put( key, value );
        try {
            store();
        } catch( IOException ioe ) {
            throw new RuntimeException( "Error saving preferences!", ioe ); 
        }
    }
    
    public String getString(String key) {
        return getString( key, null ); 
    }
    
    public String[] getStringArray(String key, String[] def) {
        return (String[])getProperty( key, def );
    }
    
    protected abstract void store() throws IOException;
    protected abstract void load()  throws IOException;
    
    public String getString(String key, String def) {
        return (String)getProperty( key, def );
    }
    
}
