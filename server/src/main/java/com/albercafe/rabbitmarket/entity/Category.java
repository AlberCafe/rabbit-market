package com.albercafe.rabbitmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "이름은 필수 값입니다.")
    private String name;

    private String icon;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> productList;
}
