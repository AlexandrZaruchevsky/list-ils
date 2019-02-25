package ru.zaa.pfr.xml.ils.list;

public class Person {

    private String lastName;
    private String firstName;
    private String midleName;
    private String snils;

    public Person() {
    }

    public Person(String lastName, String firstName, String midleName, String snils) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.midleName = midleName;
        this.snils = snils;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidleName() {
        return midleName;
    }

    public void setMidleName(String midleName) {
        this.midleName = midleName;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", midleName='" + midleName + '\'' +
                ", snils='" + snils + '\'' +
                '}';
    }
}
