package cn.myh.twesqu.messervice;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

public class DemoTest {

    @Test
    public void  concatStr() {
//        "{number:789123}"
        int num = 345657;
        System.out.println("{number:" +num+ "}");
    }
}
