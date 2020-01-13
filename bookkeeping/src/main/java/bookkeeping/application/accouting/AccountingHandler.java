package bookkeeping.application.accouting;


import bookkeeping.application.command.CommandHandler;
import bookkeeping.application.entity.Bill;
import bookkeeping.application.entity.Member;
import bookkeeping.application.entity.PKOfBill;
import bookkeeping.application.service.BillService;
import bookkeeping.application.service.MemberService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class AccountingHandler {

    private BillService billService;
    private MemberService memberService;
    private LineMessagingClient lineMessagingClient;
    private CommandHandler commandHandler;

    @Autowired
    public AccountingHandler(BillService billService, MemberService memberService, LineMessagingClient lineMessagingClient, CommandHandler commandHandler) {
        this.billService = billService;
        this.memberService = memberService;
        this.lineMessagingClient = lineMessagingClient;
        this.commandHandler = commandHandler;
    }

    public void addBill(String UID, String accountMessage){
        List<String> columnsOfBill = Arrays.asList(accountMessage.split(","));
        Member currentMember = null;
        String billTime = columnsOfBill.get(3) + " 00:00:00";

        if(memberService.findById(UID).isPresent())
            currentMember = memberService.findById(UID).get();

            PKOfBill pkOfBill = new PKOfBill(currentMember, Timestamp.valueOf(LocalDateTime.now()));

        Bill bill = new Bill(
                columnsOfBill.get(0),
                Integer.parseInt(columnsOfBill.get(1)),
                columnsOfBill.get(2),
                Timestamp.valueOf(billTime),
                pkOfBill
        );

        billService.save(bill);

        lineMessagingClient.pushMessage(new PushMessage(UID, new TextMessage("紀錄成功")));
        commandHandler.statusMap.put(UID,"!register");
    }

}
