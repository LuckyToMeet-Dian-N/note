package com.gentle.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author Gentle
 * @date 2019/09/28 : 11:26
 */
public class MailUtils {

    //发件人地址
    public static String senderAddress = "";

    public static void sendEmail(String title,String content,String recAddr) throws Exception {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();

        props.setProperty("mail.host", "smtp.qq.com"); //// 设置QQ邮件服务器
        props.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
        props.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getDefaultInstance(props,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(senderAddress, "");
            }
        });
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session,title,content,recAddr);
        //4、根据session对象获取邮件传输对象Transport
        Transport.send(msg);
    }

    private static MimeMessage getMimeMessage(Session session,String title,String content,String recipientAddress) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //设置邮件主题
        msg.setSubject(title,"UTF-8");
        //设置邮件正文
        msg.setContent(content, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }



}
