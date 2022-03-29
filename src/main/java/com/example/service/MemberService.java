package com.example.service;

import java.util.List;

import com.example.dto.MemberDTO;

public interface MemberService {

    // 판매자 등록
    public int insertMember(MemberDTO member);

    public int updateMember(MemberDTO member);

    public List<MemberDTO> selectMemberList();

    public int deleteMemberOne(String uemail);

    public MemberDTO selectMemberOne(String uemail);

    public MemberDTO selectMemberLogin(MemberDTO member);
}
