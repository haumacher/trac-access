/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lustin.trac.xmlprc;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author lustin
 */
public interface TicketExt {
    Vector update(Integer id, String author, String comment, Hashtable attributes);
}
