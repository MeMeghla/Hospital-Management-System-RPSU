/**
 * Represents a patient in the Hospital Management System.
 * Demonstrates basic OOP: private fields, constructor, getters, setters.
 */
public class Patient {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String contact;

    public Patient(String id, String name, int age, String gender, String contact) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContact() {
        return contact;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Convert patient to a line for storage in patients.txt.
     * Format: id|name|age|gender|contact
     */
    public String toFileString() {
        return clean(id) + "|" + clean(name) + "|" + age + "|" + clean(gender) + "|" + clean(contact);
    }

    /**
     * Create a Patient object from a line in patients.txt.
     * Returns null if the line is invalid.
     */
    public static Patient fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("\\|", -1);
        if (parts.length != 5) {
            return null;
        }
        try {
            String id = parts[0].trim();
            String name = parts[1].trim();
            int age = Integer.parseInt(parts[2].trim());
            String gender = parts[3].trim();
            String contact = parts[4].trim();
            if (id.isEmpty() || name.isEmpty() || age <= 0) {
                return null;
            }
            return new Patient(id, name, age, gender, contact);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Remove '|' character so it does not break the file format.
    private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("|", " ").trim();
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age
                + ", Gender: " + gender + ", Contact: " + contact;
    }
}
