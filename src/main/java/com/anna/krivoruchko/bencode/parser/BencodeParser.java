package com.anna.krivoruchko.bencode.parser;

import com.anna.krivoruchko.bencode.exception.BencodeParseException;
import com.anna.krivoruchko.bencode.type.BencodeType;

import java.io.BufferedInputStream;
import java.io.IOException;

public interface BencodeParser {

    BencodeType parse(BufferedInputStream inputStream) throws IOException, BencodeParseException;

    boolean isResponsible(char firstChar);

}
