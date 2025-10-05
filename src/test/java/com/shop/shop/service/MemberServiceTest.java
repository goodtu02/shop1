package com.shop.shop.service;

import com.shop.shop.dto.MemberFormDto;
import com.shop.shop.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //createMember를 직접 구현하기 위해 entity가 아닌 dto 사용
    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@test.com");
        memberFormDto.setPassword("123456");
        memberFormDto.setName("testname");
        memberFormDto.setAddress("testaddress");
        return Member.createMember(memberFormDto,passwordEncoder);
    }
    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
    Member member = createMember();
    Member savedMember = memberService.saveMember(member);

    assertEquals(member.getEmail(), savedMember.getEmail());
    assertEquals(member.getPassword(), savedMember.getPassword());
    assertEquals(member.getName(), savedMember.getName());
    assertEquals(member.getAddress(), savedMember.getAddress());
    assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void saveDuplicateMemberTest(){
        Member member = createMember();
        memberService.saveMember(member);
        Member member1 = createMember();
        Throwable e = assertThrows(IllegalStateException.class, () -> memberService.saveMember(member1));

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}
