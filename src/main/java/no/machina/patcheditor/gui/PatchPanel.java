/*
 * PactchEditorPanel.java
 *
 * Created on 17. juni 2004, 13:22
 */

package no.machina.patcheditor.gui;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import no.machina.patcheditor.model.*;
import no.machina.patcheditor.registry.*;
import no.machina.patcheditor.utils.*;

/**
 *
 * @author  narve
 */

// todo: test-run currently selected patch against a given path, to quickly see which patches will apply and not
public class PatchPanel extends javax.swing.JPanel {

    /** Creates new form PactchEditorPanel */
    public PatchPanel() {
        initComponents();
        setModel( null );
        setupGUI();
    }
    
    
    
    private String fileName;
    private PatchModel currentModel = null;
    
    private void setupGUI() {
        setupActions();
        setupToolbars();
        setupMenus();
        jTree1.setShowsRootHandles( true );
        //        jPanel3.invalidate();
        //        jPanel3.revalidate();
        //        jPanel3.repaint();
    }
    
    /** Creates the Language sub-menu on the File menu. */
    private void setupLanguageMenu() {
        Locale[] la = LocaleHelper.getInstance().listLocales();
        JMenu langs = new JMenu( "Language / Culture / Region" );
        mnSystem.addSeparator();
        mnSystem.add( langs );
        ButtonGroup buttonGroup = new ButtonGroup();
        for( Locale l: la ) {
            final JRadioButtonMenuItem rb = new JRadioButtonMenuItem();
            final Locale myLocale = l;
            Action action = new AbstractAction() {
                private final JRadioButtonMenuItem myItem = rb;
                public void actionPerformed( ActionEvent ae ) {
                    LocaleHelper.getInstance().setActiveLocale( myLocale );
                    setupGUI();
                    myItem.setSelected( true );
                }
            };
            String name = l.getDisplayLanguage().substring( 0, 1 ).toUpperCase() + l.getDisplayLanguage().substring( 1 );
            if( l.getDisplayVariant() != null && l.getDisplayVariant().trim().length() > 0 )
                name += " (" + l.getDisplayVariant() + ")";
            name += " / " + l.getDisplayCountry();
            action.putValue( Action.NAME, name );
            action.putValue( Action.SHORT_DESCRIPTION, name );
            boolean selected = LocaleHelper.getInstance().getActiveLocale().equals( myLocale );
            rb.setSelected( selected );
            langs.add( rb );
            rb.setAction( action );
            buttonGroup.add( rb );
        }
        JRadioButtonMenuItem klingon = getKlingon();
        langs.add( klingon );
        buttonGroup.add( klingon );
        
        JRadioButtonMenuItem street = getStreet();
        langs.add( street );
        buttonGroup.add( street );
        
    }
    
    JRadioButtonMenuItem getKlingon() {
        final JRadioButtonMenuItem rb = new JRadioButtonMenuItem();
        final Locale kl = new Locale( "og", "kl" );
        Action action = new AbstractAction() {
            private final JRadioButtonMenuItem myItem = rb;
            public void actionPerformed( ActionEvent ae ) {
                LocaleHelper.getInstance().setActiveLocale( kl );
                setupGUI();
                myItem.setSelected( true );
            }
        };
        String name = "Klingon / Klingon";
        action.putValue( Action.NAME, name );
        action.putValue( Action.SHORT_DESCRIPTION, name );
        boolean selected = LocaleHelper.getInstance().getActiveLocale().equals( kl );
        rb.setSelected( selected );
        rb.setAction( action );
        return rb;
    }
    
    
    JRadioButtonMenuItem getStreet() {
        final JRadioButtonMenuItem rb = new JRadioButtonMenuItem();
        final Locale kl = new Locale( "zz", "ZZ" );
        Action action = new AbstractAction() {
            private final JRadioButtonMenuItem myItem = rb;
            public void actionPerformed( ActionEvent ae ) {
                LocaleHelper.getInstance().setActiveLocale( kl );
                setupGUI();
                myItem.setSelected( true );
            }
        };
        String name = "Street / Tha hood";
        action.putValue( Action.NAME, name );
        action.putValue( Action.SHORT_DESCRIPTION, name );
        boolean selected = LocaleHelper.getInstance().getActiveLocale().equals( kl );
        rb.setSelected( selected );
        rb.setAction( action );
        return rb;
    }
    
