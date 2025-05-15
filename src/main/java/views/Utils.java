package views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import models.Exercise;

import javax.swing.*;

import models.UserProfile;

public class Utils {
    public static final String USER_PROFILES_PATH = "src/main/java/data/userProfiles.csv";
    public static final String EXERCISES_PATH = "src/main/java/data/exercises.csv";
    public static final String WORKOUTS_PATH = "src/main/java/data/workouts.json";

    static void setupModernUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set modern look and feel", e);
        }
    }

    public static void saveUserToCSV(String fileName, UserProfile userProfile) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(userProfile.getName()).append(",");
            writer.append(userProfile.getSurname()).append(",");
            writer.append(String.valueOf(userProfile.getAge())).append(",");
            writer.append(String.valueOf(userProfile.getWeight())).append(",");
            writer.append(String.valueOf(userProfile.getHeight())).append("\n");
        }
    }

    public static void saveUsersToCSV(String fileName, DefaultListModel<UserProfile> usersList) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < usersList.size(); i++) {
                UserProfile userProfile = usersList.getElementAt(i);
                writer.append(userProfile.getName()).append(",");
                writer.append(userProfile.getSurname()).append(",");
                writer.append(String.valueOf(userProfile.getAge())).append(",");
                writer.append(String.valueOf(userProfile.getWeight())).append(",");
                writer.append(String.valueOf(userProfile.getHeight())).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving users to CSV", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public static void updateUserInCSV(String fileName, UserProfile oldUser, UserProfile newUser) {
        try {
            File file = new File(fileName);
            if (!file.exists())
                return;

            List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));
            String oldLine = toCSV(oldUser);
            String newLine = toCSV(newUser);

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(oldLine)) {
                    lines.set(i, newLine);
                    break;
                }
            }

            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String toCSV(UserProfile user) {
        return String.join(",",
                user.getName(),
                user.getSurname(),
                String.valueOf(user.getAge()),
                String.valueOf(user.getWeight()),
                String.valueOf(user.getHeight()));
    }

    public static void saveExerciseToCSV(String fileName, Exercise exercise) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(exercise.getName()).append(",");
            writer.append(exercise.getType()).append(",");
            writer.append(exercise.getMuscleGroup()).append(",");
            writer.append(String.valueOf(exercise.getRepetitions())).append(",");
            writer.append(String.valueOf(exercise.getSets())).append("\n");
        }
    }

    public static void saveExercisesToCSV(String fileName, DefaultListModel<Exercise> exercisesList) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < exercisesList.size(); i++) {
                Exercise exercise = exercisesList.getElementAt(i);
                writer.append(exercise.getName()).append(",");
                writer.append(exercise.getType()).append(",");
                writer.append(exercise.getMuscleGroup()).append(",");
                writer.append(String.valueOf(exercise.getRepetitions())).append(",");
                writer.append(String.valueOf(exercise.getSets())).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving exercises to CSV", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void updateExerciseInCSV(String fileName, Exercise oldExercise, Exercise newExercise) {
        try {
            File file = new File(fileName);
            if (!file.exists())
                return;

            List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));
            String oldLine = toCSV(oldExercise);
            String newLine = toCSV(newExercise);

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(oldLine)) {
                    lines.set(i, newLine);
                    break;
                }
            }

            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static String toCSV(Exercise exercise) {
        return String.join(",",
                exercise.getName(),
                exercise.getType(),
                exercise.getMuscleGroup(),
                String.valueOf(exercise.getRepetitions()),
                String.valueOf(exercise.getSets()));
    }
    
    
}
