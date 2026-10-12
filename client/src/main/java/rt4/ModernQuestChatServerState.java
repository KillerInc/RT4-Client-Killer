package rt4;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * Client-side semantic Quest Chat state delivered by the server through the
 * reserved Modern RUN_CS2 id.
 */
public final class ModernQuestChatServerState {
    public static final int RUNSCRIPT_ID = 65001;

    private static boolean active;
    private static int speakerType;
    private static int speakerId;
    private static int expression;
    private static boolean canContinue;
    private static String speakerName = "";
    private static List<String> lines =
        Collections.emptyList();
    private static List<String> options =
        Collections.emptyList();

    private ModernQuestChatServerState() {
    }

    public static boolean handleRunScript(
        Object[] scriptArgs
    ) {
        if (scriptArgs == null
            || scriptArgs.length < 1
            || !(scriptArgs[0] instanceof Integer)
            || ((Integer) scriptArgs[0]) != RUNSCRIPT_ID) {
            return false;
        }

        if (scriptArgs.length < 2
            || scriptArgs[1] == null) {
            reset();
            return true;
        }

        parse(scriptArgs[1].toString());
        return true;
    }

    public static boolean isActive() {
        return active;
    }

    public static int getSpeakerType() {
        return speakerType;
    }

    public static int getSpeakerId() {
        return speakerId;
    }

    public static int getExpression() {
        return expression;
    }

    public static boolean canContinue() {
        return canContinue;
    }

    public static String getSpeakerName() {
        return speakerName;
    }

    public static List<String> getLines() {
        return new ArrayList<>(lines);
    }

    public static List<String> getOptions() {
        return new ArrayList<>(options);
    }

    public static void reset() {
        active = false;
        speakerType = 0;
        speakerId = -1;
        expression = -1;
        canContinue = false;
        speakerName = "";
        lines = Collections.emptyList();
        options = Collections.emptyList();
    }

    private static void parse(String payload) {
        try {
            String[] parts =
                payload.split("\\|", -1);
            if (parts.length < 2
                || Integer.parseInt(parts[0]) != 1) {
                reset();
                return;
            }

            if ("CLOSE".equals(parts[1])) {
                reset();
                return;
            }

            if (!"OPEN".equals(parts[1])
                || parts.length < 9) {
                reset();
                return;
            }

            int index = 2;
            speakerType =
                Integer.parseInt(parts[index++]);
            speakerId =
                Integer.parseInt(parts[index++]);
            expression =
                Integer.parseInt(parts[index++]);
            canContinue =
                Integer.parseInt(parts[index++]) != 0;
            speakerName =
                decode(parts[index++]);

            int lineCount =
                Integer.parseInt(parts[index++]);
            List<String> newLines =
                new ArrayList<>();
            for (int i = 0;
                i < lineCount
                    && index < parts.length;
                i++) {
                newLines.add(
                    decode(parts[index++])
                );
            }

            if (index >= parts.length) {
                reset();
                return;
            }

            int optionCount =
                Integer.parseInt(parts[index++]);
            List<String> newOptions =
                new ArrayList<>();
            for (int i = 0;
                i < optionCount
                    && index < parts.length;
                i++) {
                newOptions.add(
                    decode(parts[index++])
                );
            }

            lines = newLines;
            options = newOptions;
            active = true;

            DisplayDebug.log(
                "MODERN_UI quest-chat payload"
                    + " speakerType=" + speakerType
                    + " speakerId=" + speakerId
                    + " lines=" + lines.size()
                    + " options=" + options.size()
                    + " continue=" + canContinue
            );
        } catch (Throwable ex) {
            reset();
            DisplayDebug.log(
                "MODERN_UI quest-chat payload parse failed",
                ex
            );
        }
    }

    private static String decode(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        return new String(
            Base64.getDecoder().decode(value),
            StandardCharsets.UTF_8
        );
    }
}
