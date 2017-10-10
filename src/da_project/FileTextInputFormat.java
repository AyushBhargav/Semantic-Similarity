/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da_project;

import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 *
 * @author ayush
 */
public class FileTextInputFormat extends FileInputFormat<Text, BytesWritable> {
    
    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        return false;
    }

    @Override
    public RecordReader<Text, BytesWritable> 
        createRecordReader(InputSplit split, TaskAttemptContext context) 
            throws IOException, InterruptedException {
        FileTextInputReader reader = new FileTextInputReader();
        reader.initialize(split, context);
        return reader;
    }
}
