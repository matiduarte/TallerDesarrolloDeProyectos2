package service.mailing;

import java.util.Properties;

public class MailGmail extends IMail {

	public MailGmail(  String mail_destino, String nombre_destino ) {		
		
		this.setMailDestino( mail_destino );
		this.setNombreDestino( nombre_destino );
		
		// inicio la sesion con las propiedades seteadas.
		this.iniciarSesion();
	}
	
	@Override
	void setearPropiedades() {
		this.propiedades = new Properties();
		this.propiedades.put("mail.smtp.auth", "true");
		this.propiedades.put("mail.smtp.starttls.enable", "true");
		this.propiedades.put("mail.smtp.host", "smtp.gmail.com");
		this.propiedades.put("mail.smtp.connectiontimeout", "10000");
		this.propiedades.put("mail.smtp.timeout", "10000");
		this.propiedades.put("mail.smtp.port", "25");
	}

}
