package com.ipponusa;

import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Usage: SparkConsume <brokers> <topics>
 *   <brokers> is a list of one or more Kafka brokers
 *   <topics> is a list of one or more kafka topics to consume from
 *
 * Example:
 *    $ bin/run-example streaming.JavaDirectKafkaWordCount broker1-host:port,broker2-host:port \
 *      topic1,topic2
 */

@SuppressWarnings("unused")
public final class SparkConsumer {
	private static final Pattern SPACE = Pattern.compile(" ");

	@SuppressWarnings({ "serial" })
	public static void main(String[] args) throws Exception {
		String brokers = "localhost:9092";
		String topics = "DATACONFIRM";

		// abhi:Create context with a x seconds batch interval
		SparkConf sparkConf = 
				new SparkConf().setAppName("PeachSpark")
				.setMaster("local");
		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(1));

		Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
		
		
		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("bootstrap.servers", brokers);
		kafkaParams.put("auto.offset.reset", "smallest");

		// abhi:Create direct kafka stream with brokers and topics
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
		//abhi:topics passed as RDD
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
			
		// abhi:Start the computation
		jssc.start();
		jssc.awaitTermination();
	}
}


