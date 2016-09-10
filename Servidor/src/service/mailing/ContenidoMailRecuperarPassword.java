package service.mailing;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class ContenidoMailRecuperarPassword extends IContenidoMail {

	
	private String user;
	private String pass;
	
	public ContenidoMailRecuperarPassword(String user, String pass) {
		this.pass = pass;
		this.user = user;
		this.generar();
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public void generar() {
			this.setSubject( "Recuperar contraseña" );
			
			try {
				  InternetHeaders headers = new InternetHeaders();
				  headers.addHeader("Content-type", "text/html; charset=UTF-8");
				  String html = "Hola " + this.getUser() + '\n'
						  + "Su contraseña es: " + this.getPass();
				  
				  Multipart mime_multi_part = new MimeMultipart("alternative");
				  
				  MimeBodyPart body = new MimeBodyPart(headers, html.getBytes("UTF-8"));
				  mime_multi_part.addBodyPart(body);
				  
				  this.setMultipart( mime_multi_part );
				  
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}	
		
	}

}
