package com.cz.springbootneo4j.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Movie {

    @Id
    private Long id;

    private String name;
}
