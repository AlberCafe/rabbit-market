package com.albercafe.rabbitmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;

    private String title;

    private String price;

    private String content;

    private String categoryName;

    private List<String> photo;
}
