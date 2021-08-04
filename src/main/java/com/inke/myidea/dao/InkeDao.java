package com.inke.myidea.dao;

import com.inke.myidea.model.InkeListDo;

import java.util.List;

/**
 * 操控数据库 和 mapper中方法名一致
 */
public interface InkeDao {
    List<InkeListDo> listInke();

    void insertInke(String name);

    void updateInke(int id, String name);

    void deleteInke(int id);

}
