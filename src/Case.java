//for android app
package com.example.lithuanianapp; // package calls must be always at the beginning, before imports

//import java.util.Scanner;
import java.sql.SQLOutput;
import java.util.*;

public class Case {

    // Attributes
    //String language; // ISO country code (for future additional languages)
    String caseType; // or char caseType (g, d, ...)
    //String gender or char gender (M vs F)
    //String number or char number (S vs P)
    String inputWord;

    //Attributes which are not initialized in the Constructor
    //In this way this variable will be shared by all method, and whenever a method is called, the previous content is gonna be overwritten
    String inputWordRoot;
    String inputSuffix;
    String outputSuffix;
    String outputWord;


    // Constructor
    public Case(String selectedCaseType, String selectedWord) {
        caseType = selectedCaseType.toLowerCase(); // in order to have correct input (see switch)
        inputWord = selectedWord.toLowerCase();

        inputWordRoot = null; //initialise with null (recommended) if the String in going to be used with an assignment (str = ...), else initialise with ""
        inputSuffix = null;
        outputSuffix = null;
        outputWord = null;

        // 1-letter suffixes
        if (inputWord.charAt(inputWord.length() - 1) == 'a') {
            inputSuffix = "a";
        } else if (inputWord.charAt(inputWord.length() - 1) == 'ė') {
            inputSuffix = "ė";
        } else if ((inputWord.charAt(inputWord.length() - 1) == 'i') && (inputWord.charAt(inputWord.length() - 2) != 'a')) { //exclude 2-letter suffix ai"
            inputSuffix = "i";

        //FOR PROPER DETECTION, check possible suffixes in descending order (from longest to shortest)
        // 5-letter suffix "džiai" (plural)
        } else if (inputWord.substring(inputWord.length() - 5).equals("džiai")) {
            inputSuffix = "džiai";

        // 4-letter suffix "čiai" (plural)
        } else if (inputWord.substring(inputWord.length() - 4).equals("čiai")) {
            inputSuffix = "čiai";

        // 3-letter suffixes
        } else if (inputWord.substring(inputWord.length() - 4).equals("ios")) { //"ios" (aggettivi F plurali di -(i)us, ex: skani -> skanios)
            inputSuffix = "ios";
        } else if (inputWord.substring(inputWord.length() - 3).equals("tis")) {
            inputSuffix = "tis";
            //inputSuffix = inputWord.substring(inputWord.length() - 3);
        } else if (inputWord.substring(inputWord.length() - 3).equals("tys")) {
            inputSuffix = "tys";
            //inputSuffix = inputWord.substring(inputWord.length() - 3);
        } else if (inputWord.substring(inputWord.length() - 3).equals("dis")) {
            inputSuffix = "dis";

        // 2-letter suffixes
        } else {
            inputSuffix = inputWord.substring(inputWord.length() - 2);
        }
        inputWordRoot = inputWord.substring(0, inputWord.length() - inputSuffix.length());
    }//Case constructor


    //Number method
    public String getNumber() {

        switch (inputSuffix) {

            //S -> P
            case "as": //M
            case "us":
                outputSuffix = "ai (plural)";
                break;
            case "is": //M
            case "ys":
                outputSuffix = "iai (plural)";
                break;
            case "tis": //M
                outputSuffix = "čiai (plural)";
                break;
            case "dis": //M
                outputSuffix = "džiai (plural)";
                break;
            case "a": //F
                outputSuffix = "os (plural)";
                break;
            case "ė": //F
                outputSuffix = "ės (plural)";
                break;
            case "i": //F (aggettivi F plurali di -(i)us, ex: skani -> skanios)
                outputSuffix = "ios (plural)";
                break;

            //P -> S
            case "ai": //M
                outputSuffix = "as/us (singular)";
                break;
            case "iai": //M
                outputSuffix = "is/ys (singular)";
                break;
            case "čiai": //M
                outputSuffix = "tis (singular)";
                break;
            case "džiai": //M
                outputSuffix = "dis (singular)";
                break;
            case "os": //F
                outputSuffix = "a (singular)";
                break;
            case "ės": //F
                outputSuffix = "ė (singular)";
                break;
            case "ios": //F (aggettivi F plurali di -(i)us, ex: skani -> skanios)
                outputSuffix = "i (plural)";
                break;

            default:
                outputSuffix = "err";
        }//switch inputSuffix

        if (outputSuffix.equals("err"))
            return outputSuffix;
        else {
            outputWord = inputWordRoot + outputSuffix;
            outputWord = toTitleCase(outputWord);
            return outputWord;
        }
    }//getNumber()


