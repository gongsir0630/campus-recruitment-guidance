package top.yzhelp.campus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:40
 * 你的指尖,拥有改变世界的力量
 * @description 搜索记录
 */
@Data
@TableName("search_record")
public class SearchRecord {
    @TableId(type = IdType.AUTO)
    private long id;
    private String openId;
    private String keyword;
    private String subscribe;
    @TableField(exist = false)
    private Record record;

    @Data
    public static class Record {
        private String indexRec;
        private String ntRec;
        private String memberRec;
    }
}
