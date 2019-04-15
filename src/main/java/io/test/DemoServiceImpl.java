package io.test;

/**
 * Creat by GuoJF on mac
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public Integer sum(Integer x, Integer y) {
        System.out.println("sum");
        return x + y;
    }
}
