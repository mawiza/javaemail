/*
 * HeaderField.java
 *
 * Created on June 23, 2003, 2:10 AM
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

/**
 *
 * @author  William Miles
 */
public class HeaderField extends BaseMessageObject{
    
    /** Holds value of property name. */
    private String name;
    
    /** Holds value of property body. */
    private String body;
        
    /** Getter for property field name.
     * @return Value of property field name.
     *
     */
    public String getName() {
        return this.name;
    }
    
    /** Setter for property field name.
     * @param fieldName New value of property field name.
     *
     */
    protected void setName(String name) {
        this.name = name;
    }
    
    /** Getter for property field body.
     * @return Value of property field body.
     *
     */
    public String getBody() {
        return this.body;
    }
    
    /** Setter for property field body.
     * @param fieldValue New value of property field body.
     *
     */
    protected void setBody(String body) {
        this.body = body;
    }
    
}
