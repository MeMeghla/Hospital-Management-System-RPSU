import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles patient, doctor and appointment operations (CRUD).
 * Loads records from text files when created and saves after every change.
 */
public class HospitalService {

    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Appointment> appointments;

    public HospitalService() {
        patients = new ArrayList<Patient>();
        doctors = new ArrayList<Doctor>();
        appointments = new ArrayList<Appointment>();
        FileManager.createFolderAndFiles();
        loadAll();
    }

    // ---------------- Load / Save ----------------

    private void loadAll() {
        loadPatients();
        loadDoctors();
        loadAppointments();
    }

    private void loadPatients() {
        patients.clear();
        List<String> lines = FileManager.readAllLines(FileManager.PATIENT_FILE);
        for (String line : lines) {
            Patient p = Patient.fromFileString(line);
            if (p != null) {
                patients.add(p);
            }
        }
    }

    private void loadDoctors() {
        doctors.clear();
        List<String> lines = FileManager.readAllLines(FileManager.DOCTOR_FILE);
        for (String line : lines) {
            Doctor d = Doctor.fromFileString(line);
            if (d != null) {
                doctors.add(d);
            }
        }
    }

    private void loadAppointments() {
        appointments.clear();
        List<String> lines = FileManager.readAllLines(FileManager.APPOINTMENT_FILE);
        for (String line : lines) {
            Appointment a = Appointment.fromFileString(line);
            if (a != null) {
                appointments.add(a);
            }
        }
    }

    private void savePatients() {
        List<String> lines = new ArrayList<String>();
        for (Patient p : patients) {
            lines.add(p.toFileString());
        }
        FileManager.writeAllLines(FileManager.PATIENT_FILE, lines);
    }

    private void saveDoctors() {
        List<String> lines = new ArrayList<String>();
        for (Doctor d : doctors) {
            lines.add(d.toFileString());
        }
        FileManager.writeAllLines(FileManager.DOCTOR_FILE, lines);
    }

    private void saveAppointments() {
        List<String> lines = new ArrayList<String>();
        for (Appointment a : appointments) {
            lines.add(a.toFileString());
        }
        FileManager.writeAllLines(FileManager.APPOINTMENT_FILE, lines);
    }

    // ---------------- Patient operations ----------------

    public boolean addPatient(Patient patient) {
        if (patient == null || findPatientById(patient.getId()) != null) {
            return false; // null or duplicate ID
        }
        patients.add(patient);
        savePatients();
        return true;
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<Patient>(patients);
    }

    public Patient findPatientById(String id) {
        if (id == null) {
            return null;
        }
        for (Patient p : patients) {
            if (p.getId().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    public boolean updatePatient(String id, String newName, int newAge, String newGender, String newContact) {
        Patient p = findPatientById(id);
        if (p == null) {
            return false;
        }
        p.setName(newName);
        p.setAge(newAge);
        p.setGender(newGender);
        p.setContact(newContact);
        savePatients();
        return true;
    }

    public boolean deletePatient(String id) {
        Patient p = findPatientById(id);
        if (p == null) {
            return false;
        }
        patients.remove(p);
        savePatients();
        return true;
    }

    // ---------------- Doctor operations ----------------

    public boolean addDoctor(Doctor doctor) {
        if (doctor == null || findDoctorById(doctor.getId()) != null) {
            return false; // null or duplicate ID
        }
        doctors.add(doctor);
        saveDoctors();
        return true;
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<Doctor>(doctors);
    }

    public Doctor findDoctorById(String id) {
        if (id == null) {
            return null;
        }
        for (Doctor d : doctors) {
            if (d.getId().equalsIgnoreCase(id.trim())) {
                return d;
            }
        }
        return null;
    }

    public boolean deleteDoctor(String id) {
        Doctor d = findDoctorById(id);
        if (d == null) {
            return false;
        }
        doctors.remove(d);
        saveDoctors();
        return true;
    }

    // ---------------- Appointment operations ----------------

    /**
     * Book a new appointment. Generates a unique appointment ID automatically.
     * Returns the created Appointment, or null if patient/doctor missing or date invalid.
     */
    public Appointment bookAppointment(String patientId, String doctorId, String date) {
        if (findPatientById(patientId) == null) {
            return null; // patient must exist
        }
        if (findDoctorById(doctorId) == null) {
            return null; // doctor must exist
        }
        if (!isValidDate(date)) {
            return null; // date must be valid (yyyy-MM-dd)
        }
        String newId = generateAppointmentId();
        Appointment appointment = new Appointment(newId, patientId.trim(), doctorId.trim(), date.trim());
        appointments.add(appointment);
        saveAppointments();
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<Appointment>(appointments);
    }

    public Appointment findAppointmentById(String id) {
        if (id == null) {
            return null;
        }
        for (Appointment a : appointments) {
            if (a.getId().equalsIgnoreCase(id.trim())) {
                return a;
            }
        }
        return null;
    }

    public boolean cancelAppointment(String id) {
        Appointment a = findAppointmentById(id);
        if (a == null) {
            return false;
        }
        appointments.remove(a);
        saveAppointments();
        return true;
    }

    // ---------------- Helpers ----------------

    /**
     * Generate next appointment ID like A001, A002, ...
     * Looks at existing IDs to avoid duplicates.
     */
    private String generateAppointmentId() {
        int max = 0;
        for (Appointment a : appointments) {
            String id = a.getId();
            if (id.length() > 1 && (id.charAt(0) == 'A' || id.charAt(0) == 'a')) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException e) {
                    // ignore non-numeric IDs
                }
            }
        }
        int next = max + 1;
        if (next < 1000) {
            // Format as A001, A010, A100 ...
            if (next < 10) {
                return "A00" + next;
            } else if (next < 100) {
                return "A0" + next;
            } else {
                return "A" + next;
            }
        }
        return "A" + next;
    }

    /**
     * Check date format yyyy-MM-dd and that it is a real calendar date.
     */
    public boolean isValidDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(dateStr.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
