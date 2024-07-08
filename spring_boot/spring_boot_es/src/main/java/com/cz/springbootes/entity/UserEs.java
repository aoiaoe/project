package com.cz.springbootes.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Document(indexName = "user_spring_boot_jpa", createIndex = true)
public class UserEs {

    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Integer)
    private Integer age;
    @Field(type = FieldType.Keyword)
    private String sex;

    // 本字段使用的ik_max_word分词
    // 如果在查询的时候，查询条件未指定分词器，那么默认会使用本字段的分词器
    @Field(type = FieldType.Text, index = true, analyzer = "ik_max_word")
    private String description;

}
