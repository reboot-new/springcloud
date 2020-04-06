package com.tan.springcloud2producer.test;

import java.io.Serializable;

public class StaticClassTest {

    /** Tracks the total query count and number of aggregate bytes for a particular group. */
    public class Stats implements Serializable {

        private final int count;
        private final int numBytes;

        public Stats(int count, int numBytes) {
            this.count = count;
            this.numBytes = numBytes;
        }
        public Stats merge(Stats other) {
            return new Stats(count + other.count, numBytes + other.numBytes);
        }

        public String toString() {
            return String.format("bytes=%s\tn=%s", numBytes, count);
        }
    }

    public String outMethor(){
        return "out";
    }

    public void printStats(){
        Stats s = new Stats(2,1);
        System.out.println(s.toString());
    }


    public static void main(String[] args) {
        StaticClassTest st = new StaticClassTest();

        st.printStats();
    }
}
