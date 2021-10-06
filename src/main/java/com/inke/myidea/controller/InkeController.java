package com.inke.myidea.controller;

import com.inke.myidea.model.FileDo;
import com.inke.myidea.model.InkeListDo;
import com.inke.myidea.service.HttpResult;
import com.inke.myidea.service.InkeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class InkeController {
    @Autowired
    private InkeService inkeService;

    @ApiOperation(value = "查询映客人员列表操作")
    @GetMapping("/inke/personList")
    public HttpResult<List<InkeListDo>> listInkePersonList() {
        return inkeService.listInke();
    }

    @ApiOperation(value = "添加映客人员操作")
    @PostMapping("/inke/insertPerson")
    public HttpResult insertInke(String name) {
        return inkeService.insertInke(name);
    }

    @ApiOperation(value = "修改映客人员操作")
    @PostMapping("/inke/updatePerson")
    public HttpResult updatePerson(int id, String name) {
        return inkeService.updateInke(id, name);
    }

    @ApiOperation(value = "删除映客人员操作")
    @DeleteMapping("/inke/deletePerson")
    public HttpResult deletePerson(int id) {
        return inkeService.deleteInke(id);
    }

    @ApiOperation(value = "获取文件资源")
    @GetMapping("/inke/createFileRequest")
    public HttpResult<FileDo> createFileRequest(String classPath, String className) {
        Map<String, String> map = new HashMap<>();
        map.put("classPath", classPath);
        map.put("className", className);
        return inkeService.createFileRequest(map);
    }

    @ApiOperation(value = "上传文件")
    @PostMapping("/inke/upload")
    public HttpResult<FileDo> uploadFile(@RequestParam("file") MultipartFile file) {
        return inkeService.uploadFile(file);
    }
}
