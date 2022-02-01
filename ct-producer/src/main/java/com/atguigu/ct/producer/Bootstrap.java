package com.atguigu.ct.producer;

import com.atguigu.ct.common.bean.Producer;
import com.atguigu.ct.producer.bean.LocalFileProducer;
import com.atguigu.ct.producer.io.LocalFileDataIn;
import com.atguigu.ct.producer.io.LocalFileDataOut;

public class Bootstrap {
    public static void main(String[] args) throws Exception {

        if (args.length < 2){
            System.out.println("Argument is wrong, Producer.jar path1 path2");
            System.exit(1);
        }
        Producer producer = new LocalFileProducer();
//        producer.setIn(new LocalFileDataIn("D:\\BaiduNetdiskDownload\\尚硅谷大数据学科全套教程（总185.88GB）\\3.尚硅谷大数据学科--项目实战\\尚硅谷大数据技术之电信客服综合案例\\2.资料\\辅助文档\\contact.log"));
//        producer.setOut(new LocalFileDataOut("D:\\BaiduNetdiskDownload\\尚硅谷大数据学科全套教程（总185.88GB）\\3.尚硅谷大数据学科--项目实战\\尚硅谷大数据技术之电信客服综合案例\\2.资料\\辅助文档\\call.log"));
        producer.setIn(new LocalFileDataIn(args[0]));
        producer.setOut(new LocalFileDataOut(args[1]));
        producer.produce();
        producer.close();
    }
}
