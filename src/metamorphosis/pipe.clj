(ns metamorphosis.pipe
  "This is a fairly straight translation of the Pipe app at:
   http://kafka.apache.org/11/documentation/streams/tutorial."
  (:import [java.util Properties]
           [org.apache.kafka.streams KafkaStreams StreamsBuilder StreamsConfig]
           [org.apache.kafka.common.serialization Serdes])
  (:gen-class))

(def props
  (doto (new Properties)
    (.put StreamsConfig/APPLICATION_ID_CONFIG "streams-pipe")
    (.put StreamsConfig/BOOTSTRAP_SERVERS_CONFIG "localhost:9092")
    (.put StreamsConfig/DEFAULT_KEY_SERDE_CLASS_CONFIG (class (Serdes/String)))
    (.put StreamsConfig/DEFAULT_VALUE_SERDE_CLASS_CONFIG (class (Serdes/String)))))

(def builder (new StreamsBuilder))
(-> builder
    (.stream "streams-plaintext-input")
    (.to "streams-pipe-output"))
(def topology (.build builder))

(def streams (new KafkaStreams topology props))
