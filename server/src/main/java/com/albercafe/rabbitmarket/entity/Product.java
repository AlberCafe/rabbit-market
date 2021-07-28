package com.albercafe.rabbitmarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @Column(name = "product_photo")
    private List<String> photo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @NotNull(message = "중고거래 제목은 필수 입니다.")
    private String title;

    @CreatedDate
    private OffsetDateTime createdDate;

    @Lob
    @Column(name = "content", length = 1024)
    @NotBlank(message = "내용은 최소 한글자 이상이어야 합니다.")
    private String content;

    @Pattern(
            regexp = "^(0|[1-9]\\d*)$",
            message = "매물 값은 양의 정수만 가능합니다."
    )
    private String price;
}
