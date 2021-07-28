package com.albercafe.rabbitmarket.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotNull(message = "중고거래 제목은 필수 입니다.")
    private String title;

    @NotNull(message = "카테고리 선정은 필수 입니다.")
    private String categoryName;

    @NotBlank(message = "내용은 최소 한글자 이상이어야 합니다.")
    private String content;

    @Pattern(
            regexp = "^(0|[1-9]\\d*)$",
            message = "매물 값은 양의 정수만 가능합니다."
    )
    private String price;

    private List<String> photo;
}
