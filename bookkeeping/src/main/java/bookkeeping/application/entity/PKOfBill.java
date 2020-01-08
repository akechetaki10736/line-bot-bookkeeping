package bookkeeping.application.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Timestamp;

@Embeddable
@Data

public class PKOfBill implements Serializable {
    private static final long serialVersionUID = -5581303592163117516L;
    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;
    private Timestamp insertTime;

    public  PKOfBill(Member member, Timestamp insertTime) {
        this.member = member;
        this.insertTime = insertTime;
    }

    public PKOfBill() {
    }
}
