package com.sawol.refactor.web.login;

import com.sawol.refactor.domain.LoginService;
import com.sawol.refactor.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public String login(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("binding error {}", bindingResult);
            return "error";
        }

        Member loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 틀렸습니다.");
            return "error!";
        }
        return "success!";
    }
}
