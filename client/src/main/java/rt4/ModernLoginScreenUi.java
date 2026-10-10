package rt4;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Complete Modern replacement for the username/password login form.
 *
 * Vanilla widgets are used only as action/state backends. Their sprites,
 * fonts, positions and sizes are never rendered.
 */
public final class ModernLoginScreenUi {
    private static boolean logged;

    private ModernLoginScreenUi() {
    }

    public static boolean isLoginScreenActive(Component[] components) {
        return discover(components, 0, 0) != null;
    }

    public static void prepareInput(
        Component[] components,
        int parentX,
        int parentY
    ) {
        Backend backend = discover(components, parentX, parentY);
        if (backend == null) {
            return;
        }

        ModernLoginScreenLayout layout =
            ModernLoginScreenLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        ModernUiRect clip = layout.panel;

        if (backend.usernameAction != null) {
            ModernUiInputRouter.bind(
                backend.usernameAction,
                layout.usernameInput,
                clip
            );
        }
        if (backend.passwordAction != null) {
            ModernUiInputRouter.bind(
                backend.passwordAction,
                layout.passwordInput,
                clip
            );
        }
        if (backend.loginAction != null) {
            ModernUiInputRouter.bind(
                backend.loginAction,
                layout.loginButton,
                clip
            );
        }
        if (backend.mainMenuAction != null) {
            ModernUiInputRouter.bind(
                backend.mainMenuAction,
                layout.mainMenuButton,
                clip
            );
        }
    }

    public static void render(
        Component[] components,
        int parentX,
        int parentY
    ) {
        Backend backend = discover(components, parentX, parentY);
        if (backend == null) {
            return;
        }

        ModernLoginScreenLayout layout =
            ModernLoginScreenLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        prepareInput(components, parentX, parentY);

        if (!logged) {
            logged = true;
            DisplayDebug.log(
                "MODERN_UI login-screen declarative"
                    + " panel=" + layout.panel
                    + " logo=" + layout.logo
                    + " username=" + layout.usernameInput
                    + " password=" + layout.passwordInput
                    + " login=" + layout.loginButton
                    + " mainMenu=" + layout.mainMenuButton
                    + " actions="
                    + idOf(backend.usernameAction) + ","
                    + idOf(backend.passwordAction) + ","
                    + idOf(backend.loginAction) + ","
                    + idOf(backend.mainMenuAction)
            );
        }

        drawAsset("main-menu/logo", layout.logo);
        drawAsset("audio-options/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        drawCentered(
            textOr(backend.title, "Please Log In"),
            layout.title,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_TITLE,
            ModernUiMetrics.TEXT_GOLD
        );

        drawCentered(
            textOr(
                backend.world,
                "World " + Math.max(1, client.worldListId)
            ),
            layout.world,
            ModernUiFontRegistry.PLAIN_11,
            ModernUiMetrics.FONT_CONTROL,
            ModernUiMetrics.TEXT_MUTED
        );

        drawCentered(
            textOr(
                backend.message,
                "Enter your username and password."
            ),
            layout.message,
            ModernUiFontRegistry.PLAIN_12,
            ModernUiMetrics.FONT_LABEL,
            ModernUiMetrics.TEXT_PRIMARY
        );

        drawCentered(
            "Username",
            layout.usernameLabel,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_SECTION,
            ModernUiMetrics.TEXT_PRIMARY
        );
        drawInput(
            layout.usernameInput,
            Player.usernameInput == null
                ? ""
                : Player.usernameInput.toString()
        );

        drawCentered(
            "Password",
            layout.passwordLabel,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_SECTION,
            ModernUiMetrics.TEXT_PRIMARY
        );
        drawInput(
            layout.passwordInput,
            maskPassword()
        );

        drawButton(
            layout.loginButton,
            textOr(backend.login, "Log In")
        );
        drawButton(
            layout.mainMenuButton,
            textOr(backend.mainMenu, "Main Menu")
        );
    }

