/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Hugo
 */
public class Email {

    private String username;
    private String password;

    private String destinatario;
    private String assunto;
    private String mensagem;

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Email(String destinatario, String assunto, String mensagem) {
        this.destinatario = destinatario;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.username = Config.EMAIL_PROVEDOR;
        this.password = Config.SENHA_PROVEDOR;
    }

    public Email() {
        this.username = Config.EMAIL_PROVEDOR;
        this.password = Config.SENHA_PROVEDOR;
    }

    public synchronized void enviarEmail() throws MessagingException, UnsupportedEncodingException {

        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        //DEBUGAR CÓDIGO    
//        props.put("mail.debug", "true");
//        props.put("mail.debug.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username, Config.NOME_SISTEMA));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(destinatario));
        message.setSubject(assunto);
        message.setText(mensagem, "utf-8", "html");
        Transport.send(message);
    }

}
