package com.example;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONFile {

  // read a json file and return an array
  public static JSONArray readArray(String fileName) {
    //
    // read the birthday.json file and iterate over it
    //

    // JSON parser object to parse read file
    JSONParser jsonParser = new JSONParser();

    JSONArray data = null;

    try (FileReader reader = new FileReader(fileName)) {
      Object obj = jsonParser.parse(reader);

      data = (JSONArray) obj;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return data;
  }
}
