/*
 * SwingIcons.java
 *
 * Created on 11. juni 2004, 09:50
 */

package no.machina.patcheditor.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;

/** Helper class for accessing the Java Look'n Feel Guidelines icons. 
 *
 * @author  narve
 */
public class SwingIcons {

    public static String APPLET = "toolbarButtonGraphics/development/Applet16.gif";
    public static String APPLET_LARGE = "toolbarButtonGraphics/development/Applet24.gif";
    public static String APPLICATION = "toolbarButtonGraphics/development/Application16.gif";
    public static String APPLICATION_LARGE = "toolbarButtonGraphics/development/Application24.gif";
    public static String APPLICATIONDEPLOY = "toolbarButtonGraphics/development/ApplicationDeploy16.gif";
    public static String APPLICATIONDEPLOY_LARGE = "toolbarButtonGraphics/development/ApplicationDeploy24.gif";
    public static String BEAN = "toolbarButtonGraphics/development/Bean16.gif";
    public static String BEAN_LARGE = "toolbarButtonGraphics/development/Bean24.gif";
    public static String BEANADD = "toolbarButtonGraphics/development/BeanAdd16.gif";
    public static String BEANADD_LARGE = "toolbarButtonGraphics/development/BeanAdd24.gif";
    public static String ENTERPRISEJAVABEAN = "toolbarButtonGraphics/development/EnterpriseJavaBean16.gif";
    public static String ENTERPRISEJAVABEAN_LARGE = "toolbarButtonGraphics/development/EnterpriseJavaBean24.gif";
    public static String ENTERPRISEJAVABEANJAR = "toolbarButtonGraphics/development/EnterpriseJavaBeanJar16.gif";
    public static String ENTERPRISEJAVABEANJAR_LARGE = "toolbarButtonGraphics/development/EnterpriseJavaBeanJar24.gif";
    public static String HOST = "toolbarButtonGraphics/development/Host16.gif";
    public static String HOST_LARGE = "toolbarButtonGraphics/development/Host24.gif";
    public static String J2EEAPPLICATION = "toolbarButtonGraphics/development/J2EEApplication16.gif";
    public static String J2EEAPPLICATION_LARGE = "toolbarButtonGraphics/development/J2EEApplication24.gif";
    public static String J2EEAPPLICATIONCLIENT = "toolbarButtonGraphics/development/J2EEApplicationClient16.gif";
    public static String J2EEAPPLICATIONCLIENT_LARGE = "toolbarButtonGraphics/development/J2EEApplicationClient24.gif";
    public static String J2EEAPPLICATIONCLIENTADD = "toolbarButtonGraphics/development/J2EEApplicationClientAdd16.gif";
    public static String J2EEAPPLICATIONCLIENTADD_LARGE = "toolbarButtonGraphics/development/J2EEApplicationClientAdd24.gif";
    public static String J2EESERVER = "toolbarButtonGraphics/development/J2EEServer16.gif";
    public static String J2EESERVER_LARGE = "toolbarButtonGraphics/development/J2EEServer24.gif";
    public static String JAR = "toolbarButtonGraphics/development/Jar16.gif";
    public static String JAR_LARGE = "toolbarButtonGraphics/development/Jar24.gif";
    public static String JARADD = "toolbarButtonGraphics/development/JarAdd16.gif";
    public static String JARADD_LARGE = "toolbarButtonGraphics/development/JarAdd24.gif";
    public static String SERVER = "toolbarButtonGraphics/development/Server16.gif";
    public static String SERVER_LARGE = "toolbarButtonGraphics/development/Server24.gif";
    public static String WAR = "toolbarButtonGraphics/development/War16.gif";
    public static String WAR_LARGE = "toolbarButtonGraphics/development/War24.gif";
    public static String WARADD = "toolbarButtonGraphics/development/WarAdd16.gif";
    public static String WARADD_LARGE = "toolbarButtonGraphics/development/WarAdd24.gif";
    public static String WEBCOMPONENT = "toolbarButtonGraphics/development/WebComponent16.gif";
    public static String WEBCOMPONENT_LARGE = "toolbarButtonGraphics/development/WebComponent24.gif";
    public static String WEBCOMPONENTADD = "toolbarButtonGraphics/development/WebComponentAdd16.gif";
    public static String WEBCOMPONENTADD_LARGE = "toolbarButtonGraphics/development/WebComponentAdd24.gif";
    public static String ABOUT = "toolbarButtonGraphics/general/About16.gif";
    public static String ABOUT_LARGE = "toolbarButtonGraphics/general/About24.gif";
    public static String ADD = "toolbarButtonGraphics/general/Add16.gif";
    public static String ADD_LARGE = "toolbarButtonGraphics/general/Add24.gif";
    public static String ALIGNBOTTOM = "toolbarButtonGraphics/general/AlignBottom16.gif";
    public static String ALIGNBOTTOM_LARGE = "toolbarButtonGraphics/general/AlignBottom24.gif";
    public static String ALIGNCENTER = "toolbarButtonGraphics/general/AlignCenter16.gif";
    public static String ALIGNCENTER_LARGE = "toolbarButtonGraphics/general/AlignCenter24.gif";
    public static String ALIGNJUSTIFYHORIZONTAL = "toolbarButtonGraphics/general/AlignJustifyHorizontal16.gif";
    public static String ALIGNJUSTIFYHORIZONTAL_LARGE = "toolbarButtonGraphics/general/AlignJustifyHorizontal24.gif";
    public static String ALIGNJUSTIFYVERTICAL = "toolbarButtonGraphics/general/AlignJustifyVertical16.gif";
    public static String ALIGNJUSTIFYVERTICAL_LARGE = "toolbarButtonGraphics/general/AlignJustifyVertical24.gif";
    public static String ALIGNLEFT = "toolbarButtonGraphics/general/AlignLeft16.gif";
    public static String ALIGNLEFT_LARGE = "toolbarButtonGraphics/general/AlignLeft24.gif";
    public static String ALIGNRIGHT = "toolbarButtonGraphics/general/AlignRight16.gif";
    public static String ALIGNRIGHT_LARGE = "toolbarButtonGraphics/general/AlignRight24.gif";
    public static String ALIGNTOP = "toolbarButtonGraphics/general/AlignTop16.gif";
    public static String ALIGNTOP_LARGE = "toolbarButtonGraphics/general/AlignTop24.gif";
    public static String BOOKMARKS = "toolbarButtonGraphics/general/Bookmarks16.gif";
    public static String BOOKMARKS_LARGE = "toolbarButtonGraphics/general/Bookmarks24.gif";
    public static String COMPOSEMAIL = "toolbarButtonGraphics/general/ComposeMail16.gif";
    public static String COMPOSEMAIL_LARGE = "toolbarButtonGraphics/general/ComposeMail24.gif";
    public static String CONTEXTUALHELP = "toolbarButtonGraphics/general/ContextualHelp16.gif";
    public static String CONTEXTUALHELP_LARGE = "toolbarButtonGraphics/general/ContextualHelp24.gif";
    public static String COPY = "toolbarButtonGraphics/general/Copy16.gif";
    public static String COPY_LARGE = "toolbarButtonGraphics/general/Copy24.gif";
    public static String CUT = "toolbarButtonGraphics/general/Cut16.gif";
    public static String CUT_LARGE = "toolbarButtonGraphics/general/Cut24.gif";
    public static String DELETE = "toolbarButtonGraphics/general/Delete16.gif";
    public static String DELETE_LARGE = "toolbarButtonGraphics/general/Delete24.gif";
    public static String EDIT = "toolbarButtonGraphics/general/Edit16.gif";
    public static String EDIT_LARGE = "toolbarButtonGraphics/general/Edit24.gif";
    public static String EXPORT = "toolbarButtonGraphics/general/Export16.gif";
    public static String EXPORT_LARGE = "toolbarButtonGraphics/general/Export24.gif";
    public static String FIND = "toolbarButtonGraphics/general/Find16.gif";
    public static String FIND_LARGE = "toolbarButtonGraphics/general/Find24.gif";
    public static String FINDAGAIN = "toolbarButtonGraphics/general/FindAgain16.gif";
    public static String FINDAGAIN_LARGE = "toolbarButtonGraphics/general/FindAgain24.gif";
    public static String HELP = "toolbarButtonGraphics/general/Help16.gif";
    public static String HELP_LARGE = "toolbarButtonGraphics/general/Help24.gif";
    public static String HISTORY = "toolbarButtonGraphics/general/History16.gif";
    public static String HISTORY_LARGE = "toolbarButtonGraphics/general/History24.gif";
    public static String IMPORT = "toolbarButtonGraphics/general/Import16.gif";
    public static String IMPORT_LARGE = "toolbarButtonGraphics/general/Import24.gif";
    public static String INFORMATION = "toolbarButtonGraphics/general/Information16.gif";
    public static String INFORMATION_LARGE = "toolbarButtonGraphics/general/Information24.gif";
    public static String NEW = "toolbarButtonGraphics/general/New16.gif";
    //public static final String NEW        = "/icons/full/clcl16/add_att.gif";
    public static String NEW_LARGE = "toolbarButtonGraphics/general/New24.gif";
    public static String OPEN = "toolbarButtonGraphics/general/Open16.gif";
    public static String OPEN_LARGE = "toolbarButtonGraphics/general/Open24.gif";
    public static String PAGESETUP = "toolbarButtonGraphics/general/PageSetup16.gif";
    public static String PAGESETUP_LARGE = "toolbarButtonGraphics/general/PageSetup24.gif";
    public static String PASTE = "toolbarButtonGraphics/general/Paste16.gif";
    public static String PASTE_LARGE = "toolbarButtonGraphics/general/Paste24.gif";
    public static String PREFERENCES = "toolbarButtonGraphics/general/Preferences16.gif";
    public static String PREFERENCES_LARGE = "toolbarButtonGraphics/general/Preferences24.gif";
    public static String PRINT = "toolbarButtonGraphics/general/Print16.gif";
    public static String PRINT_LARGE = "toolbarButtonGraphics/general/Print24.gif";
    public static String PRINTPREVIEW = "toolbarButtonGraphics/general/PrintPreview16.gif";
    public static String PRINTPREVIEW_LARGE = "toolbarButtonGraphics/general/PrintPreview24.gif";
    public static String PROPERTIES = "toolbarButtonGraphics/general/Properties16.gif";
    public static String PROPERTIES_LARGE = "toolbarButtonGraphics/general/Properties24.gif";
    public static String REDO = "toolbarButtonGraphics/general/Redo16.gif";
    public static String REDO_LARGE = "toolbarButtonGraphics/general/Redo24.gif";
    public static String REFRESH = "toolbarButtonGraphics/general/Refresh16.gif";
    public static String REFRESH_LARGE = "toolbarButtonGraphics/general/Refresh24.gif";
    public static String REMOVE = "toolbarButtonGraphics/general/Remove16.gif";
    public static String REMOVE_LARGE = "toolbarButtonGraphics/general/Remove24.gif";
    public static String REPLACE = "toolbarButtonGraphics/general/Replace16.gif";
    public static String REPLACE_LARGE = "toolbarButtonGraphics/general/Replace24.gif";
    public static String SAVE = "toolbarButtonGraphics/general/Save16.gif";
    public static String SAVE_LARGE = "toolbarButtonGraphics/general/Save24.gif";
    public static String SAVEALL = "toolbarButtonGraphics/general/SaveAll16.gif";
    public static String SAVEALL_LARGE = "toolbarButtonGraphics/general/SaveAll24.gif";
    public static String SAVEAS = "toolbarButtonGraphics/general/SaveAs16.gif";
    public static String SAVEAS_LARGE = "toolbarButtonGraphics/general/SaveAs24.gif";
    public static String SEARCH = "toolbarButtonGraphics/general/Search16.gif";
    public static String SEARCH_LARGE = "toolbarButtonGraphics/general/Search24.gif";
    public static String SENDMAIL = "toolbarButtonGraphics/general/SendMail16.gif";
    public static String SENDMAIL_LARGE = "toolbarButtonGraphics/general/SendMail24.gif";
    public static String STOP = "toolbarButtonGraphics/general/Stop16.gif";
    public static String STOP_LARGE = "toolbarButtonGraphics/general/Stop24.gif";
    public static String TIPOFTHEDAY = "toolbarButtonGraphics/general/TipOfTheDay16.gif";
    public static String TIPOFTHEDAY_LARGE = "toolbarButtonGraphics/general/TipOfTheDay24.gif";
    public static String UNDO = "toolbarButtonGraphics/general/Undo16.gif";
    public static String UNDO_LARGE = "toolbarButtonGraphics/general/Undo24.gif";
    public static String ZOOM = "toolbarButtonGraphics/general/Zoom16.gif";
    public static String ZOOM_LARGE = "toolbarButtonGraphics/general/Zoom24.gif";
    public static String ZOOMIN = "toolbarButtonGraphics/general/ZoomIn16.gif";
    public static String ZOOMIN_LARGE = "toolbarButtonGraphics/general/ZoomIn24.gif";
    public static String ZOOMOUT = "toolbarButtonGraphics/general/ZoomOut16.gif";
    public static String ZOOMOUT_LARGE = "toolbarButtonGraphics/general/ZoomOut24.gif";
    public static String FASTFORWARD = "toolbarButtonGraphics/media/FastForward16.gif";
    public static String FASTFORWARD_LARGE = "toolbarButtonGraphics/media/FastForward24.gif";
    public static String MOVIE = "toolbarButtonGraphics/media/Movie16.gif";
    public static String MOVIE_LARGE = "toolbarButtonGraphics/media/Movie24.gif";
    public static String PAUSE = "toolbarButtonGraphics/media/Pause16.gif";
    public static String PAUSE_LARGE = "toolbarButtonGraphics/media/Pause24.gif";
    public static String PLAY = "toolbarButtonGraphics/media/Play16.gif";
    public static String PLAY_LARGE = "toolbarButtonGraphics/media/Play24.gif";
    public static String REWIND = "toolbarButtonGraphics/media/Rewind16.gif";
    public static String REWIND_LARGE = "toolbarButtonGraphics/media/Rewind24.gif";
    public static String STEPBACK = "toolbarButtonGraphics/media/StepBack16.gif";
    public static String STEPBACK_LARGE = "toolbarButtonGraphics/media/StepBack24.gif";
    public static String STEPFORWARD = "toolbarButtonGraphics/media/StepForward16.gif";
    public static String STEPFORWARD_LARGE = "toolbarButtonGraphics/media/StepForward24.gif";


