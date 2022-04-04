package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.dto.MemberAddrDTO;
import com.example.dto.MemberDTO;
import com.example.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    HttpSession httpSession;

    @Autowired
    MemberMapper mMapper;

    @GetMapping(value = "/join")
    public String joinGET() {
        return "/member/join";
    }

    @PostMapping(value = "/joinaction")
    public String joinactionPOST(
            @ModelAttribute MemberDTO member) {

        System.out.println(member);
        member.setUrole("CUSTOMER");
        System.out.println(member.toString());
        int ret = mMapper.memberJoin(member);
        if (ret == 1) {
            return "redirect:/";
        }

        return "redirect:/member/join";

    }

    @GetMapping(value = "/login")
    public String loginGET() {

        // 렌더링임. get에서만 사용해야함
        return "/member/login";
    }

    @PostMapping(value = "/loginaction")
    public String loginPOST(
            @RequestParam(name = "uemail") String em,
            @RequestParam(name = "upw") String pw) {
        System.out.println(em);
        System.out.println(pw);
        MemberDTO retmember = mMapper.memberLogin(em, pw);
        if (retmember != null) {

            httpSession.setAttribute("M_EMAIL", retmember.getUemail());
            httpSession.setAttribute("M_NAME", retmember.getUname());
            httpSession.setAttribute("M_ROLE", retmember.getUrole());

            // 로그인성공
            String url = (String) httpSession.getAttribute("BACKURL");
            return "redirect:" + url;
        }
        // 로그인실패
        return "redirect:/member/login";
    }

    @PostMapping(value = "/logout")
    public String logoutPOST() {
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping(value = "/address")
    public String addressGET(
            Model model) {
        String em = (String) httpSession.getAttribute("M_EMAIL");
        String ro = (String) httpSession.getAttribute("M_ROLE");
        if (em != null) { // 로그인 되었는지
            if (ro.equals("CUSTOMER")) { // 권한이 정확한지
                System.out.println(em);
                List<MemberAddrDTO> list = mMapper.addrSelect(em);
                model.addAttribute("list", list);
                return "/member/address";
            }
            return "redirect:/403page";
        }
        return "redirect:/member/login";
    }

    @PostMapping(value = "/addraction")
    public String addrPOST(
            @ModelAttribute MemberAddrDTO addr) {
        String em = (String) httpSession.getAttribute("M_EMAIL");
        String ro = (String) httpSession.getAttribute("M_ROLE");
        if (em != null) {
            if (ro.equals("CUSTOMER")) {
                addr.setUemail(em);

                mMapper.memberAddrInsert(addr);

                return "redirect:/member/address";
            }
        }
        return "redirect:/member/login";
    }

    @PostMapping(value = "/chkaction")
    public String addrPOST(
            @RequestParam(name = "chk") Long chk) {
        System.out.println(chk);
        mMapper.memberAddrUpdate(chk);
        return "redirect:/member/address";
    }

    @GetMapping(value = "/updateaddr")
    public String updateaddrPOST(
            Model model,
            @RequestParam(name = "code") Long code) {
        String email = (String) httpSession.getAttribute("M_EMAIL");
        if (email != null) {
            MemberAddrDTO retaddr = mMapper.memberselectOne(code, email);
            model.addAttribute("addr", retaddr);
            return "/member/updateaddr";
        }

        return "/member/login";
    }

    @PostMapping(value = "/deleteaddr")
    public String deleteaddrPOST(
            @RequestParam(name = "chk") Long chk) {
        System.out.println(chk);
        int ret = mMapper.memberAddrDelete(chk);
        if (ret == 1) {
            return "redirect:/member/address";
        }
        return "redirect:/member/login";
    }

    @PostMapping(value = "/updateAddrAction")
    public String updateAddrActionPOST(
            @ModelAttribute MemberAddrDTO memberaddr) {
        String email = (String) httpSession.getAttribute("M_EMAIL");
        memberaddr.setUemail(email);
        System.out.println("--------------------" + memberaddr);
        // MemberAddrDTO retmemaddr =
        int ret = mMapper.MemberUpdateAddrAction(memberaddr);
        if (ret == 1) {
            return "redirect:/member/address";
        }
        return "/member/updateaddr?code=" + memberaddr.getUcode();

    }

}
