package models;

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

    public String getName() {
        return name;
    }

    public String getType() {
        return Type;
    }

    public String getMuscleGroup() {
        return MuscleGroup;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public int getSets() {
        return sets;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s: %d reps, %d sets", name, Type, MuscleGroup, repetitions, sets);
    }
}
