/*
 * Message.java
 *
 * Created on June 21, 2003, 12:40 AM
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

/*Import java*/
import java.util.Set;

/**
 *
 * @author  William Miles
 */
public class Message extends BaseMessageObject{
    
    /** Holds value of property headerFields. */
    private Set<HeaderField> headerFields;
    
    /** Holds value of property body. */
    private Body body;
        
    /** Holds value of property preamble. */
    private String preamble;
    
    /** Holds value of property epilogue. */
    private String epilogue;
    
    /** Getter for property headers.
     * @return Value of property headers.
     *
     */
    public Set<HeaderField> getHeaderFields() {
        return this.headerFields;
    }
    
    /** Setter for property headers.
     * @param headers New value of property headers.
     *
     */
    protected void setHeaderFields(Set<HeaderField> headerFields) {
        this.headerFields = headerFields;
    }
    
    /** Getter for property bodyParts.
     * @return Value of property bodyParts.
     *
     */
    public Body getBody() {
        return this.body;
    }
    
    /** Setter for property bodyParts.
     * @param bodyParts New value of property bodyParts.
     *
     */
    protected void setBody(Body body) {
        this.body = body;
    }
    
    /** Getter for property preamble.
     * @return Value of property preamble.
     *
     */
    public String getPreamble() {
        return this.preamble;
    }
    
    /** Setter for property preamble.
     * @param preamble New value of property preamble.
     *
     */
    protected void setPreamble(String preamble) {
        this.preamble = preamble;
    }
    
    /** Getter for property epilogue.
     * @return Value of property epilogue.
     *
     */
    public String getEpilogue() {
        return this.epilogue;
    }
    
    /** Setter for property epilogue.
     * @param epilogue New value of property epilogue.
     *
     */
    protected void setEpilogue(String epilogue) {
        this.epilogue = epilogue;
    }
    
}
