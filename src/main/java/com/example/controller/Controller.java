package com.example.controller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import com.example.WordMapper;
import com.example.WordReducer;

public class Controller {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: WordCount <input path> <output path>");
            System.exit(-1);
        }

        // Create new job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        
        // Set job class
        job.setJarByClass(Controller.class);
        
        // Set Mapper and Reducer classes
        job.setMapperClass(WordMapper.class);
        job.setReducerClass(WordReducer.class);
        
        // Set output key and value classes
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        // Set input and output paths
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        // Submit job and exit
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}