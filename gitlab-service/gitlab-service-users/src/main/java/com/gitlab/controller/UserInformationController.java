package com.gitlab.controller;

import com.github.pagehelper.PageInfo;
import com.gitlab.service.UserInformationService;
import com.gitlab.users.pojo.UserInformation;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author:shenjunjie
 * @Description:UserInformationController构建
 * @Date:2020/04/23
 */
@Api(value = "UserInformationController")
@RestController
@RequestMapping("/userInformation")
@CrossOrigin
public class UserInformationController {

    @Autowired
    private UserInformationService userInformationService;

    /***
     * UserInformation分页条件搜索实现
     * @param userInformation
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "UserInformation条件分页查询",notes = "分页条件查询UserInformation方法详情",tags = {"UserInformationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "UserInformation对象",value = "传入JSON数据",required = false) UserInformation userInformation, @PathVariable  int page, @PathVariable  int size) {
        //调用UserInformationService实现分页条件查询UserInformation
        PageInfo<UserInformation> pageInfo = userInformationService.findPage(userInformation, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * UserInformation分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "UserInformation分页查询",notes = "分页查询UserInformation方法详情",tags = {"UserInformationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size) {
        //调用UserInformationService实现分页查询UserInformation
        PageInfo<UserInformation> pageInfo = userInformationService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索userInformation数据
     * @param userInformation
     * @return
     */
    @ApiOperation(value = "UserInformation条件查询",notes = "条件查询UserInformation方法详情",tags = {"UserInformationController"})
    @PostMapping(value = "/search" )
    public Result<List<UserInformation>> findList(@RequestBody(required = false) @ApiParam(name = "UserInformation对象",value = "传入JSON数据",required = false) UserInformation userInformation) {
        //调用UserInformationService实现条件查询UserInformation
        List<UserInformation> list = userInformationService.findList(userInformation);
        return new Result<List<UserInformation>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除userInformation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "UserInformation根据ID删除",notes = "根据ID删除UserInformation方法详情",tags = {"UserInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id) {
        //调用UserInformationService实现根据主键删除
        userInformationService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改UserInformation数据
     * @param userInformation
     * @param id
     * @return
     */
    @ApiOperation(value = "UserInformation根据ID修改",notes = "根据ID修改UserInformation方法详情",tags = {"UserInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "UserInformation对象",value = "传入JSON数据",required = false) UserInformation userInformation,@PathVariable String id) {
        //设置主键值
        userInformation.setUserId(id);
        //调用UserInformationService实现修改UserInformation
        userInformationService.update(userInformation);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增UserInformation数据
     * @param userInformation
     * @return
     */
    @ApiOperation(value = "UserInformation添加",notes = "添加UserInformation方法详情",tags = {"UserInformationController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "UserInformation对象",value = "传入JSON数据",required = true) UserInformation userInformation) {
        //调用UserInformationService实现添加UserInformation
        userInformationService.add(userInformation);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询UserInformation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "UserInformation根据ID查询",notes = "根据ID查询UserInformation方法详情",tags = {"UserInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @GetMapping("/{id}")
    public Result<UserInformation> findById(@PathVariable String id) {
        //调用UserInformationService实现根据主键查询UserInformation
        UserInformation userInformation = userInformationService.findById(id);
        return new Result<UserInformation>(true,StatusCode.OK,"查询成功",userInformation);
    }

    /***
     * 查询UserInformation全部数据
     * @return
     */
    @ApiOperation(value = "查询所有UserInformation",notes = "查询所UserInformation有方法详情",tags = {"UserInformationController"})
    @GetMapping
    public Result<List<UserInformation>> findAll() {
        //调用UserInformationService实现查询所有UserInformation
        List<UserInformation> list = userInformationService.findAll();
        return new Result<List<UserInformation>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
