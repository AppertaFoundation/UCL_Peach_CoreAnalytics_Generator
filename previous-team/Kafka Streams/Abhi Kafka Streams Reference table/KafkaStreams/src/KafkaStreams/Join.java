
package KafkaStreams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.log4j.BasicConfigurator;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Demonstrates how to perform a join between a KStream and a KTable, i.e. an
 * example of a stateful computation, using specific data types (here: JSON
 * POJO; but can also be Avro specific bindings, etc.) for serdes in Kafka
 * Streams.
 *
 * In this example, we join a stream of DATAEXAM that reads
 * from a topic named "DATAEXAM" with a REFERENCE table that
 * reads from a topic named "REFEXAM", where the data format
 * is JSON string representing a record in the stream or table, to compute joins.
 *
 * Before running this example you must create the source topics and  (e.g. via
 * bin/kafka-topics.sh --create ...) 
 */
public class Join {

	// abhi:POJO classes
	static public class DATAATTEND {
		public String ATTPKEY;
		public String ATTPSITE;
		public String BOOKDATE;
		public String CATEGORY;
		public String OUTHRS;
		public String PATKEY;
		public String PRIORITY;
		public String RECDATE;
		public String REFDOCTORKEY;
		public String REGGPKEY;
		public String REGGPRACKEY;
		public String SITEKEY;
		public String SORTDATE;
		public String SOURCEKEY;
		public String SPECIALITYKEY;
		public String STATUS;
		public String TRANSPORT;
		public String CANCELREASONKEY;
		public String CANCELUSERKEY;
		public String PATGROUP;
		public String REQUESTDATE;
		public String CONFIRMAPPT;
		public String ADDRKEY;
		public String CANCELDATE;
		public long timestamp;

	}

	static public class DATAEXAM {
		public String APPTEND;
		public String APPTROOMKEY;
		public String APPTSTART;
		public String ATTKEY;
		public String CANCELDATE;
		public String CANCELREASONKEY;
		public String CANCELUSERKEY;
		public String EXAMPKEY;
		public String EXDATE;
		public String LETTERPRINTED;
		public String LINKREFEXAMKEY;
		public String ORDERNUM;
		public String PREREPORTEDSTATUS;
		public String PRINTNEWAPPTEND;
		public String PRINTNEWAPPTSTART;
		public String PRINTOLDAPPTEND;
		public String PRINTOLDAPPTSTART;
		public String REFEXAMKEY;
		public String REFROOMKEY;
		public String SORTDATE;
		public String STATUS;
		public String PENDDATE;
		public String ORGAPPTDATE;
		public long timestamp;
		public String time;
	}

	static public class REFEXAM {
		public String REFEXAMKEY; // rename it to REFEXAMKEY from REFEXAMPKEY
		public String REFEXAMCODE;
		public String APPTEXAMTYPE;
		public String DESCRIP;
		public String EXAMDESCKEY;
		public String EXAMTIME;
		public String EXAMTYPEKEY;
		public String EXPOSURES;
		public String IMAGE;
		public String INACTIV;
		public String KCAT;
		public String KCODE;
		public String KH12MODKEY;
		public String KH12TYPE;
		public String LMPMESS;
		public String ORDINAL;
		public String PLABEL;
		public String PORTABLE;
		public String PRIORITY;
		public String REPORT;
		public String PASCODE;
		public String LMPSAFEDAYS;
		public String CONFIRMREQUIRED;
		public String MANDATORYKVP;
		public String MANDATORYMAS;
		public long timestamp;
		public String time;
	}

