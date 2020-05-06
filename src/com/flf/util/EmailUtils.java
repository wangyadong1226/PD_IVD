package com.flf.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.flf.entity.Email;


public class EmailUtils {

    private static Logger log = Logger.getLogger(String.valueOf(EmailUtils.class));
    public static void sendEmail(Email email) throws Exception {
        Properties props = new Properties();
        props=PropertiesLoaderUtils.loadAllProperties("config.properties");
        String testingEnvironment=props.get("testingEnvironment").toString();//正式环境 False!@#
        if(!testingEnvironment.equals("False!@#")){
            email.setTo("jielian@annoroad.com");//收件人
            email.setCc("chuangli@annoroad.com");//抄送
            email.setBcc("chuangli@annoroad.com");//抄送
            //return;
        }
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setRecipients(RecipientType.TO, email.getTo());
        message.setFrom(new InternetAddress(email.getUsername()));
        message.setSubject(email.getSubject());

        message.setRecipients(RecipientType.TO,InternetAddress.parse(email.getTo()));//������
        if(email.getCc() != null && !email.getCc().equals(""))
            message.setRecipients(RecipientType.CC,InternetAddress.parse(email.getCc()));//������
        if(email.getBcc() != null && !email.getBcc().equals(""))
            message.setRecipients(RecipientType.BCC,InternetAddress.parse(email.getBcc()));//������
        MimeMultipart mimeMuti = new MimeMultipart("mixed");
        if(email.getFiles() != null && email.getFiles().length > 0){
            for(String file : email.getFiles()){
                MimeBodyPart bodyPartAttch = createAttachMent(file);//����
                mimeMuti.addBodyPart(bodyPartAttch);
            }
        }
        MimeBodyPart bodyPartContentAndPic = createContentAndPic(email.getContent());//�ı�����
        mimeMuti.addBodyPart(bodyPartContentAndPic);
        message.setContent(mimeMuti);
        message.saveChanges();

        Transport transport = session.getTransport("smtp");
        transport.connect(email.getHost(), email.getUsername(), email.getPassword());
        transport.sendMessage(message, message.getAllRecipients());
        log.info("【发送邮件】====收件人："+email.getTo()+"==抄送人："+email.getCc()+"==主题："+email.getSubject());//日志打印
    }

    protected static MimeBodyPart createAttachMent(String path) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        FileDataSource dataSource = new FileDataSource(new File(path));
        mimeBodyPart.setDataHandler(new DataHandler(dataSource));
        mimeBodyPart.setFileName(dataSource.getName());
        return mimeBodyPart;
    }

    protected static MimeBodyPart createContentAndPic(String content) throws MessagingException, UnsupportedEncodingException{
        MimeMultipart mimeMutiPart = new MimeMultipart("related");
        MimeBodyPart contentBodyPart = new MimeBodyPart();
        contentBodyPart.setContent(content,"text/html;charset=gbk");
        mimeMutiPart.addBodyPart(contentBodyPart);
        MimeBodyPart allBodyPart = new MimeBodyPart();
        allBodyPart.setContent(mimeMutiPart);
        return allBodyPart;
    }

}
