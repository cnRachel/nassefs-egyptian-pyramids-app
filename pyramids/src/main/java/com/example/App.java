package com.example;

import java.util.*;
import org.json.simple.*;

public class App {


  // I've used two arrays here for O(1) reading of the pharaohs and pyramids.
  // other structures or additional structures can be used
  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;

  static HashSet<Pyramid> requestedPyramids = new HashSet<Pyramid>();

  public static void main(String[] args) {
    // create and start the app
    App app = new App();
    app.start();
  }

  // main loop for app
  public void start() {
    Scanner scan = new Scanner(System.in);
    Character command = '_';

    // loop until user quits
    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      executeCommand(scan, command);
    }
  }

  // constructor to initialize the app and read commands
  public App() {
    // read egyptian pharaohs
    String pharaohFile =
      "C:/Users/rache/Documents/GitHub/nassefs-egyptian-pyramids-app/pyramids/src/main/java/com/example/pharaoh.json";
    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);

    // create and intialize the pharaoh array
    initializePharaoh(pharaohJSONArray);

    // read pyramids
    String pyramidFile =
      "C:/Users/rache/Documents/GitHub/nassefs-egyptian-pyramids-app/pyramids/src/main/java/com/example/pyramid.json";
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

    // create and initialize the pyramid array
    initializePyramid(pyramidJSONArray);

  }

  // initialize the pharaoh array
  private void initializePharaoh(JSONArray pharaohJSONArray) {
    // create array and hash map
    pharaohArray = new Pharaoh[pharaohJSONArray.size()];

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
      pharaohArray[i] = p;
    }
  }

    // initialize the pyramid array
    private void initializePyramid(JSONArray pyramidJSONArray) {
      // create array and hash map
      pyramidArray = new Pyramid[pyramidJSONArray.size()];
  
      // initalize the array
      for (int i = 0; i < pyramidJSONArray.size(); i++) {
        // get the object
        JSONObject o = (JSONObject) pyramidJSONArray.get(i);
  
        // parse the json object
        Integer id = toInteger(o, "id");
        String name = o.get("name").toString();
        JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
        Pharaoh[] contributors = new Pharaoh[contributorsJSONArray.size()];
        for (int j = 0; j < contributorsJSONArray.size(); j++) {
          String c = contributorsJSONArray.get(j).toString();
          for (int x = 0; x < pharaohArray.length; x++) {
            if (pharaohArray[x].hieroglyphic.compareTo(c) == 0) {
              contributors[j] = pharaohArray[x];
            }
          }
        }
  
        // add a new pyramid to array
        Pyramid p = new Pyramid(id, name, contributors);
        pyramidArray[i] = p;
      }
    }

  // get a integer from a json object, and parse it
  private Integer toInteger(JSONObject o, String key) {
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
  private void printAllPharaoh() {
    for (int i = 0; i < pharaohArray.length; i++) {
      printMenuLine();
      pharaohArray[i].print();
      printMenuLine();
    }
  }

  private void printAllPyramid() {
    for (int i = 0; i < pyramidArray.length; i++) {
      printMenuLine();
      printOnePyramid(pyramidArray[i]);
      printMenuLine();
    }
  }

  private void printOnePyramid(Pyramid p) {
    Integer totalCoins = 0;
    System.out.printf("Pyramid %s\n", p.name);
    System.out.printf("\tid: %d\n", p.id);

    // print out all contributors
    for (int x = 0; x < p.contributors.length; x++) {
      Pharaoh currentPharaoh = p.contributors[x];
      totalCoins += currentPharaoh.contribution;
      System.out.printf("\tcontributor %d: %s %d gold coins\n", x+1, currentPharaoh.name, currentPharaoh.contribution);
    }

    System.out.printf("\ttotal contribution: %d gold coins\n", totalCoins);
  }

  private Boolean executeCommand(Scanner scan, Character command) {
    Boolean success = true;

    switch (command) {
      case '1':
        printAllPharaoh();
        break;
      case '2':
        displaySpecificPharaoh(scan);
        break;
      case '3':
        printAllPyramid();
        break;
      case '4':
        displaySpecificPyramid(scan);
        break;
      case '5':
        displayRequestedPyramids();
        break;
        
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        System.out.println("ERROR: Unknown command");
        success = false;
    }

    return success;
  }

  private void displaySpecificPharaoh(Scanner scan) {
    Integer requestedId;
    System.out.print("Enter a pharoah id: ");
    if (scan.hasNextInt()) {
      requestedId = scan.nextInt();
      if (requestedId >= pharaohArray.length || requestedId < 0) {
        System.out.println("ERROR: Unknown pharaoh");
      } else {
        printMenuLine();
        pharaohArray[requestedId].print();
      }
    } else {
      System.out.println("ERROR: Invalid input");
    }
    scan.nextLine();
    printMenuLine();
  }

  private void displaySpecificPyramid(Scanner scan) {
    Integer requestedId;
    System.out.print("Enter a pyramid id: ");
    if (scan.hasNextInt()) {
      requestedId = scan.nextInt();
      if (requestedId >= pyramidArray.length || requestedId < 0) {
        System.out.println("ERROR: Unknown pyramid");
      } else {
        printMenuLine();
        printOnePyramid(pyramidArray[requestedId]);
        requestedPyramids.add(pyramidArray[requestedId]);
      }
    } else {
      System.out.println("ERROR: Invalid input");
    }
    scan.nextLine();
    printMenuLine();
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
    printMenuCommand('2', "Displays a specific Egyptian pharaoh");
    printMenuCommand('3', "List all the pyramids");
    printMenuCommand('4', "Displays a specific pyramid");
    printMenuCommand('5', "Displays a list of requested pyramids");
    printMenuCommand('q', "Quit");
    printMenuLine();
  }


  private static void displayRequestedPyramids() {
    printMenuLine();
    System.out.printf("\tId\tName\n");
    System.out.printf("\t---\t----------------\n");
    for (Pyramid p : requestedPyramids) {
      System.out.printf("\t%d\t%s\n", p.id, p.name);
    }
    printMenuLine();
  }

}