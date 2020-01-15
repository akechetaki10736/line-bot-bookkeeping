package bookkeeping.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data

public class Member {
    @Id
    @JsonIgnore
    private String UID;
    @JsonIgnore
    private String nickname;
    @JsonIgnore
    private Timestamp joinTimestamp;
    @OneToMany(mappedBy = "pkOfBill.member")
    @JsonIgnore
    private List<Bill> bills;


    public Member(String UID, String nickname, Timestamp joinTimestamp) {
        this.UID = UID;
        this.nickname = nickname;
        this.joinTimestamp = joinTimestamp;
    }

    public Member() {
    }
}
