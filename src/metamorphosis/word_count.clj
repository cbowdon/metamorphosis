(ns metamorphosis.word-count
  "This is a fairly straight translation of the Word Count app at:
   http://kafka.apache.org/11/documentation/streams/tutorial."
  (:require [clojure.string :as str]
            [metamorphosis.config :refer [get-props]])
  (:import [org.apache.kafka.streams KafkaStreams StreamsBuilder]
           [org.apache.kafka.streams.kstream ValueMapper KeyValueMapper Materialized Produced]
           [org.apache.kafka.common.serialization Serdes]))

(def regularize-words
  "Value mapper to split text into lowercase words."
  (reify ValueMapper
    (apply [this v] (str/split (str/lower-case v) #"\s+"))))

(def get-value
  "Key-value mapper to just return the value."
  (reify KeyValueMapper
    (apply [this k v] v)))

(def builder (new StreamsBuilder))
(-> builder
    (.stream "streams-plaintext-input")
    (.flatMapValues regularize-words)
    (.groupBy get-value)
    (.count (Materialized/as "counts-store"))
    (.toStream)
    (.to "streams-wordcount-output" (Produced/with (Serdes/String) (Serdes/Long))))
(def topology (.build builder))

(def streams (new KafkaStreams topology (get-props "streams-wordcount")))
