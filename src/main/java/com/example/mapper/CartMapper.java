package com.example.mapper;

import com.example.dto.CartDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    @Insert({
            "INSERT INTO CART(CNO, CCNT, ICODE, UEMAIL )",
            " VALUES(SEQ_CART_NO.NEXTVAL, #{obj.ccnt}, #{obj.icode}, #{obj.uemail})"
    })
    public int insertCartOne(@Param(value = "obj") CartDTO cart);
}
