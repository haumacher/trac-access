/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

