(ns metamorphosis.core
  "This is a fairly straight translation of the Kafka Streams tutorial at:
   http://kafka.apache.org/11/documentation/streams/tutorial."
  (:require [clojure.string :as str]
            [metamorphosis.pipe :as pipe]
            [metamorphosis.line-split :as line-split])
  (:import [java.util.concurrent CountDownLatch])
  (:gen-class))

(def programs
  "The programs that can be run as part of the Kafka Streams tutorial."
  {"pipe" pipe/streams
   "line-split" line-split/streams})

(defn usage
  "Print the usage statement"
  []
  (let [program-lines (map #(format "lein run %s" %) (keys programs))]
    (println (str/join "\n" (conj program-lines "Usage:")))))

(defn run-streams
  "Run streams and block, shutting down gracefully on interrupt."
  [streams]
  (let [latch (new CountDownLatch 1)]
    ;; Remember to run with `lein trampoline run`
    ;; otherwise Lein will intercept the C-c (shared JVM)
    (.addShutdownHook (Runtime/getRuntime)
                      (new Thread #(do (println "Shutting down...")
                                       (.close streams)
                                       (.countDown latch))))
    (println "Starting streams...")
    (.start streams)
    (println "Awaiting latch...")
    (.await latch)))

(defn run
  "Run the example kafka streams app."
  [streams]
  (try
    (run-streams streams)
    (catch Throwable e
      (println (format "Caught this: %s" e))
      (System/exit 1)))
  (System/exit 0))

(defn -main
  "Run the example kafka streams app."
  [& args]
  (println "Hello, World!")
  (let [streams (get programs (first args))]
    (if streams
      (run streams)
      (usage))))
