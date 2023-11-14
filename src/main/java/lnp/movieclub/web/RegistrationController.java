package lnp.movieclub.web;

import lnp.movieclub.user.UserService;
import lnp.movieclub.user.dto.UserRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/rejestracja")
    public String registrationForm(Model model){
        UserRegistrationDto userRegistration = new UserRegistrationDto();
        model.addAttribute("user",userRegistration);
        return "registration-form";
    }

    @PostMapping("/rejestracja")
    public String register(UserRegistrationDto userRegistration){
        userService.registrationUserWithDefaultRole(userRegistration);
        return "redirect:/";
    }
}