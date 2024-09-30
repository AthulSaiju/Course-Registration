package com.project.scope.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.scope.model.Course;
import com.project.scope.model.User;

import com.project.scope.repository.UserRepo;
import com.project.scope.service.CourseService;
import com.project.scope.service.EmailService;
import com.project.scope.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private EmailService emailService;
	
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CourseService courseService;
	
	
    @GetMapping("/")
	public String homePage(@CookieValue(value = "email",required = false)String email,Model model) {
    	if(email !=null) {
    	model.addAttribute("log",true);
    	}
    	else {
    		model.addAttribute("log",false);
    	}
		return "index";
	}

    @GetMapping("/about")	
	public String aboutPage(@CookieValue(value = "email",required = false)String email,Model model) {
    	
    	if(email !=null) {
        	model.addAttribute("log",true);
        	}
        	else {
        		model.addAttribute("log",false);
        	}
    	
		return "about";
	}
    
    
    
    @GetMapping("/courses")	
	public String coursesPage(@CookieValue(value = "email",required = false)String email,Model model) {
    	if(email !=null) {
    		
    		return "courses";
        
        	}
        	else {
        		
        	}
    	 return "redirect:/login"; 
	}
    
    @RequestMapping("/selectCourse")
    public String selectCourse() {
    	return "redirect:/courses";
    	
    }
    
    
    
    @GetMapping("/contact")
   	public String contactPage(@CookieValue(value = "email",required = false)String email,Model model) {
    	
    	if(email !=null) {
        	model.addAttribute("log",true);
        	}
        	else {
        		model.addAttribute("log",false);
        	}
   		return "contact";
   	}
    
    
    @PostMapping("/contactEmail")
    public String sendEmail(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("phone") String phone,
        @RequestParam("message") String message,
        Model model) {

        try {
            // Delegate email sending to the service
            emailService.contactEmail(name, email, phone, message);
            model.addAttribute("successMessage", "Email sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("successMessage", "Error sending email. Please try again.");
        }

        return "contact"; // Return to the form page with the success or error message
    }
    
    
    
    @GetMapping("/login")	
   	public String loginPage() {
   		return "login";
   	}
	
	@GetMapping("/forgotPassword")
	public String forgotPass() {
		return "sendOtpPage";
	}

	 @GetMapping("/protected")
	 public String protectedPage() {
	   		return "protectedFile";
	   	}
	 
	 @GetMapping("/dashboard")
	 public String dashBoard(@CookieValue(value = "email",required = false)String email,Model model) {
		 
		 if (email.isEmpty()) {
	            return "redirect:/login";
	        }

	        User user = userRepo.findByEmail(email);
	        

	        model.addAttribute("user", user);
	        return "dashboard";
	    }
	 
	 
	 @PostMapping("/dashboard/update")
	    public String updateUser(
	            @RequestParam String name,
	            
	            @RequestParam String phone,
	            @RequestParam String gender,
	            @RequestParam String course,
	          
	            @CookieValue(value = "email", defaultValue = "") String userEmail
	    ) {
	        User user = userRepo.findByEmail(userEmail);

	        if (user != null) {
	            user.setName(name);
	            user.setPhone(phone);
	            user.setGender(gender);
	            user.setCourse(course);
	           
	           

	            // Update the user details in the database
	            userRepo.save(user);
	        }

	        return "redirect:/dashboard"; // Redirect back to the dashboard after updating
	    }
	   	
	    
	
	
	@GetMapping("/register")
	
	public String showForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("countries", userService.countries());
		return "register";
		
	}
	
