/*
 * CreateDiffDialog.java
 *
 * Created on 24. februar 2005, 14:40
 */

package no.machina.patcheditor.gui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import no.machina.patcheditor.registry.PreferenceHelper;
import no.machina.patcheditor.utils.CVSHelper;

/**
 *
 * @author  stig
 */
public class CreateDiffDialog extends javax.swing.JDialog {
    public boolean ok = false;
    private String diff;
    
    /** Creates new form CreateDiffDialog */
    public CreateDiffDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        initFields();
        
    }
    
    private void initFields() {
        txtRev1.setText( PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_REV1, "" ) );
        txtRev2.setText( PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_REV2, "" ) );
        txtDate1.setText( PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_DATE1, "" ) );
        txtDate2.setText( PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_DATE2, "" ) );
        txtDir.setText( PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_DIR, "" ) );
        
        String useRDiff = PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_USE_RDIFF, "true" );
        chkRDiff.setSelected( "true".equals( useRDiff ) ); 
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtRev1 = new javax.swing.JTextField();
        txtRev2 = new javax.swing.JTextField();
        txtDate1 = new javax.swing.JTextField();
        txtDate2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDir = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        chkRDiff = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        jLabel1.setText(java.util.ResourceBundle.getBundle("i18n").getString("creatediff.revision1"));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 12, -1, -1));

        jLabel2.setText(java.util.ResourceBundle.getBundle("i18n").getString("creatediff.revision2"));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 32, -1, -1));

        jLabel3.setText(java.util.ResourceBundle.getBundle("i18n").getString("creatediff.date1"));
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 12, -1, -1));

        jLabel4.setText(java.util.ResourceBundle.getBundle("i18n").getString("creatediff.date2"));
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 32, -1, -1));

        txtRev1.setNextFocusableComponent(txtRev2);
        txtRev1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRev1ActionPerformed(evt);
            }
        });

        getContentPane().add(txtRev1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 120, 17));

        txtRev2.setNextFocusableComponent(txtDate1);
        getContentPane().add(txtRev2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 120, 17));

        txtDate1.setNextFocusableComponent(txtDate2);
        getContentPane().add(txtDate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, 17));

        txtDate2.setNextFocusableComponent(txtDir);
        getContentPane().add(txtDate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 100, 17));

        jLabel5.setText(java.util.ResourceBundle.getBundle("i18n").getString("creatediff.directory"));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 82, -1, -1));

        txtDir.setNextFocusableComponent(jButton1);
        getContentPane().add(txtDir, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 340, 17));

        jButton1.setText("...");
        jButton1.setNextFocusableComponent(chkRDiff);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 30, 17));

        jButton2.setText(java.util.ResourceBundle.getBundle("i18n").getString("buttons.ok"));
        jButton2.setNextFocusableComponent(jButton3);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 90, -1));

        jButton3.setText(java.util.ResourceBundle.getBundle("i18n").getString("buttons.cancel"));
        jButton3.setNextFocusableComponent(txtRev1);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, -1));

        chkRDiff.setText(java.util.ResourceBundle.getBundle("i18n").getString("creatediff.userdiff"));
        chkRDiff.setNextFocusableComponent(jButton2);
        getContentPane().add(chkRDiff, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 100, 20));

        jLabel6.setText(java.util.ResourceBundle.getBundle("i18n").getString("creatediff.note"));
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 440, -1));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-468)/2, (screenSize.height-190)/2, 468, 190);
    }//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        chooseDir();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    private void chooseDir() {
        // PreferenceHelper.getProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_DIR, "" )
        JFileChooser chooser = new JFileChooser( txtDir.getText() );
        chooser.setDialogType( JFileChooser.CUSTOM_DIALOG );
        chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        int returnVal = chooser.showDialog( this, java.util.ResourceBundle.getBundle("i18n").getString("general.choose") );
        if( returnVal != JFileChooser.APPROVE_OPTION )
            return;
        
        if ( chooser.getSelectedFile() == null )
            return;
        File f = chooser.getSelectedFile();
        
        txtDir.setText( f.toString() );
        
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ok = false;        
        this.setVisible( false );
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if ( txtDir.getText() == null || txtDir.getText().trim().length() == 0 )
            JOptionPane.showMessageDialog( this, java.util.ResourceBundle.getBundle("i18n").getString("general.selectdir") );
        String rev1 = txtRev1.getText();
        String rev2 = txtRev2.getText();
        String date1 = txtDate1.getText();
        String date2 = txtDate2.getText();
        
        rev1 = rev1 != null && rev1.trim().length() > 0 ? rev1 : null;
        rev2 = rev2 != null && rev2.trim().length() > 0 ? rev2 : null;
        date1 = date1 != null && date1.trim().length() > 0 ? date1 : null;
        date2 = date2 != null && date2.trim().length() > 0 ? date2 : null;
        
        diff = CVSHelper.diffDirectory( chkRDiff.isSelected(), txtDir.getText(), rev1, rev2, date1, date2, new CVSHelper.PasswordCallbackHandler() {
            public String handleCallBack( String message, String user, String server ) throws InterruptedException {
                return getPassword( message, user, server );
            }
        });
        

        ok = true;
        
        PreferenceHelper.setProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_USE_RDIFF, "" + chkRDiff.isSelected() );
        PreferenceHelper.setProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_DIR, txtDir.getText() );
        PreferenceHelper.setProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_REV1, txtRev1.getText() );
        PreferenceHelper.setProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_REV2, txtRev2.getText() );
        PreferenceHelper.setProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_DATE1, txtDate1.getText() );
        PreferenceHelper.setProperty( PreferenceHelper.LAST_USED_PATCH_CREATE_DATE2, txtDate2.getText() );
        
        this.setVisible( false );
        this.dispose();

    }//GEN-LAST:event_jButton2ActionPerformed
    
    private String getPassword( String message, String user, String server ) throws InterruptedException {
        PasswordDialog pwd = new PasswordDialog( this, true );
        pwd.setMessage( java.util.ResourceBundle.getBundle("i18n").getString("general.passwordneeded") + user + java.util.ResourceBundle.getBundle("i18n").getString("general.atserver") + server );
        pwd.setVisible( true );
        if( pwd.ok ) {
            return pwd.getPassWord();
        } else
            throw new InterruptedException(java.util.ResourceBundle.getBundle("i18n").getString("general.usercancelled"));
    }
    
    private void txtRev1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRev1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRev1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateDiffDialog(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkRDiff;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtDate1;
    private javax.swing.JTextField txtDate2;
    private javax.swing.JTextField txtDir;
    private javax.swing.JTextField txtRev1;
    private javax.swing.JTextField txtRev2;
    // End of variables declaration//GEN-END:variables
    
}