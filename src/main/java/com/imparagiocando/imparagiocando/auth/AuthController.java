package com.imparagiocando.imparagiocando.auth;

import com.imparagiocando.imparagiocando.user.MyUser;
import com.imparagiocando.imparagiocando.user.MyUserService;
import lombok.RequiredArgsConstructor;
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
        return "redirect:/activate_account";
    }

    @GetMapping("/activate-account")
    public String confirm(Model model) throws Exception {
        return "/activate_account";
    }

    @PostMapping("/activate-account")
    public String confirm(@RequestParam(value = "token") String token, Model model) throws Exception {
        authService.activateAccount(token);
        return "redirect:login?success";
    }
}