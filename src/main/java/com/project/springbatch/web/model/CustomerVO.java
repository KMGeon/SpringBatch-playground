package com.project.springbatch.web.model;//package com.project.springbatch.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerVO {
    private Long id;
    private String first_name;
    private String last_name;
    private String birthdate;
}
