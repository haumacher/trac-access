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

/**
 *
 * @author lustin
 */
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public interface Ticket

{
    public interface TicketProperty{
        Vector getAll();
        Hashtable Get(String name);
    }
    public interface Milestone  extends TicketProperty {    }
    public interface Severity   extends TicketProperty {    }
    public interface Type       extends TicketProperty {    }
    public interface Resoluton  extends TicketProperty {    }
    public interface Priority   extends TicketProperty {    }
    public interface Component  extends TicketProperty {    }
    public interface Version    extends TicketProperty {    }
    public interface Status     extends TicketProperty {    }

    Vector query(); // qstr="status!=closed"
    Vector query(String qstr);
    
    Integer delete(Integer id);
    
    Integer create( String summary, String description);
    Integer create( String summary, String description, Hashtable attribute);
    Integer create( String summary, String description, Hashtable attribute, Boolean notify);
    
    Vector get(Integer id);
  
    Vector update(Integer id, String comment);
    Vector update(Integer id, String comment, Hashtable attributes);
    Vector update(Integer id, String comment, Hashtable attributes, Boolean notify);
    
    Hashtable changeLog(Integer id);
    Hashtable changeLog(Integer id, Integer when);
    
    Vector listAttachments(Integer ticket);
    
    byte[] getAttachment(Integer ticket, String filename);
    
    String putAttachment(Integer ticket, String filename, String description, byte[] data);
    String putAttachment(Integer ticket, String filename, String description, byte[] data, Boolean replace);
    
    Boolean deleteAttachment(Integer ticket, String filename);
    
    Vector<HashMap> getTicketFields();
}

