/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lustin.trac.xmlprc;

import java.util.Vector;

/**
 *
 * @author lustin
 */
public interface Search {
    Vector getSearchFilters();
    Vector performSearch(String query);
    Vector performSearch(String query, Vector filters);
}
