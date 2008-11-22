/*
 * MessageParser.java
 *
 * Created on June 20, 2003, 10:46 AM
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
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.Logger;

/** Parse an email message using regular expressions and extract each header 
 * field and body part and map them respectively into sets of 
 * <tt>dk.miles.messenger.message.HeaderField</tt>, <tt>dk.miles.messenger.message.Body</tt> and 
 * <tt>dk.miles.messenger.message.BodyPart</tt> Beans. The sets are accessable through 
 * <tt>dk.miles.messenger.messegae.Message</tt>. 
 *
 * @author  William Miles
 * @version 0.2
 *
 * @todo Remember epilogue
 */
public class MessageParser {
    
    /***/
    private static Logger log = Logger.getLogger(MessageParser.class);
    
    /***/
    private Message message = new Message();
    
    /***/
    private final int HEADER = 0;
       
    /***/
    private final int PREAMBLE = 0;
            
    /***/
    private final int BODY = 1;
    
    /** Creates a new instance of this class.
     * @param message The email message that should be processed.
     */    
    public MessageParser(String msg) {
        message.setRaw(msg);
        parseMessage(msg);        
    }
    
    /** According to RFC2822 the header and the body are seperated by the first
     * encountered null string, a CRLF preceded by nothing.
     * parseMessage(String message) seperates the header(folded) and the body
     * and safe each part in a String array where part[0] contains the
     * header(folded) and part[1] the body(the body consist of body parts, eg.
     * attachments, etc).
     * @param message The email message to parse.
     */
    private void parseMessage(String msg){        
        try{
            String parts[] = splitAtEmpty(msg);
            message.setHeaderFields(mapHeader(parts[HEADER]));            
            message.setBody(mapBody(parts[BODY]));            
        }catch(NullPointerException nse) {
           log.debug(nse);
        }
        catch(ArrayIndexOutOfBoundsException aiobe) {
           log.debug("parseMessage(): " + aiobe);
        }
    }
    
    
    /** Split the text at the first empy line(regex = "^$") encountered.
     * @param string The string to split.
     * @return An array containing two parts(only if an empty line was encountered).
     */    
    private String [] splitAtEmpty(String string){
        final String RULE1 = "^$";
        final int LIMIT  = 2;
        
        Pattern p = Pattern.compile(RULE1, Pattern.MULTILINE);
        String parts[] = p.split(string, LIMIT);
        
        return parts;        
    }
    
    /** Split the text before the first specified boundary is encountered.
     * @param string The string to split.
     * @param string The boundary to use.
     * @param boolean Should a double hyphen(dh) be prepended to the boundary.
     * @param boolean Should the boundary be included again, true if it should
     *        (the split will remove the boundary).
     * @return An array containing two parts(only the boundary was encountered).
     */    
    private String [] splitBeforeBoundry(String string, String boundary, boolean dh, boolean include){
        final int LIMIT  = 2;
        
        if(dh)
            boundary = "--" + boundary;
              
        Pattern p = Pattern.compile(boundary, Pattern.MULTILINE);
        
        //this removes the boundary, we do not always want that
        String parts[] = p.split(string, LIMIT);
        if(include){
                parts[1] = boundary + parts[1];
        }
        
        return parts;        
    }
        
