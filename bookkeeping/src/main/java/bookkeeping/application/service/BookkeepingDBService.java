package bookkeeping.application.service;

import bookkeeping.application.entity.Member;

import java.util.List;
import java.util.Optional;

public interface BookkeepingDBService {
    void save(Member member);

    List<Member> findAll();

    Optional<Member> findById(String UID);

}
