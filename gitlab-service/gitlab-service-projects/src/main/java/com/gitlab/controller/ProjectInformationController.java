package com.gitlab.controller;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.ProjectInformation;
import com.gitlab.service.ProjectInformationService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @Author:shenjunjie
 * @Description:ProjectInformationController构建
 * @Date:2020/04/23
 */
@Api(value = "ProjectInformationController")
@RestController
@RequestMapping("/projectInformation")
@CrossOrigin
public class ProjectInformationController {

    @Autowired
    private ProjectInformationService projectInformationService;

    /***
     * ProjectInformation分页条件搜索实现
     * @param projectInformation
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "ProjectInformation条件分页查询",notes = "分页条件查询ProjectInformation方法详情",tags = {"ProjectInformationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "ProjectInformation对象",value = "传入JSON数据",required = false) ProjectInformation projectInformation, @PathVariable  int page, @PathVariable  int size) {
        //调用ProjectInformationService实现分页条件查询ProjectInformation
        PageInfo<ProjectInformation> pageInfo = projectInformationService.findPage(projectInformation, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * ProjectInformation分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "ProjectInformation分页查询",notes = "分页查询ProjectInformation方法详情",tags = {"ProjectInformationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size) {
        //调用ProjectInformationService实现分页查询ProjectInformation
        PageInfo<ProjectInformation> pageInfo = projectInformationService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索projectInformation数据
     * @param projectInformation
     * @return
     */
    @ApiOperation(value = "ProjectInformation条件查询",notes = "条件查询ProjectInformation方法详情",tags = {"ProjectInformationController"})
    @PostMapping(value = "/search" )
    public Result<List<ProjectInformation>> findList(@RequestBody(required = false) @ApiParam(name = "ProjectInformation对象",value = "传入JSON数据",required = false) ProjectInformation projectInformation) {
        //调用ProjectInformationService实现条件查询ProjectInformation
        List<ProjectInformation> list = projectInformationService.findList(projectInformation);
        return new Result<List<ProjectInformation>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除projectInformation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "ProjectInformation根据ID删除",notes = "根据ID删除ProjectInformation方法详情",tags = {"ProjectInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id) {
        //调用ProjectInformationService实现根据主键删除
        projectInformationService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改ProjectInformation数据
     * @param projectInformation
     * @param id
     * @return
     */
    @ApiOperation(value = "ProjectInformation根据ID修改",notes = "根据ID修改ProjectInformation方法详情",tags = {"ProjectInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "ProjectInformation对象",value = "传入JSON数据",required = false) ProjectInformation projectInformation,@PathVariable String id) {
        //设置主键值
        projectInformation.setProjId(id);
        //调用ProjectInformationService实现修改ProjectInformation
        projectInformationService.update(projectInformation);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增ProjectInformation数据
     * @param projectInformation
     * @return
     */
    @ApiOperation(value = "ProjectInformation添加",notes = "添加ProjectInformation方法详情",tags = {"ProjectInformationController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "ProjectInformation对象",value = "传入JSON数据",required = true) ProjectInformation projectInformation) {
        //调用ProjectInformationService实现添加ProjectInformation
        projectInformationService.add(projectInformation);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询ProjectInformation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "ProjectInformation根据ID查询",notes = "根据ID查询ProjectInformation方法详情",tags = {"ProjectInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @GetMapping("/{id}")
    public Result<ProjectInformation> findById(@PathVariable String id) {
        //调用ProjectInformationService实现根据主键查询ProjectInformation
        ProjectInformation projectInformation = projectInformationService.findById(id);
        return new Result<ProjectInformation>(true,StatusCode.OK,"查询成功",projectInformation);
    }

    /***
     * 查询ProjectInformation全部数据
     * @return
     */
    @ApiOperation(value = "查询所有ProjectInformation",notes = "查询所ProjectInformation有方法详情",tags = {"ProjectInformationController"})
    @GetMapping
    public Result<List<ProjectInformation>> findAll() {
        //调用ProjectInformationService实现查询所有ProjectInformation
        List<ProjectInformation> list = projectInformationService.findAll();
        return new Result<List<ProjectInformation>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
