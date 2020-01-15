package bookkeeping.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Member member;
    @JsonIgnore
    private Timestamp insertTime;

    public  PKOfBill(Member member, Timestamp insertTime) {
        this.member = member;
        this.insertTime = insertTime;
    }

    public PKOfBill() {
    }
}
