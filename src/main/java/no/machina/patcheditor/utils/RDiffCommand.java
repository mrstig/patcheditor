/*
 * RDiffCommand.java
 *
 * Created on 25. februar 2005, 15:51
 */

package no.machina.patcheditor.utils;

import java.util.LinkedList;
import org.netbeans.lib.cvsclient.ClientServices;
import org.netbeans.lib.cvsclient.command.CommandException;
import org.netbeans.lib.cvsclient.command.diff.DiffCommand;
import org.netbeans.lib.cvsclient.connection.AuthenticationException;
import org.netbeans.lib.cvsclient.event.EventManager;
import org.netbeans.lib.cvsclient.request.ArgumentRequest;

import org.netbeans.lib.cvsclient.request.Request;
import org.netbeans.lib.cvsclient.request.RootRequest;

/**
 *
 * @author stig
 */


public class RDiffCommand extends DiffCommand {
    private String module;
    
    public void execute(ClientServices client, EventManager em) throws CommandException, AuthenticationException {
        client.ensureConnection();
        
        eventManager = em;
        
        requests = new LinkedList();
        
        
        if (client.isFirstCommand()) {
            addRequest(new RootRequest(client.getRepository()));
        }
        
        
        try {
            // parameters come now..
            if (getKeywordSubst() != null && !getKeywordSubst().equals("")) {
                requests.add(new ArgumentRequest("-k" + getKeywordSubst()));
            }
            
            addArgumentRequest(isIgnoreAllWhitespace(), "-w");
            addArgumentRequest(isIgnoreBlankLines(), "-B");
            addArgumentRequest(isIgnoreSpaceChange(), "-b");
            addArgumentRequest(isIgnoreCase(), "-i");
            addArgumentRequest(isContextDiff(), "-c");
            addArgumentRequest(isUnifiedDiff(), "-u");
            addRDSwitches();
            requests.add(new ArgumentRequest(getModule()));
            
//            addRequestForWorkingDirectory(client);
            addArgumentRequests();
            requests.add( new Request() {
                public String getRequestString() {
                    return "rdiff\n";
                }
                public boolean isResponseExpected() {
                    return true;
                }
            });
            client.processRequests(requests);
        } catch (CommandException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CommandException(ex, ex.getLocalizedMessage());
        } finally {
            requests.clear();
        }
    }
    
    /**
     * includes the logic of setting the -r and -D switches to the diff command
     */
    private void addRDSwitches() {
        if (getRevision2() != null) {
            requests.add(new ArgumentRequest("-r"));
            requests.add(new ArgumentRequest(getRevision2()));
        } else {
            if (getBeforeDate2() != null) {
                requests.add(new ArgumentRequest("-D " + getBeforeDate2()));
            }
        }
        // -r switch has precendence over the -d switch - is that right??
        if (getRevision1() != null) {
            requests.add(new ArgumentRequest("-r"));
            requests.add(new ArgumentRequest(getRevision1()));
        } else {
            if (getBeforeDate1() != null) {
                requests.add(new ArgumentRequest("-D " + getBeforeDate1()));
            } else {
                // when neither revision nor flag is set for the command, it is assumed
                // that the second parameters are not set either..
                return;
            }
        }
    }
    
    public String getModule() {
        return module;
    }
    
    public void setModule(String module) {
        this.module = module;
    }
}
