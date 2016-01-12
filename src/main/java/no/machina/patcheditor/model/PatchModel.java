package no.machina.patcheditor.model;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

/*
 * PatchViewer.java
 *
 * Created on 9. juni 2004, 17:54
 */

/** Main executable class and business logic holder for PatchEditor
 * Typical usage:
 *
 * diff -x CVS -urN misc misc-4644 > fwdport.patch
 *
 * TODO: fix space-trouble in filenames
 * TODO: save (not as)
 * TODO: open includeset from saved patch (treewalker i treebuilder?)
 * TODO: better icons for tree in general (per type etc)
 * TODO: diffcolouring in text field
 * TODO: side-by-side diff view
 * TODO: sorting dirs before files in nodes
 * TODO: add patch to tree (merge or something)
 * TODO: edit patch in textbox (for saving to resultset)
 * TODO: confirm for exit (if dirty)
 * TODO: right-click menu in tree.
 * TODO: General code clean-up (move code away from PatchGUI)
 * TODO: Support single file diff (no diff header)
 * TODO: Better parsing in Patch.setHeader()
 * TODO: command line arguments
 * TODO: Windows integration :)
 * TODO: File save as dialog text(s)
 * TODO: Localization
 * TODO: Unit tests
 * TODO: Automated GUI tests
 * TODO: Web start launching
 * TODO: Fix main class properly
 * TODO: Options.
 *
 * @author  narve
 */
public class PatchModel implements DiffObject {
    
    public static final String EOL = System.getProperty( "line.separator" );
    
    public List<FileDiff> diffs = new ArrayList<FileDiff>();
    
    /** Creates a new instance of PatchViewer */
    public PatchModel() {
    }
    
    
    
    /** Adds diff, maitaining some order */
    private void addDiff( FileDiff diff ) {
        if( new File( diff.file ).isDirectory() )
            diffs.add( 0, diff );
        else
            diffs.add( diff );
    }
    
