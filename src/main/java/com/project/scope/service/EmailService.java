package com.project.scope.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project.scope.model.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	JavaMailSender javaMailSender;

     public  void mailsend(User user, String url,String token) throws MessagingException {
    	 
    	 
    	 
	
	     String Url= url+"/verify?token="+token;
	
		
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		helper.setTo(user.getEmail());
		helper.setFrom("athulsaiju01@gmail.com");
		helper.setSubject("Email Verification");
		
		
		helper.setText("<div style=\"max-width: 600px; background-color: #dedede; margin: 20px auto; padding: 20px; border-radius: 8px; color: #000000; text-align: center; border: 1px solid black; padding-bottom: 10px;\">\r\n"
				+ "        <div style=\"margin-bottom: 20px;\">\r\n"
				+ "           \r\n"
				+ "        </div>\r\n"
				+ "        <h1 style=\"color: #333;\">SCOPE INDIA</h1>\r\n"
				+ "        <p style=\"font-size: 16px; margin-top: 20px; letter-spacing: 1px; line-height: 25px;\">Thank you for registering with SCOPE INDIA. To complete the verification process, please confirm your email address by clicking the link below:</p>\r\n"
				+ "        <div style=\"background-color: transparent; padding: 15px; margin-top: 30px; font-size: 24px; font-weight: bold; border-radius: 5px; color: white;\">\r\n"
				+ "<a href='" + Url + "'> Verify Your Mail</a>"
				+ "        </div>\r\n"
				+ "        \r\n"
				+ "       \r\n"
				+ "        \r\n"
				+ "        <p style=\"font-size: 16px; margin-top: 30px; letter-spacing: 1px; line-height: 25px;\">If you did not request this, please ignore this email.</p>\r\n"
				+ "\r\n"
				+ "        <p style=\"font-size: 16px; margin-top: 30px; letter-spacing: 1px; line-height: 25px;\">\r\n"
				+ "            SCOPE INDIA</p>\r\n"
				+ "        <footer style=\"font-size: 12px; color: #7d7a7a; margin-top: 25px; text-align: center;\">\r\n"
				+ "            <a href=\"#\" style=\"color: #7d7a7a; text-decoration: none;\">FAQs</a> | \r\n"
				+ "            <a href=\"#\" style=\"color: #7d7a7a; text-decoration: none;\">Terms & Conditions</a> | \r\n"
				+ "            <a href=\"#\" style=\"color: #7d7a7a; text-decoration: none;\">Contact Us</a>\r\n"
				+ "        </footer>"

				+ "</div>",true);
		
		javaMailSender.send(msg);
		
	}
     
     
     
     
     public void sendOtpEmail(String email, String otp) throws MessagingException {
         MimeMessage msg = javaMailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(msg, true);

         helper.setTo(email);
         helper.setFrom("athulsaiju01@gmail.com"); // Update with your email address
         helper.setSubject("Your OTP Code");
		 helper.setText("Your OTP code is: " + otp, true);
         
         helper.setText("<div style=\"max-width: 600px; background-color: #ffffff; margin: 20px auto; padding: 20px; border-radius: 8px; color: #333333; text-align: center; border: 1px solid black; padding-bottom: 5px;\">"
 				+ " <div style=\"margin-bottom: 20px;\">"
 				+ "</div>"
 				+ "  <h1 style=\"color: #333;\">SCOPE INDIA</h1>\r\n"
 				+ "        <p style=\"font-size: 16px; margin-top: 20px; letter-spacing: 1px; line-height: 25px;\">Thank you for registering with us! To complete your registration, please verify your email address by entering the One-Time Password (OTP) provided below:</p>\r\n"
 				+ "        <div style=\"background-color: #eeeeee; padding: 15px; margin-top: 30px; font-size: 24px; font-weight: bold; border-radius: 5px; letter-spacing: 20px;\">\r\n"
 				+ otp // Dynamic OTP insertion
 				+ "        </div>"
 				
 				
 				+ "<p style=\"font-size: 16px; margin-top: 30px; letter-spacing: 1px; line-height: 25px;\">Please enter this code in the required field to verify your email. This OTP is valid for the next 10 minutes.</p>\r\n"
 				+ "       \r\n"
 				+ "        \r\n"
 				+ "        <p style=\"font-size: 16px; margin-top: 30px; letter-spacing: 1px; line-height: 25px;\">If you did not request this, please ignore this email.</p>\r\n"
 				+ "\r\n"
 				+ "        <p style=\"font-size: 16px; margin-top: 30px; letter-spacing: 1px; line-height: 25px;\">\r\n"
 				+ "            SCOPE INDIA</p>"
 				
 				
 				
 				+ "<footer style=\"font-size: 12px; color: #7d7a7a; margin-top: 25px; text-align: center;\">\r\n"
 				+ "            <a href=\"#\" style=\"color: #7d7a7a; text-decoration: none;\">FAQs</a> | \r\n"
 				+ "            <a href=\"#\" style=\"color: #7d7a7a; text-decoration: none;\">Terms & Conditions</a> | \r\n"
 				+ "            <a href=\"#\" style=\"color: #7d7a7a; text-decoration: none;\">Contact Us</a>\r\n"
 				+ "        </footer>"
 				+ "</div>",true);

         javaMailSender.send(msg);
     }
     
     
     
     
     public void contactEmail(String name, String email, String phone, String message) throws Exception {
         // Construct the email content
         String emailContent = "Name: " + name + "\n" +
                               "Email: " + email + "\n" +
                               "Phone: " + phone + "\n" +
                               "Message: " + message;

         // Create a mail message
         MimeMessage msg = javaMailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(msg, true);
         helper.setFrom("athulsaiju01@gmail.com");  // Change this to your email
         helper.setTo("athulsaiju02@gmail.com");         // Recipient's email
         helper.setSubject("New Contact Form Submission");
         helper.setText(emailContent);

         // Send the email
         javaMailSender.send(msg);
     }
     
     
    
     
     

}
