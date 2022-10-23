package com.sawol.refactor.web.login;

import com.sawol.refactor.domain.LoginService;
import com.sawol.refactor.domain.member.Member;
import com.sawol.refactor.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final MemberRepository memberRepository;

    @PostMapping
    public String login(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            log.info("binding error {}", bindingResult);
            return "error";
        }

        Member loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다.");
            return "error!";
        }

        // 로그인 성공 처리
        // 쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 쿠키 삭제)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);
        return "success!";
    }

    @GetMapping
    public ResponseEntity<Member> checkLogin(@CookieValue(name = "memberId", required = false) Long memberId) {
        if (memberId == null) {
            return null;
        }

        Member findMember = memberRepository.findById(memberId).orElse(null);
        return ResponseEntity.ok(findMember);
    }
}
