/*
 * LocaleHelper.java
 *
 * Created on 16. juni 2004, 19:41
 */

package no.machina.patcheditor.utils;

import java.util.*;

/** Simple class for listing up all locales for which there exists a resource bundle.
 *
 * @author  narve
 */
public class LocaleHelper {
    
    /**
     * Holds value of property activeLocale.
     */
    private Locale activeLocale;
    
    private static LocaleHelper instance = null;
    
    /**
     * Holds value of property resourceBundleName.
     */
    private String resourceBundleName = "i18n.i18n";
    
    private LocaleHelper() {
        //        activeLocale = Locale.getDefault();
        activeLocale = ResourceBundle.getBundle( resourceBundleName ).getLocale();
    }
    
    /** Factory method */
    public static LocaleHelper getInstance() {
        if( instance == null)
            instance = new LocaleHelper();
        return instance;
    }
    
    /** Utility method -- breaking singleton-paradigm for simplicity:)
     */
    public static String getString( String key ) {
        return ResourceBundle.getBundle( getInstance().getResourceBundleName() ).getString( key );
    }
    
    /** Lists all Locale's for which there exists a resource bundle.
     *  Returns an empty list if no localization found.
     *
     */
    public Locale[] listLocales() {
        try {
            Comparator<Locale> localeComparator = new Comparator<Locale>() {
               public int compare( Locale l1, Locale l2 )  {
                   return l1.toString().compareTo( l2.toString() ); 
               }
            }; 
            Set<Locale> s = new TreeSet<Locale>( localeComparator );
            Locale[] la = Locale.getAvailableLocales();
//            Arrays.sort( la, localeComparator ); 
//            System.out.println("Available locales: " + Arrays.asList( la ) );
            for( Locale l: la ) {
                ResourceBundle bundle = ResourceBundle.getBundle( "i18n", l );
                if( bundle.getLocale().toString().length() > 0 ) {
                    s.add( bundle.getLocale() );
                }
            }
            return (Locale[])s.toArray( new Locale[ s.size() ] );
        } catch( Exception e ) {
            System.out.println("Warning: " + e );
            return new Locale[ 0 ];
        }
    }
    
    /**
     * Getter for property activeLocale.
     * @return Value of property activeLocale.
     */
    public Locale getActiveLocale() {
        return this.activeLocale;
    }
    
    /**
     * Setter for property activeLocale.
     * @param activeLocale New value of property activeLocale.
     */
    public void setActiveLocale(Locale activeLocale) {
        this.activeLocale = activeLocale;
        Locale.setDefault( activeLocale );
    }
    
    
    
    /**
     * Getter for property resourceBundleName.
     * @return Value of property resourceBundleName.
     */
    public String getResourceBundleName() {
        return this.resourceBundleName;
    }
    
    /**
     * Setter for property resourceBundleName.
     * @param resourceBundleName New value of property resourceBundleName.
     */
    public void setResourceBundleName(String resourceBundleName) {
        this.resourceBundleName = resourceBundleName;
    }
    
}