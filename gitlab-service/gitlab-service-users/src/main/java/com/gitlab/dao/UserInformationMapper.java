package com.gitlab.dao;

import com.gitlab.users.dto.DtoLoginInformation;
import com.gitlab.users.dto.DtoUserInformation;
import com.gitlab.users.pojo.UserInformation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:shenjunjie
 * @Description:UserInformationDao构建
 * @Date:2020/04/23
 *****/
public interface UserInformationMapper extends Mapper<UserInformation> {
    @Select( {"select user_id , email , password from tb_user_information where email = #{email}" })
    @Results({
            @Result(column="user_id", property="id"),
            @Result(column="email", property="username"),
            @Result(column="password", property="password")
    })
    DtoLoginInformation loadUserByEmail(@Param("email") String email);
}
