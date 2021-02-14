package com.cz.springcloud.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserEntityMix extends User {

    private List<Entity> entities;
}
