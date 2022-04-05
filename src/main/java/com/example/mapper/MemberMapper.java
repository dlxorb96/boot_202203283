package com.example.mapper;

import java.util.List;

import com.example.dto.MemberAddrDTO;
import com.example.dto.MemberDTO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {

        // 파라미터 n개 가능 대신 명칭을 부여해줘야 함.
        // INSERT INTO 테이블 명 (컬럼명들) VALUES(추가할 값들)
        @Insert({
                        "INSERT INTO MEMBER (UEMAIL, UPW, UNAME, UPHONE, UROLE, UREGDATE) VALUES(#{obj.uemail}, #{obj.upw}, #{obj.uname}, #{obj.uphone}, #{obj.urole}, CURRENT_DATE)" })
        public int memberJoin(@Param(value = "obj") MemberDTO member);

        @Select({
                        "SELECT UEMAIL, UNAME, UROLE FROM MEMBER",
                        " WHERE UEMAIL=#{email} AND UPW=#{pw}"
        })
        public MemberDTO memberLogin(
                        @Param(value = "email") String em,
                        @Param(value = "pw") String pw);
        // em에 담고 email을 쓰는거임 em 대신 아무거나 써도됨

        @Insert({
                        "INSERT INTO MEMBERADDR(UCODE, UADDRESSS, UPOSTCODE, UREGDATE, UEMAIL)",
                        " VALUES(SEQ_MEMBERADDR_UCODE.NEXTVAL, #{obj.uaddresss}, #{obj.upostcode}, CURRENT_DATE, #{obj.uemail})"
        })
        public int memberAddrInsert(
                        @Param(value = "obj") MemberAddrDTO addr);

        @Select({
                        "SELECT * FROM MEMBERADDR WHERE UEMAIL=#{email} ORDER BY UCHK DESC"
        })
        public List<MemberAddrDTO> addrSelect(
                        @Param(value = "email") String em);

        @Update({
                        "UPDATE MEMBERADDR SET UCHK=SEQ_MEMBERADDR_UCODE.NEXTVAL WHERE UCODE=#{chk}"
        })
        public int memberAddrUpdate(@Param(value = "chk") Long chk);

        @Delete({
                        "DELETE FROM MEMBERADDR WHERE UCODE=#{code}"
        })
        public int memberAddrDelete(@Param(value = "code") long code);

        // 1개 주소 정보 가죠오기

        //
        @Select({
                        "SELECT UCODE, UADDRESSS, UPOSTCODE FROM MEMBERADDR WHERE UCODE=#{code} AND UEMAIL=#{email}"
        })

        public MemberAddrDTO memberselectOne(
                        @Param(value = "code") long code,
                        @Param(value = "email") String email);

        @Update({
                        "UPDATE MEMBERADDR SET UADDRESSS = #{memberaddr.uaddresss},UPOSTCODE=#{memberaddr.upostcode} WHERE UCODE=#{memberaddr.ucode} AND UEMAIL= #{memberaddr.uemail}"
        })
        public int MemberUpdateAddrAction(
                        @Param(value = "memberaddr") MemberAddrDTO memberaddr);

        @Select({
                        "SELECT UEMAIL, UNAME, UPW, UROLE FROM MEMBER",
                        " WHERE UEMAIL=#{email}"
        })

        public MemberDTO memberEmail(
                        @Param(value = "email") String email);

        @Select({
                        "SELECT UEMAIL, UNAME, UPW, UROLE, UPHONE FROM MEMBER",
                        " WHERE UEMAIL=#{email}"
        })

        public MemberDTO memberEmail2(
                        @Param(value = "email") String email);

}
