package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.dto.MemberDTO;
import com.example.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/seller")
public class SellerController {

    @Autowired
    MemberService mService;

    // 127.0.0.1:9090/ROOT/seller/select
    @GetMapping(value = "/select")
    public String selectGET() {
        return "/seller/select";
    }

    // 127.0.0.1:9090/ROOT/seller/select
    @PostMapping(value = "/select")
    public String selectPOST(
            HttpSession httpSession,
            @ModelAttribute MemberDTO member) {
        System.out.println(member.toString());
        MemberDTO retmem = mService.selectMemberLogin(member);
        System.out.println("------------------------------------------" + retmem);
        if (retmem != null) {
            // 세션에 정보를 기록
            // 자료가 유지되는 시간은 기본값 60*30 = 1800초
            httpSession.setAttribute("SESSION_EMAIL", retmem.getUemail());
            httpSession.setAttribute("SESSION_NAME", retmem.getUname());
            httpSession.setAttribute("SESSION_ROLE", retmem.getUrole());
            return "redirect:/home";
        }
        return "redirect:/seller/select";
    }

    @RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
    public String logoutGETPOST(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";

    }

    // 127.0.0.1:9090/ROOT/seller/insert
    @GetMapping(value = "/insert")
    public String insertGET() {
        // templates폴더 seller폴더 insert.html 표시 렌더링
        return "/seller/insert";
    }

    @PostMapping(value = "/insert")
    public String insertPost(@ModelAttribute MemberDTO member) {
        System.out.println("sellercontroller>  :" + member.toString());
        System.out.println("------------------");
        mService.insertMember(member);
        // 주소를 바꾼 다음 엔터키
        return "redirect:/home";
    }

    // @RequestMapping(value = "/delete")
    @GetMapping(value = "/delete")
    public String deleteGET(@RequestParam(name = "uemail") String em) {
        int ret = mService.deleteMemberOne(em);
        if (ret == 1) {
            return "redirect:/seller/selectlist";
        } else {
            return "redirect:/seller/selectlist";
        }
        // return "redirect:/seller/selectlist";
    }

    @GetMapping(value = "/update")
    public String updateGET(
            Model model,
            @RequestParam(name = "uemail") String em) {
        MemberDTO member = mService.selectMemberOne(em);
        model.addAttribute("obj", member);
        return "/seller/update";
    }

    @PostMapping(value = "/update")
    public String updatePost(@ModelAttribute MemberDTO member) {
        int ret = mService.updateMember(member);
        if (ret == 1) {
            return "redirect:/seller/selectlist";
        }
        return "redirect:/seller/update?uemail" + "member.uemail";
    }

    // 127.0.0.1:ROOT/seller/selectlist
    @GetMapping(value = "/selectlist")
    public String selectlistGET(Model model) {
        List<MemberDTO> list = mService.selectMemberList();
        model.addAttribute("list", list);

        return "/seller/selectlist";
    }

}
