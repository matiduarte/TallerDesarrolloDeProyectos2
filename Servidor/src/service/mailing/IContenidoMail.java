package service.mailing;

import javax.mail.Multipart;

public abstract class IContenidoMail {
	
	private String subject;
	private Multipart multi_part;
	
	abstract public void generar();
	
	public String getSubject() {
		return this.subject;
	}

	public void setSubject( String subject ) {
		this.subject = subject;
	}
	
	public Multipart getMultipart() {
		return this.multi_part;
	}
	
	public void setMultipart( Multipart multi_part ) {
		this.multi_part = multi_part;
	}
}
