package com.atguigu.ct.common.bean;

import java.io.Closeable;

public interface Consumer extends Closeable {
    public void consume();
}
