package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.BencodeParseException;
import com.anna.krivoruchko.bencode.exception.InvalidFormatException;
import com.anna.krivoruchko.bencode.type.BencodeString;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


public class StringParser implements BencodeParser {

    @Override
    public BencodeString parse(BufferedInputStream inputStream) throws IOException, BencodeParseException {
        int stringLength = readStringLength(inputStream);
        String value = readValue(inputStream, stringLength);
        return new BencodeString(value);
    }

    @Override
    public boolean isResponsible(char firstChar) {
        return Character.isDigit(firstChar);
    }

    private int readStringLength(InputStream inputStream) throws IOException, InvalidFormatException {
        StringBuilder stringBuilder = new StringBuilder();
        int currentByte;
        while ((currentByte = inputStream.read()) > -1) {
            char currentChar = (char) currentByte;
            if (currentChar == BencodeString.LENGTH_SEPARATOR) {
                break;
            } else if (!Character.isDigit(currentChar)) {
                throw new InvalidFormatException();
            }
            stringBuilder.append(currentChar);
        }

        return parseInt(stringBuilder.toString());
    }

    private int parseInt(String value) throws InvalidFormatException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException();
        }
    }

    private String readValue(InputStream inputStream, int stringLength) throws IOException, InvalidFormatException {
        byte[] bytes = new byte[stringLength];
        int readBytes = inputStream.read(bytes);
        if (readBytes < stringLength) {
            throw new InvalidFormatException();
        }
        return new String(bytes);
    }
}
