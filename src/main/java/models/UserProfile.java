package models;

/*
 * Exercise class representing a workout exercise.
 */
public class UserProfile {
    private final String name;
    private final String surname;
    private final int age;
    private final double weight;
    private final double height;

    public UserProfile(String name, String surname, int age, double weight, double height) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    // Getters for user profile properties
    public String getName() {
        return name;
    }

    // Getters for user profile properties
    public String getSurname() {
        return surname;
    }

    // Getters for user profile properties
    public int getAge() {
        return age;
    }

    // Getters for user profile properties
    public double getWeight() {
        return weight;
    }

    // Getters for user profile properties
    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + age + " " + weight + " " + height;
    }
}
