/*
 * XMLFSPreferenceStore.java
 *
 * Created on 18. juni 2004, 10:17
 */

package no.machina.patcheditor.registry;

import java.beans.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author  narve
 */
public class XMLFSPreferenceStore extends FileSystemPreferenceStore {
    
    /** Creates a new instance of XMLFSPreferenceStore */
    public XMLFSPreferenceStore( String fname ) {
        super( fname );
    }
    
    protected void load() throws IOException {
        System.out.println("Loading " + fileName );
        BufferedInputStream bis = new BufferedInputStream( new FileInputStream( fileName ) );
        XMLDecoder xd = new XMLDecoder( bis );
        values = (Map<String, Object>) xd.readObject();
        bis.close();
    }
    
    protected void store() throws IOException {
        File f = new File( fileName );
        if( !f.exists() ) {
            System.out.println("Creating " + f );
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        BufferedOutputStream bis = new BufferedOutputStream( new FileOutputStream( fileName ) );
        XMLEncoder xd = new XMLEncoder( bis );
        xd.writeObject( values );
        xd.close(); 
        bis.close();
    }
    
    
}
