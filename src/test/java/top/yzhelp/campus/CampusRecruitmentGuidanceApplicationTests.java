package top.yzhelp.campus;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.School;

import java.util.ArrayList;

@SpringBootTest
class CampusRecruitmentGuidanceApplicationTests {

  @Test
  void contextLoads() {
    ArrayList<String> strings = ListUtil.toList("123".split(","));
    System.out.println(strings);
    System.out.println(String.join(",",strings));
  }

  @Test
  void testSendMail() {
    School school = new School();
    school.setName("西南石油大学");
    EduInfo eduInfo = new EduInfo();
    eduInfo.setSchool(school);
    String today= DateUtil.today();
    String url = "localhost:8082/wx/edu/checkToken?edu_token_id=xxx";
    String mail = "gongsir0630@163.com";
    String content = "<p>亲爱的柚子帮同学:</p>"
      +"<p>你正在认证教育信息: "+eduInfo.getSchool().getName()+", 请确认邮箱地址以完成认证</p>"
      +"<p><a href='"+url+"'>点此确认邮箱地址</a></p>"
      +"<p>若链接点击无响应, 请复制以下链接在浏览器中打开, 验证后即可完成认证（<font color='red'>如非本人，请勿点击</font>）</p>"
      +"<a href='"+url+"'>"+url+"</a><br/><br/>"
      +"<p>西南石油大学-柚子帮</p>"
      +"<p>"+today+"</p>";
    MailUtil.send(mail,"柚子帮学校邮箱认证",content,true);
  }

}