    public static String VOLUME = "toolbarButtonGraphics/media/Volume16.gif";
    public static String VOLUME_LARGE = "toolbarButtonGraphics/media/Volume24.gif";
    public static String BACK = "toolbarButtonGraphics/navigation/Back16.gif";
    public static String BACK_LARGE = "toolbarButtonGraphics/navigation/Back24.gif";
    public static String DOWN = "toolbarButtonGraphics/navigation/Down16.gif";
    public static String DOWN_LARGE = "toolbarButtonGraphics/navigation/Down24.gif";
    public static String FORWARD = "toolbarButtonGraphics/navigation/Forward16.gif";
    public static String FORWARD_LARGE = "toolbarButtonGraphics/navigation/Forward24.gif";
    public static String HOME = "toolbarButtonGraphics/navigation/Home16.gif";
    public static String HOME_LARGE = "toolbarButtonGraphics/navigation/Home24.gif";
    public static String UP = "toolbarButtonGraphics/navigation/Up16.gif";
    public static String UP_LARGE = "toolbarButtonGraphics/navigation/Up24.gif";
    public static String COLUMNDELETE = "toolbarButtonGraphics/table/ColumnDelete16.gif";
    public static String COLUMNDELETE_LARGE = "toolbarButtonGraphics/table/ColumnDelete24.gif";
    public static String COLUMNINSERTAFTER = "toolbarButtonGraphics/table/ColumnInsertAfter16.gif";
    public static String COLUMNINSERTAFTER_LARGE = "toolbarButtonGraphics/table/ColumnInsertAfter24.gif";
    public static String COLUMNINSERTBEFORE = "toolbarButtonGraphics/table/ColumnInsertBefore16.gif";
    public static String COLUMNINSERTBEFORE_LARGE = "toolbarButtonGraphics/table/ColumnInsertBefore24.gif";
    public static String ROWDELETE = "toolbarButtonGraphics/table/RowDelete16.gif";
    public static String ROWDELETE_LARGE = "toolbarButtonGraphics/table/RowDelete24.gif";
    public static String ROWINSERTAFTER = "toolbarButtonGraphics/table/RowInsertAfter16.gif";
    public static String ROWINSERTAFTER_LARGE = "toolbarButtonGraphics/table/RowInsertAfter24.gif";
    public static String ROWINSERTBEFORE = "toolbarButtonGraphics/table/RowInsertBefore16.gif";
    public static String ROWINSERTBEFORE_LARGE = "toolbarButtonGraphics/table/RowInsertBefore24.gif";


