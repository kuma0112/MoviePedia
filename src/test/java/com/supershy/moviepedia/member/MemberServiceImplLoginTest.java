package com.supershy.moviepedia.member;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.auth.utils.JwtTokenProvider;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.member.repository.MemberRepository;
import com.supershy.moviepedia.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplLoginTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginMember_success(){
        // given
        LoginRequestDto loginRequestDto = new LoginRequestDto("user1@example.com", "password123");
        Member member = Member.builder()
                .email("user1@example.com")
                .password("encodedPassword")
                .nickname("user1")
                .build();

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn("user1@example.com");
        when(jwtTokenProvider.createToken("user1@example.com")).thenReturn("jwtToken");
        when(memberRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(member));

        // when
        Map<String, String> result = memberService.loginMember(loginRequestDto);

        // then
        assertNotNull(result);
        assertEquals("jwtToken", result.get("token"));
        assertEquals("user1", result.get("nickname"));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).createToken("user1@example.com");
        verify(memberRepository).findByEmail("user1@example.com");
    }

}
