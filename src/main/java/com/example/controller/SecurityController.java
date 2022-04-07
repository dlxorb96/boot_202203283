package com.example.controller;

import javax.servlet.http.HttpSession;

import com.example.dto.MemberDTO;
import com.example.dto.MyUserDTO;
import com.example.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {

    @Autowired
    MemberMapper mMapper;

    @Autowired
    HttpSession httpSession;

    // 403페이지
    @GetMapping(value = { "/security_403" })
    public String security_403Get() {
        return "/security/403page";
    }

    // 홈화면(로그인 전에)
    @GetMapping(value = { "/security_home" })
    public String security_homeGet(
            Model model,
            @AuthenticationPrincipal MyUserDTO user) {
        if (user != null) {

            System.out.println(user.getUsername());
            System.out.println(user.getUsername());
            System.out.println(user.getAuthorities().toArray()[0]);
        }
        System.out.println("-----------------------" + user);
        model.addAttribute("user", user);
        // model.addAttribute("userrole", user.getAuthorities().toArray()[0]);
        // model.addAttribute("userid", user.getUsername());
        // model.addAttribute("userpw", user.getPassword());
        // null이면 다음이 안돼서 오류가 난다.
        return "/security/home";
    }

    // 관리자 홈화면(로그인 후에)
    @GetMapping(value = { "/security_admin/home" })
    public String security_admin_homeGet() {
        return "/security/admin_home";
    }

    // 판매자 홈화면(로그인 후에)
    @GetMapping(value = { "/security_seller/home" })
    public String security_seller_homeGet() {
        return "/security/seller_home";
    }

    // 고객 홈화면(로그인 후에)
    @GetMapping(value = { "/security_customer/home" })
    public String security_customer_homeGet() {
        return "/security/customer_home";
    }

    @GetMapping(value = "/security_join")
    public String securityJoinGet() {
        return "/security/join";
    }

    @PostMapping(value = "/security_join")
    public String security_joinPost(
            @ModelAttribute MemberDTO member) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        // 암호를 가져와서 해시한 후 다시 추가하기
        member.setUpw(bcpe.encode(member.getUpw()));
        member.setUrole("CUSTOMER");

        int ret = mMapper.memberJoin(member);
        if (ret == 1) {
            return "redirect:/security_home";
        }
        return "redirect:/member/security_join";
    }

    @GetMapping(value = "/member/security_login")
    public String securityloginGET() {

        // 렌더링임. get에서만 사용해야함
        return "/security/login";
    }

    // @GetMapping(value = "/member/security_loginaction")
    // public String securityloginActinoGET(
    // @RequestParam(name = "uemail") String em,
    // @RequestParam(name = "upw") String pw) {

    // return "redirect:/security_home";

    // }

}
