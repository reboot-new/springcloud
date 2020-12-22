package com.tan.springcloud2producer.test.guava;


import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.UUID;

public class ComparisonChainTest {
    public static void main(String[] args) {
        System.out.println(ComparisonChain.start().compare("AAA", "1212").result());

        System.out.println(UUID.randomUUID().toString());
    }
}
