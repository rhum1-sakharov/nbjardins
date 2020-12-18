package org.rlsv.adatpers.secondaries.mails.javamail;

import com.sun.mail.smtp.SMTPTransport;
import domain.models.DemandeDeDevisDN;
import domain.models.MailDN;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import org.rlsv.adatpers.secondaries.mails.AbstractMail;
import org.rlsv.adatpers.secondaries.mails.ServerMail;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;

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
import java.util.Properties;


public class JavaMailDevisAR extends AbstractMail implements MailDevisServicePT {


    public JavaMailDevisAR(ServerMail serverMail, LocalizeServicePT localize) {
        super(serverMail, localize);
    }

    @Override
    public ResponseDN<MailDN> send(MailDN mailDN) {

        ResponseDN<MailDN> responseDNMail = new ResponseDN<MailDN>();
        responseDNMail.setOne(mailDN);

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

            msg.setFrom(new InternetAddress(mailDN.getFrom()));

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailDN.getTo(), false));

            msg.setSubject(mailDN.getSubject());

            // HTML email
            msg.setDataHandler(new DataHandler(new HTMLDataSource(mailDN.getMessage())));


            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(serverMail.getHost(), serverMail.getUserAccount(), serverMail.getPasswordAccount());

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
            responseDNMail.addErrorMessage(e.getMessage());
        }


        return responseDNMail;

    }

    @Override
    public ResponseDN<DemandeDeDevisDN> sendToWorker(RequestDN<DemandeDeDevisDN> request) {
        return null;
    }

    @Override
    public ResponseDN<DemandeDeDevisDN> sendAcknowledgementToSender(RequestDN<DemandeDeDevisDN> request) {
        return null;
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
