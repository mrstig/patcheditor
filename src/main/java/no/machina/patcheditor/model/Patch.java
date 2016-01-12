/*
 * Patch.java
 *
 * Created on 21. juni 2004, 10:26
 */

package no.machina.patcheditor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author  narve
 */

public class Patch implements DiffObject {
    
    public enum Status {NotRun, OK, AlreadyApplied, NotOK}
    
    public static final String EOL = System.getProperty( "line.separator" );
    
    protected String trimPlusMinusSpace( String s ) {
        s = s.trim();
        if ( s.startsWith(",") || s.startsWith("+") || s.startsWith("-") )
            s = s.substring(1);
        if ( s.endsWith(",") || s.endsWith("+") || s.endsWith("-") )
            s = s.substring(0,s.length() - 2);
        return s;
    }
    
    public Patch( FileDiff parent ) {
        parentDiff = parent;
        
    }
    private String header;
    private String prefix = "@@";
    private String postfix = "@@";
    private int minusStart;
    private int minusCount;
    private int plusStart;
    private int plusCount;
    private Status status = Patch.Status.NotRun;
    
    public void setHeader( String header ) {
        try {
            this.header = header;
            String s = header.substring( prefix.length() );
            s = s.substring( 0, s.length() - postfix.length() );
            StringTokenizer st = new StringTokenizer( s, " " );
            String a = null, b = null, c = null, d = null;
            try {
                a = trimPlusMinusSpace( st.nextToken(",") );
            } catch ( Exception e ) {
            }
            try {
                b = trimPlusMinusSpace( st.nextToken(" ") );
            } catch ( Exception e ) {
            }
            try {
                c = trimPlusMinusSpace( st.nextToken(",") );
            } catch ( Exception e ) {
            }
            try {
                d = trimPlusMinusSpace( st.nextToken(" ") );
            } catch ( Exception e ) {
            }
            String l = "", r = "";
            
            // "Pre: %d-%d Post: %d-%d"
            if ( b == null ) {
                setMinusStart(0);
                setMinusCount((int)new Integer( a ));
                l = "Pre: 0 - "+a;
            } 
            else {
                setMinusStart((int)new Integer( a ));
                setMinusCount((int)new Integer( b ));
                int x = (int)new Integer( a ) + (int)new Integer( b );
                l = "Pre: "+a+" - "+x;
            }
            if ( d == null ) {
                setPlusStart(0);
                setPlusCount((int)new Integer( c ));
                r = "Post: 0 - "+c;
            } else {
                setPlusStart((int)new Integer( c ));
                setPlusCount((int)new Integer( d ));
                int x = (int)new Integer( c ) + (int)new Integer( d );
                r = "Post: "+c+" - "+x;
            }
            
            displayName = l + ", " + r;
        } catch ( Exception e ) {
            e.printStackTrace();
            displayName = "[PARSE ERROR] " + header;
        }
    }
    public String displayName;
    public List<String> lines = new ArrayList<String>();
    public String toString() {
//        return "[" + status + "]" + displayName;
        return displayName;
    }
    
    public MutableTreeNode node() {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode( this );
        return node;
    }
    
    public String marshal() {
        String s = new String();
        s += header + EOL;
        for( String p: lines )
            s += p + EOL;
        return s;
    }
    
    /**
     * Getter for property include.
     * @return Value of property include.
     */
    public boolean isInclude() {
        return this.include;
    }
    
    /**
     * Setter for property include.
     * @param include New value of property include.
     */
    public void setInclude(boolean include) {
        this.include = include;
    }
    
    public FileDiff parentDiff;
    
    /**
     * Holds value of property include.
     */
    private boolean include;

    public int getMinusStart() {
        return minusStart;
    }

    public void setMinusStart(int minusStart) {
        this.minusStart = minusStart;
    }

    public int getMinusCount() {
        return minusCount;
    }

    public void setMinusCount(int minusCount) {
        this.minusCount = minusCount;
    }

    public int getPlusStart() {
        return plusStart;
    }

    public void setPlusStart(int plusStart) {
        this.plusStart = plusStart;
    }

    public int getPlusCount() {
        return plusCount;
    }

    public void setPlusCount(int plusCount) {
        this.plusCount = plusCount;
    }
   
    public List<String> getMinusLinesNoPrefix() {
        List<String> res = new ArrayList<String>();
        for ( String s : lines )
            if ( s.startsWith(" ") || s.startsWith("-" ) )
                res.add( s.substring( 1, s.length() ) );
        return res;
            
    }
    
    public List<String> getPlusLinesNoPrefix() {
        List<String> res = new ArrayList<String>();
        for ( String s : lines )
            if ( s.startsWith(" ") || s.startsWith("+" ) )
                res.add( s.substring( 1, s.length() ) );
        return res;
            
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus( Status status ) {
        this.status = status;
    }
    
}

