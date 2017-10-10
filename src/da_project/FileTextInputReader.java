/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da_project;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 *
 * @author ayush
 */
public class FileTextInputReader extends RecordReader<Text, BytesWritable>{
    
    private FileSplit fileSplit;
    private Configuration config;
    private byte[] value;
    private boolean processed = false;

    @Override
    public void initialize(InputSplit is, TaskAttemptContext tac) throws IOException, InterruptedException {
        this.fileSplit = (FileSplit)is;
        this.config = tac.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if(!processed) {
            byte[] contents = new byte[(int) fileSplit.getLength()];
            Path file = fileSplit.getPath();
            FileSystem fs = file.getFileSystem(config);
            FSDataInputStream in = null;
            
            try {
                in = fs.open(file);
                IOUtils.readFully(in, contents, 0, contents.length);
                value = contents;
            }
            finally {
                IOUtils.closeStream(in);
            }
            processed = true;
            return true;
        }
        return false;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return new Text(this.fileSplit.getPath().getName());
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return new BytesWritable(this.value);
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return processed ? 1.0f : 0.0f;
    }

    @Override
    public void close() throws IOException {
        /* We don't need to do anything */
    }
    
}
