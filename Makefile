KAFKA_HOME = ~/kafka/kafka_2.11-1.1.0/

zookeeper:
	cd $(KAFKA_HOME) && bin/zookeeper-server-start.sh config/zookeeper.properties

kafka:
	cd $(KAFKA_HOME) && bin/kafka-server-start.sh config/server.properties

topics:
	cd $(KAFKA_HOME) && bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-plaintext-input &
	cd $(KAFKA_HOME) && bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-pipe-output --config cleanup.policy=compact &
	cd $(KAFKA_HOME) && bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-linesplit-output --config cleanup.policy=compact &
	cd $(KAFKA_HOME) && bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-wordcount-output --config cleanup.policy=compact &

producer:
	cd $(KAFKA_HOME) && bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input

consumer-pipe:
	cd $(KAFKA_HOME) && bin/kafka-console-consumer.sh \
						--bootstrap-server localhost:9092 \
						--topic streams-pipe-output \
						--from-beginning \
						--formatter kafka.tools.DefaultMessageFormatter \
						--property print.key=true \
						--property print.value=true \
						--property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
						--property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer

consumer-linesplit:
	cd $(KAFKA_HOME) && bin/kafka-console-consumer.sh \
						--bootstrap-server localhost:9092 \
						--topic streams-linesplit-output \
						--from-beginning \
						--formatter kafka.tools.DefaultMessageFormatter \
						--property print.key=true \
						--property print.value=true \
						--property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
						--property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer

consumer-wordcount:
	cd $(KAFKA_HOME) && bin/kafka-console-consumer.sh \
						--bootstrap-server localhost:9092 \
						--topic streams-wordcount-output \
						--from-beginning \
						--formatter kafka.tools.DefaultMessageFormatter \
						--property print.key=true \
						--property print.value=true \
						--property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
						--property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
