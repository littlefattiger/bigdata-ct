package com.atguigu.ct.analysis.reducer;

import com.atguigu.ct.analysis.kv.AnalysisKey;
import com.atguigu.ct.analysis.kv.AnalysisValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class AnalysisBeanReducer extends Reducer<AnalysisKey, Text, AnalysisKey, AnalysisValue> {

    protected void reduce(AnalysisKey key, Iterable<Text> values, Reducer<AnalysisKey, Text, AnalysisKey, AnalysisValue>.Context context) throws IOException, InterruptedException {
        int sumCall = 0;
        int sumDuration = 0;

        for (Text value : values) {
            int duration = Integer.parseInt(value.toString());
            sumDuration = sumDuration + duration;
            sumCall++;
        }
        context.write(key, new AnalysisValue("" + sumCall, "" + sumDuration));
    }
}
