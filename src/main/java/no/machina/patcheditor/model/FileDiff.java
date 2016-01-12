/*
 * FileDiff.java
 *
 * Created on 21. juni 2004, 10:25
 */

package no.machina.patcheditor.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
/**
 *
 * @author  narve
 */

public class FileDiff implements DiffObject {
    
    public static final String EOL = System.getProperty( "line.separator" );
    
    public FileDiff( String header ) {
        if ( header != null ) {
            this.header = header;
            String[] sa = header.split( " " );
            String s1 = sa[ sa.length -1 ];
            file = s1;
        }
    }
    public String file;
    public String header;
    public String plus;
    public String minus;
    public List<String> props = new ArrayList<String>();
    public boolean binary;
    
    public List<Patch> patches = new ArrayList<Patch>();
    public String toString() {
        String[] sa = file.split( "/" );
        return sa[ sa.length-1 ];
        //            return file;
    }
    
    public MutableTreeNode node() {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode( this );
        for( Patch p: patches ) {
            node.add( p.node() );
        }
        return node;
    }
    
    public String marshal() {
        return marshal( false );
    }
    
    public String marshal( boolean onlyIncluded ) {
        String s = new String();
        
        s += "Index: " + file + EOL;
        s += "===================================================================" + EOL;
        if ( header != null )
            s += header + EOL;
        s += minus + EOL;
        s += plus + EOL;
        int count = 0;
        for( Patch p: patches ) {
            if( !( onlyIncluded && !p.isInclude() ) ) {
                s += p.marshal();
                count++;
            }
        }
        if ( count > 0 ) {
            for ( String line : props ) {
                s += line + EOL;
            }
        }
        if( count > 0 )
            return s;
        else
            return "";
    }
    
    public static boolean matchAt( List<String> haystack, int origin, List<String> needle ) {
        if ( origin < 0 || origin >= haystack.size() )
            return false;
        for ( int i = 0; i < needle.size(); i++ ) {
            if ( i + origin >=  haystack.size() )
                return false;
            String straw = haystack.get( i + origin );
            String needleBit = needle.get( i );
            if ( !straw.equals( needleBit ) )
                return false;
        }
        return true;
    }
    
    public static int findMatch( List<String> haystack, int origin, int preSlack, int postSlack, List<String> needle, StringBuffer log ) {
        if ( preSlack == -1 )
            preSlack = haystack.size();
        if ( postSlack == -1 )
            postSlack = haystack.size();
        int goingBK = origin;
        int goingFW = origin;
        while ( goingFW - goingBK <= haystack.size() ) {
            if ( matchAt( haystack, goingBK, needle ) ) {
                log.append( "Adjusted by " + ( goingBK - origin ) + "\n" );
                return goingBK;
            }
            if ( matchAt( haystack, goingFW, needle ) ) {
                log.append( "Adjusted by +" + ( goingFW - origin ) + "\n" );
                return goingFW;
            }
            goingBK--;
            goingFW++;
        }
        return -1;
    }
    
    public String apply( String original, StringBuffer log ) {
        List<String> linesfdas = Arrays.asList( original.split("\n") );
        int res = apply( linesfdas, log );
        StringBuffer resfdsa = new StringBuffer();
        for( String line: linesfdas )
            resfdsa.append( line );
        return resfdsa.toString();
    }
    
    public int apply( List<String> lines, StringBuffer log ) {
        return apply( lines, log, false );
    }
    public int apply( List<String> lines, StringBuffer log, boolean reverse ) {
        int res = -1; // FIXME MAGIC
        for ( Patch p : patches ) {
            if( !p.isInclude() ) {
                p.setStatus(Patch.Status.NotRun);
                continue;
            }
            int preStart = p.getMinusStart();
            if ( preStart >= lines.size() )
                preStart = lines.size() - 1;
            
            int preCount = p.getMinusCount();
            
            List<String> minusLines;
            if ( reverse )
                minusLines = p.getPlusLinesNoPrefix();
            else
                minusLines = p.getMinusLinesNoPrefix();
            int foundAt = -1;
            if ( minusLines.size() == 0 )
                foundAt = 0;
            else
                foundAt = findMatch( lines, preStart, Integer.getInteger("patcheditor.slack", -1 ), Integer.getInteger("patcheditor.slack", -1 ), minusLines, log );
            if ( foundAt == -1 ) {
                log.append( "*** Could not apply hunk " + p.displayName + "\n" );
                List<String> plusLines;
                if ( reverse )
                    plusLines = p.getMinusLinesNoPrefix();
                else
                    plusLines = p.getPlusLinesNoPrefix();
                int alreadyAppliedFoundAt = findMatch( lines, preStart, Integer.getInteger("patcheditor.slack", -1 ), Integer.getInteger("patcheditor.slack", -1 ), plusLines, new StringBuffer() );
                if ( alreadyAppliedFoundAt != -1 ) {
                    log.append( "    Expected result lines found at " + alreadyAppliedFoundAt + ". Patch already applied?\n" );
                    p.setStatus(Patch.Status.AlreadyApplied);
                } else {
                    p.setStatus(Patch.Status.NotOK);
                }
                
            } else {
                for ( int i = 0; i < minusLines.size(); i++ )
                    lines.remove( foundAt );
                if ( reverse )
                    lines.addAll( foundAt, p.getMinusLinesNoPrefix() );
                else
                    lines.addAll( foundAt, p.getPlusLinesNoPrefix() );
                log.append( "Applied hunk " + p.displayName + "\n" );
                res = 0;
                p.setStatus(Patch.Status.OK);
            }
        }
        return res;
    }
    
    public boolean hasIncludedPatch() {
        for ( Patch p : patches ) {
            if( p.isInclude() ) {
                return true;
            }
        }
        return false;
        
    }
    
 
}

