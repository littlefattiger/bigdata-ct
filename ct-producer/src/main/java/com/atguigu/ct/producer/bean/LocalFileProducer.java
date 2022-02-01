package com.atguigu.ct.producer.bean;

import com.atguigu.ct.common.bean.DataIn;
import com.atguigu.ct.common.bean.DataOut;
import com.atguigu.ct.common.bean.Producer;
import com.atguigu.ct.common.util.DateUtil;
import com.atguigu.ct.common.util.NumberUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LocalFileProducer implements Producer {
    private DataIn in;
    private DataOut out;
    private volatile boolean flg = true;

    @Override
    public void setIn(DataIn in) {
        this.in = in;
    }

    @Override
    public void setOut(DataOut out) {
        this.out = out;
    }

    @Override
    public void produce() {

        try {
            List<Contact> contacts = in.read(Contact.class);
//            for (Contact contact : contacts) {
//                System.out.println(contact);
//            }
            while (flg) {
                int call1Index = new Random().nextInt(contacts.size());
                int call2Index;

                while (true) {
                    call2Index = new Random().nextInt(contacts.size());
                    if (call1Index != call2Index) {
                        break;
                    }
                }

                Contact call1 = contacts.get(call1Index);
                Contact call2 = contacts.get(call2Index);
                String startDate = "20180101000000";
                String endDate = "20190101000000";
                long startTime = DateUtil.parse(startDate, "yyyyMMddHHmmss").getTime();
                long endTime = DateUtil.parse(endDate, "yyyyMMddHHmmss").getTime();

                long calltime = startTime + (long) ((endTime - startTime) * Math.random());
                String callTimeString = DateUtil.format(new Date(calltime), "yyyyMMddHHmmss");

                String duration = NumberUtil.format(new Random().nextInt(3000), 4);
                Calllog log = new Calllog(call1.getTel(), call2.getTel(), callTimeString, duration);

                System.out.println(log);
                out.write(log);
                Thread.sleep(500);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void close() throws IOException {

        if (in != null) {
            in.close();
        }

        if (out != null) {
            out.close();
        }
    }
}
