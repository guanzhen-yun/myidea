package com.inke.myidea.service;

import com.inke.myidea.model.FileDo;
import com.inke.myidea.model.InkeListDo;

import java.util.List;
import java.util.Map;

public interface InkeService {
    HttpResult<List<InkeListDo>> listInke();

    HttpResult insertInke(String name);

    HttpResult updateInke(int id, String name);

    HttpResult deleteInke(int id);

    HttpResult<FileDo> createFileRequest(Map<String, String> map);
}
