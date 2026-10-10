package rt4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Persists only the last successfully authenticated username.
 *
 * Passwords are never written to disk.
 */
public final class ModernLoginIdentityStore {
    private static final String DIRECTORY = ".osrs-client-killer";
    private static final String FILE_NAME = "last-user.txt";

    private ModernLoginIdentityStore() {
    }

    public static String loadLastUser() {
        Path path = path();
        try {
            if (!Files.isRegularFile(path)) {
                return "";
            }

            String value = new String(
                Files.readAllBytes(path),
                StandardCharsets.UTF_8
            );
            return sanitize(value);
        } catch (IOException | SecurityException ex) {
            DisplayDebug.log(
                "MODERN_UI login identity load failed type="
                    + ex.getClass().getSimpleName()
            );
            return "";
        }
    }

    public static void recordSuccessfulLogin(JagString username) {
        if (username == null) {
            return;
        }

        String value = sanitize(username.toString());
        if (value.isEmpty()) {
            return;
        }

        Path path = path();
        Path directory = path.getParent();
        Path temp = directory.resolve(FILE_NAME + ".tmp");

        try {
            Files.createDirectories(directory);
            Files.write(
                temp,
                value.getBytes(StandardCharsets.UTF_8)
            );
            try {
                Files.move(
                    temp,
                    path,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
                );
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(
                    temp,
                    path,
                    StandardCopyOption.REPLACE_EXISTING
                );
            }

            DisplayDebug.log(
                "MODERN_UI saved last successful login user"
            );
        } catch (IOException | SecurityException ex) {
            DisplayDebug.log(
                "MODERN_UI login identity save failed type="
                    + ex.getClass().getSimpleName()
            );
            try {
                Files.deleteIfExists(temp);
            } catch (IOException ignored) {
            }
        }
    }

    private static Path path() {
        String home = System.getProperty("user.home", ".");
        return Paths.get(home, DIRECTORY, FILE_NAME);
    }

    private static String sanitize(String value) {
        if (value == null) {
            return "";
        }

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < value.length() && out.length() < 64; i++) {
            char ch = value.charAt(i);
            if (ch >= 32 && ch != 127 && ch != '\r' && ch != '\n') {
                out.append(ch);
            }
        }
        return out.toString().trim();
    }
}
