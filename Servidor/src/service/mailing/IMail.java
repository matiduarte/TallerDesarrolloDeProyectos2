package service.mailing;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class IMail {

	Properties propiedades = null;
	Session sesion = null;
	
	String mail_origen = "tdp2.2c.2016@gmail.com";
	String nombre_origen = "Juan Perez (FIUBA Cursos Staff)";
	String mail_destino;
	String nombre_destino;
	String password;
	
	public String getMailOrigen() {
		return this.mail_origen;
	}
	public void setMailOrigen( String mail_origen ) {
		this.mail_origen = mail_origen;
	}
	public String getNombreOrigen() {
		return this.nombre_origen;
	}
	public void setNombreOrigen( String nombre_origen ) {
		this.nombre_origen = nombre_origen;
	}
	public String getMailDestino() {
		return this.mail_destino;
	}
	public void setMailDestino( String mail_destino ) {
		this.mail_destino = mail_destino;
	}
	public String getNombreDestino() {
		return this.nombre_destino;
	}
	public void setNombreDestino( String nombre_destino ) {
		this.nombre_destino = nombre_destino;
	}	
	public String getPassword() {
		return this.password;
	}
	public void setPassword( String password ) {
		this.password = password;
	}	
	
	abstract void setearPropiedades();
	
	void iniciarSesion() {
		// seteo las propiedades para un mail de gmail.
		this.setearPropiedades();
		
		this.sesion = Session.getDefaultInstance(this.propiedades, 
			    new Authenticator() {
	        protected PasswordAuthentication  getPasswordAuthentication() {
	        return new PasswordAuthentication(
	        		// esto va hardcodeado porq siempre se va a mandar desde el mismo mail.
	                    "tdp2.2c.2016@gmail.com", "t4ll3rd3d3s4rr0ll0");
	                }
	    });
	}
	
	public void enviar( IContenidoMail contenido ) {
		
		try {
			  Message msg = new MimeMessage( this.sesion );
			  
			  // seteo el emisor del mail
			  msg.setFrom(new InternetAddress( this.getMailOrigen(), this.getNombreOrigen() ));
			  
			  // seteo el receptor del mail
			  msg.addRecipient(Message.RecipientType.TO, new InternetAddress( this.getMailDestino(), this.getNombreDestino() ));
			  
			  // seteo el tema del mail
			  msg.setSubject( contenido.getSubject() );
			  
			  // seteo el contenido
			  msg.setContent( contenido.getMultipart() );

			  // lo envio
			  Transport.send(msg);
			  
			} catch (AddressException e) {
			  System.out.print( e );
			} catch (MessagingException e) {
				System.out.print( e );
			} catch (UnsupportedEncodingException e) {
				
			}
	}
	
	
}
