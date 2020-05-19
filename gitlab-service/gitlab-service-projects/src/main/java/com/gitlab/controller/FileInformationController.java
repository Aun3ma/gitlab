package com.gitlab.controller;

import com.github.pagehelper.PageInfo;
import com.gitlab.projects.pojo.FileInformation;
import com.gitlab.service.FileInformationService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author:shenjunjie
 * @Description:FileInformationController构建
 * @Date:2020/04/23
 */
@Api(value = "FileInformationController")
@RestController
@RequestMapping("/projects/fileInformation")
@CrossOrigin
public class FileInformationController {

    @Autowired
    private FileInformationService fileInformationService;

    /***
     * FileInformation分页条件搜索实现
     * @param fileInformation
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "FileInformation条件分页查询",notes = "分页条件查询FileInformation方法详情",tags = {"FileInformationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "FileInformation对象",value = "传入JSON数据",required = false) FileInformation fileInformation, @PathVariable  int page, @PathVariable  int size) {
        //调用FileInformationService实现分页条件查询FileInformation
        PageInfo<FileInformation> pageInfo = fileInformationService.findPage(fileInformation, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * FileInformation分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "FileInformation分页查询",notes = "分页查询FileInformation方法详情",tags = {"FileInformationController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size) {
        //调用FileInformationService实现分页查询FileInformation
        PageInfo<FileInformation> pageInfo = fileInformationService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索fileInformation数据
     * @param fileInformation
     * @return
     */
    @ApiOperation(value = "FileInformation条件查询",notes = "条件查询FileInformation方法详情",tags = {"FileInformationController"})
    @PostMapping(value = "/search" )
    public Result<List<FileInformation>> findList(@RequestBody(required = false) @ApiParam(name = "FileInformation对象",value = "传入JSON数据",required = false) FileInformation fileInformation) {
        //调用FileInformationService实现条件查询FileInformation
        List<FileInformation> list = fileInformationService.findList(fileInformation);
        return new Result<List<FileInformation>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除fileInformation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "FileInformation根据ID删除",notes = "根据ID删除FileInformation方法详情",tags = {"FileInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id) {
        //调用FileInformationService实现根据主键删除
        fileInformationService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改FileInformation数据
     * @param fileInformation
     * @param id
     * @return
     */
    @ApiOperation(value = "FileInformation根据ID修改",notes = "根据ID修改FileInformation方法详情",tags = {"FileInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "FileInformation对象",value = "传入JSON数据",required = false) FileInformation fileInformation,@PathVariable String id) {
        //设置主键值
        fileInformation.setFileId(id);
        //调用FileInformationService实现修改FileInformation
        fileInformationService.update(fileInformation);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增FileInformation数据
     * @param fileInformation
     * @return
     */
    @ApiOperation(value = "FileInformation添加",notes = "添加FileInformation方法详情",tags = {"FileInformationController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "FileInformation对象",value = "传入JSON数据",required = true) FileInformation fileInformation) {
        //调用FileInformationService实现添加FileInformation
        fileInformationService.add(fileInformation);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询FileInformation数据
     * @param id
     * @return
     */
    @ApiOperation(value = "FileInformation根据ID查询",notes = "根据ID查询FileInformation方法详情",tags = {"FileInformationController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @GetMapping("/{id}")
    public Result<FileInformation> findById(@PathVariable String id) {
        //调用FileInformationService实现根据主键查询FileInformation
        FileInformation fileInformation = fileInformationService.findById(id);
        return new Result<FileInformation>(true,StatusCode.OK,"查询成功",fileInformation);
    }

    /***
     * 查询FileInformation全部数据
     * @return
     */
    @ApiOperation(value = "查询所有FileInformation",notes = "查询所FileInformation有方法详情",tags = {"FileInformationController"})
    @GetMapping
    public Result<List<FileInformation>> findAll() {
        //调用FileInformationService实现查询所有FileInformation
        List<FileInformation> list = fileInformationService.findAll();
        return new Result<List<FileInformation>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
