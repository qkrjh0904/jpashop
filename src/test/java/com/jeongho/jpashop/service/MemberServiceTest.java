package com.jeongho.jpashop.service;

import com.jeongho.jpashop.domain.Member;
import com.jeongho.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("박정호");

        // when
        Long memberId = memberService.join(member);
        em.flush();

        // result
        assertEquals(member, memberRepository.findOne(memberId));
    }

    @Test
    public void 중복회원예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("박정호");

        Member member2 = new Member();
        member2.setName("박정호");

        // when
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });

        // result
    }
}