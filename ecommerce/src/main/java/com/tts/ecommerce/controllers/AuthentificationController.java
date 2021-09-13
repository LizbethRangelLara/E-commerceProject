package com.tts.ecommerce.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.ecommerce.models.User;
import com.tts.ecommerce.services.UserService;

@Controller
public class AuthentificationController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/signin")
	public String login()
	{
		return "signin";
	}
	
	@PostMapping("/signin")
	public String signup(@Valid User user,
						 BindingResult bindingResult,
			             @RequestParam String submit,
			             HttpServletRequest request) throws ServletException
	{
		System.out.println("MADE IT TO SIGNIN\n");
		if(userService.getLoggedInUser() != null)
		{
			bindingResult.reject("error.user", "Already logged in.");
		}
		
		String password = user.getPassword();
		if(!bindingResult.hasErrors() && submit.equals("up"))
		{
			if(userService.findByUsername(user.getUsername()) == null) 
			{
				userService.saveNew(user);
			}
			else
			{
				bindingResult.rejectValue("username", "error.user", "Username is already taken.");				
			}						
		}
		if(!bindingResult.hasErrors() && userService.findByUsername(user.getUsername()) == null) 
		{
			bindingResult.rejectValue("username", "error.user", "User does not exist.");		
		}		
		if(!bindingResult.hasErrors())
		{
			System.out.println("No errors");			
			request.login(user.getUsername(), password);
			return "redirect:/";
		}
		return "signin";
	}
}