    private static void drawInput(
        ModernUiRect rect,
        String value
    ) {
        boolean hover = rect.contains(
            Mouse.lastMouseX,
            Mouse.lastMouseY
        );

        drawAsset(
            hover
                ? "controls/button-active"
                : "controls/button",
            rect
        );

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_12,
            value == null ? "" : value,
            rect.x + 12,
            rect.y,
            Math.max(1, rect.width - 24),
            rect.height,
            ModernUiMetrics.TEXT_PRIMARY,
            0,
            1,
            ModernUiMetrics.FONT_DROPDOWN,
            false
        );
    }

    private static void drawButton(
        ModernUiRect rect,
        String text
    ) {
        boolean hover = rect.contains(
            Mouse.lastMouseX,
            Mouse.lastMouseY
        );

        drawAsset(
            hover
                ? "audio-options/button-active"
                : "audio-options/button",
            rect
        );

        drawCentered(
            text,
            rect,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_BUTTON,
            ModernUiMetrics.TEXT_PRIMARY
        );
    }

    private static void drawCentered(
        String text,
        ModernUiRect rect,
        String font,
        float size,
        int color
    ) {
        ModernTrueTypeFont.drawInBox(
            font,
            text == null ? "" : text,
            rect.x,
            rect.y,
            rect.width,
            rect.height,
            color,
            1,
            1,
            size,
            false
        );
    }

    private static void drawAsset(
        String path,
        ModernUiRect rect
    ) {
        ModernUiImage image = ModernUiAssetResolver.get(
            path,
            rect.width,
            rect.height
        );
        if (image != null) {
            image.render(rect.x, rect.y);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:" + path,
                rect.x,
                rect.y,
                rect.width,
                rect.height
            );
        }
    }

    private static String maskPassword() {
        int length =
            Player.password == null
                ? 0
                : Player.password.length();
        if (length <= 0) {
            return "";
        }

        StringBuilder out =
            new StringBuilder(Math.min(length, 64));
        for (int i = 0; i < length && i < 64; i++) {
            out.append('*');
        }
        return out.toString();
    }

    private static Backend discover(
        Component[] components,
        int parentX,
        int parentY
    ) {
        List<Entry> entries = new ArrayList<>();
        collect(
            components,
            -1,
            parentX,
            parentY,
            entries
        );

        TextEntry title = find(entries, Kind.TITLE);
        TextEntry username = find(entries, Kind.USERNAME);
        TextEntry password = find(entries, Kind.PASSWORD);
        TextEntry login = find(entries, Kind.LOGIN);
        TextEntry mainMenu = find(entries, Kind.MAIN_MENU);

        if (title == null
            || username == null
            || password == null
            || login == null
            || mainMenu == null) {
            return null;
        }

        TextEntry world = find(entries, Kind.WORLD);
        TextEntry message = find(entries, Kind.MESSAGE);

        Component usernameAction =
            findFieldAction(
                entries,
                username,
                password
            );
        Component passwordAction =
            findFieldAction(
                entries,
                password,
                login
            );
        Component loginAction =
            actionForText(entries, login);
        Component mainMenuAction =
            actionForText(entries, mainMenu);

        return new Backend(
            title,
            world,
            message,
            username,
            password,
            login,
            mainMenu,
            usernameAction,
            passwordAction,
            loginAction,
            mainMenuAction
        );
    }

    private static Component findFieldAction(
        List<Entry> entries,
        TextEntry label,
        TextEntry next
    ) {
        int targetX = label.rect.centerX();
        int minY = label.rect.bottom() - 2;
        int maxY = Math.max(minY + 20, next.rect.y - 2);

        Entry best = null;
        int bestScore = Integer.MAX_VALUE;

        for (Entry entry : entries) {
            Component component = entry.component;
            if (!isInteractive(component)
                || component == label.component) {
                continue;
            }

            int width = Math.max(1, entry.rect.width);
            int height = Math.max(1, entry.rect.height);
            int cy = entry.rect.centerY();

            if (cy < minY || cy >= maxY
                || width < 40 || width > 420
                || height < 10 || height > 100) {
                continue;
            }

            int dx = Math.abs(entry.rect.centerX() - targetX);
            if (dx > 220) {
                continue;
            }

            int sizePenalty =
                Math.abs(width - 180) / 3
                    + Math.abs(height - 32) * 2;
            int typePenalty =
                component.buttonType != 0
                    ? 0
                    : component.hasEventHandlers
                        ? 15
                        : 40;
            int score = dx + sizePenalty + typePenalty;

            if (score < bestScore) {
                bestScore = score;
                best = entry;
            }
        }

        return best == null ? null : best.component;
    }

    private static Component actionForText(
        List<Entry> entries,
        TextEntry text
    ) {
        if (isInteractive(text.component)) {
            return text.component;
        }

        Entry best = null;
        int bestScore = Integer.MAX_VALUE;

        for (Entry entry : entries) {
            if (!isInteractive(entry.component)) {
                continue;
            }

            int width = Math.max(1, entry.rect.width);
            int height = Math.max(1, entry.rect.height);
            if (width > 360 || height > 100) {
                continue;
            }

            int dx =
                Math.abs(
                    entry.rect.centerX()
                        - text.rect.centerX()
                );
            int dy =
                Math.abs(
                    entry.rect.centerY()
                        - text.rect.centerY()
                );
            if (dx > 180 || dy > 50) {
                continue;
            }

            int priority =
                entry.component.buttonType != 0
                    ? 0
                    : entry.component.hasEventHandlers
                        ? 20
                        : 45;
            int score = dx + dy * 2 + priority;
            if (score < bestScore) {
                bestScore = score;
                best = entry;
            }
        }

        return best == null ? null : best.component;
    }

    private static boolean isInteractive(Component component) {
        return component != null
            && (component.buttonType != 0
                || component.hasEventHandlers
                || component.clientCode != 0
                || InterfaceList.getServerActiveProperties(
                    component
                ).events != 0);
    }

    private static void collect(
        Component[] components,
        int layer,
        int parentX,
        int parentY,
        List<Entry> out
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null
                || component.overlayer != layer) {
                continue;
            }
            if (component.if3
                && InterfaceList.isHidden(component)) {
                continue;
            }
            if (component.type == 0
                && !component.if3
                && InterfaceList.isHidden(component)
                && InterfaceList.hoveredComponent != component) {
                continue;
            }

            int x = parentX + component.x;
            int y = parentY + component.y;
            ModernUiRect rect = new ModernUiRect(
                x,
                y,
                Math.max(1, component.width),
                Math.max(1, component.height)
            );

            String text = currentText(component);
            out.add(
                new Entry(
                    component,
                    rect,
                    text
                )
            );

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;
                collect(
                    components,
                    component.id,
                    childX,
                    childY,
                    out
                );
                if (component.createdComponents != null) {
                    collect(
                        component.createdComponents,
                        component.id,
                        childX,
                        childY,
                        out
                    );
                }
            }
        }
    }

    private static String currentText(Component component) {
        if (component.type != 4
            && component.type != 8) {
            return "";
        }

        JagString current = component.text;
        if (Cs1ScriptRunner.isTrue(component)
            && component.activeText != null
            && component.activeText.length() > 0) {
            current = component.activeText;
        }
        if (!component.if3 && current != null) {
            current = Cs1ScriptRunner.interpolate(
                component,
                current
            );
        }

        return current == null
            ? ""
            : current.toString();
    }

    private static TextEntry find(
        List<Entry> entries,
        Kind kind
    ) {
        for (Entry entry : entries) {
            if (entry.text.isEmpty()) {
                continue;
            }

            String normalized = normalize(entry.text);
            if (kind.matches(normalized)) {
                return new TextEntry(
                    entry.component,
                    entry.rect,
                    display(entry.text)
                );
            }
        }
        return null;
    }

    private static String textOr(
        TextEntry entry,
        String fallback
    ) {
        return entry == null
            || entry.text == null
            || entry.text.isEmpty()
                ? fallback
                : entry.text;
    }

    private static String display(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder out = new StringBuilder(text.length());
        boolean insideTag = false;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '<') {
                insideTag = true;
                continue;
            }
            if (ch == '>' && insideTag) {
                insideTag = false;
                out.append(' ');
                continue;
            }
            if (!insideTag) {
                out.append(
                    ch == '\n' || ch == '\r' || ch == '\t'
                        ? ' '
                        : ch
                );
            }
        }

        return out.toString()
            .trim()
            .replaceAll("\\s+", " ");
    }

    private static String normalize(String text) {
        return display(text)
            .toLowerCase(Locale.ROOT);
    }

    private static int idOf(Component component) {
        return component == null ? -1 : component.id;
    }

    private enum Kind {
        TITLE {
            @Override
            boolean matches(String text) {
                return text.equals("please log in")
                    || text.equals("please login");
            }
        },
        WORLD {
            @Override
            boolean matches(String text) {
                return text.startsWith("world ");
            }
        },
        MESSAGE {
            @Override
            boolean matches(String text) {
                return text.contains("enter your username")
                    || text.contains("username and password");
            }
        },
        USERNAME {
            @Override
            boolean matches(String text) {
                return text.equals("username")
                    || text.equals("username:");
            }
        },
        PASSWORD {
            @Override
            boolean matches(String text) {
                return text.equals("password")
                    || text.equals("password:");
            }
        },
        LOGIN {
            @Override
            boolean matches(String text) {
                return text.equals("log in")
                    || text.equals("login");
            }
        },
        MAIN_MENU {
            @Override
            boolean matches(String text) {
                return text.equals("main menu");
            }
        };

        abstract boolean matches(String text);
    }

    private static final class Backend {
        private final TextEntry title;
        private final TextEntry world;
        private final TextEntry message;
        private final TextEntry username;
        private final TextEntry password;
        private final TextEntry login;
        private final TextEntry mainMenu;
        private final Component usernameAction;
        private final Component passwordAction;
        private final Component loginAction;
        private final Component mainMenuAction;

        private Backend(
            TextEntry title,
            TextEntry world,
            TextEntry message,
            TextEntry username,
            TextEntry password,
            TextEntry login,
            TextEntry mainMenu,
            Component usernameAction,
            Component passwordAction,
            Component loginAction,
            Component mainMenuAction
        ) {
            this.title = title;
            this.world = world;
            this.message = message;
            this.username = username;
            this.password = password;
            this.login = login;
            this.mainMenu = mainMenu;
            this.usernameAction = usernameAction;
            this.passwordAction = passwordAction;
            this.loginAction = loginAction;
            this.mainMenuAction = mainMenuAction;
        }
    }

    private static final class Entry {
        private final Component component;
        private final ModernUiRect rect;
        private final String text;

        private Entry(
            Component component,
            ModernUiRect rect,
            String text
        ) {
            this.component = component;
            this.rect = rect;
            this.text = text == null ? "" : text;
        }
    }

    private static final class TextEntry {
        private final Component component;
        private final ModernUiRect rect;
        private final String text;

        private TextEntry(
            Component component,
            ModernUiRect rect,
            String text
        ) {
            this.component = component;
            this.rect = rect;
            this.text = text;
        }
    }
}
