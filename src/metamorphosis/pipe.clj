(ns metamorphosis.pipe
  "This is a fairly straight translation of the Pipe app at:
   http://kafka.apache.org/11/documentation/streams/tutorial."
  (:import [java.util Properties]
           [java.util.concurrent CountDownLatch]
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

(def latch (new CountDownLatch 1))

;; Remember to run with `lein trampoline run`
;; otherwise Lein will intercept the C-c (shared JVM)
(.addShutdownHook (Runtime/getRuntime)
                  (new Thread #(do (println "Shutting down...")
                                   (.close streams)
                                   (.countDown latch))))

(defn run
  "Run the example kafka streams app."
  [& args]
  (println "Hello, World!")
  (try
    (println "Starting streams...")
    (.start streams)
    (println "Awaiting latch...")
    (.await latch)
    (catch Throwable e
      (println (format "Caught this: %s" e))
      (System/exit 1)))
  (System/exit 0))
