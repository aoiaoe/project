package com.geekbang.rpc.demo.server;

import com.geekbang.rpc.demo.client.CalculatorService;

public class CalculatorServiceImpl implements CalculatorService {

  @Override
  public int add(int a, int b) {
    return a + b;
  }
}
