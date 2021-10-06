package com.inke.myidea.service.impl;

import com.inke.myidea.createfile.CreateFileUtils;
import com.inke.myidea.dao.InkeDao;
import com.inke.myidea.model.FileDo;
import com.inke.myidea.model.InkeListDo;
import com.inke.myidea.service.HttpResult;
import com.inke.myidea.service.InkeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;

@Service
public class InkeServiceImpl implements InkeService {
    @Autowired
    private InkeDao inkeDao;

    @Override
    public HttpResult<List<InkeListDo>> listInke() {
        HttpResult<List<InkeListDo>> httpResult = new HttpResult<>();
        httpResult.setCode(HttpResult.SUCCESS_CODE);
        httpResult.setMessage("获取映客人员列表成功");
        httpResult.setData(inkeDao.listInke());
        return httpResult;
    }

    @Override
    public HttpResult insertInke(String name) {
        HttpResult httpResult = new HttpResult<>();
        httpResult.setCode(HttpResult.SUCCESS_CODE);
        httpResult.setMessage("添加映客人员成功");
        inkeDao.insertInke(name);
        return httpResult;
    }

    @Override
    public HttpResult updateInke(int id, String name) {
        HttpResult httpResult = new HttpResult<>();
        httpResult.setCode(HttpResult.SUCCESS_CODE);
        httpResult.setMessage("修改映客人员成功");
        inkeDao.updateInke(id, name);
        return httpResult;
    }

    @Override
    public HttpResult deleteInke(int id) {
        HttpResult httpResult = new HttpResult<>();
        httpResult.setCode(HttpResult.SUCCESS_CODE);
        httpResult.setMessage("删除映客人员成功");
        inkeDao.deleteInke(id);
        return httpResult;
    }

    @Override
    public HttpResult<FileDo> createFileRequest(Map<String, String> map) {
        HttpResult<FileDo> httpResult = new HttpResult<>();
        httpResult.setCode(HttpResult.SUCCESS_CODE);
        httpResult.setMessage("创建文件信息成功");
        httpResult.setData(CreateFileUtils.createFile(map));
        return httpResult;
    }

    @Override
    public HttpResult<FileDo> uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(
                                "/Users/guanzhen/Desktop", "bigpng.jpg")));
                System.out.println(file.getName());
                out.write(file.getBytes());
                out.flush();
                out.close();
                HttpResult<FileDo> httpResult = new HttpResult<>();
                httpResult.setCode(HttpResult.SUCCESS_CODE);
                httpResult.setMessage("创建文件信息成功");
                FileDo fileDo = new FileDo();
                fileDo.setFileResult("文件大小" + file.getSize());
                httpResult.setData(fileDo);
                return httpResult;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
        return null;

    }

}
