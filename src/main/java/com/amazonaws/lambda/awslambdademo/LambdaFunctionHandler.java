package com.amazonaws.lambda.awslambdademo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Email, String> {
	
	private static final String MY="pranavpathak29@gmail.com";
	private static final String SUBJECT="Re: Contact You";
	private final SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
    @Override
    public String handleRequest(Email email, Context context) {
//        context.getLogger().log("Input: " + input);
    	
    	MailSender mailSender= new MailSender();
    	
    	email.setFrom(MY);
    	email.setTo(MY);
    	String  message="Hello Pranav, \n\n"+"      "+email.getName()+"("+email.getFrom()+") is trying to connect you on "+simpleDateFormat.format(getTime())
    			+ ". Message is as per below.\n\n "+email.getMessage();
    	email.setSubject(SUBJECT);
    	email.setMessage(message);
    	
    	mailSender.sendMail(email);
    	
    	return "Sent";
    }
    
    
    private Date getTime(){
    	Calendar calendar = Calendar.getInstance();
        TimeZone fromTimeZone = calendar.getTimeZone();
        TimeZone toTimeZone = TimeZone.getTimeZone("IST");

        calendar.setTimeZone(fromTimeZone);
        calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
        if (fromTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
        }
        
        calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        if (toTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
        }
        
        return calendar.getTime();
    }

}
