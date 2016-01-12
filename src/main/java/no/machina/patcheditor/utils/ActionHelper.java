/*
 * ActionHelper.java
 *
 * Created on 17. juni 2004, 11:36
 */

package no.machina.patcheditor.utils;

import javax.swing.*;

/**
 *
 * @author  narve
 */
public class ActionHelper {
    
    
    public static final String ACTION_IDENTIFIER = "ACTION_IDENTIFIER";
    
    
    public static void addActions( JToolBar tb, Action[] actions ) {
        for( Action a: actions ) {
            if( a == null ) {
                tb.addSeparator();
            } else {
                tb.add( new JButton( a ) );
            }
        }
    }
    
    public static void addActions( JMenu tb, Action[] actions ) {
        for( Action a: actions ) {
            if( a == null ) {
                tb.addSeparator();
            } else {
                tb.add( new JMenuItem( a ) );
            }
        }
    }
    
    
    public static void configure( JMenu tb, String menuName, Action[] actions ) {
        addActions( tb, actions );
        tb.setText( LocaleHelper.getString("Menu." + menuName + ".Title" ) ); 
        KeyStroke mn = KeyStroke.getKeyStroke( LocaleHelper.getString("Menu." + menuName + ".Key" ) ); 
	if( mn != null )
            tb.setMnemonic( mn.getKeyCode() ); 
        else
            System.out.println("missing mnemnomic for " + menuName ); 
    }
    
    public static void addActions( JPopupMenu tb, Action[] actions ) {
        for( Action a: actions ) {
            if( a == null ) {
                tb.addSeparator();
            } else {
                tb.add( new JMenuItem( a ) );
            }
        }
    }
    
    
    /** Sets NAME, SHORT_DESCRIPTION, ACCELERATOR_KEY AND ACTION_IDENTIFIER on this action. */
    public static void putProperties( Action action, String actionName ) {
        try {
            action.putValue( Action.NAME, LocaleHelper.getString("actions." + actionName + ".title" ) );
            action.putValue( Action.SHORT_DESCRIPTION, LocaleHelper.getString("actions." + actionName + ".desc") );
            action.putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke( LocaleHelper.getString("actions." + actionName + ".key") ) );
            action.putValue( ACTION_IDENTIFIER, actionName );
            //        action.putValue( Action.MNEMONIC_KEY, new Integer( KeyEvent.VK_X ) );
        } catch( Exception e ) {
            System.out.println("Warning: " + e );
        }
    }
    
}
