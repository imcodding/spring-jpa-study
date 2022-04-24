package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // MemberForm 처럼, 따로 폼을 만들어서 사용하는 것 권장. Member 엔티티 그대로 받아서 쓰는 경우 없음.
    public String create(@Valid MemberForm form, BindingResult result) { // BindingResult 는 타임리프 라이브러리에서 제공

        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipCode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        // Member 엔티티로 바로 데이터를 받기 보다, dto(화면에 맞는 형태) 를 따로 만들어서 하는 것 권장.
        // 현재는 간단하고 Member 에 따로 변경 포인트가 없기 때문에 Member 로 받음.
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
    /*
     API 를 만들 때는 절대 Entity 를 외부로 반환하면 안 된다.
     - 패스워드를 필드를 추가할 경우, 노출될 가능성 있음
     - 필드가 추가됨에 따라 api 스팩이 변경되게 됨.
     */
}
