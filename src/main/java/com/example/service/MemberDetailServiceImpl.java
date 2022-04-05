package com.example.service;

import java.util.Collection;

import com.example.dto.MemberDTO;
import com.example.dto.MyUserDTO;
import com.example.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailServiceImpl implements UserDetailsService {

    @Autowired
    MemberMapper mMapper;

    // 로그인에서 입력하는 정보중에서 아이디를 받음
    // MemberMapper를 이용해서 정보를 가져와서 UserDetails
    // userdetailservice에서 미리 설계가 되어 있음.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username" + username);
        MemberDTO member = mMapper.memberEmail(username);
        System.out.println("--------------------------" + member);

        // 권한 정보를 문자열 배열로 만듦.
        String[] strRole = { member.getUrole() };

        // String 배열 권한을 collection<Granted ...> 타입으로 변환함
        Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);
        // 권한을 여러개 줄 수 있다 customer, seller, admin 이렇게

        // 아이디, 암호, 권한들 ... 직접 커스터마이징할 수 있게 만들어진거임.
        // User user = new User(member.getUemail(), member.getUpw(), role);

        MemberDTO member2 = mMapper.memberEmail2(username);
        MyUserDTO userDTO = new MyUserDTO(member2.getUemail(), member2.getUpw(), role,
                member2.getUphone(), member2.getUname());
        // 암호화를 했기 때문에 안 됨.

        return userDTO;
    }

}
