package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.example.dto.ItemDTO;
import com.example.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
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
    ItemService iService;

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
            item.setImagesize(file.getSize());
            item.setImagetype(file.getContentType());
            item.setImagename(file.getOriginalFilename());
        }

        item.setUemail((String) httpSession.getAttribute("SESSION_EMAIL"));
        System.out.println(item);
        int ret = iService.insertItem(item);
        if (ret == 1) {
            return "redirect:/home";
        }
        return "redirect:/item/insert";
    }

    // 127.0.0.1:9090/ROOT/item/selectlist?txt=검색어&page=1
    @GetMapping(value = "selectlist")
    public String selectlistGET(
            @RequestParam(name = "txt", defaultValue = "") String txt,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        Map<String, Object> map = new HashMap<>();
        map.put("txt", txt);
        map.put("start", page * 10 - 9);
        map.put("end", page * 10);

        List<ItemDTO> list = iService.selectItemList(map);
        model.addAttribute("list", list);

        long cnt = iService.selectItemCount(txt);
        model.addAttribute("pages", (cnt - 1) / 10 + 1);
        return "/item/selectlist";
    }

    @GetMapping(value = "selectone")
    public String selectoneGET(
            @RequestParam(name = "icode") int icode,
            Model model) {
        ItemDTO retItem = iService.selectOne(icode);
        System.out.println(retItem);
        model.addAttribute("item", retItem);

        return "/item/selectone";
    }
}
