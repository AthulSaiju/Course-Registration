package com.project.scope.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.project.scope.model.Country;
import com.project.scope.model.District;
import com.project.scope.model.State;
import com.project.scope.model.User;
import com.project.scope.repository.CountryRepo;
import com.project.scope.repository.DistrictRepo;
import com.project.scope.repository.StateRepo;
import com.project.scope.repository.UserRepo;

import jakarta.mail.MessagingException;
import net.bytebuddy.utility.RandomString;



@Service
public class UserService {
	
	@Autowired
	
	private UserRepo userRepo;
	
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private StateRepo stateRepo;
	 
	@Autowired
	private DistrictRepo districtRepo;
	
	@Autowired
	private EmailService emailService;
	
	
	
	
	 
    
    
    public void saveUser(User user, String url) throws MessagingException {
		  Country c = countryRepo.findById(Integer.parseInt(user.getCountry())).orElse(null);
		  State s = stateRepo.findById(Integer.parseInt(user.getState())).orElse(null);
		  
		  if(c!= null & s!=null) {
			  
			  user.setCountry(c.getcName());
			  user.setState(s.getsName());
			  String token= RandomString.make(64);
			  user.setToken(token);
			  
			  
//			  String otp = generateOTP();
//	          user.setOtp(otp);

			  emailService.mailsend(user,url,token);
			  user.setEnable(false);
			  
			
			  userRepo.save(user);
			  
		  }else
			  userRepo.save(user);
		 
		
	}
    
	 public boolean verifyToken(String token) {
	       
	        User user = userRepo.findByToken(token);

	        if (user != null) {
	            
	            user.setEnable(true);
	            user.setToken(null); 
	            userRepo.save(user); 
	            return true;
	        } else {
	           
	            return false;
	        }
	    }
	 
    
    
    // Method to generate and send OTP
    public void generateAndSendOtp(String email) throws MessagingException {
        User user = userRepo.findByEmail(email);

        if (user != null) {
            String otp = generateOtp();
            LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(10); // OTP valid for 10 minutes

            user.setOtp(otp);
            user.setOtpExpiry(expiryDate);
            userRepo.save(user);

            emailService.sendOtpEmail(email, otp);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    
    
    
    
    
    // Generate a 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    
    
    // Method to verify OTP
    public boolean verifyOtp(String email, String otp) {
        User user = userRepo.findByEmail(email);

        if (user != null && otp.equals(user.getOtp()) && LocalDateTime.now().isBefore(user.getOtpExpiry())) {
            // OTP is valid and not expired

           user.setOtp(null);
           user.setOtpExpiry(null);
           userRepo.save(user);
           
            return true;
        }

        // OTP is either incorrect or expired
        return false;
    }
    
    
 
	 
	 
	 public void updatePassword(String email, String password) {
	        // Fetch user by email
	        User user = userRepo.findByEmail(email);

	        if (user != null) {
	        	 System.out.println("User found: " + user.getEmail());
	        	 
	        	
	            user.setPassword(password);
	            userRepo.save(user); 
	    }else {
	    	 System.out.println("User not found with email: " + email);
	    }
	 }
	 
	
	public List<Country> countries(){
		return countryRepo.findAll();
	}
	
	public List<State> getStatesByCountryId(int countryId) {
      return stateRepo.findByCountryId(countryId);
  }
	
	public List<District> getDistrictsByStateId(int stateId) {
      return districtRepo.findByStateId(stateId);
  }
	
	
	
	    
	    
	    
	    

	

}
