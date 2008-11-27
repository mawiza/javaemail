/*
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Parse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			File f = new File("dk/miles/messenger/message/complex_message.txt");
			FileInputStream in = new FileInputStream(f);
			StringBuffer buf = new StringBuffer();
			for (int i = in.read(); i != -1; i = in.read( )) {
				buf.append((char)i);
			}			
			in.close();
			
			System.out.println(buf.toString());
			
			Message message = new MessageParser(buf.toString()).getMessage();
			System.out.println(message.toString());
			
			XMLIntrospector introspector = new XMLIntrospector();
			System.out.println();
			
			FileOutputStream out = new FileOutputStream("introSpectedMessage.xml");
			out.write(introspector.mapToXML(message).getBytes());
			out.close();
			
			MessageUtil messageUtil = new MessageUtil(message);
			System.out.println("From email: " + messageUtil.getFromEmail());
			System.out.println("From name: " + messageUtil.getFromName());			
			System.out.println("return-path: " + messageUtil.getReturnPath());
			System.out.println("Send date: " + messageUtil.getSendDate());
			System.out.println("Size: " + messageUtil.getSize() + " bytes");
			System.out.println("Subject: " + messageUtil.getSubject());
			System.out.println("To: " + messageUtil.getTo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
}