	static public class EXAMREF {
		public String APPTEND;
		public String APPTROOMKEY;
		public String APPTSTART;
		public String ATTKEY;
		public String CANCELDATE;
		public String CANCELREASONKEY;
		public String CANCELUSERKEY;
		public String EXAMPKEY;
		public String EXDATE;
		public String LETTERPRINTED;
		public String LINKREFEXAMKEY;
		public String ORDERNUM;
		public String PREREPORTEDSTATUS;
		public String PRINTNEWAPPTEND;
		public String PRINTNEWAPPTSTART;
		public String PRINTOLDAPPTEND;
		public String PRINTOLDAPPTSTART;
		public String REFEXAMKEY;
		public String REFROOMKEY;
		public String SORTDATE;
		public String STATUS;
		public String PENDDATE;
		public String ORGAPPTDATE;// last exam
		// public String REFEXAMPKEY;
		public String REFEXAMCODE;
		public String APPTEXAMTYPE;
		public String DESCRIP;
		public String EXAMDESCKEY;
		public String EXAMTIME;
		public String EXAMTYPEKEY;
		public String EXPOSURES;
		public String IMAGE;
		public String INACTIV;
		public String KCAT;
		public String KCODE;
		public String KH12MODKEY;
		public String KH12TYPE;
		public String LMPMESS;
		public String ORDINAL;
		public String PLABEL;
		public String PORTABLE;
		public String PRIORITY;
		public String REPORT;
		public String PASCODE;
		public String LMPSAFEDAYS;
		public String CONFIRMREQUIRED;
		public String MANDATORYKVP;
		public String MANDATORYMAS;
		public long timestamp;
		public String time;

	}

	static public class WindowedEXAMREF {
		public long windowStart;
		public String REFEXAMKEY;
	}

