package com.jeongho.jpashop.service;

import com.jeongho.jpashop.domain.Member;
import com.jeongho.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 조금 더 최적화해줌. 디폴트는 false.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
    * @desc : 회원가입
    * @author : 박정호
    * @date : 2021-08-15 오후 9:48
    */
    @Transactional  // 쓰기의 경우에는 readOnly=false 로 설정
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
    * @desc : 중복회원 체크
    * @author : 박정호
    * @date : 2021-08-15 오후 9:49
    */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
    * @desc : 회원 한명 조회
    * @author : 박정호
    * @date : 2021-08-15 오후 9:52
    */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    /**
    * @desc : 회원 전체 조회
    * @author : 박정호
    * @date : 2021-08-15 오후 9:48
    */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

}
