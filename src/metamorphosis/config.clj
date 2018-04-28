(ns metamorphosis.config
  (:import [java.util Properties]
           [org.apache.kafka.streams StreamsConfig]
           [org.apache.kafka.common.serialization Serdes]))

(defn get-props
  "Get a valid Java Properties for this app-id."
  [app-id]
  (doto (new Properties)
    (.put StreamsConfig/APPLICATION_ID_CONFIG app-id)
    (.put StreamsConfig/BOOTSTRAP_SERVERS_CONFIG "localhost:9092")
    (.put StreamsConfig/DEFAULT_KEY_SERDE_CLASS_CONFIG (class (Serdes/String)))
    (.put StreamsConfig/DEFAULT_VALUE_SERDE_CLASS_CONFIG (class (Serdes/String)))))
