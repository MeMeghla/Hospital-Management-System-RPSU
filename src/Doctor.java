/**
 * Represents a doctor in the Hospital Management System.
 * Demonstrates basic OOP: private fields, constructor, getters, setters.
 */
public class Doctor {
    private String id;
    private String name;
    private String specialization;
    private String contact;

    public Doctor(String id, String name, String specialization, String contact) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContact() {
        return contact;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Convert doctor to a line for storage in doctors.txt.
     * Format: id|name|specialization|contact
     */
    public String toFileString() {
        return clean(id) + "|" + clean(name) + "|" + clean(specialization) + "|" + clean(contact);
    }

    /**
     * Create a Doctor object from a line in doctors.txt.
     * Returns null if the line is invalid.
     */
    public static Doctor fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("\\|", -1);
        if (parts.length != 4) {
            return null;
        }
        String id = parts[0].trim();
        String name = parts[1].trim();
        String specialization = parts[2].trim();
        String contact = parts[3].trim();
        if (id.isEmpty() || name.isEmpty()) {
            return null;
        }
        return new Doctor(id, name, specialization, contact);
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
        return "ID: " + id + ", Name: " + name
                + ", Specialization: " + specialization + ", Contact: " + contact;
    }
}
