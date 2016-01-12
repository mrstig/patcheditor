/*
 * CVSHelper.java
 *
 * Created on 24. februar 2005, 09:19
 */

package no.machina.patcheditor.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.netbeans.lib.cvsclient.CVSRoot;
import org.netbeans.lib.cvsclient.Client;
import org.netbeans.lib.cvsclient.admin.StandardAdminHandler;
import org.netbeans.lib.cvsclient.command.CommandAbortedException;
import org.netbeans.lib.cvsclient.command.GlobalOptions;
import org.netbeans.lib.cvsclient.command.diff.DiffCommand;
import org.netbeans.lib.cvsclient.connection.AuthenticationException;
import org.netbeans.lib.cvsclient.connection.Connection;
import org.netbeans.lib.cvsclient.connection.PServerConnection;
import org.netbeans.lib.cvsclient.connection.StandardScrambler;
import org.netbeans.lib.cvsclient.event.BinaryMessageEvent;
import org.netbeans.lib.cvsclient.event.CVSAdapter;
import org.netbeans.lib.cvsclient.event.FileInfoEvent;
import org.netbeans.lib.cvsclient.event.MessageEvent;
import org.netbeans.lib.cvsclient.util.Logger;


/**
 *
 * @author stig
 */
public class CVSHelper {
    
    
    public static class StringListener extends CVSAdapter {
        
        public StringBuffer err = new StringBuffer();
        public StringBuffer out = new StringBuffer();
        
        /**
         * Stores a tagged line
         */
        private final StringBuffer taggedLine = new StringBuffer();
        
        private static final String lineSep = System.getProperty("line.separator");
        
        public void messageSent( BinaryMessageEvent bme ) {
            //            System.out.println("Got binary...");
        }
        public void fileInfoGenerated( FileInfoEvent fie ) {
            //            System.out.println( fie );
        }
        
        /**
         * Called when the server wants to send a message to be displayed to
         * the user. The message is only for information purposes and clients
         * can choose to ignore these messages if they wish.
         * @param e the event
         */
        public void messageSent(MessageEvent e) {
            String line = e.getMessage();
            StringBuffer buf = e.isError() ? err
                    : out;
            
            if (e.isTagged()) {
                String message = MessageEvent.parseTaggedMessage(taggedLine, line);
                // if we get back a non-null line, we have something
                // to output. Otherwise, there is more to come and we
                // should do nothing yet.
                if (message != null) {
                    buf.append(message + lineSep);
                }
            } else {
                buf.append(line + lineSep);
            }
        }
    }

    public static Connection getConnectionFromDir( String dirName ) {
        String rootPath = dirName + File.separator + "CVS" + File.separator + "Root";
        File rootFile = new File( rootPath );
        if ( !rootFile.exists() )
            throw new RuntimeException("No CVS info found in " + dirName );
        
        String cvsRoot;
        try {
            BufferedReader br = new BufferedReader( new FileReader( rootFile ) );
            cvsRoot = br.readLine();
        } catch ( IOException ioe ) {
            throw new RuntimeException( ioe );
        }
        
        CVSRoot root = CVSRoot.parse( cvsRoot );
        System.out.println( "Root: " + root );
        String method = root.getMethod();
        Connection res;
        if ( method.equals( CVSRoot.METHOD_PSERVER ) ) {
            PServerConnection ps = new PServerConnection();
            ps.setUserName( root.getUserName() );
            ps.setHostName( root.getHostName() );
            ps.setRepository( root.getRepository() );
//            ps.setHostName( "localhost" );
//            ps.setPort( 2402 );
            res = ps;
        } else
            throw new IllegalStateException("Unimplemented connection method '" + method + "'" );
        
        return res;
        
    }
    
    public static interface PasswordCallbackHandler {
        public String handleCallBack( String message, String userName, String server ) throws InterruptedException;
    }
    
    
    /**
     * This is now officially a mess. 
     *
     * @todo: clean up
     *
     */
    public static String diffDirectory( boolean rdiff, String dirName, String rev1, String rev2, String date1, String date2, PasswordCallbackHandler pwHandler ) {
        try {
            Connection c = getConnectionFromDir( dirName );
            try {
                c.open();
            } catch ( AuthenticationException ae ) {
                
                if ( c instanceof PServerConnection ) {
                    while ( c.isOpen() ) {
                        PServerConnection ps = (PServerConnection)c;
                        String pw = pwHandler.handleCallBack( ae.getMessage(), ps.getUserName(), ps.getHostName() );
                        ps.setEncodedPassword( StandardScrambler.getInstance().scramble( pw ) );
                        try {
                            ps.open();
                        } catch ( AuthenticationException aae ) {
                        } catch ( CommandAbortedException ccae ) {
                            throw new InterruptedException( ccae.getMessage() );
                        }
                    }
                }
            } catch ( CommandAbortedException cae ) {
                
            }
            
            
            
            
            StandardAdminHandler sah = new StandardAdminHandler();
            Client client = new Client(c, sah);
            
            
            client.setLocalPath( dirName );
            
            DiffCommand diff;
            if ( rdiff )
                diff = new RDiffCommand();
            else
                diff = new DiffCommand();
            diff.setBeforeDate1( date1 );
            diff.setBeforeDate2( date2 );
            diff.setRevision1( rev1 );
            diff.setRevision2( rev2 );
            diff.setRecursive( true );
            diff.setUnifiedDiff( true );
            if ( rdiff ) {
                String modName = new File(dirName).getName();
                ((RDiffCommand)diff).setModule( modName );
            }
            
            
            StringListener l = new StringListener();
            
            client.getEventManager().addCVSListener( l );
            
            
            System.out.println("Executing: " + diff.getCVSCommand() );
            GlobalOptions go = new GlobalOptions();

            try {
            
                client.executeCommand( diff, go );

            } catch ( AuthenticationException ae ) {
                
                if ( c instanceof PServerConnection ) {
                    boolean keepOnTrying = true;
                    while ( keepOnTrying ) {
                        PServerConnection ps = (PServerConnection)getConnectionFromDir( dirName );
                        String pw = pwHandler.handleCallBack( ae.getMessage(), ps.getUserName(), ps.getHostName() );
                        ps.setEncodedPassword( StandardScrambler.getInstance().scramble( pw ) );
                        try {
                            client.setConnection( ps );
                            ps.open();
                            client.executeCommand( diff, go );
                            keepOnTrying = false;
                        } catch ( AuthenticationException aae ) {
                            aae.printStackTrace();
                        } catch ( CommandAbortedException ccae ) {
                            throw new InterruptedException( ccae.getMessage() );
                        }
                    }
                }
            } catch ( CommandAbortedException cae ) {
                
            }
            
            //            System.out.println("Std:");
            //            System.out.println(l.out);
            //            System.out.println();
            //            System.out.println();
            //            System.out.println("Err:");
            //            System.out.println(l.err);
            return l.out.toString();
            
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
    
    
}
