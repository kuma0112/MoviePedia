package com.supershy.moviepedia.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import jakarta.validation.constraints.Pattern;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class MemberDto {
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 10, message = "닉네임은 1자 이상 10자 이하로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 7, max = 20, message = "비밀번호는 7자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}$", message = "비밀번호는 영어와 숫자를 포함해야 합니다.")
    private String password;
}
