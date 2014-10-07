package com.anna.krivoruchko.bencode.exception;

public class InvalidFormatException extends BencodeParseException {

    public InvalidFormatException() {
    }

    public InvalidFormatException(String message) {
        super(message);
    }
}
