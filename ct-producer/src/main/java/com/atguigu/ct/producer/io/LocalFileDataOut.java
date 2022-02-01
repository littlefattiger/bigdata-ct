package com.atguigu.ct.producer.io;

import com.atguigu.ct.common.bean.DataOut;

import java.io.*;

public class LocalFileDataOut implements DataOut {
    private PrintWriter writer = null;
    private String path;

    public LocalFileDataOut(String path) {
        setPath(path);
    }

    @Override
    public void setPath(String path) {

        try {
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void write(Object data) throws Exception {
        write(data.toString());
    }

    @Override
    public void write(String data) throws Exception {
        writer.println(data);
        writer.flush();
    }

    @Override
    public void close() throws IOException {

        if (writer != null){
            writer.close();
        }
    }
}
