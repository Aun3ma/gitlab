package com.gitlab.users.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;

/**
 * @Author:shenjunjie
 * @Description:UserInformation构建
 * @Date:2020/04/23
 */

@ApiModel(description = "UserInformation",value = "UserInformation")
@Table(name="tb_user_information")
public class UserInformation implements Serializable{

	@ApiModelProperty(value = "",required = false)
	@Id
    @Column(name = "user_id")
	private String userId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "user_name")
	private String userName;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "email")
	private String email;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "password")
	private String password;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "gitlab_user_handle")
	private String gitlabUserHandle;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "bio")
	private String bio;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "create_time")
	private Date createTime;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "update_time")
	private Date updateTime;//



	//get方法
	public String getUserId() {
		return userId;
	}

	//set方法
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//get方法
	public String getUserName() {
		return userName;
	}

	//set方法
	public void setUserName(String userName) {
		this.userName = userName;
	}
	//get方法
	public String getEmail() {
		return email;
	}

	//set方法
	public void setEmail(String email) {
		this.email = email;
	}
	//get方法
	public String getPassword() {
		return password;
	}

	//set方法
	public void setPassword(String password) {
		this.password = password;
	}
	//get方法
	public String getGitlabUserHandle() {
		return gitlabUserHandle;
	}

	//set方法
	public void setGitlabUserHandle(String gitlabUserHandle) {
		this.gitlabUserHandle = gitlabUserHandle;
	}
	//get方法
	public String getBio() {
		return bio;
	}

	//set方法
	public void setBio(String bio) {
		this.bio = bio;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	//get方法
	public Date getUpdateTime() {
		return updateTime;
	}

	//set方法
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}
