package bookkeeping.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class LiffController {

    @GetMapping("/demo")
    public String getIndexHTML() {
        return "index";
    }

    @GetMapping("/personalAccount")
    public String getAccount(Model model) {
            return "personalAccount";
    }

    @GetMapping("/query")
    @ResponseBody
    public String test(@RequestParam String UID){
        log.warn(UID);
        return "foo";
    }
}
