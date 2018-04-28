(ns metamorphosis.line-split
  "This is a fairly straight translation of the Line Split app at:
   http://kafka.apache.org/11/documentation/streams/tutorial."
  (:require [clojure.string :as str]
            [metamorphosis.config :refer [get-props]])
  (:import [org.apache.kafka.streams KafkaStreams StreamsBuilder]
           [org.apache.kafka.streams.kstream ValueMapper]
           [org.apache.kafka.common.serialization Serdes]))

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

(def streams (new KafkaStreams topology (get-props "streams-linesplit")))