    public void read( String fileName ) throws Exception {
        BufferedReader br = new BufferedReader( new FileReader( fileName ));
        read( br );
    }
    public void read( Reader r ) throws Exception {
        BufferedReader br = new BufferedReader( r );
        String line;
        String currentPlusPlusPlus = null;
        String currentMinusMinusMinus = null;
        String currentIndex = null;
        String currentDiffLine = null;
        
        FileDiff currentDiff = null;
        Patch currentPatch = null;
        int counter = 0;
        while( (line=br.readLine() ) != null )  {
            //        for ( int counter = 0; counter < lines.size(); counter++ ) {
            //            String line = lines.get( counter );
            if ( counter % 100 == 0 ) {
                System.err.println("At line " + counter ); //+ "/" + lines.size()  );
                System.err.println( line );
                long free = Runtime.getRuntime().freeMemory();
                long max = Runtime.getRuntime().maxMemory();
                long total = Runtime.getRuntime().totalMemory();
                long avail = max - total;
                System.err.println("Free mem: " + free + ", Max: " + max + ", Total: " + total + ", Available: " + avail );
            }
            if ( line.startsWith( "+++" ) ) {
                currentPlusPlusPlus = line;
                if ( currentDiffLine == null && currentIndex == null )
                    throw new IllegalStateException("No \"diff\" or \"Index:\" found... I wasn't trained for this, I'm afraid. Tell my lazy programmer to check the unified diff spec." );
                currentDiff = new FileDiff( currentDiffLine );
                if ( currentIndex != null )
                    currentDiff.file = currentIndex.substring( 7 );
                currentDiff.plus = currentPlusPlusPlus;
                currentDiff.minus = currentMinusMinusMinus;
                addDiff( currentDiff );
            } else if ( line.startsWith("---") ) {
                currentMinusMinusMinus = line;
            } else if ( line.startsWith("Index: ") ) {
                currentIndex = line;
            } else if ( line.startsWith("diff ") ) {
                currentDiffLine = line;
            } else if ( line.startsWith("=====") ) {
                // ignore
            } else if ( line.startsWith("RCS file") ) {
                // ignore
            } else if ( line.startsWith("retrieving") ) {
                // ignore
            } else if( line.length() == 0 ) {
                // ignore...
            } else if( line.startsWith( "@@") ) {
                currentPatch = new Patch( currentDiff );
                currentDiff.patches.add( currentPatch );
                currentPatch.setHeader( line );
            } else if ( line.startsWith( "+" ) || line.startsWith( "-" ) || line.startsWith( " " ) || line.startsWith( "\\" ) )  {
                if ( currentPatch == null )
                    throw new IllegalStateException("no current patch for line '" + line +"'");
                currentPatch.lines.add( line );
            } else if ( line.startsWith( "?" ) )  {
                // Ignore, not version controlled
            } else if ( line.startsWith( "svn:mime-type = " ) )  {
                // Ignore, taken care of below
            } else if( line.startsWith( "Cannot display: file marked as a binary type.") || line.startsWith( "Binary files" ) ) {
                if ( currentDiff == null ) {
                    currentDiff = new FileDiff( currentDiffLine );
                    if ( currentIndex != null )
                        currentDiff.file = currentIndex.substring( 7 );
                }
                currentDiff.binary = true;
                currentDiff.minus = "[Binary]";
                currentDiff.plus = "[Binary]";
                currentPatch = new Patch( currentDiff );
                currentDiff.patches.add( currentPatch );
                currentPatch.displayName = "[Binary]";
                
            } else if( line.startsWith( "Property changes") ) {
                currentDiff.props.add( "" );
                currentDiff.props.add( line );
                while ( (line=br.readLine() ) != null && line.length() > 0 ) {
                    currentDiff.props.add( line );
                }
                currentDiff.props.add( "" );
            } else {
                JOptionPane.showMessageDialog( null, "Unable to parse line '" + line + "'" );
            }
            //            lies.set( counter, null );
            counter++;
        }
    }
    
    
    private static Comparator<FileDiff> fileComparator = new Comparator<FileDiff>() {
        public int compare( FileDiff fd1, FileDiff fd2 ) {
            return fd1.file.compareTo( fd2.file );
        }
    };
    
    public static class FileNode extends DefaultMutableTreeNode {
        //        Map<String, FileNode> subs = new TreeMap<String, FileNode>( fileDiffComparator );
        Map<String, FileNode> subs = new TreeMap<String, FileNode>();
        public final FileDiff fileDiff;
        public String name;
        
        public int dirInsertPoint = 0;
        
        public String toString() {
            return name;
        }
        public FileNode( FileDiff fd, String n ) {
            name = n;
            fileDiff = fd;
        }
        public void add( FileDiff child, String file ) {
            String[] sa = file.split( "/" );
            
            if( sa.length == 1 ) {
                super.add( child.node() );
                return;
            }
            
            String s = file.substring( sa[ 0 ].length() + 1 );
            
            FileNode node = (FileNode) subs.get( sa[ 0 ] );
            if( node == null ){
                node = new FileNode( child, sa[ 0 ] );
                subs.put( sa[ 0 ], node );
                super.insert( node, dirInsertPoint );
                dirInsertPoint++;
            }
            node.add( child, s );
        }
        
