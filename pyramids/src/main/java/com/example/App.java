package com.example;

import java.util.*;
import org.json.simple.*;

public class App {

  static HashSet<String> pyramidHistory = new HashSet<String>();

  static String pharaohFile =
    "C:/Users/rache/Documents/GitHub/nassefs-egyptian-pyramids-app/pyramids/src/main/java/com/example/Pharaoh.java";
  static JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);

  // create and intialize the pharaoh array
  static Pharaoh[] pharaohArray = initializePharaoh(pharaohJSONArray);

  // read pyramids
  static String pyramidFile =
    "C:/Users/rache/Documents/GitHub/nassefs-egyptian-pyramids-app/pyramids/src/main/java/com/example/pyramid.json";
  static JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

  // create and initialize the pyramid array
  static Pyramid[] pyramidArray = initializePyramid(pyramidJSONArray);
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    Character command = '_';

    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);
      executeCommand(scan, command);
    }

    scan.close();
    
  }


  // initialize the pharaoh array
  private static Pharaoh[] initializePharaoh(JSONArray pharaohJSONArray) {
    // create array and hash map
    Pharaoh[] pharaohArr = new Pharaoh[pharaohJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();

      // add a new pharoah to array
      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohArr[i] = p;
    }

    return pharaohArr;
  }

    // initialize the pyramid array
    private static Pyramid[] initializePyramid(JSONArray pyramidJSONArray) {
      // create array and hash map
      Pyramid[] pyramidArr = new Pyramid[pyramidJSONArray.size()];
  
      // initalize the array
      for (int i = 0; i < pyramidJSONArray.size(); i++) {
        // get the object
        JSONObject o = (JSONObject) pyramidJSONArray.get(i);
  
        // parse the json object
        Integer id = toInteger(o, "id");
        String name = o.get("name").toString();
        JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
        String[] contributors = new String[contributorsJSONArray.size()];
        for (int j = 0; j < contributorsJSONArray.size(); j++) {
          String c = contributorsJSONArray.get(j).toString();
          contributors[j] = c;
        }
  
        // add a new pyramid to array
        Pyramid p = new Pyramid(id, name, contributors);
        pyramidArr[i] = p;
      }

      return pyramidArr;
    }

  // get a integer from a json object, and parse it
  private static Integer toInteger(JSONObject o, String key) {
    String s = o.get(key).toString();
    Integer result = Integer.parseInt(s);
    return result;
  }

  // get first character from input
  private static Character menuGetCommand(Scanner scan) {
    Character command = '_';

    String rawInput = scan.nextLine();

    if (rawInput.length() > 0) {
      rawInput = rawInput.toLowerCase();
      command = rawInput.charAt(0);
    }

    return command;
  }

  // print all pharaohs
  private static void printAllPharaoh() {
    for (int i = 0; i < pharaohArray.length; i++) {
      printMenuLine();
      pharaohArray[i].print();
      printMenuLine();
    }
  }

  private static Boolean executeCommand(Scanner scan, Character command) {
    Boolean success = true;

    switch (command) {
      case '1':
        printAllPharaoh();
        break;
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        System.out.println("ERROR: Unknown commmand");
        success = false;
    }

    return success;
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t\t%s\n", command, desc);
  }

  private static void printMenuLine() {
    System.out.println(
      "--------------------------------------------------------------------------"
    );
  }

  // prints the menu
  public static void printMenu() {
    printMenuLine();
    System.out.println("Nassef's Egyptian Pyramids App");
    printMenuLine();
    System.out.printf("Command\t\tDescription\n");
    System.out.printf("-------\t\t---------------------------------------\n");
    printMenuCommand('1', "List all the pharoahs");
    printMenuCommand('q', "Quit");
    printMenuLine();
  }
}
