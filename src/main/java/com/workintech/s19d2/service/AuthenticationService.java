package com.workintech.s19d2.service;

import com.workintech.s19d2.entity.Member;
import com.workintech.s19d2.entity.Role;
import com.workintech.s19d2.repository.MemberRepository;
import com.workintech.s19d2.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(MemberRepository memberRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member register(String email, String password){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()){
            throw new RuntimeException("User with given email already exist" + email);
        }

        String encodePassword = passwordEncoder.encode(password);

        List<Role> authorities = new ArrayList<>();

        Optional<Role> optionalRoleAdmin = roleRepository.findByAuthority("ADMIN");
        if (!optionalRoleAdmin.isPresent()) {
            Role roleAdminEntity = new Role();
            roleAdminEntity.setAuthority("ADMIN");
            roleRepository.save(roleAdminEntity);
            authorities.add(roleAdminEntity);
        } else {
            authorities.add(optionalRoleAdmin.get());
        }

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodePassword);
        member.setAuthorities(authorities);

        return memberRepository.save(member);
    }
}