    /** Configures the actions. */
    private void setupActions() {
        
        ActionHelper.putProperties( patchAddAction, "addpatch" );
        ActionHelper.putProperties( patchAddRecurAction, "addsubtree" );
        ActionHelper.putProperties( patchDelAction, "delpatch" );
        ActionHelper.putProperties( patchDelRecurAction, "delsubtree" );
        ActionHelper.putProperties( fileOpenAction, "open" );
        ActionHelper.putProperties( fileSaveAction, "save" );
        ActionHelper.putProperties( exitAction, "exit" );
        ActionHelper.putProperties( aboutAction, "about" );
        ActionHelper.putProperties( newTabAction, "newtab" );
        ActionHelper.putProperties( applyPatchAction, "applypatch" );
        ActionHelper.putProperties( testPatchAction, "testpatch" );
        ActionHelper.putProperties( applyPatchActionReverse, "applypatchreverse" );
        ActionHelper.putProperties( createDiffAndOpenAction, "creatediffandopen" );
        ActionHelper.putProperties( reRootAction, "reroothere" );
        
        patchAddAction.putValue( Action.MNEMONIC_KEY, new Integer( KeyEvent.VK_A ) );
        patchAddAction.putValue( Action.SMALL_ICON, SwingIcons.getIcon( SwingIcons.ADD ) );
        
        patchAddRecurAction.putValue( Action.SMALL_ICON, SwingIcons.getIcon( SwingIcons.ADD ) );
        
        patchDelAction.putValue( Action.MNEMONIC_KEY, new Integer( KeyEvent.VK_D ) );
        patchDelAction.putValue( Action.SMALL_ICON, SwingIcons.getIcon( SwingIcons.DELETE ) );
        
        patchDelRecurAction.putValue( Action.SMALL_ICON, SwingIcons.getIcon( SwingIcons.DELETE ) );
        
        ActionHelper.putProperties( expandSubAction, "expandsub" );
        ActionHelper.putProperties( collapseSubAction, "collapsesub" );
        
        fileOpenAction.putValue( Action.MNEMONIC_KEY, new Integer( KeyEvent.VK_O ) );
        fileOpenAction.putValue( Action.SMALL_ICON, SwingIcons.getIcon( SwingIcons.OPEN ) );
        
        fileSaveAction.putValue( Action.MNEMONIC_KEY, new Integer( KeyEvent.VK_S ) );
        fileSaveAction.putValue( Action.SMALL_ICON, SwingIcons.getIcon( SwingIcons.SAVEAS ) );
        
        exitAction.putValue( Action.MNEMONIC_KEY, new Integer( KeyEvent.VK_X ) );
        //        exitAction.putValue( Action.SMALL_ICON, SwingIcons.getIcon( SwingIcons. ) );
    }
    
    private void setupToolbars() {
        tbNode.removeAll();
        tbSystem.removeAll();
        ActionHelper.addActions( tbNode, nodeActions );
        ActionHelper.addActions( tbSystem, systemActions );
    }
    
    private void setupMenus() {
        mnNode.removeAll();
        mnSystem.removeAll();
        mnWindow.removeAll();
        mnHelp.removeAll();
        ActionHelper.addActions( mnNode, nodeActions );
        ActionHelper.addActions( mnSystem, systemActions );
        ActionHelper.configure( mnWindow, "Windows", windowActions );
        ActionHelper.configure( mnHelp, "Help", helpActions );
        setupMRUMenu();
        setupLanguageMenu();
    }
    
