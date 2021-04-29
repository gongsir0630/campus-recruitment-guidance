package top.yzhelp.campus;

import cn.hutool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
class CampusRecruitmentGuidanceApplicationTests {

  @Test
  void contextLoads() {
    ArrayList<String> strings = ListUtil.toList("123".split(","));
    System.out.println(strings);
    System.out.println(String.join(",",strings));
  }

}
