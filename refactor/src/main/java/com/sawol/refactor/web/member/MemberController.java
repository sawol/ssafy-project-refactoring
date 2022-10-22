package com.sawol.refactor.web.member;

import com.sawol.refactor.domain.member.Member;
import com.sawol.refactor.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("binding error {}", bindingResult.getModel());
            return "Error!";
        }
        memberRepository.save(member);
        return "Success!";
    }
}
