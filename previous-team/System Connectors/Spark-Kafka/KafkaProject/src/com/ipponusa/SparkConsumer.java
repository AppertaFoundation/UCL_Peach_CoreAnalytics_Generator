package com.ipponusa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import scala.Tuple2;
import kafka.serializer.StringDecoder;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka.KafkaUtils;

import org.apache.spark.streaming.Durations;

/**
 * Usage: SparkConsume <brokers> <topics>
 *   <brokers> is a list of one or more Kafka brokers
 *   <topics> is a list of one or more kafka topics to consume from
 *
 * Example:
 *    $ bin/run-example streaming.JavaDirectKafkaWordCount broker1-host:port,broker2-host:port \
 *      topic1,topic2
 */

public final class SparkConsumer {
	private static final Pattern SPACE = Pattern.compile(" ");

	@SuppressWarnings({ "serial" })
	public static void main(String[] args) throws Exception {
		String brokers = "localhost:9093";
		String topics = "DATAEXAM";

		// Create context with a x seconds batch interval
		SparkConf sparkConf = 
				new SparkConf().setAppName("PeachSpark")
				.setMaster("local");
		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(1));

		Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
		
		
		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("bootstrap.servers", brokers);
		kafkaParams.put("auto.offset.reset", "smallest");

		// Create direct kafka stream with brokers and topics
		JavaPairInputDStream<String, String> messages = KafkaUtils.createDirectStream(
				jssc,
				String.class,
				String.class,
				StringDecoder.class,
				StringDecoder.class,
				kafkaParams,
				topicsSet
				);

//		System.out.println("--------------------------------------------");
//		System.out.println(messages.count());
//		System.out.println("--------------------------------------------");
		messages.foreachRDD(new VoidFunction<JavaPairRDD<String, String>>(){
		
			@Override
			public void call(JavaPairRDD<String, String> pairRDD) throws Exception {
				pairRDD.foreach(new VoidFunction<Tuple2<String, String>>(){
					@Override
					public void call(Tuple2<String, String> tuple) throws Exception {
						System.out.println("--------------------------------------------");
						System.out.println("key: " + tuple._1() + ", value: " + tuple._2());
						System.out.println("--------------------------------------------"+pairRDD.count());
					}		
				});	
				
			}	
		});
		
		
		// Start the computation
		jssc.start();
		jssc.awaitTermination();
	}
}















