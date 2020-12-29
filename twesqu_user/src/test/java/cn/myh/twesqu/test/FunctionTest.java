package cn.myh.twesqu.test;

import org.junit.Test;

public class FunctionTest {

    @Test
    public void testException() {
        System.out.println("开始");
        try {
            int a = 1/0;
        } catch (Exception e) {
            System.out.println("捕获异常");
            e.printStackTrace();
        }
        System.out.println("结束");
    }
}
