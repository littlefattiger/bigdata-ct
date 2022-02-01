package com.atguigu.ct.consumer.coprocessor;

import com.atguigu.ct.common.bean.BaseDao;
import com.atguigu.ct.common.constant.Names;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessor;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.coprocessor.RegionObserver;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.wal.WALEdit;


import java.io.IOException;
import java.util.Optional;

public class InsertCalleeCoprocessor   implements RegionObserver, RegionCoprocessor {

    @Override
    public Optional<RegionObserver> getRegionObserver() {
        return RegionCoprocessor.super.getRegionObserver();
    }

    /**
     * @param c
     * @param put
     * @param edit
     * @param durability
     * @throws IOException
     */


    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> c, Put put, WALEdit edit, Durability durability) throws IOException {



        CoprocessprDao dao = new CoprocessprDao();
        Table table = c.getEnvironment().getConnection().getTable(TableName.valueOf(Names.TABLE.getValue()));
        String rowkey = Bytes.toString(put.getRow());
        String[] values = rowkey.split("_");

        String call1 = values[1];
        String call2 = values[3];
        String calltime = values[2];
        String duration = values[4];
        String flg = values[5];
        if ("1".equals(flg)) {
            String calleeRowkey = dao.getRegionNum(call2, calltime) + "_" + call2 + "_" + calltime + "_" + call1 + "_" + duration + "_0";

            Put calleePut = new Put(Bytes.toBytes(calleeRowkey));
            byte[] calleeFamily = Bytes.toBytes(Names.CF_CALLEE.getValue());
            calleePut.addColumn(calleeFamily, Bytes.toBytes("call1"), Bytes.toBytes(call2));
            calleePut.addColumn(calleeFamily, Bytes.toBytes("call2"), Bytes.toBytes(call1));
            calleePut.addColumn(calleeFamily, Bytes.toBytes("calltime"), Bytes.toBytes(calltime));
            calleePut.addColumn(calleeFamily, Bytes.toBytes("duration"), Bytes.toBytes(duration));
            calleePut.addColumn(calleeFamily, Bytes.toBytes("flg"), Bytes.toBytes(flg));
            table.put(calleePut);
            table.close();
        }
    }

    private class CoprocessprDao extends BaseDao {
        public int getRegionNum(String tel, String time) {
            return genRegionNum(tel, time);
        }
    }


}
