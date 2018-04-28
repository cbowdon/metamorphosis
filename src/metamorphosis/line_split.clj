(ns metamorphosis.line-split
  (:import [java.util Properties]
           [org.apache.kafka.streams KafkaStreams StreamsBuilder StreamsConfig]
           [org.apache.kafka.common.serialization Serdes]))

(def props
  (doto (new Properties)
    (.put StreamsConfig/APPLICATION_ID_CONFIG "streams-linesplit")
    (.put StreamsConfig/BOOTSTRAP_SERVERS_CONFIG "localhost:9092")
    (.put StreamsConfig/DEFAULT_KEY_SERDE_CLASS_CONFIG (class (Serdes/String)))
    (.put StreamsConfig/DEFAULT_VALUE_SERDE_CLASS_CONFIG (class (Serdes/String)))))

(def builder (new StreamsBuilder))
(-> builder
    (.stream "streams-plaintext-input")
    (.to "streams-linesplit-output"))
(def topology (.build builder))

(def streams (new KafkaStreams topology props))
