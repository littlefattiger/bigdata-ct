package com.atguigu.ct.common.constant;

import com.atguigu.ct.common.bean.Val;

/**
 * Name enum class
 */
public enum Names implements Val {
    NAMESPACE("ct")
    , TABLE("ct:calllog")
    , CF_CALLER("caller")
    , CF_CALLEE("callee")
    , CF_INFO("info")
    , TOPIC("ct");

    private String name;

    private Names(String name) {
        this.name = name;
    }


    @Override
    public void setValue(Object value) {
        this.name = (String) value;
    }

    @Override
    public String getValue() {
        return this.name;
    }
}
