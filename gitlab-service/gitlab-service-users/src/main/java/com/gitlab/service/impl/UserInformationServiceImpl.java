package com.gitlab.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gitlab.config.JwtTokenUtil;
import com.gitlab.dao.UserInformationMapper;
import com.gitlab.service.UserInformationService;
import com.gitlab.users.dto.DtoLoginInformation;
import com.gitlab.users.pojo.UserInformation;
import entity.IdWorker;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:shenjunjie
 * @Description:UserInformation业务层接口实现类
 * @Date:2020/04/23
 *****/
@Service
public class UserInformationServiceImpl implements UserInformationService {

    @Autowired
    private UserInformationMapper userInformationMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken( username, password );
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        DtoLoginInformation userDetails = (DtoLoginInformation) this.userDetailsService.loadUserByUsername( username );
        String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    /**
     * UserInformation条件+分页查询
     * @param userInformation 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<UserInformation> findPage(UserInformation userInformation, int page, int size) {
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(userInformation);
        //执行搜索
        return new PageInfo<UserInformation>(userInformationMapper.selectByExample(example));
    }

    /**
     * UserInformation分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<UserInformation> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<UserInformation>(userInformationMapper.selectAll());
    }

    /**
     * UserInformation条件查询
     * @param userInformation
     * @return
     */
    @Override
    public List<UserInformation> findList(UserInformation userInformation) {
        //构建查询条件
        Example example = createExample(userInformation);
        //根据构建的条件查询数据
        return userInformationMapper.selectByExample(example);
    }


    /**
     * UserInformation构建查询对象
     * @param userInformation
     * @return
     */
    public Example createExample(UserInformation userInformation) {
        Example example = new Example(UserInformation.class);
        Example.Criteria criteria = example.createCriteria();
        if(userInformation != null) {
                criteria.andEqualTo("email",userInformation.getEmail());
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        userInformationMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改UserInformation
     * @param userInformation
     */
    @Override
    public void update(UserInformation userInformation) {
        userInformationMapper.updateByPrimaryKey(userInformation);
    }

    /**
     * 增加UserInformation
     * @param userInformation
     */
    @Override
    public void add(UserInformation userInformation) {
        userInformationMapper.insert(userInformation);
    }

    /**
     * 根据ID查询UserInformation
     * @param id
     * @return
     */
    @Override
    public UserInformation findById(String id) {
        return  userInformationMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询UserInformation全部数据
     * @return
     */
    @Override
    public List<UserInformation> findAll() {
        return userInformationMapper.selectAll();
    }

    /***
     * 更改密码
     */
    @Override
    public boolean changePassword(String id , String new_password){
        Example example = new Example(UserInformation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",id);
        userInformationMapper.selectByExample(example);
        List<UserInformation> users = userInformationMapper.selectByExample(example);
        if(!users.isEmpty()){
            UserInformation userInformation = users.get(0);
            userInformation.setPassword(new_password);
            userInformationMapper.updateByPrimaryKey(userInformation);
            return true;
        }
        return false;
    }
}
