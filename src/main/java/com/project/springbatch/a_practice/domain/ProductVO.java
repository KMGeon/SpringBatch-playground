package com.project.springbatch.a_practice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private Long id;
    private String name;
    private Integer price;
    private String type;
}
