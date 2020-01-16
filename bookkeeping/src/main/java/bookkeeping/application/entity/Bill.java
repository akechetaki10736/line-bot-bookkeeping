package bookkeeping.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "Bill")
@Data
@ToString(exclude = {"pkOfBill"})

public class Bill implements Serializable {
    private static final long serialVersionUID = 187670321407859561L;

//    @JsonIgnore
    private String item;
//    @JsonIgnore
    private Integer price;
//    @JsonIgnore
    private String comment;
//    @JsonIgnore
    private Timestamp billTime;
    @EmbeddedId
    @JsonIgnore
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
