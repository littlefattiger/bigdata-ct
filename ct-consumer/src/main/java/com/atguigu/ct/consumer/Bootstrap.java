package com.atguigu.ct.consumer;

import com.atguigu.ct.common.bean.Consumer;
import com.atguigu.ct.consumer.bean.CalllogConsumer;

public class Bootstrap {
    public static void main(String[] args) throws Exception{

        Consumer consumer = new CalllogConsumer();
        consumer.consume();
        consumer.close();
    }
}
