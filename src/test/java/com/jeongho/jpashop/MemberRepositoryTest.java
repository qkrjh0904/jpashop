package com.jeongho.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional  // test에 있으면 db를 롤백시켜버림
    @Rollback(value = false)
    public void save() throws Exception{
        // given
        Member member = new Member();
        member.setUserName("정호");

        // when
        Long id = memberRepository.save(member);
        Member findMember = memberRepository.find(id);

        // result
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}