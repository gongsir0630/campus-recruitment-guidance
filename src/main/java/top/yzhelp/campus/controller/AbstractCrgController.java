package top.yzhelp.campus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.yzhelp.campus.shiro.ShiroRealm;

/**
 * @author gongtao <gongtao@kuaishou.com>
 * Created on 2023-10-03
 */
public abstract class AbstractCrgController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String getOpenId() {
        return ShiroRealm.getShiroAccount().getAuthName();
    }
}
