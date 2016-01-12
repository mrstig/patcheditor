package no.machina.patcheditor.model; 
import javax.swing.tree.*;

/*
 * DiffObject.java
 *
 * Created on 10. juni 2004, 12:50
 */

/**
 *
 * @author  narve
 */

public interface DiffObject {
    public MutableTreeNode node();
    public String marshal();
}
