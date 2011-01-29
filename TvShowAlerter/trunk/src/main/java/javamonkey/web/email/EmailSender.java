package javamonkey.web.email;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailSender {

	private Properties emailProperties;

	public EmailSender( String emailPropertyFileLocation ) {
		emailProperties = new Properties();
		try {
			emailProperties.load( EmailSender.class.getResourceAsStream( emailPropertyFileLocation ) );
		}
		catch ( Throwable throwable ) {
			throw new RuntimeException( throwable );
		}
	}

	public EmailSender() {
		this( "/email.properties" );
	}

	public void sendEmail( String subject, String text, String password ) throws Throwable {
		sendEmail( subject, text, emailProperties.getProperty( "userName" ), password );
	}

	public void sendEmail( String subject, String text ) throws Throwable {
		sendEmail( subject, text, emailProperties.getProperty( "userName" ), emailProperties.getProperty( "password" ) );
	}

	public void sendEmail( String subject, String text, String userName, String password ) throws Throwable {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom( emailProperties.getProperty( "fromAddress" ) );
		mailMessage.setTo( emailProperties.getProperty( "toAddress" ) );
		mailMessage.setText( text );
		mailMessage.setSubject( subject );

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost( emailProperties.getProperty( "host" ) );

		sender.setUsername( userName );
		sender.setPassword( password );

		sender.send( mailMessage );
	}

}
