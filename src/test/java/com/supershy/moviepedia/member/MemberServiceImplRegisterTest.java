package com.supershy.moviepedia.member;

import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.member.repository.MemberRepository;
import com.supershy.moviepedia.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplRegisterTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    @Test
    @DisplayName("정상적으로 회원이 등록되는가?")
    void registerMember_Success() {
        // given
        MemberDto memberDto = MemberDto.builder()
                .nickname("test_nickname")
                .email("test@gmail.com")
                .password("password")
                .build();

        String encodedPassword = "encode_password";
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .nickname(memberDto.getNickname())
                .password(memberDto.getPassword())
                .build();

        when(passwordEncoder.encode(memberDto.getPassword())).thenReturn(encodedPassword);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        memberServiceImpl.registerMember(memberDto);

        // then
        verify(passwordEncoder, times(1)).encode(memberDto.getPassword());
    }


    @Test
    @DisplayName("중복된 이메일 회원 등록 시 예외가 발생하는가?")
    void registerMember_Fail() {
        // given
        MemberDto memberDto = MemberDto.builder()
                .nickname("test_nickname")
                .email("test@gmail.com")
                .password("password")
                .build();

        when(memberRepository.save(any(Member.class))).
                thenThrow(DataIntegrityViolationException.class);

        // when then
        assertThrows(DataIntegrityViolationException.class, ()
                -> memberServiceImpl.registerMember(memberDto));
    }

}
