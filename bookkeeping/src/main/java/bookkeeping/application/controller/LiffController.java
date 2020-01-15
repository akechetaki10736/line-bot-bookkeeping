package bookkeeping.application.controller;

import bookkeeping.application.entity.Bill;
import bookkeeping.application.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class LiffController {

    private BillService billService;

    @Value("${pageid}")
    private String pageid;


    private List<String> hash;

    public LiffController(BillService billService) {
        this.billService = billService;
        hash = new ArrayList<>();

    }

    @GetMapping("/demo")
    public String getIndexHTML() {
        return "index";
    }

    @GetMapping("/personalAccount")
    public String getAccount(Model model) {
        String sessionKey = UUID.randomUUID().toString();
        model.addAttribute("requestId", sessionKey);
        model.addAttribute("pageid", pageid);
        return "personalAccount";
    }

    @GetMapping("/query")
    @ResponseBody
    public List<Bill> test(@RequestParam String UID, @RequestParam String session) {
        List<Bill> billList = new ArrayList<>();
        if(hash.contains(session)) {
            billList = billService.findByFK(UID);
        }
        return billList;
    }
}