        public void marshalFromHere( Writer w ) throws IOException {
            int childCount = this.getChildCount();
            for ( int i = 0; i < childCount; i++ ) {
                TreeNode tn = getChildAt( i );
                if ( tn instanceof  DefaultMutableTreeNode  ) {
                    DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)tn;
                    Object o = dmtn.getUserObject();
                    if ( o instanceof FileDiff )  {
                        String s = ((FileDiff)o).marshal();
                        w.write( s );
                    }
                }
            }
            for ( FileNode fn : subs.values() )
                fn.marshalFromHere( w );
        }
        
    }
    
    
    public MutableTreeNode node() {
        FileNode node = new FileNode( null, "Root" );
        Collections.sort( diffs, fileComparator );
        for( FileDiff p: diffs ) {
            node.add( p, p.file );
        }
        return (MutableTreeNode) node;
    }
    
    public String marshal() {
        String s = new String();
        for( FileDiff p: diffs )
            s += p.marshal();
        return s;
    }
    
    public void marshal( Writer w ) throws IOException {
        for( FileDiff p: diffs )
            w.write( p.marshal() );
    }
    
    public String marshal( Writer w, boolean onlyIncluded ) throws IOException {
        StringBuffer sb = new StringBuffer();
        for( FileDiff p: diffs ) {
            String res = p.marshal(onlyIncluded);
            if ( res.trim().length() > 0 && p.binary )
                sb.append("Binary file '" + p.file +"' must be manually included.\n" );
            w.write( res );
        }
        return sb.toString();
    }
    
    public void marshal( OutputStream w ) throws IOException {
        for( FileDiff p: diffs )
            w.write( p.marshal().getBytes() );
    }
    
    public static String getFileAsString( String fileName ) throws IOException {
        BufferedReader br = new BufferedReader( new FileReader( fileName ) );
        StringBuffer sb = new StringBuffer();
        int read;
        while ( (read = br.read() ) >= 0 ) {
            sb.append( (char)read );
        }
        return sb.toString();
    }
    
    public static List<String> getFileAsStringList( StringBuffer log, String fileName, boolean create ) throws IOException {
        File f = new File( fileName );
        if ( !f.exists() && create ) {
            f.createNewFile();
            log.append( "Created file " + fileName );
        }
        BufferedReader br = new BufferedReader( new FileReader( f ) );
        List<String> lines = new ArrayList<String>();
        String line;
        while( (line=br.readLine() ) != null )  {
            lines.add( line );
        }
        return lines;
    }
    
    public String toStringWithBackslashN( List<String> lines ) {
        StringBuffer resSB = new StringBuffer();
        for( String line: lines )
            resSB.append( line + "\n" );
        return resSB.toString();
    }
    
    public String apply( String basePath, boolean reverse, boolean test ) {
        
        StringBuffer log = new StringBuffer();
        boolean atLeastOneIncluded = false;
        for( FileDiff diff: diffs ) {
            if ( !diff.hasIncludedPatch() ) {
                continue;
            }
            atLeastOneIncluded = true;
            String absFile = basePath + File.separator + diff.file;
            log.append( "Reading " + absFile + "\n" );
            List<String> original;
            try {
                original = getFileAsStringList( log, absFile, true );
            } catch ( IOException ioe ) {
                log.append( "Error: "+ ioe.getMessage() + "\n" );
                continue;
            }
            log.append( "Applying...\n" );
            int res = diff.apply( original, log, reverse );
            if ( res == -1 ) {
                log.append( "Nothing changed, not writing " + absFile + "\n" );
                continue;
            }
            if ( test ) {
                log.append( "Test only, not writing " + absFile + "\n" );
            } else {
                log.append( "Writing " + absFile + "\n" );
                try {
                    writeLines( absFile, original );
                } catch ( IOException ioe ) {
                    log.append( "Error: "+ ioe.getMessage() + "\n" );
                    continue;
                }
            }
        }
        if ( !atLeastOneIncluded )
            log.append("No patches were marked as included - you might want to check that...\n");
        return log.toString();
    }
    
    public static void writeLines( String absFileName, List<String> lines ) throws IOException {
        BufferedWriter bw = new BufferedWriter( new FileWriter( absFileName ) );
        String lineSep = System.getProperty( "line.separator" );
        for ( String line: lines ) {
            bw.write( line + lineSep );
        }
        bw.close();
    }
    

    
    
    
    
}

