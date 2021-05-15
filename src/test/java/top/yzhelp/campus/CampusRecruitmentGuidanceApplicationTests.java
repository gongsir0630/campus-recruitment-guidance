package top.yzhelp.campus;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yzhelp.campus.controller.wx.vo.Constants;
import top.yzhelp.campus.model.other.SearchRecord;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.School;
import top.yzhelp.campus.wx.service.WxService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CampusRecruitmentGuidanceApplicationTests {

  @Autowired
  private WxService wxService;

  @Test
  void testUpdate() {
    SearchRecord record = new SearchRecord();
    record.setOpenId("201731061426");
    update(record,"index","index1,index2,index3");
    update(record,"nt","nt1,nt2,nt3");
    update(record,"member","mem1,mem2,mem3");
    update(record,"member","4,5,6");
  }

  void update(SearchRecord mySearch,String key,String val) {
    SearchRecord.Record record;
    if (StrUtil.isBlank(mySearch.getKeyword())) {
      record = new SearchRecord.Record();
    } else {
      record = JSONObject.parseObject(mySearch.getKeyword(), SearchRecord.Record.class);
    }
    switch (key) {
      case "index":
        record.setIndexRec(val);
        break;
      case "nt":
        record.setNtRec(val);
        break;
      case "member":
        record.setMemberRec(val);
        break;
      default:
        break;
    }
    mySearch.setKeyword(JSONObject.toJSONString(record));
    System.out.println(mySearch);
  }

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

  @Test
  void sendSubMsgTest() {
    WxMaSubscribeMessage message = new WxMaSubscribeMessage();
    // 消息内容
    List<WxMaSubscribeMessage.Data> data = new ArrayList<>();
    // 认证类型
    data.add(new WxMaSubscribeMessage.Data("thing1","教育信息认证:西南石油大学"));
    // 认证结果
    data.add(new WxMaSubscribeMessage.Data("phrase2","认证通过"));
    // 认证时间
    data.add(new WxMaSubscribeMessage.Data("date3",DateUtil.today()));
    // 接收人
    message.setToUser("olAW-4vIdX8DTkzftHveDWIlR4zU");
    // 模板ID
    message.setTemplateId(Constants.INFO_AUTH_ID);
    message.setData(data);
    wxService.sendSubMsg(message);
  }

}
