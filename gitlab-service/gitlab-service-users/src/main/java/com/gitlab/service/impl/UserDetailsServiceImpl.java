package com.gitlab.service.impl;

import com.gitlab.dao.UserInformationMapper;
import com.gitlab.users.dto.DtoLoginInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserInformationMapper userMapper;

    @Override
    public DtoLoginInformation loadUserByUsername(String email) throws UsernameNotFoundException {
        //查数据库
         DtoLoginInformation dtoLoginInformation = userMapper.loadUserByEmail(email);
         System.out.println("?:"+dtoLoginInformation.getId());
        return dtoLoginInformation;
    }
}
