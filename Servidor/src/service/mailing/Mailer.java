package service.mailing;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class Mailer {
	
	private static Mailer instancia = null;
	
	public static Mailer getInstancia() {
		if ( instancia == null ) {
			instancia = new Mailer();
		}
		return instancia;
	}
	
	protected Mailer(){}
	
	public void mandarMail( String mail ) {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.connectiontimeout", "10000");
	    props.put("mail.smtp.timeout", "10000");
		props.put("mail.smtp.port", "25");
		Session session = Session.getDefaultInstance(props, 
			    new Authenticator() {
	        protected PasswordAuthentication  getPasswordAuthentication() {
	        return new PasswordAuthentication(
	                    "tdp2.2c.2016@gmail.com", "t4ll3rd3d3s4rr0ll0");
	                }
	    });
		
		try {
			  Message msg = new MimeMessage(session);
			  msg.setFrom(new InternetAddress("tdp2.2c.2016@gmail.com", "Example.com Admin"));
			  msg.addRecipient(Message.RecipientType.TO,
			                   new InternetAddress("m.iglesias91@gmail.com", "Mr. User"));
			  msg.setSubject("Your Example.com account has been activated");
			  msg.setText("Holanda");
			  // Transport.send(msg);
			  Transport transport = session.getTransport("smtp");
			  transport.connect("smtp.gmail.com", 25, "tdp2.2c.2016@gmail.com", "t4ll3rd3d3s4rr0ll0");
			  transport.sendMessage(msg, msg.getAllRecipients());
			  transport.close(); 			  
			  System.out.print( "D" );
			} catch (AddressException e) {
			  System.out.print( e );
			} catch (MessagingException e) {
				System.out.print( e );
			} catch (UnsupportedEncodingException e) {
				
			}
	}	
}
