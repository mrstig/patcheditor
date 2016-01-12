/*
 * PatchTreeRenderer.java
 *
 * Created on 11. juni 2004, 17:33
 */

package no.machina.patcheditor.gui;

import java.awt.Color;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import no.machina.patcheditor.model.*;
import no.machina.patcheditor.utils.*;

/**
 *
 * @author  stig
 */
public class PatchTreeRenderer extends DefaultTreeCellRenderer {
    Color defaultNonSelect;
    Color defaultSelect;
    /** Creates a new instance of PatchTreeRenderer */
    public PatchTreeRenderer() {
        defaultNonSelect = getBackgroundNonSelectionColor();
        defaultSelect = getBackgroundSelectionColor();
    }
    
    public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        DefaultTreeCellRenderer renderer =  (DefaultTreeCellRenderer)super.getTreeCellRendererComponent( tree, value, selected, expanded, leaf, row, hasFocus );
        renderer.setBackgroundNonSelectionColor(defaultNonSelect);
        
        if ( value instanceof DefaultMutableTreeNode ) {
            Object o = ((DefaultMutableTreeNode)value).getUserObject();
            if ( o instanceof FileDiff ) {
                renderer.setIcon( renderer.getLeafIcon() );
            }
            if ( o instanceof Patch ) {
                Patch p = ((Patch)o);
                if ( p.isInclude() )
                    renderer.setIcon(SwingIcons.getIcon(  SwingIcons.FORWARD ) );
                else
                    renderer.setIcon(SwingIcons.getIcon(  SwingIcons.PAUSE ) );
                Color bg = null;
                System.out.println("Patch status: " + p.getStatus() );
                switch ( p.getStatus() ) {
                    case OK:
                        bg = Color.GREEN;
                        break;
                    case NotOK:
                        bg = Color.ORANGE;
                        break;
                    case AlreadyApplied:
                        bg = Color.YELLOW;
                        break;
                    case NotRun:
                    default:
                        // noop;
                }
                //                if ( !selected )  {
                if ( bg != null ) {
                    renderer.setBackgroundNonSelectionColor(bg);
                    System.out.println("Setting bg to " + bg );
                } 
                //                } else
                //                    renderer.setBackground(renderer.getBackgroundSelectionColor());
                
                
            }
        }
        return renderer;
    }
    
}
