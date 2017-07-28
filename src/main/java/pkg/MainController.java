package pkg;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    String index(Authentication auth, Model model) {
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("loggedIn", true);
            // model.addAttribute("user", auth.getName());
            CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
            model.addAttribute("user", user.getFullName());
        } else
            model.addAttribute("loggedIn", false);

        return "unrestricted";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/restricted")
    String restricted(Authentication auth, Model model) {
        // model.addAttribute("user", auth.getName());
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("user", user.getFullName());
        return "restricted";
    }
}
