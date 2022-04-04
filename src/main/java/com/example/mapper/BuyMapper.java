package com.example.mapper;

import java.util.List;
import java.util.Map;

import com.example.dto.BuyDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BuyMapper {
    @Insert({
            "INSERT INTO BUY(BNO, BCNT, ICODE, UEMAIL )",
            " VALUES(SEQ_BUY_NO.NEXTVAL, #{obj.bcnt}, #{obj.icode}, #{obj.uemail})"
    })
    public int insertBuyOne(@Param(value = "obj") BuyDTO buy);

    // 주문내역 조회 리턴은 DTO가 아닌 Map컬렉션을 이용함
    @Select({
            "SELECT * FROM BUYLIST_VIEW WHERE UEMAIL=#{em}"
    })
    public List<Map<String, Object>> selectBuyListMap(@Param(value = "em") String em);
}
