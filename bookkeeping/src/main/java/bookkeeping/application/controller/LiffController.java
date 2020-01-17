package bookkeeping.application.controller;

import bookkeeping.application.entity.Bill;
import bookkeeping.application.entity.Member;
import bookkeeping.application.entity.PKOfBill;
import bookkeeping.application.service.BillService;
import bookkeeping.application.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@Slf4j
public class LiffController {

    private BillService billService;
    private MemberService memberService;

    @Value("${pageid}")
    private String pageid;

    private Map<String, String> hash;

    public LiffController(BillService billService, MemberService memberService) {
        this.billService = billService;
        this.memberService = memberService;
        hash = new HashMap<>();

    }

    @GetMapping("/demo")
    public String getIndexHTML() {
        return "index2";
    }

    @GetMapping("/index")
    public String getIndex(Model model){
        model.addAttribute("pageid", pageid);
        return "index";
    }

    @PostMapping("/directToBookkeeping")
    public String getBookkeeping(Model model, @RequestBody String UID){
        String sessionKey = UUID.randomUUID().toString();
        String UIDAfterProcess = UID.substring(4);
        hash.put(sessionKey, UIDAfterProcess);
        log.info(UIDAfterProcess);
        Bill bill = new Bill();
        model.addAttribute("bill", bill);
        model.addAttribute("sessionKey", sessionKey);
        return "bookkeeping";
    }

    @PostMapping("/directToPersonalAccount")
    public String getPersonalAccount(Model model, @RequestBody String UID){
        String sessionKey = UUID.randomUUID().toString();
//        hash.add(sessionKey);
        model.addAttribute("requestId", sessionKey);
        model.addAttribute("pageid", pageid);
        return "personalAccount";
    }

//    @GetMapping("/bookkeeping")
//    public String getBookkeeping(Model model){
//        Bill bill = new Bill();
//        model.addAttribute("bill", bill);
//        return "bookkeeping";
//    }

    @PostMapping("/bookkeeping")
    public String submitBill(@ModelAttribute("bill") Bill bill, @RequestBody String requestBody){
        String key = "";
        String UID = "";
        if(requestBody.indexOf("sessionKey") != -1)
            key = requestBody.substring(requestBody.indexOf("sessionKey=") + "sessionKey=".length());
        if(hash.containsKey(key))
            UID = hash.get(key);

        Member member = memberService.findById(UID).isPresent() ? memberService.findById(UID).get() : null ;
        bill.setPkOfBill(new PKOfBill(member, Timestamp.valueOf(LocalDateTime.now())));
        billService.save(bill);
        log.info(bill.getBillTime().toString());
        log.info(bill.getItem());
        return "bookkeeping";
    }


//    @GetMapping("/personalAccount")
//    public String getAccount(Model model) {
//        String sessionKey = UUID.randomUUID().toString();
//        hash.add(sessionKey);
//        model.addAttribute("requestId", sessionKey);
//        model.addAttribute("pageid", pageid);
//        return "personalAccount";
//    }

    @GetMapping("/query")
    @ResponseBody
    public List<Bill> test(@RequestParam String UID, @RequestParam String session) {
        log.warn(UID);
        List<Bill> billList = new ArrayList<>();

//        if(hash.contains(session)) {
//            billList = billService.findByFK(UID);
//            log.info(String.valueOf(billList.size()));
//            hash.remove(session);
//        }
        return billList;
    }


}
