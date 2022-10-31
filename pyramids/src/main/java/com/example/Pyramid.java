package com.example;

// pyramid class, that corresponds to the information in the json file
public class Pyramid {

  protected Integer id;
  protected String name;
  protected Pharaoh[] contributors;

  // constructor
  public Pyramid(
    Integer pyramidId,
    String pyramidName,
    Pharaoh[] pyramidContributors
  ) {
    id = pyramidId;
    name = pyramidName;
    contributors = pyramidContributors;
  }

  
}