    /** Unfolds and map each header field into a dk.miles.messenger.message.HeaderField
     * object. The dk.miles.messenger.message.HeaderField objects are saved in a HashSet
     * in dk.miles.messenger.message.Message. If the header field exist then the field to
     * be mapped will be appended to the existing field, for example "Received:".
     * @param header The header string to map.
     */
    private Set<HeaderField> mapHeader(String header){
        Set<HeaderField> fields = new HashSet<HeaderField>();
        
        /* Match the header field name
         * \\w = a word charachter([a-zA-Z_0-9])
         * X+? = X, one or more times
         * (?:X) = X, as a non-capturing group
         * \\u002d = '-'
         * \\u003a = ':'
         * \\u0020 = '"'
         * \\u002e = '.'
         */        
        final String RULE1 = "^\\w+?(?:[\\u002d\\u002e]\\w+?){0,}[\\u003a\\u0020]";        
        
        /* Match the header field body
         * \\u000a = 'NL'
         * \\u0009 = 'TAB'
         * \\u0020 = 'Space'
         */
        final String RULE2 = ".*\\u000a(?:^(?:[\\u0020\\u0009]){1,}.*$){0,}";
        
        /* match any tabs or spaces or NL or CR
         * u000d = 'CR'
         */
        final String RULE3 = "^(?:[\\u0020\\u0009]){1,}|\\u000a|\\u000d";
        
        /* match tabs
         */
        final String RULE4 = "\\u0009";
    
        try{
            Pattern pattern1 = Pattern.compile(RULE1 + RULE2, Pattern.MULTILINE);
            Pattern pattern2 = Pattern.compile(RULE1, Pattern.MULTILINE);
            
            Matcher matcher1 = pattern1.matcher(header);
            while(matcher1.find()){
                String raw = matcher1.group();
                
                Matcher matcher2 = pattern2.matcher(raw);
                if(matcher2.find()){                   
                    String fieldName = matcher2.group();
                    
                    //Getting rid of the field name
                    String fieldBody = raw.replaceFirst(RULE1, "");
                    //Unfolding the body
                    fieldBody = fieldBody.replaceAll(RULE3, "");
                    //Replacing any leftover tabs with a single space
                    fieldBody = fieldBody.replaceAll(RULE4, " ");
                    
                    //Finding out if the field name exists
                    HeaderField fieldObj = findHeaderField(fields, fieldName);                    
                    if(fieldObj == null){
                        fieldObj = new HeaderField();
                        fieldObj.setRaw(raw);
                        fieldObj.setName(fieldName);
                        fieldObj.setBody(fieldBody);
                        fields.add(fieldObj);
                    }
                    else{
                        fieldObj.setRaw(fieldObj.getRaw() + raw);
                        fieldObj.setBody(fieldObj.getBody() + " " + fieldBody);
                    }
                }
            }            
            //log.debug("[HEADER_FIELDS]: " + fields.toString());
        }catch(PatternSyntaxException pse) {
           logPatternSyntaxException(pse);        
        }
        return fields;
    }
    
