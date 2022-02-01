package com.atguigu.ct.common.bean;

public abstract class Data implements Val{
    public String content;
    @Override
    public void setValue(Object value){
        this.content= (String) value;
    };



    @Override
    public String getValue() {
        return this.content;
    }
}
