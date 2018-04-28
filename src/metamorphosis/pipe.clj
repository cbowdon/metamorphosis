(ns metamorphosis.pipe
  "This is a fairly straight translation of the Pipe app at:
   http://kafka.apache.org/11/documentation/streams/tutorial."
  (:require [metamorphosis.config :refer [get-props]])
  (:import [org.apache.kafka.streams KafkaStreams StreamsBuilder]
           [org.apache.kafka.common.serialization Serdes]))

(def builder (new StreamsBuilder))
(-> builder
    (.stream "streams-plaintext-input")
    (.to "streams-pipe-output"))
(def topology (.build builder))

(def streams (new KafkaStreams topology (get-props "streams-pipe")))
