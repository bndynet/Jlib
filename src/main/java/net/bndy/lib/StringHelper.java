/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.lib;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.commons.codec.binary.Base64;


/**
 * Utils for {@code String}
 */
public class StringHelper {

    /**
     * Generates an UUID string.
     *
     * @return the resulting string
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates a random string specified length.
     *
     * @param length the resulting string length
     * @return the resulting string
     */
    public static String generateRandomString(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int count = chars.length();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(count)));
        }

        return sb.toString();
    }

    /**
     * Generates a random code.
     *
     * @param length the resulting string length
     * @return the resulting string
     */
    public static String generateRandomCode(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[length / 2];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16);
    }

    /**
     * Cuts the specified length string.
     *
     * @param source the original string
     * @param length the length need to cut
     * @return the resulting string
     */
    public static String cut(String source, int length) {
        return cut(source, length, "");
    }

    /**
     * Cuts string.
     *
     * @param source   the original string
     * @param length   the length need to cut
     * @param ellipsis the suffix string if cut
     * @return the resulting string
     */
    public static String cut(String source, int length, String ellipsis) {
        if (source.length() <= length) {
            return source;
        } else {
            return source.substring(0, length) + (ellipsis == null ? "" : ellipsis);
        }
    }

    /**
     * Capitalizes the first letter of words in specified string.
     *
     * @param text the text
     * @return the resulting string
     */
    public static String capitalize(String text) {
        if (text != null && !"".equals(text)) {
            StringBuilder sb = new StringBuilder();
            String[] words = text.split(" ");
            for (String w : words) {
                if (w != "") {
                    sb.append(w.substring(0, 1).toUpperCase() + w.substring(1));
                }
            }
            return sb.toString();
        }

        return text;
    }

    /**
     * Formats date.
     *
     * @param date the date
     * @param format the format string of {@code SimpleDateFormat}
     * @return  the formatted date string
     */
    public static String formatDateTime(Date date, String format) {
        SimpleDateFormat ft = new SimpleDateFormat(format);
        return ft.format(date);
    }

    /**
     * Parses date.
     *
     * @param dateString the date string
     * @param format the format of dateString
     * @return  a {@code Date} instance
     * @throws ParseException   if dateString or format is invalid
     */
    public static Date parseDate(String dateString, String format) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat(format);
        return ft.parse(dateString);
    }

    /**
     * Appends text content to a file.
     *
     * @param content   the file content needs to append
     * @param filename  the file full name includes path
     * @throws IOException  if the writing is failed
     */
    public static void appendToFile(String content, String filename) throws IOException {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(filename, true));
            out.write(content);
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Splits a string and ignore white spaces.
     *
     * @param source    the string needs to split
     * @param separator the separator string
     * @return  an array typed String
     */
    public static String[] splitWithoutWhitespace(String source, String separator) {
        List<String> result = new ArrayList<String>();
        String[] items = source.split(separator);
        for (String s : items) {
            if (!"".equals(s.trim())) {
                result.add(s.trim());
            }
        }

        return result.toArray(new String[result.size()]);
    }

    /**
     * Encodes with Base64.
     *
     * @param source    the string needs to encode
     * @return  the resulting string
     * @throws UnsupportedEncodingException if the encoding is not supported
     */
    public static String encodeBase64(String source) throws UnsupportedEncodingException {
        return Base64.encodeBase64String(source.getBytes("UTF-8"));
    }

    /**
     * Decodes the Base64 string.
     *
     * @param base64String  the Base64 string
     * @return  the decoded string
     * @throws UnsupportedEncodingException if the encoding is not supported
     */
    public static String decodeBase64(String base64String) throws UnsupportedEncodingException {
        return new String(Base64.decodeBase64(base64String), "UTF-8");
    }

    /**
     * Converts json string to an object.
     *
     * @param content   the json string
     * @param valueType the result type
     * @param <T>       the result type
     * @return  an object typed T
     * @throws IOException if a low-level I/O problem (unexpected end-of-input,
     *   network error) occurs (passed through as-is without additional wrapping -- note
     *   that this is one case where {@link DeserializationFeature#WRAP_EXCEPTIONS}
     *   does NOT result in wrapping of exception even if enabled)
     */
    public static <T> T toJson(String content, Class<T> valueType) throws IOException {
        return JsonHelper.parse(content, valueType);
    }
}