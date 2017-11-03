/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da_project;

/**
 *
 * @author ayush
 */
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class DA_Project {

    public static void main(String [] args) throws Exception {

        Configuration c=new Configuration();
        String[] files=new GenericOptionsParser(c,args).getRemainingArgs();
        Path input=new Path(files[0]);
        Path output=new Path(files[1]);
        Job j=new Job(c,"semanticSimilarity");
        j.setJarByClass(DA_Project.class);
        j.setMapperClass(SemanticMapper.class);
        j.setReducerClass(SemanticReducer.class);
        j.setOutputKeyClass(LongWritable.class);
        j.setOutputValueClass(Text.class);
        FileTextInputFormat.addInputPath(j, input);
        FileOutputFormat.setOutputPath(j, output);
        System.exit(j.waitForCompletion(true)?0:1);
    }

    public static class SemanticMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

        public void map(LongWritable key, Text value, Context con) throws IOException, InterruptedException {

            String text = value.toString();
            String[] words= text.split(" ");
            StopWords stopWords = new StopWords();
            for(String word: words ) {
                if(stopWords.isStopWord(word))
                    continue;
                word = word.replaceAll("\\W+","");
                if(word.equals(""))
                    continue;
                LongWritable outputKey = key;
                Text outputValue = new Text(word.toLowerCase().trim());
                con.write(outputKey, outputValue);
            }
        }
    }

    public static class SemanticReducer extends Reducer<LongWritable, Text, Text, Text> {

        public void reduce(LongWritable docid, Iterable<Text> values, Context con) throws IOException, InterruptedException {
        
            String tokens = "";
            for(Text value : values) {
                tokens += value.toString() + ",";
            }
            con.write(new Text(String.valueOf(docid.get())), new Text(tokens));

        }

    }
}
