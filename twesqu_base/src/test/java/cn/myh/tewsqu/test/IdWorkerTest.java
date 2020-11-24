package cn.myh.tewsqu.test;

import cn.myh.twesqu.base.BaseApplication;
import cn.myh.twesqu.common.util.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes= BaseApplication.class)
public class IdWorkerTest {

    @Autowired
    private IdWorker idWorker;

    @Test
    public void test1() {
        System.out.println(idWorker.nextId());
    }
}
