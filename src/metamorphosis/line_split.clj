(ns metamorphosis.line-split
  "This is a fairly straight translation of the Line Split app at:
   http://kafka.apache.org/11/documentation/streams/tutorial."
  (:require [clojure.string :as str])
  (:import [java.util Properties]
           [org.apache.kafka.streams KafkaStreams StreamsBuilder StreamsConfig]
           [org.apache.kafka.streams.kstream ValueMapper]
           [org.apache.kafka.common.serialization Serdes]))

(def props
  (doto (new Properties)
    (.put StreamsConfig/APPLICATION_ID_CONFIG "streams-linesplit")
    (.put StreamsConfig/BOOTSTRAP_SERVERS_CONFIG "localhost:9092")
    (.put StreamsConfig/DEFAULT_KEY_SERDE_CLASS_CONFIG (class (Serdes/String)))
    (.put StreamsConfig/DEFAULT_VALUE_SERDE_CLASS_CONFIG (class (Serdes/String)))))

(def split-words
  "Value mapper to split text on whitespace. Consecutive spaces count as one."
  (reify ValueMapper
    (apply [this value] (str/split value #"\s+"))))

(def builder (new StreamsBuilder))
(-> builder
    (.stream "streams-plaintext-input")
    (.flatMapValues split-words)
    (.to "streams-linesplit-output"))
(def topology (.build builder))

(def streams (new KafkaStreams topology props))
