package com.semicolon.util;

import java.io.IOException;
import java.io.Reader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONParserUtils {
    public static JSONObject extractor(Reader in){
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(in);
 
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
