/*
 * HeaderField.java
 *
 * Created on June 23, 2003, 2:10 AM
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Library General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307,
 * USA.
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
