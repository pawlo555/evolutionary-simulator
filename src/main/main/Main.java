package main;

import GUI.JSONSettingsParser;

public class Main {

    public static void main(String[] args) {
		Genome first = new Genome();
		Genome second = new Genome();
		System.out.println(first);
		System.out.println(second);
		Genome third = new Genome(first, second);
		System.out.println(third);
		try {
			System.out.println("File: " + new JSONSettingsParser().ReadFile());
		}
		catch (Exception e) {
			System.out.println("Mistake");
			System.out.println(e.getMessage());
		}

    }
}
