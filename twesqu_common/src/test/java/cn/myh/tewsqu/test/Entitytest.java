package cn.myh.tewsqu.test;

import cn.myh.twesqu.common.entity.Result;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entitytest {

    @Test
    public void test1() {
        Result rs = new Result(true,200,"ddd","aaa");
        System.out.println(rs);
        System.out.println(rs.isFlag());
    }

    @Test
    public void test2() {
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(null));
    }

    @Test
    public void test3() {
        System.out.println(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(new Date());
        System.out.println(format);
    }

}
