package com.project.springbatch.a_practice.dao;

import com.project.springbatch.web.model.CustomerVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    public ProductDAO(@Qualifier("sqlSessionTemplate")SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


}
