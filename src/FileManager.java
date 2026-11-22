import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all text-file reading and writing.
 * Files are stored in the "data" folder:
 *   data/patients.txt
 *   data/doctors.txt
 *   data/appointments.txt
 */
public class FileManager {

    public static final String DATA_FOLDER = "data";
    public static final String PATIENT_FILE = "data" + java.io.File.separator + "patients.txt";
    public static final String DOCTOR_FILE = "data" + java.io.File.separator + "doctors.txt";
    public static final String APPOINTMENT_FILE = "data" + java.io.File.separator + "appointments.txt";

    /**
     * Create the data folder and the three text files if they do not exist.
     * Safe to call every time the program starts.
     */
    public static void createFolderAndFiles() {
        try {
            Path folder = Paths.get(DATA_FOLDER);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }
            createFileIfMissing(PATIENT_FILE);
            createFileIfMissing(DOCTOR_FILE);
            createFileIfMissing(APPOINTMENT_FILE);
        } catch (IOException e) {
            System.out.println("Warning: could not create data files. " + e.getMessage());
        }
    }

    private static void createFileIfMissing(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    /**
     * Read all lines from a text file.
     * Returns an empty list if the file is missing or cannot be read.
     * Invalid lines are NOT filtered here; the caller decides what is valid.
     */
    public static List<String> readAllLines(String fileName) {
        List<String> lines = new ArrayList<String>();
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            return lines;
        }
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(path);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read file " + fileName + ". " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore close error
                }
            }
        }
        return lines;
    }

    /**
     * Overwrite a text file with the given lines.
     * Used after update/delete operations.
     */
    public static void writeAllLines(String fileName, List<String> lines) {
        Path path = Paths.get(fileName);
        BufferedWriter writer = null;
        try {
            if (path.getParent() != null && !Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            writer = Files.newBufferedWriter(path,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: could not write to file " + fileName + ". " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // ignore close error
                }
            }
        }
    }
}
