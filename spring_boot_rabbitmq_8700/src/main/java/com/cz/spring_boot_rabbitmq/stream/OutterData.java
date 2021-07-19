package com.cz.spring_boot_rabbitmq.stream;

import lombok.Builder;

import java.io.Serializable;

@lombok.Data
public class OutterData implements Serializable {

    private Object data;

    private String uuid;
}
