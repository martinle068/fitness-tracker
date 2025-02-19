package models;

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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + age + " " + weight + " " + height;
    }
}
