/*
 * BaseMessageObject.java
 *
 * Created on July 24, 2003, 6:24 PM
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
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

/**
 *
 * @author  William Miles
 */
public class BaseMessageObject implements Serializable{
    
    /** Holds value of property raw. */
    private String raw;
    
    /**
     * @return
     */    
    public String toString() {
        StringBuffer results = new StringBuffer();
        Class clazz = getClass();
        Class superclazz = clazz.getSuperclass();

        results.append(getClass().getName() + "\n");
        //results.append(superclazz.getName() + "\n");

        Field[] fields = clazz.getDeclaredFields();
        Field[] superfields = superclazz.getDeclaredFields();

        try {
            AccessibleObject.setAccessible(fields, true);
            AccessibleObject.setAccessible(superfields, true);

            for (int i = 0; i < fields.length; i++) {
                results.append("\t" + fields[i].getName() + "=" +
                               fields[i].get(this) + "\n");
            }
            
            for (int i = 0; i < superfields.length; i++) {
                results.append("\t" + superfields[i].getName() + "=" +
                               superfields[i].get(this) + "\n");
            }
        } catch (Exception e) {
            // ignored!
        }

        return results.toString();
    }
            
    /*Decide how you should do that!!! Should you create a an abstract class
     * with the below method as signature or ...
     */
    /** Getter for property raw.
     * @return Value of property raw.
     *
     */
    protected String getRaw() {
        return this.raw;
    }
    
    /** Setter for property raw.
     * @param raw New value of property raw.
     *
     */
    protected void setRaw(String raw) {
        this.raw = raw;
    }
    
}
