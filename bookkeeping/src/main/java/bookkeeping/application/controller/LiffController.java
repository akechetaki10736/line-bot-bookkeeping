package bookkeeping.application.controller;

import bookkeeping.application.entity.Bill;
import bookkeeping.application.entity.Member;
import bookkeeping.application.entity.PKOfBill;
import bookkeeping.application.service.BillService;
import bookkeeping.application.service.MemberService;
import com.linecorp.bot.client.LineMessagingClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@Slf4j
public class LiffController {

    private BillService billService;
    private MemberService memberService;
    private LineMessagingClient lineMessagingClient;

    @Value("${pageid}")
    private String pageid;

    private Map<String, String> hash;

    public LiffController(BillService billService, MemberService memberService, LineMessagingClient lineMessagingClient) {
        this.billService = billService;
        this.memberService = memberService;
        this.lineMessagingClient = lineMessagingClient;
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
    public String getPersonalAccount(Model model, @RequestBody String requestBody) throws Exception {
        log.info(requestBody);
        String prefix = "UID=";

        List<String> tokenList = new ArrayList<>();
        tokenList.addAll(Arrays.asList(requestBody.split("&")));
        String UIDAfterProcess = tokenList.get(0).substring("UID=".length());
        String AccessTokenAfterProcess = tokenList.get(1).substring("accessToken=".length());

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.line.me/v2/profile").addHeader("Authorization","Bearer " + AccessTokenAfterProcess).build();
        Response response = okHttpClient.newCall(request).execute();
        if(!response.body().string().contains(UIDAfterProcess))
            return "index";
        String sessionKey = UUID.randomUUID().toString();
        hash.put(sessionKey, UIDAfterProcess);
        model.addAttribute("requestId", sessionKey);
        model.addAttribute("pageid", pageid);
        return "personalAccount";
    }

    @PostMapping("/bookkeeping")
    public String submitBill(@ModelAttribute("bill") Bill bill, @RequestBody String requestBody, Model model){
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
        return getIndex(model);
    }




    @GetMapping("/query")
    @ResponseBody
    public List<Bill> test(@RequestParam String session) {
        log.warn(session);
        List<Bill> billList = new ArrayList<>();

        if(hash.containsKey(session)) {
            billList = billService.findByFK(hash.get(session));
            log.info(String.valueOf(billList.size()));
            hash.remove(session);
        }
        return billList;
    }


}