    // Genitive method
    public String getGenitive() {

        switch (inputSuffix) {

            //SINGULAR
            case "as":
                outputSuffix = "o";
                break;
            case "is":
                outputSuffix = "IO/IES (MasculineSingular/FeminineSingular, e.g. brolis-brolio/pilis-pilies)";
                break;
            case "ys": //SINGULAR or PLURAL
                outputSuffix = "IO/IŲ (MasculineSingular/FemininePlural; e.g. kambarys-kambario/pilys-pilių)";
                break;
            case "us": //also case "ius"
                outputSuffix = "aus"; //"(i)aus
                break;
            case "tis":
                outputSuffix = "ČIO/TIES (MasculineSingular/FeminineSingular, e.g. dviratis-dviračio/sultis-sulties)";
                break;
            case "dis":
                outputSuffix = "džio";
                break;
            case "a":
                outputSuffix = "os";
                break;
            case "ė":
                outputSuffix = "ės";
                break;
            case "i":
                outputSuffix = "ios";

            //PLURAL
            case "ai": //also case "iai"
            case "os": //also case "ios
                outputSuffix = "ų"; //"(i)ų"
                break;
            case "ės":
            //case "ys": //duplicate "ys", overlapping with SINGULAR
                //outputSuffix = "ių";
                //break;
            case "tys":
                outputSuffix = "čių";
                break;


            default:
                outputSuffix = "err";
        }//switch inputSuffix

        if (outputSuffix.equals("err"))
            return outputSuffix;
        else {
            outputWord = inputWordRoot + outputSuffix;
            outputWord = toTitleCase(outputWord);
            return outputWord;
        }
    }//getGenitive()


    // Locative method
    public String getLocative() {

        switch (inputSuffix) {
            case "as":
                outputSuffix = "e";
                break;
            case "is":
            case "ys":
                outputSuffix = "yje";
                break;
            case "a":
                outputSuffix = "oje";
                break;
            case "us": //also case "ius":
                outputSuffix = "uje"; //"(i)uje
                break;
            case "ė":
                outputSuffix = "ėjė";
                break;
            default:
                outputSuffix = "err";
        }//switch inputSuffix

        if (outputSuffix.equals("err"))
            return outputSuffix;
        else {
            outputWord = inputWordRoot + outputSuffix;
            outputWord = toTitleCase(outputWord);
            return outputWord;
        }
    }//getLocative()


    // Accusative method
    public String getAccusative() {

        switch (inputSuffix) {
            case "as":
            case "a":
                outputSuffix = "ą";
                break;
            case "is":
            case "ys":
                outputSuffix = "į";
                break;
            case "us": //also case "ius":
                outputSuffix = "ų";//"(i)ų"
                break;
            case "ė":
                outputSuffix = "ę";
                break;
            default:
                outputSuffix = "err";
        }//switch inputSuffix

        if (outputSuffix.equals("err"))
            return outputSuffix;
        else {
            outputWord = inputWordRoot + outputSuffix;
            outputWord = toTitleCase(outputWord);
            return outputWord;
        }
    }//getAccusative()


