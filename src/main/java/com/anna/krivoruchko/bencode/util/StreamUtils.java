package com.anna.krivoruchko.bencode.util;

import java.io.*;

public class StreamUtils {

    public static int readWithoutExtractionOfByte(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(1);
        int currentByte = bufferedInputStream.read();
        bufferedInputStream.reset();
        return currentByte;
    }

    public static int readByteAndMarkPosition(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(1);
        return bufferedInputStream.read();
    }

    public static BufferedInputStream createInputStreamFrom(String value) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(value.getBytes());
        return new BufferedInputStream(byteArrayInputStream);
    }
}
