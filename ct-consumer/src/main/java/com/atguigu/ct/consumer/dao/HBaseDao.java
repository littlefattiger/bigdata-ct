package com.atguigu.ct.consumer.dao;

import com.atguigu.ct.common.bean.BaseDao;
import com.atguigu.ct.common.constant.Names;
import com.atguigu.ct.common.constant.ValueConstant;
import com.atguigu.ct.consumer.bean.Calllog;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

public class HBaseDao extends BaseDao {
    public void init() throws Exception {

        start();

        createNamespaceNX(Names.NAMESPACE.getValue());
        createTableXX(Names.TABLE.getValue(), "com.atguigu.ct.consumer.coprocessor.InsertCalleeCoprocessor", ValueConstant.REGION_COUNT, Names.CF_CALLER.getValue(), Names.CF_CALLEE.getValue());
        end();
    }

    public void insertData(Calllog log) throws Exception {
        log.setRowkey(genRegionNum(log.getCall1(), log.getCalltime()) + "_" + log.getCall1() + "_"
                + log.getCalltime() + "_" + log.getCall2() + "_" + log.getDuration());


        putData(log);
    }

    /**
     * insert data
     *
     * @param value
     */
    public void insertData(String value) throws Exception {
        String[] values = value.split("\t");
        String call1 = values[0];
        String call2 = values[1];
        String calltime = values[2];
        String duration = values[3];
        String rowkey = genRegionNum(call1, calltime) + "_" + call1 + "_" + calltime + "_" + call2 + "_" + duration + "_1";
        Put put = new Put(Bytes.toBytes(rowkey));

        byte[] family = Bytes.toBytes(Names.CF_CALLER.getValue());
        put.addColumn(family, Bytes.toBytes("call1"), Bytes.toBytes(call1));
        put.addColumn(family, Bytes.toBytes("call2"), Bytes.toBytes(call2));
        put.addColumn(family, Bytes.toBytes("calltime"), Bytes.toBytes(calltime));
        put.addColumn(family, Bytes.toBytes("duration"), Bytes.toBytes(duration));
        put.addColumn(family, Bytes.toBytes("flg"), Bytes.toBytes("1"));

//        String calleeRowkey = genRegionNum(call2, calltime) + "_" + call2 + "_" + calltime + "_" + call1 + "_" + duration + "_0";
//        Put calleePut = new Put(Bytes.toBytes(calleeRowkey));
//        byte[] calleeFamily = Bytes.toBytes(Names.CF_CALLEE.getValue());
//        calleePut.addColumn(calleeFamily, Bytes.toBytes("call1"), Bytes.toBytes(call2));
//        calleePut.addColumn(calleeFamily, Bytes.toBytes("call2"), Bytes.toBytes(call1));
//        calleePut.addColumn(calleeFamily, Bytes.toBytes("calltime"), Bytes.toBytes(calltime));
//        calleePut.addColumn(calleeFamily, Bytes.toBytes("duration"), Bytes.toBytes(duration));
//        calleePut.addColumn(calleeFamily, Bytes.toBytes("flg"), Bytes.toBytes("0"));

        List<Put> puts = new ArrayList<Put>();
        puts.add(put);
//        puts.add(calleePut);
        putData(Names.TABLE.getValue(), puts);

    }

}
