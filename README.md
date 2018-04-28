# metamorphosis

This is a fairly straight translation of the Kafka Streams tutorial at: http://kafka.apache.org/11/documentation/streams/tutorial.

## Installation

- Requires Kafka 1.1.0 and Lein

## Usage

```
cd /path/to/kafka
# If you care about the output, start these in diff terminals
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-plaintext-input &
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-pipe-output --config cleanup.policy=compact &
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input <<EOF
hello
is it me
you're looking for?
EOF
cd -
lein trampoline run

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic streams-pipe-output \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```

## License

Copyright © 2018 Chris Bowdon

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
