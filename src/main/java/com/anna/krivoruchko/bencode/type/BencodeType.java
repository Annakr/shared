package com.anna.krivoruchko.bencode.type;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 */
public interface BencodeType {

    public static final char COMMON_POSTFIX = 'e';

    void encode(OutputStream os) throws IOException;

}