    private void setupMRUMenu() {
        JMenu mru = new JMenu( LocaleHelper.getString( "Menu.RecentFileList" ) );
        String[] mrus = PreferenceHelper.getStore().getStringArray( PreferenceHelper.MRU, new String[ 0 ] );
        
        for( String s: mrus ) {
            final String dummy = s;
            Action fAction = new AbstractAction() {
                public void actionPerformed( ActionEvent ae ) {
                    fileOpen( dummy );
                }
            };
            fAction.putValue( Action.NAME, s );
            mru.insert( new JMenuItem( fAction ), 0 );
        }
        mnSystem.addSeparator();
        mnSystem.add( mru );
    }
    
    
    
    // BEGIN ACTION DECLARATIONS...
    
    
    private Action patchAddAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            patchAdd();
            setTreeFocus();
        }
    };
    private Action patchDelAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            patchDel();
            setTreeFocus();
        }
    };
    
    private Action fileOpenAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            fileOpen();
            setTreeFocus();
        }
    };
    private Action createDiffAndOpenAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            createDiffAndOpen();
            setTreeFocus();
        }
    };
    private Action fileSaveAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            fileSave();
            setTreeFocus();
        }
    };
    private Action applyPatchAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            applyPatch( false, false );
            setTreeFocus();
        }
    };
    private Action testPatchAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            applyPatch( false, true );
            setTreeFocus();
        }
    };
    private Action applyPatchActionReverse = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            applyPatch( true, false );
            setTreeFocus();
        }
    };
    private Action exitAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            getPatchFrame().close();
        }
    };
    
    private Action patchDelRecurAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            patchDelRecur();
            setTreeFocus();
        }
    };
    private Action patchAddRecurAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            patchAddRecur();
            setTreeFocus();
        }
    };
    private Action expandSubAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            expandAll();
        }
    };
    private Action collapseSubAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            collapseAll();
        }
    };
    
    private Action newTabAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            newTab();
        }
    };
    
    private Action aboutAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            about();
        }
    };
    
    private Action reRootAction = new AbstractAction() {
        public void actionPerformed( ActionEvent evt ) {
            reRoot();
            setTreeFocus();
        }
    };
    
    private Action[] nodeActions = new Action[] {
        patchAddAction,
                patchAddRecurAction,
                patchDelAction,
                patchDelRecurAction,
                null,
                expandSubAction,
                collapseSubAction,
                null,
                reRootAction,
    };
    
    private Action[] systemActions = new Action[] {
        fileSaveAction,
                fileOpenAction,
                createDiffAndOpenAction,
                null,
                applyPatchAction,
                testPatchAction,
                applyPatchActionReverse,
                null,
                exitAction,
    };
    
    private Action[] windowActions = new Action[] {
        newTabAction,
    };
    
    private Action[] helpActions = new Action[] {
        aboutAction,
    };
    
    // END ACTIONS
    
    
    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setModel( PatchModel pm ) {
        currentModel = pm;
        if( currentModel != null )
            jTree1.setModel( new DefaultTreeModel( currentModel.node() ) );
        else
            jTree1.setModel( new DefaultTreeModel( new DefaultMutableTreeNode( "No data" ) ) );
        updateActionsEnabled();
        setDisplayedText( "" );
    }
    
    
    private void showPopup( MouseEvent evt  ) {
        JPopupMenu m = new JPopupMenu();
        ActionHelper.addActions( m, nodeActions );
        m.show( evt.getComponent(), evt.getX(), evt.getY() );
    }
    
    private void fileOpen() {
        File fx = fileName != null ? new File( fileName ) : null;
        JFileChooser chooser = new JFileChooser( PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_DIR , fx == null ? null : fx.getPath() ) );
        int returnVal = chooser.showOpenDialog( this );
        if( returnVal != JFileChooser.APPROVE_OPTION )
            return;
        
        File f = chooser.getSelectedFile();
        if( f == null )
            return;
        
        fileName = f.toString();
        fileOpen( fileName );
    }
    
    
    public void fileOpen( String fileName ) {
        PreferenceHelper.setProperty( PreferenceHelper.LAST_USED_DIR, new File( fileName ).getPath() );
        
        PatchModel pm = new PatchModel();
        try {
            pm.read( fileName );
            setModel( pm );
            getPatchFrame().refreshTitles();
        } catch( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
        }
        addMRU( fileName );
        getPatchFrame().refreshTitles();
        jTree1.requestFocus();
    }
    
    private void addMRU( String fname ) {
        String[] ss = PreferenceHelper.getStore().getStringArray( PreferenceHelper.MRU, new String[ 0 ] );
        List<String> mrus = new ArrayList<String>();
        mrus.addAll( Arrays.asList( ss ) );
        if( mrus.contains( fname ) )
            mrus.remove( fname );
        mrus.add( fname );
        ss = (String[]) mrus.toArray( new String[ mrus.size() ] );
        PreferenceHelper.getStore().setProperty( PreferenceHelper.MRU, ss );
        setupMenus();
    }
    
    
    
    private void patchAddRecur() {
        if( jTree1.getSelectionPaths() == null )
            return;
        for( int i = 0; i < jTree1.getSelectionPaths().length; i++ ) {
            DefaultMutableTreeNode n = ((DefaultMutableTreeNode)jTree1.getSelectionPaths()[ i ].getLastPathComponent());
            addRecur( n );
        }
    }
    
    private void addRecur( DefaultMutableTreeNode n ) {
        if( n.getUserObject() instanceof Patch ) {
            addPatch( (Patch)n.getUserObject(), n );
        }
        for( int i = 0; i < n.getChildCount(); i++ ) {
            addRecur( (DefaultMutableTreeNode)n.getChildAt( i ) );
        }
    }
    
    private void delRecur( DefaultMutableTreeNode n ) {
        if( n.getUserObject() instanceof Patch ) {
            delPatch( (Patch)n.getUserObject(), n );
        }
        for( int i = 0; i < n.getChildCount(); i++ ) {
            delRecur( (DefaultMutableTreeNode)n.getChildAt( i ) );
        }
    }
    
    private void patchDelRecur() {
        if( jTree1.getSelectionPaths() == null )
            return;
        for( int i = 0; i < jTree1.getSelectionPaths().length; i++ ) {
            if ( jTree1.getSelectionPaths()[ i ] != null ) {
                DefaultMutableTreeNode n = ((DefaultMutableTreeNode)jTree1.getSelectionPaths()[ i ].getLastPathComponent());
                delRecur( n );
            }
        }
    }
    
    private void patchAdd() {
        if( jTree1.getSelectionPaths() == null )
            return;
        for( int i = 0; i < jTree1.getSelectionPaths().length; i++ ) {
            if ( jTree1.getSelectionPaths()[ i ] != null ) {
                TreePath tp = jTree1.getSelectionPaths()[ i ];
                DefaultMutableTreeNode tn = (DefaultMutableTreeNode)tp.getLastPathComponent();
                Object o = tn.getUserObject();
                if ( o instanceof Patch ) {
                    addPatch( (Patch)o, tn );
                }
            }
        }
    }
    
    private void addPatch( Patch patch, TreeNode tp ) {
        patch.setInclude( true );
        jTree1.getModel().valueForPathChanged( new TreePath(((DefaultMutableTreeNode)tp).getPath()), patch );
    }
    
    
    
    private void patchDel() {
        if( jTree1.getSelectionPaths() == null )
            return;
        
        for( int i = 0; i < jTree1.getSelectionPaths().length; i++ ) {
            DefaultMutableTreeNode tn = ((DefaultMutableTreeNode)jTree1.getSelectionPaths()[ i ].getLastPathComponent());
            Object o = tn.getUserObject();
            if ( o instanceof Patch ) {
                Patch patch = (Patch)o;
                delPatch( patch, tn );
            }
            
        }
    }
    
    private void delPatch( Patch patch, TreeNode tp ) {
        patch.setInclude( false );
        jTree1.getModel().valueForPathChanged( new TreePath(((DefaultMutableTreeNode)tp).getPath()), patch );
    }
    
    private void fileSave() {
        try {
            File fx = new File( fileName + ".out" );
            JFileChooser chooser = new JFileChooser( PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_DIR , fx.getPath() ) );
            int returnVal = chooser.showSaveDialog( this );
            if( returnVal != JFileChooser.APPROVE_OPTION )
                return;
            
            if ( chooser.getSelectedFile() == null )
                return;
            File f = chooser.getSelectedFile();
            PreferenceHelper.setProperty( PreferenceHelper.LAST_USED_DIR, f.getPath() );
            FileWriter fw = new FileWriter( f );
            BufferedWriter bw = new BufferedWriter( fw );
            String log = currentModel.marshal( bw, true );
            bw.close();
            JOptionPane.showMessageDialog( this, "Wrote to file " + f.getName() + "\n\n" + log, "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch ( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
        }
        
    }
    
    private void createDiffAndOpen() {
        try {
            CreateDiffDialog cdd = new CreateDiffDialog( (JFrame)SwingUtilities.getAncestorOfClass( JFrame.class, this) , true );
            cdd.setVisible( true );
            String diff = cdd.getDiff();
            
            if ( cdd.ok && diff == null ) {
                ResultDialog rd = new ResultDialog( (JFrame)SwingUtilities.getAncestorOfClass( JFrame.class, this) , true );
                rd.setTitle( "Error!" );
                rd.setMessage( "Error while creating diff" );
                rd.setDetails( "A more satisfying error log should be here, don't you think?" );
                rd.setVisible( true );
            } else if ( cdd.ok ) {
                PatchModel pm = new PatchModel();
                try {
                    StringReader sr = new StringReader( diff );
                    pm.read( sr );
                    setModel( pm );
                    getPatchFrame().refreshTitles();
                } catch( Exception e ) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
                }
                
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
        }
        
    }
    
    
    
    private void applyPatch( boolean reverse, boolean test ) {
        try {
            JFileChooser chooser = new JFileChooser( PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_PATCH_APPLY_DIR, "" ) );
            //            chooser.setFileFilter( directoryFilter );
            chooser.setDialogType( JFileChooser.CUSTOM_DIALOG );
            chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            int returnVal = chooser.showDialog( this, "Choose" );
            if( returnVal != JFileChooser.APPROVE_OPTION )
                return;
            
            if ( chooser.getSelectedFile() == null )
                return;
            File f = chooser.getSelectedFile();
            PreferenceHelper.setProperty( PreferenceHelper.LAST_USED_PATCH_APPLY_DIR, f.getPath() );
            
            String result = currentModel.apply( f.toString(), reverse, false );
            
            ResultDialog rd = new ResultDialog( (JFrame)SwingUtilities.getAncestorOfClass( JFrame.class, this) , true );
            rd.setTitle( "Patch complete!" );
            rd.setMessage( "Patch complete. The following log was generated for your perusal." );
            rd.setDetails( result );
            rd.setVisible( true );
            jTree1.repaint();
//            this.setModel(currentModel);
        } catch ( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
        }
    }
    
    
    public void expandAll( ) {
        if( jTree1.getSelectionPath() == null )
            return;
        expandSub( (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent() );
    }
    
    public void expandSub( DefaultMutableTreeNode node ) {
        jTree1.expandPath( new TreePath( node.getPath() ) );
        for( int i = 0; i < node.getChildCount(); i++ ) {
            DefaultMutableTreeNode subNode = (DefaultMutableTreeNode) node.getChildAt( i );
            expandSub( subNode );
        }
    }
    
    
    public void collapseAll( ) {
        if( jTree1.getSelectionPath() == null )
            return;
        collapseSub( (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent() );
    }
    
    public void collapseSub( DefaultMutableTreeNode node ) {
        for( int i = 0; i < node.getChildCount(); i++ ) {
            DefaultMutableTreeNode subNode = (DefaultMutableTreeNode) node.getChildAt( i );
            collapseSub( subNode );
        }
        jTree1.collapsePath( new TreePath( node.getPath() ) );
    }
    
    
    private void about() {
        JOptionPane.showMessageDialog( this, "Try this: \n\n" +
                "cvs diff -RN -u -r <branch> -r HEAD > toBeEdited.patch\n" +
                "<edit and save as saved.patch>\n" +
                "patch -p0 < saved.patch\n\n" +
                "caveat: lots. added/removed files are probably going to disappear.", "About", JOptionPane.INFORMATION_MESSAGE );
    }
    
    private void newTab() {
        getPatchFrame().newTab();
    }
    
    private void reRoot() {
        if( jTree1.getSelectionPaths() == null )
            return;
        try {
            
            StringBuffer reRootedSB = new StringBuffer();
            for( int i = 0; i < jTree1.getSelectionPaths().length; i++ ) {
                DefaultMutableTreeNode tn = ((DefaultMutableTreeNode)jTree1.getSelectionPaths()[i].getLastPathComponent());
                if ( tn instanceof PatchModel.FileNode ) {
                    StringWriter w = new StringWriter();
                    ((PatchModel.FileNode)tn).marshalFromHere( w );
                    String newDiff = w.toString();
                    //                System.out.println( newDiff );
                    Object[] path = tn.getPath();
                    path[ path.length - 1 ] = null;
                    
                    StringBuffer sb = new StringBuffer();
                    for( Object el : path ) {
                        if ( el == null )
                            continue;
                        sb.append( el.toString() );
                        sb.append( "/" );
                    }
                    String strPath = sb.toString();
                    if ( strPath.startsWith( "Root/" ) );
                    strPath = strPath.substring( 5 );
                    System.out.println( strPath );
                    String reRooted = newDiff.replaceAll( Pattern.quote( "Index: " + strPath ), "Index: " );
                    reRooted = reRooted.replaceAll( Pattern.quote( "--- " + strPath ), "--- " );
                    reRooted = reRooted.replaceAll( Pattern.quote( "+++ " + strPath ), "+++ " );
                    //                System.out.println( reRooted );
                    reRootedSB.append( reRooted );
                    
                }
            }
            PatchModel pm = new PatchModel();
            pm.read( new StringReader( reRootedSB.toString() ) );
            this.setModel( pm );
            
        } catch ( Throwable t ) {
            JOptionPane.showMessageDialog( this, t.getMessage() );
        }
        
    }
    
    private DiffObject getCurrentDiffObject() {
        if ( jTree1.getLeadSelectionPath() == null )
            return null;
        
        Object o1 = jTree1.getLeadSelectionPath().getLastPathComponent();

        DefaultMutableTreeNode tn1 = (DefaultMutableTreeNode)o1;
        
        Object o2 = tn1.getUserObject();
        
        if( o2 instanceof DiffObject ) {
            return (DiffObject)o2;
        }
        return null;
    }
    
    private void treeSelection( TreeSelectionEvent evt ) {
        updateActionsEnabled();
        
        
        if ( evt.getNewLeadSelectionPath() == null )
            return;
        
        Object o1 = evt.getNewLeadSelectionPath().getLastPathComponent();
        DefaultMutableTreeNode tn1 = (DefaultMutableTreeNode)o1;
        
        Object o2 = tn1.getUserObject();
        
        if( o2 instanceof DiffObject ) {
            DiffObject d = (DiffObject)o2;
            
            if( d instanceof PatchModel )
                setDisplayedText( "Entire patch model:)" );
            else
                setDisplayedText( d.marshal() );
        } else {
            setDisplayedText( "" );
        }
        updateStatus( "", "" );
    }
    
    
    private void updateStatus(String a, String b) {
        String showing = a == null ? getDisplayedText() : a;
        DiffObject dob = getCurrentDiffObject();
        String orig = b == null ? (dob == null ? null : dob.marshal() ) : b;
        System.out.println("Showing: \n" + showing );
        System.out.println("Orig: \n" + orig);
        if ( Utils.nullSafeEquals( showing, orig ) ) {
            lblStatus2.setText( "" );
        } else {
            lblStatus2.setText( "Text changed" );
        }
    }
    
    private void setTreeFocus() {
        TreePath path = jTree1.getSelectionPath();
        if( path == null )
            return;
        
        jTree1.requestFocus();
    }
    
    private void updateActionsEnabled() {
        boolean isPatch = false;
        boolean isDir = false;
        boolean hasFile = currentModel != null;
        
        if ( jTree1.getSelectionPath() != null ) {
            DefaultMutableTreeNode node = ((DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent());
            Object o = node.getUserObject();
            isPatch = o instanceof Patch;
            isDir = node instanceof PatchModel.FileNode || o instanceof FileDiff;
        }
        patchAddAction.setEnabled( isPatch );
        patchDelAction.setEnabled( isPatch );
        patchAddRecurAction.setEnabled( isDir );
        patchDelRecurAction.setEnabled( isDir );
        fileSaveAction.setEnabled( hasFile );
        expandSubAction.setEnabled( hasFile && isDir );
        collapseSubAction.setEnabled( hasFile && isDir );
        applyPatchAction.setEnabled( hasFile );
        testPatchAction.setEnabled( hasFile );
        applyPatchActionReverse.setEnabled( hasFile );
    }
    
    private void setDisplayedText( String t ) {
        this.jTextArea1.setText( t );
    }
    
    private String getDisplayedText() {
        return this.jTextArea1.getText();
    }
    
    
    private PatchFrame getPatchFrame() {
        return (PatchFrame)SwingUtilities.getAncestorOfClass( PatchFrame.class, this );
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        mbTop = new javax.swing.JMenuBar();
        mnSystem = new javax.swing.JMenu();
        mnNode = new javax.swing.JMenu();
        mnWindow = new javax.swing.JMenu();
        mnHelp = new javax.swing.JMenu();
        jPanel3 = new javax.swing.JPanel();
        tbSystem = new javax.swing.JToolBar();
        tbNode = new javax.swing.JToolBar();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblStatus1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblStatus2 = new javax.swing.JLabel();

        mnSystem.setText("System");
        mnSystem.setMnemonic('y');
        mbTop.add(mnSystem);

        mnNode.setText("Node");
        mnNode.setMnemonic('n');
        mbTop.add(mnNode);

        mbTop.add(mnWindow);

        mbTop.add(mnHelp);

        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel3.setAutoscrolls(true);
        jPanel3.setPreferredSize(new java.awt.Dimension(0, 36));
        jPanel3.add(tbSystem);

        jPanel3.add(tbNode);

        add(jPanel3, java.awt.BorderLayout.NORTH);

        jSplitPane1.setDividerLocation(250);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea1KeyTyped(evt);
            }
        });

        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTree1.setCellRenderer(new PatchTreeRenderer());
        jTree1.setRootVisible(false);
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(jTree1);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(new javax.swing.border.EtchedBorder());
        jPanel5.add(lblStatus1, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel6.setBorder(new javax.swing.border.EtchedBorder());
        jPanel6.setPreferredSize(new java.awt.Dimension(100, 18));
        lblStatus2.setMinimumSize(new java.awt.Dimension(100, 14));
        jPanel6.add(lblStatus2, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6, java.awt.BorderLayout.EAST);

        add(jPanel4, java.awt.BorderLayout.SOUTH);

    }//GEN-END:initComponents

    private void jTextArea1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyTyped
//        updateStatus(null, null);
    }//GEN-LAST:event_jTextArea1KeyTyped
    
    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        if( evt.getButton() == MouseEvent.BUTTON3 && evt.getComponent() != null ) {
            showPopup( evt );
        }
    }//GEN-LAST:event_jTree1MouseClicked
    
    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        // TODO add your handling code here:
        treeSelection( evt );
    }//GEN-LAST:event_jTree1ValueChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JSplitPane jSplitPane1;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTree jTree1;
    public javax.swing.JLabel lblStatus1;
    public javax.swing.JLabel lblStatus2;
    public javax.swing.JMenuBar mbTop;
    public javax.swing.JMenu mnHelp;
    public javax.swing.JMenu mnNode;
    public javax.swing.JMenu mnSystem;
    public javax.swing.JMenu mnWindow;
    public javax.swing.JToolBar tbNode;
    public javax.swing.JToolBar tbSystem;
    // End of variables declaration//GEN-END:variables
    
    
    public JMenuBar getMBTop() {
        return mbTop;
    }
}
