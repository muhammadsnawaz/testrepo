package com.adobe.aem.guides.wknd.core.services;

public class TrainingService {
    // Your program begins with a call to main().
    // Prints "Hello, World" to the terminal window.
    public static void main(String args[]) {
        System.out.println("in Main function");
        sampleFunction();
    }

    public static void sampleFunction(){
        System.out.println("Hello, World from a sampleFunction method");
        secondFunction();
    }
    public static void secondFunction(){
        System.out.println("subject not found");
        CustomService objCustomService = new CustomService();
        String var = objCustomService.almari();
        System.out.println("var value from another class --> "+var);
    }

}