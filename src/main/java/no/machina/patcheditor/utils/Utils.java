/*
 * Utils.java
 *
 * Created on 14. mars 2005, 16:36
 */

package no.machina.patcheditor.utils;

/**
 *
 * @author stig
 */
public class Utils {
    
    
    public static boolean nullSafeEquals( Object o1, Object o2 ) {
        
        // ...and null == null...
        if ( o1 == o2 )
            return true;
        
        if ( o1 == null ) {
            return false;
        } else {
            return o1.equals( o2 );
        }
    }
    

}
