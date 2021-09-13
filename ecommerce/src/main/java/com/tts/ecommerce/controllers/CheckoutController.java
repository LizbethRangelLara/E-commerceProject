package com.tts.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.ecommerce.models.ChargeRequest;

@Controller
public class CheckoutController 
{
	@Value("${STRIPE_PUBLIC_KEY}")
	private String stripePublicKey;
	
	@RequestMapping("/checkout")
    public String checkout(@RequestParam("amount") double amount, Model model) 
	{
        model.addAttribute("amount", (int)amount); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        return "checkout";
    }

}
