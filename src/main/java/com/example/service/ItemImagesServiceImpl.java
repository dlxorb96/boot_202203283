package com.example.service;

import java.util.List;

import com.example.dto.ItemimageDTO;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemImagesServiceImpl implements ItemImagesService {

    @Autowired
    SqlSessionFactory sqlFactory;

    @Override
    public int insertItemImageBatch(List<ItemimageDTO> list) {
        int cnt = 0;
        for (ItemimageDTO item : list) {
            // 3번 반복
            long seq = sqlFactory.openSession().selectOne("ItemImage.seqItemImage");
            item.setImgcode(seq);

            cnt = cnt + sqlFactory.openSession().insert("ItemImage.insertItemImageOne", item);
        }
        if (list.size() == cnt) {
            return 1;
        }
        return 0;
    }

    @Override
    public ItemimageDTO selectItemImageOne(long imgcode) {
        return sqlFactory.openSession().selectOne("ItemImage.selectItemImageOne", imgcode);
    }

    @Override
    public int updateItemImageOne(ItemimageDTO itemimage) {
        return sqlFactory.openSession().update("ItemImage.updateItemImageOne", itemimage);
    }

    @Override
    public int deleteItemImageOne(long imgcode) {
        return sqlFactory.openSession().delete("ItemImage.deleteItemImageOne", imgcode);
    }

    @Override
    public List<Long> selectItemImageList(long icode) {
        return sqlFactory.openSession().selectList("ItemImage.selectItemImageList", icode);
    }

}
