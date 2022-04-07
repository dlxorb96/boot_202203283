package com.example.mapper;

import java.util.List;

import com.example.dto.ItemDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemMapper {
        // SELECT * FROM ITEM
        // 등록일 기준 내림차순1
        // 물품명 기준 오름차순2
        // 가격 기준 오름차순3
        // 가격 기준 내림차순4

        // if문 반복문을 넣으려면 script를 넣는다
        @Select({
                        "<script>",
                        " select * from (SELECT I.ICODE, I.INAME, I.IPRICE, ROW_NUMBER() OVER(",
                        "<choose>",
                        "<when test='type == 1'>ORDER BY IREGDATE DESC</when>",
                        "<when test='type == 2'>ORDER BY INAME ASC</when>",
                        "<when test='type == 3'>ORDER BY IPRICE DESC</when>",
                        "</choose>",
                        ") rown FROM ITEM I) where rown between 1 and 12 order by rown asc",
                        " </script>"
        })
        public List<ItemDTO> selectItemList(
                        @Param(value = "type") int type);

        @Select({
                        "select * from item where icode = #{code}"
        })
        public ItemDTO selectItemOne(@Param(value = "code") long code);

        @Insert({ "INSERT INTO ITEM( ICODE, INAME, ICONTENT, IPRICE, ",
                        "			IQUANTITY, IIMAGE, IIMAGESIZE, IIMAGETYPE, ",
                        "			IIMAGENAME, UEMAIL ) ",
                        "		VALUES ( SEQ_ITEM_ICODE.NEXTVAL, #{obj.iname},#{obj.icontent},",
                        "			#{obj.iprice}, #{obj.iquantity},",
                        "			#{obj.iimage, jdbcType=BLOB}, #{obj.iimagesize},#{obj.iimagetype},",
                        "			#{obj.iimagename}, #{obj.uemail} )" })
        public int insertItemOne(@Param(value = "obj") ItemDTO item);
}