	static public class EXAMREFCount {
		public long count;
		public String REFEXAMKEY;
	}
	//abhi:main method definition
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KafkaStreams-Join");
		props.put(StreamsConfig.STATE_DIR_CONFIG, "streams-Join");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
		props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG,JsonTimestampExtractor.class);

		// setting offset reset to earliest so that we can re-run the demo code
		// with the same pre-loaded data
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		KStreamBuilder builder = new KStreamBuilder();

		// TODO: the following can be removed with a serialization factory
		Map<String, Object> serdeProps = new HashMap<>();

		final Serializer<REFEXAM> pageViewSerializer = new JsonPOJOSerializer<>();
		serdeProps.put("JsonPOJOClass", REFEXAM.class);
		pageViewSerializer.configure(serdeProps, false);

		final Deserializer<REFEXAM> pageViewDeserializer = new JsonPOJODeserializer<>();
		serdeProps.put("JsonPOJOClass", REFEXAM.class);
		pageViewDeserializer.configure(serdeProps, false);

		final Serde<REFEXAM> pageViewSerde = Serdes.serdeFrom(pageViewSerializer, pageViewDeserializer);

		final Serializer<DATAEXAM> userProfileSerializer = new JsonPOJOSerializer<>();
		serdeProps.put("JsonPOJOClass", DATAEXAM.class);
		userProfileSerializer.configure(serdeProps, false);

		final Deserializer<DATAEXAM> userProfileDeserializer = new JsonPOJODeserializer<>();
		serdeProps.put("JsonPOJOClass", DATAEXAM.class);
		userProfileDeserializer.configure(serdeProps, false);

		final Serde<DATAEXAM> userProfileSerde = Serdes.serdeFrom(userProfileSerializer, userProfileDeserializer);

		final Serializer<WindowedEXAMREF> wPageViewByRegionSerializer = new JsonPOJOSerializer<>();
		serdeProps.put("JsonPOJOClass", WindowedEXAMREF.class);
		wPageViewByRegionSerializer.configure(serdeProps, false);

		final Deserializer<WindowedEXAMREF> wPageViewByRegionDeserializer = new JsonPOJODeserializer<>();
		serdeProps.put("JsonPOJOClass", WindowedEXAMREF.class);
		wPageViewByRegionDeserializer.configure(serdeProps, false);

		@SuppressWarnings("unused")
		final Serde<WindowedEXAMREF> wPageViewByRegionSerde = Serdes.serdeFrom(wPageViewByRegionSerializer,
				wPageViewByRegionDeserializer);

		final Serializer<EXAMREFCount> regionCountSerializer = new JsonPOJOSerializer<>();
		serdeProps.put("JsonPOJOClass", EXAMREFCount.class);
		regionCountSerializer.configure(serdeProps, false);

		final Deserializer<EXAMREFCount> regionCountDeserializer = new JsonPOJODeserializer<>();
		serdeProps.put("JsonPOJOClass", EXAMREFCount.class);
		regionCountDeserializer.configure(serdeProps, false);
		@SuppressWarnings("unused")
		final Serde<EXAMREFCount> regionCountSerde = Serdes.serdeFrom(regionCountSerializer, regionCountDeserializer);

		final Serializer<EXAMREF> examProfileSerializer = new JsonPOJOSerializer<>();
		serdeProps.put("JsonPOJOClass", EXAMREF.class);
		userProfileSerializer.configure(serdeProps, false);

		final Deserializer<EXAMREF> examProfileDeserializer = new JsonPOJODeserializer<>();
		serdeProps.put("JsonPOJOClass", EXAMREF.class);
		userProfileDeserializer.configure(serdeProps, false);

		@SuppressWarnings("unused")
		final Serde<EXAMREF> examProfileSerde = Serdes.serdeFrom(examProfileSerializer, examProfileDeserializer);

		KStream<String, DATAEXAM> views = builder.stream(Serdes.String(), userProfileSerde, "DATAEXAM");

		KTable<String, REFEXAM> users = builder.table(Serdes.String(), pageViewSerde, "REFEXAM");

		System.out.println("Test printing");
		views.print();
		users.print();
		System.out.println("Stop printing");
		//abhi:perform left join
		KStream<String, EXAMREF> regionCount = views.leftJoin(users, new ValueJoiner<DATAEXAM, REFEXAM, EXAMREF>() {
			@Override
			public EXAMREF apply(DATAEXAM view, REFEXAM profile) {
				EXAMREF viewByRegion = new EXAMREF();
				// viewByRegion.APPTEND = view.APPTEND;
				// viewByRegion.APPTROOMKEY = view.APPTROOMKEY;
				// viewByRegion.APPTSTART = view.APPTSTART;
				// viewByRegion.CANCELDATE = view.CANCELDATE;
				// viewByRegion.CANCELREASONKEY = view.CANCELREASONKEY;
				// viewByRegion.CANCELUSERKEY = view.CANCELUSERKEY;
				// viewByRegion.EXAMPKEY = view.EXAMPKEY;
				// viewByRegion.EXDATE = view.EXDATE;
				// viewByRegion.LETTERPRINTED = view.LETTERPRINTED;
				// viewByRegion.LINKREFEXAMKEY= view.LINKREFEXAMKEY;
				// viewByRegion.ORDERNUM = view.ORDERNUM;
				System.out.println("Trying to do a join");
				// viewByRegion.APPTEND = "APPTEND is joined";
				// viewByRegion.timestamp = view.timestamp;
				// viewByRegion.KH12MODKEY = profile.KH12MODKEY;
				// viewByRegion.DESCRIP = profile.DESCRIP;
				// viewByRegion.EXAMTIME = profile.EXAMTIME;
				viewByRegion.EXAMPKEY = view.EXAMPKEY;
				viewByRegion.REFEXAMCODE = profile.REFEXAMCODE;

				viewByRegion.REFEXAMKEY = profile.REFEXAMKEY;
				return viewByRegion;
			}
		})

				// abhi:Map

				.map(new KeyValueMapper<String, EXAMREF, KeyValue<String, EXAMREF>>() {
					@Override
					public KeyValue<String, EXAMREF> apply(String user, EXAMREF viewRegion) {
						return new KeyValue<>(viewRegion.REFEXAMKEY, viewRegion);
					}
				});

		regionCount.to("EXAMREF");

		KafkaStreams streams = new KafkaStreams(builder, props);

		streams.start();

		// usually the stream application would be running forever,
		// in this example we just let it run for some time and stop since the
		// input data is finite.
		// Thread.sleep(5000L);

		// streams.close();
	}
}
