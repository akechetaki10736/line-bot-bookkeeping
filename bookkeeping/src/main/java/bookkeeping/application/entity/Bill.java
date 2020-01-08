package bookkeeping.application.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "Bill")
@Data
public class Bill {

    private String item;
    private Integer price;
    private String comment;
    private Date billTime;
    @EmbeddedId
    private PKOfBill pkOfBill;

    public Bill( String item, Integer price, String comment, Date billTime, PKOfBill pkOfBill) {
        this.item = item;
        this.price = price;
        this.comment = comment;
        this.billTime = billTime;
        this.pkOfBill = pkOfBill;
    }

    public Bill() {
    }


}
