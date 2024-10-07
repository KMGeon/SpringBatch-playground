package com.project.springbatch.web.service;

import com.project.springbatch.web.dao.CustomerDAO;
import com.project.springbatch.web.model.CustomerVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    public List<CustomerVO> selectCustomerList() {
        return customerDAO.findCustomerList();
    }

}
