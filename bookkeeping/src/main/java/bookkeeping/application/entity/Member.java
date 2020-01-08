package bookkeeping.application.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Data
public class Member {
    @Id
    private String UID;
    private String nickname;

    public Member(String UID, String nickname) {
        this.UID = UID;
        this.nickname = nickname;
    }

    public Member() {
    }
}
