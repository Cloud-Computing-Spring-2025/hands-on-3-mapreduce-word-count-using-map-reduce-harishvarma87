package com.example;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) 
            throws IOException, InterruptedException {
        // Convert the line to string
        String line = value.toString();
        
        // Split the line into words
        String[] words = line.split("\\s+");
        
        // Emit each word with count 1
        for (String str : words) {
            if (str.length() > 0) {
                word.set(str.toLowerCase());
                context.write(word, one);
            }
        }
    }
}