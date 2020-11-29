package com.nbjardins.adatpers.secondaries.mails;

import com.sun.mail.smtp.SMTPTransport;
import domain.entities.DemandeDeDevis;
import domain.entityresponse.Response;
import domain.utils.Utils;
import usecase.ports.LocalizeServicePT;
import usecase.ports.MailServicePT;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;


public class JavaMailAR extends AbstractMail implements MailServicePT {


    public JavaMailAR(ServerMail serverMail, LocalizeServicePT localize) {
        super(serverMail, localize);
    }

    @Override
    public Response<DemandeDeDevis> send(DemandeDeDevis demandeDeDevis) {

        Locale locale = demandeDeDevis.getLocale();

        Mail mail = new Mail(demandeDeDevis.getSujet(), demandeDeDevis.getEmailEmetteur(), demandeDeDevis.getEmailDestinataire(), demandeDeDevis.getMessage());
        Response<Mail> mailResponse = send(mail);
        Response<DemandeDeDevis> demandeDeDevisResponse = Utils.initResponse(localize.getMsg("mail.error",locale), mailResponse.isError());

        return demandeDeDevisResponse;
    }


    private Response<Mail> send(Mail mail) {

        Response<Mail> responseMail = new Response<Mail>();
        responseMail.setOne(mail);

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", serverMail.getHost());
        prop.put("mail.smtp.auth", serverMail.isAuth());
        prop.put("mail.smtp.starttls.enable", serverMail.isEnableTls());
        prop.put("mail.smtp.port", serverMail.getPort());


        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(serverMail.getUserAccount(), serverMail.getPasswordAccount());
            }
        });
        Message msg = new MimeMessage(session);

        try {

            msg.setFrom(new InternetAddress(mail.getFrom()));

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mail.getTo(), false));

            msg.setSubject(mail.getSubject());

            // HTML email
            msg.setDataHandler(new DataHandler(new HTMLDataSource(mail.getMessage())));


            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(serverMail.getHost(), serverMail.getUserAccount(), serverMail.getPasswordAccount());

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
            responseMail.setError(true);
            responseMail.addErrorMessage(e.getMessage());
        }


        return responseMail;
    }


    static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }


        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }


        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }


        public String getContentType() {
            return "text/html";
        }


        public String getName() {
            return "HTMLDataSource";
        }
    }

}
