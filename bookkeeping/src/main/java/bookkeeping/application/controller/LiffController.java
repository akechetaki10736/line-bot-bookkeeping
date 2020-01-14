package bookkeeping.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LiffController {

    @GetMapping("/demo")
    public String getIndexHTML() {
        return "index";
    }
}
