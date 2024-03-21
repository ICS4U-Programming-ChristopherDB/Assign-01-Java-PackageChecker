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

    /**
     * Master method that gets all user input.
     * Returns a boolean value to indicate successful operation.
     * @return executedSuccessfully.
     */
    private static boolean GetUserValues() {
        // Dumb variable to please linter
        boolean executedSuccessfully;

        Scanner valuesScanner = new Scanner(System.in);

        // Calls method that presents all units in a list.
        PresentOptionsMenu("MASS UNITS", massUnits);
        System.out.print("Enter the mass unit you would like to use:\n> ");

        /*Gets the user's mass unit and passes it to a method that
         * checks if the user's input was valid.
        */
        userMassUnit = GetChoice(valuesScanner.nextLine(), massUnits);

        /*If GetChoice did not execute successfully, tell the user they
         * made a mistake and prompt the user to restart.
        */
        if (userMassUnit.equals("Choice does not exist")) {
            System.out.println("\nYou must select a valid mass unit!");
            executedSuccessfully = false;
            return executedSuccessfully;
        }

        // Same process but for length units...
        PresentOptionsMenu("LENGTH UNITS", lengthUnits);
        System.out.print("Enter the length unit you would like to use:\n> ");
        userLengthUnit = GetChoice(valuesScanner.nextLine(), lengthUnits);

        if (userLengthUnit.equals("Choice does not exist")) {
            System.out.println("\nYou must select a valid length unit!");
            executedSuccessfully = false;
            return executedSuccessfully;
        }

        if (!GetMeasurements()) {
            System.out.println("\nYou must enter numbers for measurements!");
            executedSuccessfully = false;
            return executedSuccessfully;
        }

        // If no errors were found, exit successfully.
        executedSuccessfully = true;
        return executedSuccessfully;
    }

    /**
     * Simple method that generates a titled list of options
     * from an array of strings.
     * @param title passed.
     * @param options passed.
     */
    private static void PresentOptionsMenu(String title, String[] options) {
        System.out.println("\n===== " + title + " =====");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    /**
     * Method that gets the user's choice from a list.
     * It accepts user input in the form of an integer or string and
     * performs comparisons accordingly.
     * @param userChoice passed.
     * @param options passed.
     * @return returnMessage.
     */
    private static String GetChoice(String userChoice, String[] options) {
        // Dumb variable to please linter.
        String returnMessage;
        int userChoiceNum;
        for (String option : options) {
            if (userChoice.equals(option)) {
                returnMessage = option;
                return returnMessage;
            }
        }

        try {
            userChoiceNum = Integer.parseInt(userChoice) - 1;
        } catch (Exception e) {
            returnMessage = "Choice does not exist";
            return returnMessage;
        }

        if (userChoiceNum < options.length && userChoiceNum > options.length) {
            returnMessage = "Choice out of bounds exist";
            return returnMessage;
        }

        returnMessage = options[userChoiceNum];
        return returnMessage;
    }

    /**
     * Method that gets the user's measurements.
     * @return returnMessage.
     */
    private static boolean GetMeasurements() {
        boolean executedSuccessfully;
        Scanner dimensionScanner = new Scanner(System.in);
        String[] measurements = {"length", "width", "height"};
        for (int i = 0; i < measurements.length; i++) {
            System.out.print("\nEnter the package " + measurements[i]);
            System.out.print(" in " + userLengthUnit + "\n> ");
            try {
                double userDim = Double.parseDouble(dimensionScanner.nextLine());
                packageDimensions[i] = userDim;
            } catch (Exception e) {
                executedSuccessfully = false;
                return executedSuccessfully;
            }
        }
        System.out.print("\nEnter the package mass in " + userMassUnit + "\n> ");
        try {
            packageMass = dimensionScanner.nextDouble();
        } catch (Exception e) {
            executedSuccessfully = false;
            return executedSuccessfully;
        }
        executedSuccessfully = true;
        return executedSuccessfully;
    }

    /**
     * Method that checks if the package can be loaded.
     * It converts the units provided by the user into
     * kilograms and centimeters and checks if the package can be loaded.
     * @return returnMessage.
     */
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

        if (volume > 10000d) {
            System.out.println("Cannot send package! It is too large!");
        }
        else {
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
    }

    /**
     * Method that resets all user values before restarting.
     */
    private static void ResetValues() {
        packageDimensions = new Double[3];
        packageMass = 0d;
        userMassUnit = "";
        userLengthUnit = "";
    }
}
