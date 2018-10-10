package com.sllit.ssd.oauthsample.controllers;

import com.sllit.ssd.oauthsample.models.Email;
import com.sllit.ssd.oauthsample.models.UserProfile;
import com.sllit.ssd.oauthsample.services.AuthenticationService;
import com.sllit.ssd.oauthsample.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by dinukshakandasamanage on 10/10/18.
 */
@Controller
public class HomeController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    EmailService emailService;

    @GetMapping("/home")
    public String showHomePage(Model model, HttpServletRequest request) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (authenticationService.isAuthenticated(cookies)){
            String accessToken = authenticationService.getAccessTokenForSession(authenticationService.getSessionFromCookies(cookies));
            UserProfile userProfile = emailService.getUser(accessToken);
            List<Email> emails = emailService.getEmailsForUser(accessToken, userProfile.getId(), "CATEGORY_PERSONAL");
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("emails", emails);
            return "home";
        }
        return "redirect:/";
    }
}
