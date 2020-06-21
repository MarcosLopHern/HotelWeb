package com.ipn.mx.utilidades;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utilerias {

    public static void enviarEmail(String correoDestinatario, String asunto, String texto) {
        try {
            Properties p = new Properties();
//            p.put("mail.smtp.host","smtp.gmail.com");
//            p.put("mail.smtp.starttls.enable","true");
//            p.put("mail.smtp.starttls.required","true");
//            p.put("mail.smtp.port","587");
//            p.put("mail.smtp.user","protocobas@gmail.com");
//            p.put("mail.smtp.auth","true");
//            p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "587");
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session s = Session.getInstance(p, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("protocobas@gmail.com", "pr0t0c0l0");
                }
            });
            
            
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress("protocobas@gmail.com"));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(texto);
            
            Transport.send(mensaje);
//
//            Transport t = s.getTransport("smtp");
//            t.connect("protocobas@gmail.com", "pr0t0c0l0");
//            t.sendMessage(mensaje, mensaje.getAllRecipients());
//            t.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
