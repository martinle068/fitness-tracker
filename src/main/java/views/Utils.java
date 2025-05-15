package views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Exercise;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    
    public static void saveWorkoutsToFile(Map<UserProfile, Map<LocalDate, List<Exercise>>> workoutsPerUser) {
        JSONArray usersArray = new JSONArray();

        for (Map.Entry<UserProfile, Map<LocalDate, List<Exercise>>> entry : workoutsPerUser.entrySet()) {
            JSONObject userObject = new JSONObject();
            userObject.put("user", entry.getKey().getName() + " " + entry.getKey().getSurname()); // or getId()

            JSONArray datesArray = new JSONArray();
            for (Map.Entry<LocalDate, List<Exercise>> dateEntry : entry.getValue().entrySet()) {
                JSONObject dateObject = new JSONObject();
                dateObject.put("date", dateEntry.getKey().toString());

                JSONArray exercisesArray = new JSONArray();
                for (Exercise ex : dateEntry.getValue()) {
                    JSONObject exJson = new JSONObject();
                    exJson.put("name", ex.getName());
                    exJson.put("type", ex.getType());
                    exJson.put("muscleGroup", ex.getMuscleGroup());
                    exJson.put("repetitions", ex.getRepetitions());
                    exJson.put("sets", ex.getSets());
                    exercisesArray.put(exJson);
                }

                dateObject.put("exercises", exercisesArray);
                datesArray.put(dateObject);
            }

            userObject.put("workouts", datesArray);
            usersArray.put(userObject);
        }

        try {
            Files.writeString(Path.of(Utils.WORKOUTS_PATH), usersArray.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static UserProfile findUserByUsername(Map<UserProfile, Map<LocalDate, List<Exercise>>> workoutsPerUser, String name, String surname) {
        for (UserProfile user : workoutsPerUser.keySet()) {
            if (user.getName().equals(name) && user.getSurname().equals(surname)) {
                return user;
            }
        }
        return null;

    }

    public static void loadWorkoutsFromFile(Map<UserProfile, Map<LocalDate, List<Exercise>>> workoutsPerUser) {
        Path path = Path.of(Utils.WORKOUTS_PATH);
        if (!Files.exists(path))
            return;

        try {
            String content = Files.readString(path);
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                String name;
                String surname;
                String[] nameParts = userObject.getString("user").split(" ");
                if (nameParts.length == 2) {
                    name = nameParts[0];
                    surname = nameParts[1];
                } else {
                    continue; // Skip if the format is unexpected
                }

                UserProfile user = findUserByUsername(workoutsPerUser,name, surname);
                if (user == null)
                    continue;

                Map<LocalDate, List<Exercise>> userWorkouts = workoutsPerUser.computeIfAbsent(user,
                        k -> new HashMap<>());

                JSONArray datesArray = userObject.getJSONArray("workouts");
                for (int j = 0; j < datesArray.length(); j++) {
                    JSONObject dateObject = datesArray.getJSONObject(j);
                    LocalDate date = LocalDate.parse(dateObject.getString("date"));

                    List<Exercise> exercises = new ArrayList<>();
                    JSONArray exArray = dateObject.getJSONArray("exercises");
                    for (int k = 0; k < exArray.length(); k++) {
                        JSONObject exJson = exArray.getJSONObject(k);
                        Exercise ex = new Exercise(
                                exJson.getString("name"),
                                exJson.getString("type"),
                                exJson.getString("muscleGroup"),
                                exJson.getInt("repetitions"),
                                exJson.getInt("sets"));
                        exercises.add(ex);
                    }

                    userWorkouts.put(date, exercises);
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    
}
