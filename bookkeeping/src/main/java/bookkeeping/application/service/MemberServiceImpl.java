package bookkeeping.application.service;

import bookkeeping.application.entity.Member;
import bookkeeping.application.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findById(String UID) {
        Optional<Member> result = memberRepository.findById(UID);
        if(result.isPresent())
            return result;

        return Optional.empty();
    }
}
