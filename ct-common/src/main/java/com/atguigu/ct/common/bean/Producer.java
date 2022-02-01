package com.atguigu.ct.common.bean;

import java.io.Closeable;

public interface Producer extends Closeable {

    public void setIn(DataIn in);
    public void setOut(DataOut out);
    //
//  produce data
    public void produce();
}
