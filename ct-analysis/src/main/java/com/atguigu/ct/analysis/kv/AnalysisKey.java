package com.atguigu.ct.analysis.kv;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AnalysisKey implements WritableComparable<AnalysisKey> {
    private String tel;
    private String date;

    public AnalysisKey() {

    }

    public AnalysisKey(String tel, String date) {
        this.tel = tel;
        this.date = date;
    }


    public int compareTo(AnalysisKey key) {
        int result = tel.compareTo(key.getTel());
        if(result == 0){
            result = date.compareTo(key.getDate());
        }
        return result;
    }


    public void write(DataOutput out) throws IOException {
        out.writeUTF(tel);
        out.writeUTF(date);
    }


    public void readFields(DataInput in) throws IOException {
        tel=in.readUTF();
        date=in.readUTF();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
