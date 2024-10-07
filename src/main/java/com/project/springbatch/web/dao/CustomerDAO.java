package com.project.springbatch.web.dao;

import com.project.springbatch.web.model.CustomerVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    public CustomerDAO(@Qualifier("sqlSessionTemplate")SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<CustomerVO> findCustomerList() {
        Map<String, Object> map = new HashMap<>();
        return sqlSessionTemplate.selectList("customer.findCustomerPaging", map);
    }

}
