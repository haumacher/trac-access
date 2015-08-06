/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lustin.trac.xmlprc;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author lustin
 */
public interface Wiki {
    Hashtable getRecentChanges(Date since);
    
    Integer getRPCVersionSupported();
    
    String getPage(String pagename);
    String getPage(String pagename, Integer version);
    
    String getPageVersion(String pagename);
    String getPageVersion(String pagename, Integer version);
    
    String getPageHTML(String pagename);
    String getPageHTML(String pagename, Integer version);
    
    String getPageHTMLVersion(String pagename);
    String getPageHTMLVersion(String pagename, Integer version);
            
    Vector getAllPages();
    
    Hashtable getPageInfo(String pagename);
    Hashtable getPageInfo(String pagename, Integer version);
    
    Hashtable getPageInfoVersion(String pagename);
    Hashtable getPageInfoVersion(String pagename, Integer version);
            
    Boolean putPage(String pagename, String content, Hashtable attributes);
    
    Hashtable istAttachments(String pagename);
    
    byte[] getAttachment(String path);
    
    Boolean putAttachment(String path, byte[] data);
    
    Boolean putAttachmentEx(String pagename, String filename, String description, byte[] data);
    Boolean putAttachmentEx(String pagename, String filename, String description, byte[] data, boolean replace);
    
    Boolean deletePage(String name);
    Boolean deletePage(String name, int version);
    
    Boolean deleteAttachment(String path);
    
    Vector listLinks(String pagename);
    String wikiToHtml(String text);
    
}
