/*
 * MessageUtil.java
 *
 * Created on December 4, 2003, 1:39 PM
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
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/** Miscellaneous utilities.
 *
 * @author  William Miles
 */
public class MessageUtil {
    
    /***/
    private Message message;
    
    /***/    
    private StringTokenizer tokenizeFrom = null;
    
    /***/
    private String fromName = null;
    
    /***/
    private String fromEmail = null;
    
    /** Creates a new instance of MessageUtil
     * @param message
     */
    public MessageUtil(Message message) {
        this.message = message;
        tokenizeFromAddress();
    }    
    
    /** Get the size(in bytes) of the message.
     * @return The size of the message.
     */    
    public int getSize(){        
        return message.getRaw().getBytes().length;
    }
        
    /** Find the first header field name that matches the regular expression.
     * @param regex The regular expression to use to match the header field names.
     * @return The first matched header field name.
     */
    public HeaderField findHeaderField(String regex){        
        Set<HeaderField> headerFields = message.getHeaderFields();
                
        for (HeaderField field : headerFields) {
        	if(field.getName().matches(regex))
                return field;
		}
                
        return null;
    }
    
    /** Find all the header field names that matches the regular expression
     * @param regex The regular expression to use to match the header field names.
     * @return All the matched header field names.
     */
    public Set<HeaderField> findHeaderFields(String regex){        
        Set<HeaderField> headerFields = message.getHeaderFields();
        Set<HeaderField> fieldsFound = new HashSet<HeaderField>();
        
        for (HeaderField field : headerFields) {
			if(field.getName().matches(regex)){
				fieldsFound.add(field);
			}
		}
        
        return fieldsFound;
    }
        
    /** Tokenize the from address to get rid of the name
     * (<b>"William Miles"</b> <william@miles.dk>).     
     */
    private void tokenizeFromAddress(){
        
        String from = findHeaderField("(?i)from:").getBody();
        try{
            //"William Miles" <william@miles.dk>
            //getting rid of the quotation marks(0x22)
            if(from.matches(".*\\x22.*\\x22.*<.*>.*")){
                from = from.replaceAll("\\x22", "");
            }

            //Getting rid of the < and > in William Miles <william@miles.dk>
            //braking the rest up to retreive the name(if it exists), and email address
            if(from.matches(".*<.*>")){
                //We tokenize the string at the newline and feed it to parseMail()
                tokenizeFrom = new StringTokenizer(from, "<");
                if(tokenizeFrom.hasMoreTokens()) {
                    String name = tokenizeFrom.nextToken();
                    fromName = name.replaceAll("^ ", ""); 
                }

                if(tokenizeFrom.hasMoreTokens()) {
                    from = tokenizeFrom.nextToken(">");
                    from = from.replaceAll("<", "");
                    fromEmail = from.replaceAll(" ", "");
                }
            }
            else{
                from = tokenizeFrom.nextToken(">");
                fromEmail = from.replaceAll(" ", "");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Get the email address of the person who send the message.
     * @return The email address.
     */    
    public String getFromEmail(){                
        return fromEmail;
    }        
    
    /** Get the name of the person who send the message, if specified.
     * @return The name if specified, otherwise null.
     */
    public String getFromName(){
        return fromName;
    }
    
    /** Get the email address of the person who should receive this message.
     * @return
     */    
    public String getTo(){
    	return getBody("(?i)to:");
    }
    
    /**
     * @return
     */    
    public String getReturnPath(){ 
    	return getBody("(?i)return-path:");
    }
    
    /**
     * @return
     */    
    public String getSubject(){        
        return getBody("(?i)subject:");        
    }
    
    /**
     * @return
     */    
    public String getSendDate(){        
        return getBody("(?i)date:");
        
        
    }
    
    /**Convinience method
     * @param regex
     * @return
     */
    private String getBody(String regex){
    	HeaderField field = findHeaderField(regex);
    	if(field != null){
    		return field.getBody();
    	}else{ 
    		return null;
    	}
    }
}
