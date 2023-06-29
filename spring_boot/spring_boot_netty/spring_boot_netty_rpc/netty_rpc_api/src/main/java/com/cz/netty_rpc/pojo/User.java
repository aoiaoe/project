package com.cz.netty_rpc.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private int id;

    private String name;
}