    // Instrumental method
    public String getInstrumental() {

        switch (inputSuffix) {

            //SINGULAR
            case "as":
                outputSuffix = "u";
                break;
            case "is":
                outputSuffix = "IU/IMI (MasculineSingular/FeminineSingular, e.g. peilis-peiliu/naktis-naktimi";
                break;
            case "ys":
                outputSuffix = "iu";
                break;
            case "us": // also case "ius"
                outputSuffix = "umi"; //"(i)umi"
                break;
            case "tis":
                outputSuffix = "ČIU/TIMI (MasculineSingular/FeminineSingular, e.g. dviratis-dviračiu/sultis-sultimi)";
                break;
            case "dis":
                outputSuffix = "džiu";
                break;
            case "a":
                outputSuffix = "a";
                break;
            case "ė":
                outputSuffix = "e";


            //PLURAL
            case "ai": //also case "iai"
            outputSuffix = "ais";
                break;
            case "ės":
                outputSuffix = "ėmis";
                break;
            case "os":
                outputSuffix = "omis";
                break;


            default:
                outputSuffix = "err";
        }//switch inputSuffix

        if (outputSuffix.equals("err"))
            return outputSuffix;
        else {
            outputWord = inputWordRoot + outputSuffix;
            outputWord = toTitleCase(outputWord);
            return outputWord;
        }
    }//getInstrumental()



    //STATIC METHODS

    //Sentence Method
    public static String sentenceDeclensor (String option, String inputSentence) {

        String[] inputSentenceArray = inputSentence.split(" ");
        int wordCount = inputSentenceArray.length;
        String outputSentence = "";
        Case words[] = new Case[wordCount]; //array of objects, one Case obj for each word in the sentence
        StringBuilder outputBuilder = new StringBuilder();

        switch (option) {

            case "genitive":

                int indexOfIsh = 0; //it is not possible to initialise primitive types with null, but it is possible with Integer, Boolean, etc...

                if (inputSentence.contains("iš"))   {

                    for (int i = 0; i < wordCount; i++) {
                        if (inputSentenceArray[i].equals("iš"))
                            indexOfIsh = i;
                    }//store "iš" index

                    //ASSIGN ALWAYS THE VALUE RETURNED by concat(), otherwise it will be lost; use StringBuilder as an alternative
                    for (int i = 0; i < wordCount; i++) {
                        if (inputSentenceArray[i].equals("iš")) { //never use == to compare Strings! Always use string.equals()
                            outputBuilder.append("iš ");
                        } else if (i < indexOfIsh) { //don't convert all the words before "iš"
                            outputBuilder.append(inputSentenceArray[i] + " "); //.append("(NOM) ") ???
                        } else { //convert to Genitive Case all the words after "iš"
                            words[i] = new Case("genitive", inputSentenceArray[i]);
                            outputBuilder.append(words[i].getGenitive() + " ").append("(GEN) ");
                        }
                    }//for loop

                } else {
                    for (int i = 0; i < wordCount; i++) {
                        if (i == wordCount - 1) { //last word should be a Nominative case
                            outputBuilder.append(inputSentenceArray[i] + " ").append("(NOM) ");
                        } else {
                            words[i] = new Case("genitive", inputSentenceArray[i]);
                            outputBuilder.append(words[i].getGenitive() + " ").append("(GEN) ");
                        }
                    }//for loop
                }//"iš" if-else
                break;

            case "dative": //naudininkas
                break;
            case "accusative": //galininkas
                break;
            case "instrumental": //inagininkas
                break;
            case "locative": //vietininkas
                break;
            case "vocative": //sauksmininkas
                break;
            default:
                outputBuilder.append("Invalid case type input!");

        }//switch

        outputSentence = outputBuilder.toString().trim();
        outputSentence = Case.toTitleCase(outputSentence);
        return outputSentence;
    }//sentenceDeclensor()


    //toTitleCase Method
    public static String toTitleCase (String stringToConvert) {

        stringToConvert = stringToConvert.toLowerCase();

        String[] stringArray = stringToConvert.split(" ");
        int wordCount = stringArray.length;

        String stringConverted = ""; //initialising with null is recommended over "", but it could return a NullPointerException in case it is used with concat() or similar situations

        String[] initials = new String[wordCount];
        String[] titleWords = new String[wordCount];
        StringBuilder convertedBuilder = new StringBuilder();

        for (int i = 0; i < wordCount; i++) {
            initials[i] = stringArray[i].substring(0, 1).toUpperCase(); //select first character
            titleWords[i] = initials[i].concat(stringArray[i].substring(1)); //select remaining characters and concatenate with initial
            //stringConverted = stringConverted.concat(titleWords[i] + " ");
            convertedBuilder.append(titleWords[i] + " ");
        }
        stringConverted = convertedBuilder.toString().trim();

        return stringConverted;

    }//toTitleCase()

}//Case class

