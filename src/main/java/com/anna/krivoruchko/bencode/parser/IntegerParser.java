package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.BencodeParseException;
import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.BencodeInteger;

import java.io.BufferedInputStream;
import java.io.IOException;

public class IntegerParser extends OneCharPrefixParser {


    @Override
    public BencodeInteger parse(BufferedInputStream inputStream) throws IOException, BencodeParseException {
        readAndCheckFirstByte(inputStream);

        StringBuilder stringBuilder = new StringBuilder();
        int currentByte;
        while ((currentByte = inputStream.read()) > -1) {
            char currentChar = (char) currentByte;
            if (currentChar == getPostfix()){
                return getIntegerValue(stringBuilder.toString());
            } else {
                stringBuilder.append(currentChar);
            }
        }
        throw new InvalidFormatException();
    }

    @Override
    protected char getPrefix() {
        return BencodeInteger.PREFIX;
    }

    private BencodeInteger getIntegerValue(String stringValue) throws InvalidFormatException {
        try {
            Integer result = Integer.valueOf(stringValue);
            return new BencodeInteger(result);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException();
        }
    }

}
