package com.ipn.mx.utilidades;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utilerias {
    public static void enviarEmail(String correoDestinatario,String asunto,String texto){
        try{
            Properties p = new Properties();
            p.setProperty("mail.smtp.host","smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable","true");
            p.setProperty("mail.smtp.starttls.required","true");
            p.setProperty("mail.smtp.port","587");
            p.setProperty("mail.smtp.user","protocobas@gmail.com");
            p.setProperty("mail.smtp.auth","true");
            
            Session s = Session.getDefaultInstance(p);
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress("protocobas@gmail.com"));
            mensaje.addRecipients(Message.RecipientType.TO,correoDestinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(texto);
            
            Transport t = s.getTransport("smtp");
            t.connect("protocobas@gmail.com","pr0t0c0l0");
            t.sendMessage(mensaje,mensaje.getAllRecipients());
            t.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   
}
