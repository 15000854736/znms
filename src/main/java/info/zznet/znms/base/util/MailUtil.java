package info.zznet.znms.base.util;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailUtil {
	private static WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	public static void sendMail(SimpleMailMessage message){
        
		SystemOptionBean config = webRuntimeData.getSystemOptionBean();
        JavaMailSender sender = new JavaMailSenderImpl();            
            
        if(StringUtils.isEmpty(config.getSmtpServerHostName()) ||
        		StringUtils.isEmpty(config.getSmtpUserName()) ||
        		StringUtils.isEmpty(config.getSmtpPassword())) {
        	return;
        }
        
        ((JavaMailSenderImpl)sender).setHost(config.getSmtpServerHostName());    
        ((JavaMailSenderImpl)sender).setUsername(config.getSmtpUserName());    
        ((JavaMailSenderImpl)sender).setPassword(config.getSmtpPassword());    
            
        Properties pro = System.getProperties();    
            
        pro.put("mail.smtp.auth", "true");    
        pro.put("mail.smtp.socketFactory.port", config.getSmtpPort());    
        pro.put("mail.smtp.socketFactory.fallback", "false");    
            
        //通过文件获取信息    
        ((JavaMailSenderImpl)sender).setJavaMailProperties(pro);    
       
        if(message != null) {
        	if(StringUtils.isEmpty(message.getFrom())){
        		message.setFrom(config.getSmtpUserName());
        	}
        	try {
        		sender.send(message);  
        	} catch(Exception e) {
        		ZNMSLogger.error("failed to send mail:", e);
        	}
        }
    }

    public static void sendMail(String subject,String msg){

        SystemOptionBean systemOptionBean = webRuntimeData.instance.getSystemOptionBean();
        if(!StringUtil.isNullString(systemOptionBean.getEmailAddress())){
            String[] emailAddrs = systemOptionBean.getEmailAddress().split(",");
            for(String emailAddr : emailAddrs) {
                try {
                    SimpleMailMessage mail = new SimpleMailMessage();
                    mail.setTo(StringUtils.trim(emailAddr));
                    mail.setSubject(subject);
                    mail.setText(msg);
                    sendMail(mail);
                } catch(Throwable e) {
                    ZNMSLogger.error("邮件发送失败:", e);
                }
            }
        }


    }
}