    public static String ALIGNJUSTIFY = "toolbarButtonGraphics/text/AlignJustify16.gif";
    public static String ALIGNJUSTIFY_LARGE = "toolbarButtonGraphics/text/AlignJustify24.gif";

    public static String BOLD = "toolbarButtonGraphics/text/Bold16.gif";
    public static String BOLD_LARGE = "toolbarButtonGraphics/text/Bold24.gif";
    public static String ITALIC = "toolbarButtonGraphics/text/Italic16.gif";
    public static String ITALIC_LARGE = "toolbarButtonGraphics/text/Italic24.gif";
    public static String NORMAL = "toolbarButtonGraphics/text/Normal16.gif";
    public static String NORMAL_LARGE = "toolbarButtonGraphics/text/Normal24.gif";
    public static String UNDERLINE = "toolbarButtonGraphics/text/Underline16.gif";
    public static String UNDERLINE_LARGE = "toolbarButtonGraphics/text/Underline24.gif";


    protected static Map iconCache = new HashMap();

    protected static boolean hasAlerted = false;

    public static Icon getIcon( String resource ) {
        try {
            Icon icon = (Icon)iconCache.get( resource );
            if( icon == null ) {
                // ?? Thread.currentThread().getContextClassLoader()
                // ClassLoader cl = RunnableAction.class.getClassLoader();
                URL res = Thread.currentThread().getContextClassLoader().getResource( resource );
                //Logging.NARVE.info("Res: " + res + ", resname=" + resource );
                icon = new javax.swing.ImageIcon( res );

                iconCache.put( resource, icon) ;
            }
            return icon;
        } catch( NullPointerException e ) {
            RuntimeException re = new RuntimeException( "Resource '" + resource + "' not found. ", e );
            if( !hasAlerted ) {
                re.printStackTrace();
                hasAlerted = true;
            }
            return null;
        }
    }
}