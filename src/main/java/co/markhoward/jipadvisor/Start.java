package co.markhoward.jipadvisor;

import spark.Spark;

public class Start {
  public static void main(String[] arguments) {
    Spark.get("/hello", (req, res) -> "Hello World");
  }
}
