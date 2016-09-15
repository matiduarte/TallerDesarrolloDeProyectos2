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
	
	protected Mailer() {}
	
	public IMail crearMail( String mail_destino, String nombre_destino ) {
		IMail mail = null;
		
		if ( mail_destino.contains( "gmail" ) ) {
			mail = new MailGmail( mail_destino, nombre_destino );
		} else {
			// aca van otros tipo de mail: yahoo, hotmail, etc...
			mail = new MailGmail( mail_destino, nombre_destino );
		}
		
		return mail;
	}
	
	public void mandarMailConfirmarRegistracion( String mail_origen, String nombre_origen ) {
		
		IMail mail = this.crearMail( mail_origen, nombre_origen );
		
		mail.enviar( new ContenidoMailConfirmarRegistracion( mail_origen ) );
		
	}
	
	public void mandarMailRecuperarPass( String mail_origen, String nombre_origen, String userPass ) {
		
		IMail mail = this.crearMail( mail_origen, nombre_origen );
		
		mail.enviar( new ContenidoMailRecuperarPassword(nombre_origen, userPass) );
		
	}
	
	
}
