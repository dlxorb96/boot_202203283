package com.example.mapper;

import java.util.List;

import com.example.dto.ItemimageDTO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemImageMapper {

    // 물품코드가 오면 관련되어 있는 서브이미지 번호를 반환
    @Select({
            "SELECT IMGCODE FROM ITEMIMAGE WHERE ICODE=#{code}"
    })

    public List<Long> selectItemImageCodeList(
            @Param(value = "code") long code);

    @Select({ "SELECT * FROM ITEMIMAGE WHERE IMGCODE=#{imgcode}" })
    public ItemimageDTO selectItemImageCodeOne(
            @Param(value = "imgcode") long imgcode);

}
