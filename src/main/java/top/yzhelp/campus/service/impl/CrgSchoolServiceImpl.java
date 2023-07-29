package top.yzhelp.campus.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.CrgSchoolMapper;
import top.yzhelp.campus.model.basic.CrgSchool;
import top.yzhelp.campus.service.CrgSchoolService;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2023/7/29
 */
@Lazy
@Service
public class CrgSchoolServiceImpl extends ServiceImpl<CrgSchoolMapper, CrgSchool>
    implements CrgSchoolService {
}
