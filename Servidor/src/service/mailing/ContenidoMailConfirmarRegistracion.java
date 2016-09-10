package service.mailing;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class ContenidoMailConfirmarRegistracion extends IContenidoMail {

	public ContenidoMailConfirmarRegistracion() {
		this.generar();
	}
	
	@Override
	public void generar() {
		
		this.setSubject( "Bienvenido a FIUBA Cursos!" );
		
		try {
			  InternetHeaders headers = new InternetHeaders();
			  headers.addHeader("Content-type", "text/html; charset=UTF-8");
			  String html = "Test\nholanda\n<a href='http://localhost:8080/Servidor/signin'>Test.com</a>";
			  
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
