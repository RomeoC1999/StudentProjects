package memory;

import java.io.File;
import java.util.ArrayList;

/*import com.sun.mail.util.MailConnectException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

*/


/**
 *
 * @author Romeo Carrara
 * @since 2016.03.16
 */
public class CRMail {
    
    private String sender;
    private String senderNickname;
    private String[] recipients= new String[0];
    private String subject;
    private String text;
    public ArrayList<File> attachments= new ArrayList<>();
    
    public static final String MAIL_SERVICE= "mailservice.cr@gmail.com";
    
    
    
    public CRMail(){
        init("", "", new String[]{}, "", "");
    }
    public CRMail(String sender, String senderNickname, String recipient){
        init(sender, senderNickname, new String[]{recipient}, "", "");
    }
    public CRMail(String sender, String senderNickname, String[] recipients){
        init(sender, senderNickname, recipients, "", "");
    }
    public CRMail(String sender, String senderNickname, String recipient, String subjcet, String text){
        init(sender, senderNickname, new String[]{recipient}, subject, text);
    }
    public CRMail(String sender, String senderNickname, String[] recipients, String subjcet, String text){
        init(sender, senderNickname, recipients, subject, text);
    }
    public CRMail(String sender, String recipient, String subjcet, String text){
        init(sender, null, new String[]{recipient}, subject, text);
    }
    public CRMail(String sender, String[] recipients, String subjcet, String text){
        init(sender, null, recipients, subject, text);
    }
    
    
    
    private void init(String sender, String senderNickname, String[] recipients, String subject, String text){
        setSender(sender);
        setSenderNickname(senderNickname);
        setRecipients(recipients);
        setSubject(subject);
        setText(text);
    }
    
    
    /*
    
    
    /**
     * @param debug if it must put debug
     * @throws MessagingException
     * @throws UnsupportedEncodingException 
     */
    /*public void send(boolean debug) throws MessagingException, UnsupportedEncodingException, IOException{
        
        Properties props= new Properties();
        props.put("mail.smtp.host", "smtp.net.vodafone.it");
        //props.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.smtp.port", "465");
        //props.put("mail.smtp.host", "smtp.gmail.com");
        //props.put("mail.smtp.auth", "true");
        
        Session session= Session.getDefaultInstance(props, new MyAuthenticator());
        //Session session= Session.getDefaultInstance(props, null);
        
        session.setDebug(debug);
        
        
        try{
            
            InternetAddress[] addresses= new InternetAddress[getRecipients().length];
            for(int i=0; i<addresses.length; i++){
                addresses[i]= new InternetAddress(getRecipients()[i]);
            }
            
            MimeMessage msg= new MimeMessage(session);
            msg.setRecipients(Message.RecipientType.TO, addresses);
            
            if(getSenderNickname() == null){
                msg.setFrom(new InternetAddress(getSender()));
            } else {
                msg.setFrom(new InternetAddress(getSender(), getSenderNickname()));
            }

            msg.setSubject(getSubject());
            msg.setText(getText());
            
            
            //Allegati
            MimeMultipart multipart = new MimeMultipart();
            
            for(File f : attachments){
                MimeBodyPart bodypart = new MimeBodyPart();
                bodypart.attachFile(f);
                bodypart.setFileName(f.getName());
                multipart.addBodyPart(bodypart);
            }
            System.out.println("Multipart: " + multipart.getCount());
            
            msg.setContent(multipart);
            
            Transport.send(msg);
            
        }catch (AddressException exc){
            throw exc;
        }catch (MailConnectException exc){
            throw exc;
        }catch (MessagingException | UnsupportedEncodingException exc){
            throw exc;
        } catch (IOException exc) {
            throw exc;
        }
        
    }*/
    
    /**
     * @param debug if it must put debug
     * @return true if there isn't exceptions during sending mail
     */
    /*public boolean simpleSending(boolean debug){
        
        try{
            send(debug);
        }catch(MessagingException | UnsupportedEncodingException exc){
            return false;
        } catch (IOException exx) {
            return false;
        }
        
        return true;
    }
    
    
    
    private static class MyAuthenticator extends javax.mail.Authenticator{
        
        public PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(MAIL_SERVICE, String.valueOf(getPassword()));
        }
        
        public static char[] getPassword(){
            return "Ma1lServic3".toCharArray();
        }
    }*/
    
    
    //Incapsulamento
    
    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the senderNickname
     */
    public String getSenderNickname() {
        return senderNickname;
    }

    /**
     * @param senderNickname the senderNickname to set
     */
    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    /**
     * @return the recipients
     */
    public String[] getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    
    
    
    
    
}
