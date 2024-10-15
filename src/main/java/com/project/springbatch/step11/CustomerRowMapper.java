package com.project.springbatch.step11;

import com.project.springbatch.domain.Customer;
import com.project.springbatch.web.model.CustomerVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<CustomerVO> {
    @Override
    public CustomerVO mapRow(ResultSet rs, int i) throws SQLException {
        return new CustomerVO(rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("birthdate"));
    }
}