    /** Search for a header field name in a set using a regular expression.
     * @param set The Set to searcg in.
     * @param regex The regular expression to use.
     * @return The HeaderField object containing the name.
     */    
    private HeaderField findHeaderField(Set set, String regex){
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            HeaderField obj = (HeaderField)iterator.next();
            if(obj.getName().matches(regex)){
                return obj;
            }
        }        
        return null;
    }
    
    /** This should comply to RFC2045, RFC2046, RFC2822 and RFC2387. 
     * Allthough all header fields in the MIME bodypart header not starting with 
     * "content-" can be ignored, all header fields in the bodypart are mapped.
     *
     * What we need to do here is that we should take the body, brake it up
     * if it is specified in the headers that it is a multipart message,
     * otherwise only the body content will be saved.
     *
     * @param body The body string to map.
     */    
    private Body mapBody(String bodyContent){
        Body bodyObj = new Body();
        bodyObj.setRaw(bodyContent);        
        
        //Defines the message body content
        final String RULE1 = "(?i)Content-Type[:\\u0020]";
        
        //If this is a multipart message
        final String RULE2 = "(?i)multipart.*";
        
        //Making shure the boundary is specified
        final String RULE3 = "(?i).*boundary=.*";
        
        try{
            HeaderField field = findHeaderField(message.getHeaderFields(), RULE1);            
            if(field != null){                
                String fieldBody = field.getBody();
                if(fieldBody.matches(RULE2)){ 
                    //log.debug("This is a multipart message!");
                    if(fieldBody.matches(RULE3)){
                        //Sometimes informational text is included before the 
                        //first boundary starts called the preamble this 
                        //is being captured here and mappend into 
                        //Messsage.preamble, this text normally does not get 
                        //displayed by MUAs that proparly support multipart
                        String parts[] = splitBeforeBoundry(bodyContent, 
                                                            getBoundary(field.getRaw()), 
                                                            true, 
                                                            true);
                        
                        message.setPreamble(parts[PREAMBLE]);
                        //log.debug("BODYPART[" + PREAMBLE + "]: " + parts[PREAMBLE]);
                        //log.debug("BODYPART[" + parts[BODY]);
                        bodyObj.setBodyParts(mapBodyParts(parts[BODY], getBoundary(field.getRaw())));                        
                    }
                    else{                        
                        log.error("Multipart message without a specified boundary, " +
                                  "message does no comply to any standard RFCs, " +
                                  "the content will not be processed");
                        bodyObj.setContent(bodyContent);
                    }
                }
                else{
                    //log.debug("This is not a multipart message!");
                    bodyObj.setContent(bodyContent);
                }
            }
            else{
                //log.debug("This is not a multipart message!");                
                bodyObj.setContent(bodyContent);
            }
            //log.debug("[BODYPARTS]: " + bodyObj.getBodyParts().toString());
        }catch(PatternSyntaxException pse) {
           logPatternSyntaxException(pse);
        }
        catch(NullPointerException nse) {
           log.debug(nse);
        }
        catch(ArrayIndexOutOfBoundsException aiobe) {
           log.debug("mapBody(): " + aiobe);
        }
        
        return bodyObj;
    }
        
    /** Map bodyparts recursively. This should comply to RFC2045, RFC2046, 
     * RFC2822 and RFC2387. 
     */
    private Set mapBodyParts(String body, String boundary){
        Set<BodyPart> bodyParts = new HashSet<BodyPart>();
        
        String parts[];        
        
        //This will match both --boundary and --boundary--
        final String RULE1 = "(?m)^--" + boundary + ".*$\n";
        
        try{            
            //Now we need to get the bodypart from --boundary to --boundary-- or
            //--boundary to --boundary, where boundary is the same constant.            
            parts = body.split(RULE1);
            for(int i = 0; i < parts.length; i++){
                if(parts[i].length()>0){
                    //if it is the last part and it contains '--' then we will disregard it.
                    if((parts.length-1)!=i){                        
                        //log.debug("FOUND PART[" + i + "]: " + parts[i]);                        
                        bodyParts.add(mapBodyPart(parts[i], boundary));                        
                    }
                }
            }
        }catch(PatternSyntaxException pse) {
           logPatternSyntaxException(pse);
        }
        catch(NullPointerException nse) {
           log.debug("mapBodyParts(): " + nse);
        }
        catch(ArrayIndexOutOfBoundsException aiobe) {
           log.debug("mapBodyParts(): " + aiobe);
        }
        
        return bodyParts;
    }    
    
    /** Map the bodypart.
     */
    private BodyPart mapBodyPart(String bodyPart, String boundary){
        //Defines the bodypart content
        final String RULE1 = "(?i)Content-Type[:\\u0020]";
        
        //If this is a multipart bodypart
        final String RULE2 = "(?i)multipart.*";
        
        //Making shure the boundary is specified
        final String RULE3 = "(?i).*boundary=.*";
        
        BodyPart bodyPartObj = new BodyPart();
        bodyPartObj.setRaw(bodyPart);
        bodyPartObj.setBoundary(boundary);
        
        try{                        
            //According to the RFC's there has to be a newline between the content and
            //the boundary or headers. So the text passed to this method will not contain any 
            //boundaries, this would have been removed by the mapBodyParts method. If there
            //are no headers specified, then the text will start with an empty line.
            String parts[]=splitAtEmpty(bodyPart);
                        
            //log.debug("BodyPart Header[" + HEADER + "]: " + parts[HEADER]);            
            //log.debug("BodyPart Content: " + parts[BODY]);
            bodyPartObj.setHeaderFields(mapHeader(parts[HEADER]));

            HeaderField field = findHeaderField(bodyPartObj.getHeaderFields(), RULE1);            
            if(field != null){                
                String fieldBody = field.getBody();
                if(fieldBody.matches(RULE2)){ 
                    //log.debug("This is a multipart bodypart!");                    
                    if(fieldBody.matches(RULE3)){                        
                        String nestedParts[] = splitBeforeBoundry(parts[BODY], 
                                                            getBoundary(field.getRaw()), 
                                                            true,
                                                            true);                        
                        //log.debug("NESTED_BODYPART_CONTENT: " + nestedParts[BODYPART_CONTENT]);
                        bodyPartObj.setContent(nestedParts[HEADER]); 
                        //log.debug("NESTED_BODYPART: " + nestedParts[BODY]);
                        bodyPartObj.setBodyParts(mapBodyParts(nestedParts[BODY], getBoundary(field.getRaw())));                        
                    }
                    else{                        
                        log.error("Multipart body part without a specified boundary, " +
                                  "message does no comply to any standard RFCs, " +
                                  "the content will not be processed");
                        bodyPartObj.setContent(parts[BODY]);
                    }
                }
                else{
                    //log.debug("This is not a multipart bodypart!");
                    bodyPartObj.setContent(parts[BODY]);
                }
            }
            else{
                //log.debug("This is not a multipart bodypart!!");
                bodyPartObj.setContent(parts[BODY]);
            }
        }catch(PatternSyntaxException pse) {
           logPatternSyntaxException(pse);
        }
        catch(NullPointerException nse) {
           log.debug("mapBodyPart(): " + nse);
        }
        catch(ArrayIndexOutOfBoundsException aiobe) {
           log.debug("mapBodyPart(): " + aiobe);
        }
        
        
        return bodyPartObj;
    }
    
    /** Retreives the boundary used to determine the start of the bodypart.
     *  Note: This algorithm used is only temporary until I have sorted out the 
     *  regex to do the exact match.
     */
    private String getBoundary(String contentTypeBody){
        String boundary = null;
        
        try{            
            //The boundary string.
            final String RULE1 = "(?i)(?m)boundary=.*$";
            
            //The boundary, the = sign and a '"' if it exist.
            final String RULE2 = "(?i)^(\\u0020){0,}boundary((?:=\")|(?:=))";
            
            //The colon or quote, any stray spaces and end of line.
            //String RULE3 = "(?m)((\";)|(\")|(;))((\\p{Space}){0,}|(\\u0020){0,})$";
            final String RULE3 = "(?m)((\";)|(\")|(;))((\\p{Space}){0,}|(\"){0,})$";
                        
            Pattern pattern1 = Pattern.compile(RULE1);
            Matcher matcher1 = pattern1.matcher(contentTypeBody);
            
            if(matcher1.find()){
                //Matching the boundary string
                boundary = matcher1.group();
                
                //Replacing the "boundary=" name
                boundary = boundary.replaceAll(RULE2, "");
                
                //replacing the quotes or colon if it exists
                //This is a tempoaray solution
                Pattern pattern2 = Pattern.compile(RULE3);
                Matcher matcher2 = pattern2.matcher(boundary);
                
                if(matcher2.find()){
                    boundary = boundary.substring(0,matcher2.start());
                    //The below replacement will give problems with nested quotes
                    //boundary = boundary.replaceAll(matcher2.group(), "");
                }
            }
            
            //log.debug("[BOUNDARY]: " + boundary);
        }catch(PatternSyntaxException pse) {
           logPatternSyntaxException(pse);
        }
        
        return boundary;
    }    
    
    /** MessageParser uses dk.miles.messenger.message.Message Bean to set the 
     * properties for the parsed message.
     * @return An Instance of Message containing the message properties.
     */
    public Message getMessage(){
        return message;
    }
        
    /** Log the erro messages produced by a faulty regular expression.
     * @param pse The exception that was thrown by the faulty regular expression.
     */    
    private void logPatternSyntaxException(PatternSyntaxException pse){
        log.debug("An error occured while compiling the regular expression!");
        log.debug("The pattern in question is: " + pse.getPattern());
        log.debug("The description is: " + pse.getDescription());
        log.debug("The message is: " + pse.getMessage());
        log.debug("The index is: " + pse.getIndex());
    }
}
