/*
 * Body.java
 *
 * Created on June 25, 2003, 3:25 PM
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

/*Import*/
import java.util.Set;

/**
 *
 * @author  william
 */
public class Body extends BaseMessageObject{
    
    /** Holds value of property multipart. */
    private boolean multipart=false;
    
    /** Holds value of property content. */
    private String content = null;
    
    /** Holds value of property bodyParts. */
    private Set<BodyPart> bodyParts = null;
      
    /** Getter for property multipart.
     * @return Value of property multipart.
     *
     */
    public boolean isMultipart() {
        return this.multipart;
    }
    
    /** Setter for property multipart.
     * @param multipart New value of property multipart.
     *
     */
    private void setMultipart(boolean multipart) {
        this.multipart = multipart;
    }
    
    /** Getter for property content.
     * @return Value of property content.
     *
     */
    public String getContent() {
        return this.content;
    }
    
    /** Setter for property content.
     * @param content New value of property content.
     *
     */
    protected void setContent(String content) {
        this.content = content;
    }
    
    /** Getter for property bodyParts.
     * @return Value of property bodyParts.
     *
     */
    public Set<BodyPart> getBodyParts() {
        return this.bodyParts;
    }
    
    /** Setter for property bodyParts.
     * @param bodyParts New value of property bodyParts.
     *
     */
    protected void setBodyParts(Set<BodyPart> bodyParts) {
        setMultipart(true);
        this.bodyParts = bodyParts;
    }
}
