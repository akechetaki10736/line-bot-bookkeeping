package bookkeeping.application.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Member {
    @Id
    private String UID;
    private String nickname;
    @OneToMany(mappedBy = "pkOfBill.member")
    private List<Bill> bills;

    public Member(String UID, String nickname) {
        this.UID = UID;
        this.nickname = nickname;
    }

    public Member() {
    }
}
