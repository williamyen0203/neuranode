package com.neuranode.neuranodeapp;

/**
 * Created by William on 1/31/2016.
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private int[] choices;

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName.substring(0, 1).toUpperCase().trim() + firstName.substring(1).toLowerCase().trim();
        this.lastName = lastName.substring(0, 1).toUpperCase().trim() + lastName.substring(1).toLowerCase().trim();
        this.email = email.trim();
        choices = new int[8];
    }

    public int[] getChoices() {
        return choices;
    }

    public void setChoices(int index, int value){
        choices[index] = value;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
