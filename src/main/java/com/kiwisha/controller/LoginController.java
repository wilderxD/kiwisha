package com.kiwisha.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email, 
                          @RequestParam String password, 
                          HttpSession session, 
                          Model model) {
        // Hardcoded generic user
        if ("admin@kiwisha.com".equals(email) && "123".equals(password)) {
            session.setAttribute("user", "Admin Kiwisha");
            return "redirect:/";
        } else {
            model.addAttribute("error", "Credenciales incorrectas (Usa admin@kiwisha.com / 123)");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
