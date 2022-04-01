package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.dto.ItemDTO;
import com.example.dto.ItemimageDTO;
import com.example.mapper.ItemImageMapper;
import com.example.mapper.ItemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/shop")
public class ShopController {

    @Autowired
    ItemMapper iMapper;

    @Autowired
    ItemImageMapper iimapper;

    @Autowired
    ResourceLoader resLoader;

    @Autowired
    HttpSession httpsession;

    @GetMapping(value = "/cart")
    public String cartGET() {

        return "/shop/cart";
    }

    // detail페이지에서 주문하기 눌렀을 때
    @PostMapping(value = "/cart")
    public String cartPOST(
            @RequestParam(name = "icode") long icode,
            @RequestParam(name = "cnt") long ccnt) {
        String em = (String) httpsession.getAttribute("M_EMAIL");
        if (em != null) {
            System.out.println("코드------------------------" + icode);
            System.out.println("수량=======================" + ccnt);
            System.out.println(em);
        } else {
            System.out.println("코드------------------------" + icode);
            System.out.println("수량=======================" + ccnt);
            System.out.println(httpsession.getId());
        }
        return "redirect:/shop/detail?code=" + icode;
    }

    @GetMapping(value = { "/", "/home" })
    public String shopGET(Model model) {
        List<ItemDTO> list1 = iMapper.selectItemList(1);
        model.addAttribute("list1", list1);
        List<ItemDTO> list2 = iMapper.selectItemList(2);
        model.addAttribute("list2", list2);
        List<ItemDTO> list3 = iMapper.selectItemList(3);
        model.addAttribute("list3", list3);
        return "/shop/home";
    }

    @GetMapping(value = { "/detail" })
    public String detailGET(
            Model model,
            @RequestParam(name = "code") long code) {

        // 물품 상세 정보
        model.addAttribute("item", iMapper.selectItemOne(code));
        List<Long> imgcode = iimapper.selectItemImageCodeList(code);

        // 물품에 대한 서브이미지 번호들
        model.addAttribute("imgcode", imgcode);
        return "/shop/detail";
    }

    @GetMapping(value = "/subimage")
    public ResponseEntity<byte[]> subimageGET(
            @RequestParam(name = "icode") long icode) throws IOException {
        System.out.println("-----------------------" + icode);
        ItemimageDTO retItemImg = iimapper.selectItemImageCodeOne(icode);

        if (retItemImg != null) { // 물품정보가 존재하면
            if (retItemImg.getIimagesize() > 0) { // 첨부한 파일 존재
                HttpHeaders headers = new HttpHeaders();

                if (retItemImg.getIimagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (retItemImg.getIimagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (retItemImg.getIimagetype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                // 이미지 byte[], headers, HttpStatus.Ok
                ResponseEntity<byte[]> response = new ResponseEntity<>(retItemImg.getIimage(),
                        headers, HttpStatus.OK);
                return response;
            } else {
                InputStream is = resLoader
                        .getResource("classpath:/static/img/default.png")
                        .getInputStream();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
                        headers, HttpStatus.OK);

                return response;
            }
        }
        return null;
    }

}
