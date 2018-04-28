(ns metamorphosis.core
  "This is a fairly straight translation of the Kafka Streams tutorial at:
   http://kafka.apache.org/11/documentation/streams/tutorial."
  (:require [clojure.string :as str]
            [metamorphosis.pipe :as pipe]
            [metamorphosis.line-split :as line-split])
  (:gen-class))

(def programs
  "The programs that can be run as part of the Kafka Streams tutorial."
  {"pipe" pipe/run
   "line-split" line-split/run})

(def usage
  "The usage statement"
  (let [program-lines (map #(format "lein run %s" %) (keys programs))]
    (str/join "\n" (conj program-lines "Usage:"))))

(defn -main
  "Run the example kafka streams app."
  [& args]
  (println "Hello, World!")
  (let [program (get programs (first args))]
    (if program
      (program)
      (println usage))))
