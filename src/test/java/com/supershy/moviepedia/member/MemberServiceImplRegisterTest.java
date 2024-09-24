package com.supershy.moviepedia.member;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.auth.utils.JwtTokenProvider;
import com.supershy.moviepedia.common.exception.RegistrationException;
import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.member.repository.MemberRepository;
import com.supershy.moviepedia.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplRegisterTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

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
    @DisplayName("중복된 닉네임 회원 등록 시 예외가 발생하는가?")
    void registerMemberNickname_Fail() {
        // given
        MemberDto memberDto = MemberDto.builder()
                .nickname("test_nickname")
                .email("user1@gmail.com")
                .password("password123")
                .build();

        when(memberRepository.existsByEmail(memberDto.getNickname())).thenReturn(true);

        // when then
        assertThrows(RegistrationException.class, ()
                -> memberServiceImpl.registerMember(memberDto));
    }

    @Test
    @DisplayName("중복된 이름 회원 등록 시 예외가 발생하는가?")
    void registerMember_DuplicateNickname_Fail() {
        // given
        MemberDto memberDto = MemberDto.builder()
                .nickname("user1")
                .email("test@gmail.com")
                .password("password123")
                .build();

        when(memberRepository.existsByEmail(memberDto.getEmail())).thenReturn(true);

        // when then
        assertThrows(RegistrationException.class, ()
                -> memberServiceImpl.registerMember(memberDto));
    }


    @Test
    @DisplayName("중복된 이메일 회원 등록 시 예외가 발생하는가?")
    void registerMember_DuplicateEmail_Fail() {
        // given
        MemberDto memberDto = MemberDto.builder()
                .nickname("test_nickname")
                .email("user1@gmail.com")
                .password("password123")
                .build();

        when(memberRepository.existsByEmail(memberDto.getEmail())).thenReturn(true);

        // when then
        assertThrows(RegistrationException.class, ()
                -> memberServiceImpl.registerMember(memberDto));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginMember_success() {
        // given
        String email = "user1@example.com";
        String password = "password123";
        String nickname = "user1";
        String token = "jwtToken";

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        Member member = Member.builder()
                .email(email)
                .password("encodedPassword")
                .nickname(nickname)
                .build();

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(jwtTokenProvider.createToken(email)).thenReturn(token);

        // when
        Map<String, String> result = memberServiceImpl.loginMember(loginRequestDto);

        // then
        assertNotNull(result);
        assertEquals(token, result.get("token"));
        assertEquals(nickname, result.get("nickname"));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(memberRepository).findByEmail(email);
        verify(jwtTokenProvider).createToken(email);
    }

}
