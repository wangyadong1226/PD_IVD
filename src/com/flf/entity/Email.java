package com.flf.entity;

import java.util.Properties;


public class Email {

    private String to;

    private String subject;

    private String content;

    private String username;

    private String password;

    private String port;
    private String host;

    private String cc;

    private String bcc;

    private String[] files;

    protected void init() {
        String filename = "/config.properties";
        Properties props = new Properties();
        try {
            props.load(this.getClass().getResourceAsStream(filename));

            String username = props.get("email.username").toString();
            String password = props.get("email.password").toString();

//			this.username = Base64.decode(username);
//			this.password = Base64.decode(password);
            this.username = username;
            this.password = password;

            if(this.username.toLowerCase().contains("@qq.com") || this.username.toLowerCase().contains("@annoroad.com")){
                this.host = "smtp.exmail.qq.com";
                this.port="465";
            }else if(this.username.toLowerCase().contains("@163.com")){
                this.host = "smtp.163.com";
            }else if(this.username.toLowerCase().contains("@sina.com")){
                this.host = "smtp.sina.com";
            }else if(this.username.toLowerCase().contains("@139.com")){
                this.host = "smtp.139.com";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Email
     * @param to
     * @param subject
     * @param content
     * @param cc
     * @param bcc
     * @param files
     */
    public Email(String to, String subject, String content, String cc,
                 String bcc, String[] files) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.cc = cc;
        this.bcc = bcc;
        this.files = files;
        init();
    }



    public Email(String to, String subject, String content, String cc,
                 String[] files) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.cc = cc;
        this.files = files;
        init();
    }


    public Email(String to, String subject, String content, String[] files) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.files = files;
        init();
    }


    public Email(String to, String subject, String content, String cc,
                 String bcc) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.cc = cc;
        this.bcc = bcc;
        init();
    }


    public Email(String to, String subject, String content, String cc) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.cc = cc;
        init();
    }


    public Email(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        init();
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