//////////////////////////////////////////////////////////////////////////////////////////

// Main program (app)
class LithuanianDesktopApp {

    // Main method
    public static void main(String[] args) {


        Scanner keyboardInput = new Scanner(System.in);
        //System.out.println("Please enter language (ISO country code): "); //For future multiple language databases

        String inputOptionList = "number,num,genitive,gen,dative,dat,accusative,acc,instrumental,instr,locative,loc,vocative,voc,quit";

        for (;;) {
            System.out.print("Please enter option (or QUIT to exit the application): ");
            //String inputCaseType = keyboardInput.nextLine();
            String inputOption = keyboardInput.nextLine();
            inputOption = inputOption.toLowerCase();
            inputOption = inputOption.trim();

            if (!inputOptionList.contains(inputOption)) {
                System.out.println("***************");
                System.out.println("Invalid option!");
                System.out.println("***************\n");
                continue;
            }

            if (inputOption.equals("quit")) {
                System.out.println("****************");
                System.out.println("Exit application");
                System.out.println("****************");
                break;
            }

            Case currCase = null; // in order to be able to declare objects inside an if statement

            System.out.print("Please enter term or sentence: "); // input term must be a nominative case
            String inputString = keyboardInput.nextLine();
            System.out.println("===========================================");
            inputString = inputString.toLowerCase();
            inputString = inputString.trim();

            String[] inputStringArray = inputString.split(" ");
            int inputWordCount = inputStringArray.length;

            if (inputWordCount > 1) { //sentence

                System.out.println(Case.sentenceDeclensor(inputOption, inputString));

            } else { //single word

                currCase = new Case(inputOption, inputString);

                switch (currCase.caseType) {

                    case "number":
                    case "num":
                        if (currCase.getNumber().equals("err"))
                            System.out.println("Unable to find " + currCase.caseType.toUpperCase() + " for selected word!");
                        else
                            System.out.println(currCase.getNumber());
                        break;

                    case "genitive": //kilmininkas
                    case "gen":
                        if (currCase.getGenitive().equals("err"))
                            System.out.println("Unable to find " + currCase.caseType.toUpperCase() + " for selected word!");
                        else
                            System.out.println(currCase.getGenitive());
                        break;

                    case "dative": //naudininkas
                    case "dat":
                        //currCase.getDative();
                        break;

                    case "accusative": //galininkas
                    case "acc":
                        if (currCase.getAccusative().equals("err"))
                            System.out.println("Unable to find " + currCase.caseType.toUpperCase() + " for selected word!");
                        else
                            System.out.println(currCase.getAccusative());
                        break;

                    case "instrumental": //inagininkas
                    case "instr":
                        if (currCase.getInstrumental().equals("err"))
                            System.out.println("Unable to find " + currCase.caseType.toUpperCase() + " for selected word!");
                        else
                            System.out.println(currCase.getInstrumental());
                        break;

                    case "locative": //vietininkas
                    case "loc":
                        if (currCase.getNumber().equals("err"))
                            System.out.println("Unable to find " + currCase.caseType.toUpperCase() + " for selected word!");
                        else
                            System.out.println(currCase.getLocative());
                        break;

                    case "vocative": //sauksmininkas
                    case "voc":
                        //currCase.getVocative();
                        break;

                    default:
                        System.out.print("Invalid input!");
                }//switch caseType
            }//else
            System.out.println("===========================================\n");
        }//infinite for-loop (keep app running until user quits)

    }//psvm

}//main program class (running class)


//BUG: sotto-classe Sentence (problemi anche in Android)
//running class in a separate file: if the classes used by the running class are in the same src folder, they will be imported automatically.