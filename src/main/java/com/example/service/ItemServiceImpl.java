package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.dto.ItemDTO;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    SqlSessionFactory sqlFactory;

    @Override
    public int insertItem(ItemDTO item) {
        return sqlFactory.openSession().insert("Item.insertItemOne", item);

    }

    @Override
    public List<ItemDTO> selectItemList(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("Item.selectItemlist", map);
    }

    @Override
    public long selectItemCount(Map<String, Object> map) {
        return sqlFactory.openSession().selectOne("Item.selectItemCount", map);
    }

    @Override
    public ItemDTO selectOne(int icode) {

        return sqlFactory.openSession().selectOne("Item.selectOne", icode);
    }

    @Override
    public ItemDTO selectImageOne(long code) {
        return sqlFactory.openSession().selectOne("Item.selectItemImageOne", code);
    }
}
