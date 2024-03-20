package com.example;

import java.util.Scanner;

/**
 * Change me.
 *
 * @author Christopher Di Bert
 * @version 1.0
 * @since 2024-03-20
 */

// PackageChecker class
public final class PackageChecker {

  private static boolean playAgain = true;

  private static String[] massUnits = {"lbs", "kg", "g"};
  private static String[] lengthUnits = {"mm", "cm", "m"};

  private static Double[] packageDimensions = new Double[3];
  private static Double packageMass;
  private static String userMassUnit, userLengthUnit;

  /** Private constructor to prevent instantiation. */
  private PackageChecker() {
    throw new UnsupportedOperationException("Cannot instantiate");
  }

  /**
   * This is the main method.
   *
   * @param args Unused
   */
  public static void main(final String[] args) {
    while (playAgain) {
      System.out.println("\nWelcome to the package checker.");
      if (GetUserValues()) {
        PackageCanBeLoaded();
      }
      Scanner playAgainScanner = new Scanner(System.in);
      System.out.print("\nEnter 'y' to start again:\n> ");
      String playAgain = playAgainScanner.nextLine().toLowerCase();
      if (playAgain.charAt(0) != 'y') {
        ResetValues();
        break;
      }
    }
  }

  private static boolean GetUserValues() {
    Scanner valuesScanner = new Scanner(System.in);
    PresentOptionsMenu("MASS UNITS", massUnits);
    System.out.print("Enter the mass unit you would like to use:\n> ");
    userMassUnit = GetChoice(valuesScanner.nextLine(), massUnits);

    if (userMassUnit.equals("Choice does not exist")) {
      System.out.println("\nYou must select a valid mass unit!");
      return false;
    }

    PresentOptionsMenu("LENGTH UNITS", lengthUnits);
    System.out.print("Enter the length unit you would like to use:\n> ");
    userLengthUnit = GetChoice(valuesScanner.nextLine(), lengthUnits);

    if (userLengthUnit.equals("Choice does not exist")) {
      System.out.println("\nYou must select a valid length unit!");
      return false;
    }

    if (!GetMeasurements()) {
      System.out.println("\nYou must enter numbers for measurements!");
      return false;
    }
    return true;
  }

  private static void PresentOptionsMenu(String title, String[] options) {
    System.out.println("\n===== " + title + " =====");
    for (int i = 0; i < options.length; i++) {
      System.out.println((i + 1) + ". " + options[i]);
    }
  }

  private static String GetChoice(String userChoice, String[] options) {
    int userChoiceNum;
    for (String option : options) {
      if (userChoice.equals(option)) {
        return option;
      }
    }

    try {
      userChoiceNum = Integer.parseInt(userChoice) - 1;
    } catch (Exception e) {
      return "Choice does not exist";
    }

    if (userChoiceNum < options.length && userChoiceNum > options.length) {
      return "Choice does not exist";
    }

    return options[userChoiceNum];
  }

  private static boolean GetMeasurements() {
    Scanner dimensionScanner = new Scanner(System.in);
    String[] measurements = {"length", "width", "height"};
    for (int i = 0; i < measurements.length; i++) {
      System.out.print("\nEnter the package " + measurements[i]);
      System.out.print(" in " + userLengthUnit + "\n> ");
      try {
        double userDim = Double.parseDouble(dimensionScanner.nextLine());
        packageDimensions[i] = userDim;
      } catch (Exception e) {
        return false;
      }
    }
    System.out.print("\nEnter the package mass in " + userMassUnit + "\n> ");
    try {
      packageMass = dimensionScanner.nextDouble();
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  private static void PackageCanBeLoaded() {
    double volume = 1;
    for (Double dimension : packageDimensions) {
      volume *= dimension;
    }

    switch (userLengthUnit) {
      case "mm":
        volume /= 10d;
        break;
      case "m":
        volume *= 100d;
        break;
      default:
        break;
    }

    if (volume > 10000) {
      System.out.println("Cannot send package! It is too large!");
    }

    switch (userMassUnit) {
      case "g":
        packageMass /= 1000d;
        break;
      case "lbs":
        packageMass /= 2.205d;
        break;
      default:
        break;
    }

    if (packageMass > 27d) {
      System.out.println("Cannot send package! It weighs too much!");
    } else {
      System.out.println("\n~Your package can be loaded!~");
    }
  }

  private static void ResetValues() {
    packageDimensions = new Double[3];
    packageMass = 0d;
    userMassUnit = "";
    userLengthUnit = "";
  }
}
