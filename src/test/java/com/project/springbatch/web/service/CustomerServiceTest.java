package com.project.springbatch.web.service;

import com.project.springbatch.web.model.CustomerVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void test() throws Exception{
        // given

        // when
        List<CustomerVO> customerVOS = customerService.selectCustomerList();

        // then
        System.out.println("customerVOS = " + customerVOS);
    }

}