/*
 * Copyright (c) 2005-2015, Alec Thomas et al.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the FreeBSD Project.
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
