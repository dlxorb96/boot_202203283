package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.dto.ItemDTO;

import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    // 물품등록
    public int insertItem(ItemDTO item);

    // 물품조회(검색어 + 페이네이션)
    public List<ItemDTO> selectItemList(Map<String, Object> map);

    public long selectItemCount(Map<String, Object> map);

    public ItemDTO selectOne(int icode);

    public ItemDTO selectImageOne(long icode);
}
