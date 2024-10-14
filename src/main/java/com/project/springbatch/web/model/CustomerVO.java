package com.project.springbatch.web.model;//package com.project.springbatch.domain;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVO {
    private Long id;
    private String first_name;
    private String last_name;
    private String birthdate;

    public CustomerVO(String first_name) {
        this.first_name = first_name;
    }

    public CustomerVO(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
