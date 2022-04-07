package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.example.dto.ItemDTO;
import com.example.dto.ItemimageDTO;
import com.example.mapper.ItemMapper;
import com.example.service.ItemImagesService;
import com.example.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    ItemMapper iMapper;

    @Autowired
    ItemService iService;

    @Autowired
    ResourceLoader resLoader;

    @Autowired
    ItemImagesService iiservice;

    @GetMapping(value = "insert")
    public String insertGET() {
        return "/item/insert";
    }

    @PostMapping(value = "insert")
    public String insertPOST(
            HttpSession httpSession,
            @ModelAttribute ItemDTO item,
            @RequestParam(name = "iimage2") MultipartFile file) throws IOException {
        if (file != null) {
            item.setIimage(file.getBytes());
            item.setIimagesize(file.getSize());
            item.setIimagetype(file.getContentType());
            item.setIimagename(file.getOriginalFilename());
        }

        item.setUemail((String) httpSession.getAttribute("SESSION_EMAIL"));

        int ret = iMapper.insertItemOne(item);

        // int ret = iService.insertItem(item);
        if (ret == 1) {
            return "redirect:/home";
        }
        return "redirect:/item/insert";
    }

    // 127.0.0.1:9090/ROOT/item/selectlist?txt=검색어&page=1
    @GetMapping(value = "selectlist")
    public String selectlistGET(
            HttpSession httpSession,
            @RequestParam(name = "txt", defaultValue = "") String txt,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        // 로그인 여부 확인하기
        String em = (String) httpSession.getAttribute("SESSION_EMAIL");
        if (em != null) {
            // string, object 타입을 동시에 xml로 넘기기 위해 map타입으로 만듦
            Map<String, Object> map = new HashMap<>();
            map.put("txt", txt);
            map.put("start", page * 10 - 9);
            map.put("end", page * 10);
            map.put("email", em);

            List<ItemDTO> list = iService.selectItemList(map);
            model.addAttribute("list", list);

            Map<String, Object> map2 = new HashMap<>();
            map2.put("txt", txt);
            map2.put("email", em);

            long cnt = iService.selectItemCount(map2);
            model.addAttribute("pages", (cnt - 1) / 10 + 1);
            return "/item/selectlist";
        }
        return "redirect:/seller/select";

    }

    @GetMapping(value = "selectone")
    public String selectoneGET(
            @RequestParam(name = "icode") int icode,
            Model model) {
        ItemDTO retItem = iService.selectOne(icode);
        model.addAttribute("item", retItem);

        List<Long> imgcode = iiservice.selectItemImageList(icode);
        model.addAttribute("imgcode", imgcode);

        return "/item/selectone";
    }

    @GetMapping(value = "/subimage")
    public ResponseEntity<byte[]> subimageGET(
            @RequestParam(name = "icode") long icode) throws IOException {
        System.out.println("-----------------------" + icode);
        ItemimageDTO retItemImg = iiservice.selectItemImageOne(icode);

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

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "icode") long icode) throws IOException {
        ItemDTO retItemImg = iService.selectImageOne(icode);
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

    @PostMapping(value = "/insertImages")
    public String insertimagesPOST(
            @RequestParam(name = "icode") long icode,
            @RequestParam(name = "timages") MultipartFile[] files)
            throws IOException {
        System.out.println("---------------" + icode);

        // ItemImageDTO를 n개 보관할 수 있는 list
        List<ItemimageDTO> list = new ArrayList<>();

        for (MultipartFile file : files) {
            ItemimageDTO obj = new ItemimageDTO();
            obj.setIcode(icode); // 물품코드
            obj.setIimage(file.getBytes()); // 이미지
            obj.setIimagetype(file.getContentType());// 타입
            obj.setIimagesize(file.getSize()); // 사이즈
            obj.setIimagename(file.getOriginalFilename()); // 파일명

            list.add(obj);
        }

        int ret = iiservice.insertItemImageBatch(list);
        System.out.println("-----------------" + ret);

        return "redirect:/item/selectone?icode=" + icode;
    }

    // 수정하기
    @GetMapping(value = "/update")
    public String updateGET(
            @RequestParam(name = "icode") long icode,
            @RequestParam(name = "timages") MultipartFile file) throws IOException {
        ItemimageDTO obj = new ItemimageDTO();
        obj.setIcode(icode); // 물품코드
        obj.setIimage(file.getBytes()); // 이미지
        obj.setIimagetype(file.getContentType());// 타입
        obj.setIimagesize(file.getSize()); // 사이즈
        obj.setIimagename(file.getOriginalFilename()); // 파일명
        iiservice.updateItemImageOne(obj);
        return "redirect:/item/selectone?icode=" + icode;
    }

    // 삭제하기
    @GetMapping(value = "/delete")
    public String deleteGET(
            @RequestParam(name = "icode") int icode,
            @RequestParam(name = "ucode") int ucode) {

        int ret = iiservice.deleteItemImageOne(icode);
        if (ret == 1) {
            return "redirect:/item/selectone?icode=" + ucode;
        }

        return "redirect:/item/selectone?icode=" + ucode;
    }

}
