package bookkeeping.application.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data

public class Member implements Serializable {
    private static final long serialVersionUID = -1410032090632560344L;
    @Id
    private String UID;

    private String nickname;

    private Timestamp joinTimestamp;
    @OneToMany(mappedBy = "pkOfBill.member")
    private List<Bill> bills;


    public Member(String UID, String nickname, Timestamp joinTimestamp) {
        this.UID = UID;
        this.nickname = nickname;
        this.joinTimestamp = joinTimestamp;
    }

    public Member() {
    }
}
