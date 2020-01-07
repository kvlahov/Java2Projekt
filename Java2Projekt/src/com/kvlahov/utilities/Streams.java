/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.utilities;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {
    /**
     * Converts Iterable to stream
     */
    public static <T> Stream<T> streamOf(final Iterable<T> iterable) {
        return toStream(iterable, false);
    }

    /**
     * Converts Iterable to parallel stream
     */
    public static <T> Stream<T> parallelStreamOf(final Iterable<T> iterable) {
        return toStream(iterable, true);
    }

    private static <T> Stream<T> toStream(final Iterable<T> iterable, final boolean isParallel) {
        return StreamSupport.stream(iterable.spliterator(), isParallel);
    }
}