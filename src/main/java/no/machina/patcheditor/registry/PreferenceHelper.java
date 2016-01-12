/*
 * PreferenceHelper.java
 *
 * Created on 18. juni 2004, 09:59
 */

package no.machina.patcheditor.registry;

import java.io.*;
import java.util.*;

/**
 *
 * @author  narve
 */
public class PreferenceHelper  {

    public static final String PROPSFILE = System.getProperty( "user.home" ) + File.separator + ".patcheditor" + File.separator + "user.properties";
    public static final String LAST_USED_DIR = "no.machina.patcheditor.last_used_dir";
    
    public static final String LAST_USED_PATCH_APPLY_DIR = "no.machina.patcheditor.last_used_patch_apply_dir";
    
    public static final String LAST_USED_PATCH_CREATE_USE_RDIFF = "no.machina.patcheditor.last_used_patch_create_use_rdiff";
    public static final String LAST_USED_PATCH_CREATE_DIR = "no.machina.patcheditor.last_used_patch_create_dir";
    public static final String LAST_USED_PATCH_CREATE_REV1 = "no.machina.patcheditor.last_used_patch_create_rev1";
    public static final String LAST_USED_PATCH_CREATE_REV2 = "no.machina.patcheditor.last_used_patch_create_rev2";
    public static final String LAST_USED_PATCH_CREATE_DATE1 = "no.machina.patcheditor.last_used_patch_create_date1";
    public static final String LAST_USED_PATCH_CREATE_DATE2 = "no.machina.patcheditor.last_used_patch_create_date2";

    public static final String MRU = "no.machina.patcheditor.last_used_files";
    
    private static PreferenceStore store = new XMLFSPreferenceStore( PROPSFILE ); 
    
    public static PreferenceStore getStore() {
        return store; 
    }
    
    public static String getProperty( String key, String def ) {
        return store.getString( key, def );
    }
    
    public static void setProperty( String key, String value ) {
        System.out.printf("Setting %s to %s%n", key, value );
        store.setProperty( key, value );
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        store.setProperty( "test", "testvalue" ); 
        store.setProperty( MRU, new String[] { "c:/temp/asdf.patch", "c:/temp/patch.asdf" } ); 
    }
       
}
