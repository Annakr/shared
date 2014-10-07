package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.BencodeType;

import java.io.IOException;
import java.io.InputStream;

public abstract class OneCharPrefixParser implements BencodeParser {

    @Override
    public boolean isResponsible(char firstChar) {
        return firstChar == getPrefix();
    }

    protected void readAndCheckFirstByte(InputStream inputStream) throws IOException, InvalidFormatException {
        int firstByte = inputStream.read();
        if(firstByte < 0 || firstByte != getPrefix()) {
            throw new InvalidFormatException();
        }
    }

    protected abstract char getPrefix();

    protected char getPostfix() {
        return BencodeType.COMMON_POSTFIX;
    }
}
