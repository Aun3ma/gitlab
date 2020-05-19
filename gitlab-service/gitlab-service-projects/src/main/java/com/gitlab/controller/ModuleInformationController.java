package com.gitlab.controller;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.ModuleInformation;
import com.gitlab.service.ModuleInformationService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author:shenjunjie
 * @Description:ModuleInformationController构建
 * @Date:2020/04/23
 */
@Api(value = "ModuleInformationController")
@RestController
@RequestMapping("/projects/moduleInformation")
@CrossOrigin
public class ModuleInformationController {

    @Autowired
    private ModuleInformationService moduleInformationService;

    /***
     * ModuleInformation分页条件搜索实现
     * @param moduleInformation
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "ModuleInformation条件分页查询",notes = "分页条件查询ModuleInformation方法详情",tags = {"ModuleInformationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "ModuleInformation对象",value = "传入JSON数据",required = false) ModuleInformation moduleInformation, @PathVariable  int page, @PathVariable  int size) {
        //调用ModuleInformationService实现分页条件查询ModuleInformation
        PageInfo<ModuleInformation> pageInfo = moduleInformationService.findPage(moduleInformation, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * ModuleInformation分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "ModuleInformation分页查询",notes = "分页查询ModuleInformation方法详情",tags = {"ModuleInformationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size) {
        //调用ModuleInformationService实现分页查询ModuleInformation
        PageInfo<ModuleInformation> pageInfo = moduleInformationService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索moduleInformation数据
     * @param moduleInformation
     * @return
     */
    @ApiOperation(value = "ModuleInformation条件查询",notes = "条件查询ModuleInformation方法详情",tags = {"ModuleInformationController"})
    @PostMapping(value = "/search" )
    public Result<List<ModuleInformation>> findList(@RequestBody(required = false) @ApiParam(name = "ModuleInformation对象",value = "传入JSON数据",required = false) ModuleInformation moduleInformation) {
        //调用ModuleInformationService实现条件查询ModuleInformation
        List<ModuleInformation> list = moduleInformationService.findList(moduleInformation);
        return new Result<List<ModuleInformation>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除moduleInformation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "ModuleInformation根据ID删除",notes = "根据ID删除ModuleInformation方法详情",tags = {"ModuleInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id) {
        //调用ModuleInformationService实现根据主键删除
        moduleInformationService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改ModuleInformation数据
     * @param moduleInformation
     * @param id
     * @return
     */
    @ApiOperation(value = "ModuleInformation根据ID修改",notes = "根据ID修改ModuleInformation方法详情",tags = {"ModuleInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "ModuleInformation对象",value = "传入JSON数据",required = false) ModuleInformation moduleInformation,@PathVariable String id) {
        //设置主键值
        moduleInformation.setModuleId(id);
        //调用ModuleInformationService实现修改ModuleInformation
        moduleInformationService.update(moduleInformation);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增ModuleInformation数据
     * @param moduleInformation
     * @return
     */
    @ApiOperation(value = "ModuleInformation添加",notes = "添加ModuleInformation方法详情",tags = {"ModuleInformationController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "ModuleInformation对象",value = "传入JSON数据",required = true) ModuleInformation moduleInformation) {
        //调用ModuleInformationService实现添加ModuleInformation
        moduleInformationService.add(moduleInformation);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询ModuleInformation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "ModuleInformation根据ID查询",notes = "根据ID查询ModuleInformation方法详情",tags = {"ModuleInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @GetMapping("/{id}")
    public Result<ModuleInformation> findById(@PathVariable String id) {
        //调用ModuleInformationService实现根据主键查询ModuleInformation
        ModuleInformation moduleInformation = moduleInformationService.findById(id);
        return new Result<ModuleInformation>(true,StatusCode.OK,"查询成功",moduleInformation);
    }

    /***
     * 查询ModuleInformation全部数据
     * @return
     */
    @ApiOperation(value = "查询所有ModuleInformation",notes = "查询所ModuleInformation有方法详情",tags = {"ModuleInformationController"})
    @GetMapping
    public Result<List<ModuleInformation>> findAll() {
        //调用ModuleInformationService实现查询所有ModuleInformation
        List<ModuleInformation> list = moduleInformationService.findAll();
        return new Result<List<ModuleInformation>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
