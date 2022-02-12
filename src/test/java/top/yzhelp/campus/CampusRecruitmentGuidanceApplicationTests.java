package top.yzhelp.campus;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CampusRecruitmentGuidanceApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void testHello() {
        logger.info("hello world");
    }
}
