/**
 * Represents an appointment in the Hospital Management System.
 * An appointment links one patient and one doctor on a specific date.
 */
public class Appointment {
    private String id;
    private String patientId;
    private String doctorId;
    private String date; // stored as yyyy-MM-dd, e.g. 2026-05-20

    public Appointment(String id, String patientId, String doctorId, String date) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDate() {
        return date;
    }

    // Setters
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Convert appointment to a line for storage in appointments.txt.
     * Format: id|patientId|doctorId|date
     */
    public String toFileString() {
        return clean(id) + "|" + clean(patientId) + "|" + clean(doctorId) + "|" + clean(date);
    }

    /**
     * Create an Appointment object from a line in appointments.txt.
     * Returns null if the line is invalid.
     */
    public static Appointment fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("\\|", -1);
        if (parts.length != 4) {
            return null;
        }
        String id = parts[0].trim();
        String patientId = parts[1].trim();
        String doctorId = parts[2].trim();
        String date = parts[3].trim();
        if (id.isEmpty() || patientId.isEmpty() || doctorId.isEmpty() || date.isEmpty()) {
            return null;
        }
        return new Appointment(id, patientId, doctorId, date);
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
        return "ID: " + id + ", Patient ID: " + patientId
                + ", Doctor ID: " + doctorId + ", Date: " + date;
    }
}
