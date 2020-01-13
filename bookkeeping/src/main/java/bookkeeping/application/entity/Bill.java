package bookkeeping.application.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "Bill")
@Data
public class Bill {

    private String item;
    private Integer price;
    private String comment;
    private Timestamp billTime;
    @EmbeddedId
    private PKOfBill pkOfBill;

    public Bill( String item, Integer price, String comment, Timestamp billTime, PKOfBill pkOfBill) {
        this.item = item;
        this.price = price;
        this.comment = comment;
        this.billTime = billTime;
        this.pkOfBill = pkOfBill;
    }

    public Bill() {
    }


}
