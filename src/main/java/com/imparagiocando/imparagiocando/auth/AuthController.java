package com.imparagiocando.imparagiocando.auth;

import com.imparagiocando.imparagiocando.user.MyUser;
import com.imparagiocando.imparagiocando.user.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MyUserService userService;

    @GetMapping("/login")
    String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("user" , MyUser.builder().build());
        return "register";
    }

    @PostMapping("/register")
    String registration(@ModelAttribute MyUser user) {
        userService.registerUser(user);
        return "activate_account";
    }

    @GetMapping("/activate-account")
    public String confirm(Model model) {
        return "activate_account";
    }

    @PostMapping("/activate-account")
    public String confirm(@RequestParam(value = "otp") String otp, Model model) throws Exception {
        String token = otp.substring(otp.length()-6,otp.length());
        try {
            authService.activateAccount(token);
        }catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Activation Successful! User name not found.");
        } catch (Exception e) {
            model.addAttribute("error", "Activation token has expired. A new token has been send to the same email address.");
        }
        model.addAttribute("success", "Activation Successful! Your account has been successfully activated");
        return "activate_account";
    }
}