#!/bin/bash

echo "OK?" |jshell - <<EOF
  var inBytes = System.in.readAllBytes();
import java.nio.ByteBuffer;
System.out.println("OK???");

void run() throws IOException {
  var inBuff = ByteBuffer.wrap(inBytes);
  
  while (inBuff.hasRemaining()) {
    System.out.printf("%02X\n", inBuff.get());
  }
  System.out.println();
}
run()
/exit
EOF
