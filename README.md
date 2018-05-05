# metamorphosis

This is a fairly straight translation of the Kafka Streams tutorial at: http://kafka.apache.org/11/documentation/streams/tutorial.

Done as a learning exercise rather than a demo of how best to do Kafka Streams in Clojure (there's libs available).

## Installation

- Requires make, Kafka 1.1.0 and Lein

## Usage

Run the following commands from the Makefile in separate terminals.

```
# setup server
make zookeeper
make kafka
make topics

make producer  # start entering input on stdin
make consumer-pipe    # start reading output from streams-pipe-output

lein trampoline run pipe   # run the pipe app
```

## License

Copyright © 2018 Chris Bowdon

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
