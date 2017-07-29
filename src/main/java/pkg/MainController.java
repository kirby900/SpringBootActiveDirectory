package pkg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    String index() {
        return "unrestricted";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/restricted")
    String restricted() {
        return "restricted";
    }
}
