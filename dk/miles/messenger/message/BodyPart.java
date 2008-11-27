/*
 * BodyPart.java
 *
 * Created on June 21, 2003, 12:43 AM
 *
 * This file is part of javaemail.
 * 
 * javaemail is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * javaemail is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General  Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with javaemail.  If not, see <http://www.gnu.org/licenses/>.
 */

package dk.miles.messenger.message;

/*Import java*/
import java.util.Set;

/**
 *
 * @author  William Miles
 */
public class BodyPart extends Body{
    
    /** Holds value of property headerFields. */
    private Set headerFields;
            
    /** Holds value of property boundary. */
    private String boundary;
    
    /** Getter for property headerFields.
     * @return Value of property headerFields.
     *
     */
    public Set getHeaderFields() {
        return this.headerFields;
    }
    
    /** Setter for property headerFields.
     * @param headerFields New value of property headerFields.
     *
     */
    protected void setHeaderFields(Set headerFields) {
        this.headerFields = headerFields;
    }
    
    /** Getter for property boundary.
     * @return Value of property boundary.
     *
     */
    public String getBoundary() {
        return this.boundary;
    }
    
    /** Setter for property boundary.
     * @param boundary New value of property boundary.
     *
     */
    protected void setBoundary(String boundary) {
        this.boundary = boundary;
    }    
}
