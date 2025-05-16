package models;

/*
 * Exercise class representing a workout exercise.
 */
public class Exercise {
    private final String name;
    private final String Type;
    private final String MuscleGroup;
    private final int repetitions;
    private final int sets;
    
    public Exercise(String name, String type, String muscleGroup, int repetitions, int sets) {
        this.name = name;
        Type = type;
        MuscleGroup = muscleGroup;
        this.repetitions = repetitions;
        this.sets = sets;
    }

    // Getters for exercise properties
    public String getName() {
        return name;
    }

    // Getters for exercise properties
    public String getType() {
        return Type;
    }

    // Getters for exercise properties
    public String getMuscleGroup() {
        return MuscleGroup;
    }

    // Getters for exercise properties
    public int getRepetitions() {
        return repetitions;
    }

    // Getters for exercise properties
    public int getSets() {
        return sets;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s: %d reps, %d sets", name, Type, MuscleGroup, repetitions, sets);
    }
}
