package cn.myh.tewsqu.test;

import cn.myh.twesqu.entity.Result;
import org.junit.Test;

public class Entitytest {

    @Test
    public void test1() {
        Result rs = new Result(true,200,"ddd","aaa");
        System.out.println(rs);
        System.out.println(rs.isFlag());
    }
}
