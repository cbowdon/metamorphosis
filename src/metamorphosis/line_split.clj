(ns metamorphosis.line-split
  (:import [java.util Properties]
           [org.apache.kafka.streams KafkaStreams StreamsBuilder StreamsConfig]
           [org.apache.kafka.common.serialization Serdes]))

(def streams nil)
