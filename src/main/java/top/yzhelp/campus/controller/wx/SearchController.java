package top.yzhelp.campus.controller.wx;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.controller.AbstractCrgController;
import top.yzhelp.campus.model.SearchRecord;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.service.SearchRecordService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/26 11:49
 * 你的指尖,拥有改变世界的力量
 * @description 搜索记录相关接口
 */
@Slf4j
@RestController
@RequestMapping("wx/history")
@Api(tags = "MINIAPP-搜索记录相关接口")
public class SearchController extends AbstractCrgController {

    @Resource
    private SearchRecordService recordService;

    @PostMapping
    @ApiOperation("新增、更新历史记录")
    public ResponseEntity<Message<?>> saveOrUpdateHistory(String key, String val) {
        SearchRecord mySearch = this.recordService.getById(getOpenId());
        if (mySearch == null) {
            mySearch = new SearchRecord();
            mySearch.setOpenId(getOpenId());
        }
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
                mySearch.setSubscribe(val);
                break;
        }
        mySearch.setKeyword(JSONObject.toJSONString(record));
        this.recordService.saveOrUpdate(mySearch);
        return ResponseEntity.ok(Message.success(null));
    }

    @GetMapping
    @ApiOperation("我的历史记录")
    public ResponseEntity<Message<?>> getMyHistory() {
        SearchRecord history = this.recordService.getById(getOpenId());
        if (history == null) {
            history = new SearchRecord();
            history.setOpenId(getOpenId());
            SearchRecord.Record record = new SearchRecord.Record();
            history.setKeyword(JSONObject.toJSONString(record));
        }
        history.setRecord(JSONObject.parseObject(history.getKeyword(), SearchRecord.Record.class));
        return ResponseEntity.ok(Message.success(history));
    }
}
