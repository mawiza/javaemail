/*
 * XMLIntrospector.java
 *
 * Created on December 4, 2003, 1:58 PM
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
import java.io.StringWriter;

/*Import jakarta*/
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author  william
 */
public class XMLIntrospector {
    
    /***/
    private Log log =
                LogFactory.getFactory().getInstance(this.getClass().getName());
    
    /** Creates a new instance of XMLIntrospector */
    public XMLIntrospector() {
    }
    
    public String mapToXML(Message message){
        
        String out = "";
        
        try{
            StringWriter outputWriter = new StringWriter(); 
            
            outputWriter.write("<?xml version='1.0' ?>");
            
            BeanWriter beanWriter = new BeanWriter(outputWriter);            
            beanWriter.getXMLIntrospector().setAttributesForPrimitives(false);
            beanWriter.setWriteIDs(false);
            beanWriter.enablePrettyPrint();
            beanWriter.write("message", message);
            
            out = outputWriter.toString();
        }catch(Exception e){
            log.error("mapToXML(): " + e);            
        }       
        
        return out;
    }
}
