package com.Bootcamp.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class AsyncEmailService {

    @Autowired
    private JavaMailSender sender;

//    @Autowired
//    EnvConfiguration configuration;
//
//    @Autowired
//    private TemplateEngine templateEngine;

    @Autowired
    EmailService emailService;



    public AsyncEmailService(){}

    public static int noOfQuickServiceThreads = 20;

    /**
     * this statement create a thread pool of twenty threads
     * here we are assigning send mail task using ScheduledExecutorService.submit();
     */
    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads); // Creates a thread pool that reuses fixed number of threads(as specified by noOfThreads in this case).

    public void sendASynchronousMail(String toEmail,String subject,String text) throws MailException,RuntimeException{

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setTo(toEmail);


        quickService.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    emailService.sendEmail(mailMessage);
                }catch(Exception e){
                    System.out.printf("Error occurred while sending a mail");
                }
            }
        });
    }

}