@PostMapping("/register")
	
	public String registerUser( @ModelAttribute("user") User user, HttpServletRequest request, Model model) throws MessagingException{
	     String urll=request.getRequestURL().toString();
	     String url=urll.replace(request.getServletPath(), "");
		 userService.saveUser(user, url);
		 
		 model.addAttribute("userEmail", user.getEmail());
 
	        return "verifyPage";
		
	}
    
    @RequestMapping("/verify")
	
	public String tokenVerify(@RequestParam("token") String tkn, Model model) {
       
        boolean isVerified = userService.verifyToken(tkn);

        if (isVerified) {
        	
        	  
              return "sendOtpPage";
        } else {
          
            model.addAttribute("error", "Invalid or expired token.");
            return "errorPage"; 
        }

	}
    

    
    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam("email") String email, Model model) {
        try {
            userService.generateAndSendOtp(email);
            model.addAttribute("email", email); // Pass the email to the OTP page
            return "otpPage";
        } catch (MessagingException e) {
            model.addAttribute("error", "Failed to send OTP. Please try again.");
            return "sendOtpPage";
        }
    }
    

    
    
    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp, Model model) {
        boolean isVerified = userService.verifyOtp(email, otp);

        if (isVerified) {
        	 model.addAttribute("email", email);
            return "newPass";
        } else {
            model.addAttribute("error", "Invalid or expired OTP.");
            model.addAttribute("email", email);
            return "otpPage";
        }
    }
    
   
    
    @PostMapping("/createPassword")
    public String cretePassword(@RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
    	
    	if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("email", email); // Pass the email back to the form
            return "newPass";
        }
    	
    	 userService.updatePassword(email, password);
    	 
    	 return "redirect:/login"; 
    	
		
    	
   }
    

    
    
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, @RequestParam(required = false) boolean rememberMe, HttpServletResponse response, Model model) {
        User user = userRepo.findByEmail(email);

        if (user !=null && user.getPassword().equals(password)) {
            // Create a cookie to store the session token
            Cookie cookie = new Cookie("email", email);
            cookie.setHttpOnly(true); // Prevent JavaScript access to the cookie
            
            
            if (rememberMe) {
                cookie.setMaxAge(30 * 24 * 60 * 60); // 30 days if "Remember Me" is checked
            } else {
                cookie.setMaxAge(60 * 60); // 1 hour if "Remember Me" is not checked
            }
            response.addCookie(cookie);
            return "redirect:/";
        } else {
        	 model.addAttribute("error", "Invalid email or password.");
             model.addAttribute("email", email); // Preserve the entered email
            
             return "login"; 
        }
    }
    
    
    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpSession session) {
        // Invalidate the session if it exists
        session.invalidate();
        
        // Create a new cookie to overwrite and expire the existing one
        Cookie cookie = new Cookie("email", null);
        cookie.setMaxAge(0); // Set the cookie expiration to 0 to delete it
        cookie.setHttpOnly(true); // Ensure the cookie cannot be accessed via JavaScript
        response.addCookie(cookie);
        
        
        return "redirect:/";
    }

    
    
    
    @PostMapping("/addCourse")
    public String addCourse(@RequestParam Long courseId, 
                            @CookieValue(value = "email", defaultValue = "") String userEmail, Model model) {
        
    	
    	
    	
    	User user = userRepo.findByEmail(userEmail);
    	
    	
    	 

        Course course = courseService.findCourseById(courseId);
        


        
        user.setCourse(course.getCourseName());
        userRepo.save(user);

       
        return "redirect:/dashboard";
    }
    
    
    @RequestMapping("/deleteCourse")
    public String deleteCourse(@CookieValue(value = "email", defaultValue = "") String userEmail) {
        // Fetch the user by email
        User user = userRepo.findByEmail(userEmail);

        // Set the course to null to indicate the course is removed
        user.setCourse(null);

        // Save the updated user to the database
        userRepo.save(user);

        // Redirect back to the dashboard
        return "redirect:/dashboard";
    }

    
    
    
//    @RequestMapping("/otpPage")
//    
//    public String otpMailSend(User user) throws MessagingException {
//    	
//    	String email = user.getEmail();
//    	
//		return null;
//    	
//    }
    
    
    
    
    
//    @PostMapping("/verify-otp")//otp page
//    public String verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp, Model model) {
//    	
//    	System.out.println("Verifying OTP for email: " + email + " with OTP: " + otp);
//        
//        boolean isOtpValid = userService.verifyOtp(email, otp);
//
//        if (isOtpValid) {
//        	 System.out.println("OTP is valid. Redirecting to success page.");
//        	 
//            return "newPass";
//            
//        } else {
//        	 System.out.println("Invalid OTP. Returning to OTP page.");
//            model.addAttribute("error", "Invalid or expired OTP.");
//            return "otpPage"; 
//        }
//    }
//   
    
    
    
	
	
	

}
