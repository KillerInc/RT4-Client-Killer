package rt4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Separate desktop window used to inspect and configure Modern UI styles.
 */
public final class StyleEditorWindow {
    private static JFrame frame;

    private StyleEditorWindow() {
    }

    public static void openWindow() {
        if (!ModernUiManager.isEnabled()) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            if (frame != null) {
                frame.setVisible(true);
                frame.toFront();
                frame.requestFocus();
                return;
            }
            buildWindow();
        });
    }

    public static void closeWindow() {
        SwingUtilities.invokeLater(() -> {
            if (frame != null) {
                frame.dispose();
                frame = null;
            }
        });
    }

    private static void buildWindow() {
        ModernUiManager.refreshStyles();

        JFrame window = new JFrame("OSRS Client Killer Edition - Style Editor");
        frame = window;
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        window.setMinimumSize(new Dimension(680, 520));
        window.setSize(760, 620);
        window.setLocationRelativeTo(GameShell.frame);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        window.setContentPane(root);

        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        List<UiStyleInfo> styles = UiStyleRepository.getStyles();
        JComboBox<UiStyleInfo> styleBox = new JComboBox<>(styles.toArray(new UiStyleInfo[0]));
        UiStyleInfo effective = UiStyleRepository.getEffectiveStyle(ModernUiPreferences.getStyleId());
        styleBox.setSelectedItem(effective);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        top.add(new JLabel("Active Style:"), c);
        c.gridx = 1;
        c.weightx = 1;
        top.add(styleBox, c);

        JComboBox<String> uiScale = scaleBox(ModernUiPreferences.getUiScale());
        JComboBox<String> textScale = scaleBox(ModernUiPreferences.getTextScale());
        JComboBox<String> iconScale = scaleBox(ModernUiPreferences.getIconScale());

        addScaleRow(top, c, 1, "UI Scale:", uiScale);
        addScaleRow(top, c, 2, "Text Scale:", textScale);
        addScaleRow(top, c, 3, "Icon Scale:", iconScale);

        root.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel addonPanel = new JPanel();
        addonPanel.setLayout(new BoxLayout(addonPanel, BoxLayout.Y_AXIS));
        Set<String> enabledAddons = ModernUiPreferences.getEnabledAddons();
        String activeStyleId = effective.id;

        for (UiStyleInfo addon : UiStyleRepository.getAddons()) {
            JCheckBox check = new JCheckBox(addon.name + "  " + addon.version);
            check.setSelected(enabledAddons.contains(addon.id));
            boolean requirementsOk = UiStyleRepository.requirementsSatisfied(addon, activeStyleId, enabledAddons);
            check.setEnabled(requirementsOk);
            if (!requirementsOk) {
                check.setToolTipText("Requires: " + addon.requires);
            }
            check.addActionListener(e -> ModernUiManager.setAddonEnabled(addon.id, check.isSelected()));
            addonPanel.add(check);
        }
        if (UiStyleRepository.getAddons().isEmpty()) {
            addonPanel.add(new JLabel("No add-on style archives installed."));
        }

        JPanel addonsContainer = new JPanel(new BorderLayout());
        addonsContainer.setBorder(BorderFactory.createTitledBorder("UI Add-ons"));
        addonsContainer.add(new JScrollPane(addonPanel), BorderLayout.CENTER);
        center.add(addonsContainer);

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setLineWrap(true);
        details.setWrapStyleWord(true);
        JPanel detailsContainer = new JPanel(new BorderLayout());
        detailsContainer.setBorder(BorderFactory.createTitledBorder("Style Information / Diagnostics"));
        detailsContainer.add(new JScrollPane(details), BorderLayout.CENTER);
        center.add(detailsContainer);
        root.add(center, BorderLayout.CENTER);

        Runnable updateDetails = () -> {
            UiStyleInfo selected = (UiStyleInfo) styleBox.getSelectedItem();
            StringBuilder text = new StringBuilder();
            if (!ModernUiPreferences.getStyleId().equals(effective.id)) {
                text.append("Saved style '").append(ModernUiPreferences.getStyleId())
                    .append("' is unavailable. Killer Modern UI is being used temporarily.\n\n");
            }
            if (selected != null) {
                text.append(selected.name).append("\n")
                    .append("ID: ").append(selected.id).append("\n")
                    .append("Version: ").append(selected.version).append("\n")
                    .append("Author: ").append(selected.author).append("\n")
                    .append("Base: ").append(selected.base).append("\n");
                if (!selected.description.isEmpty()) {
                    text.append("\n").append(selected.description).append("\n");
                }
            }
            List<String> diagnostics = UiStyleRepository.getDiagnostics();
            if (!diagnostics.isEmpty()) {
                text.append("\nArchive diagnostics:\n");
                for (String diagnostic : diagnostics) {
                    text.append(" - ").append(diagnostic).append("\n");
                }
            }
            details.setText(text.toString());
            details.setCaretPosition(0);
        };
        updateDetails.run();

        styleBox.addActionListener(e -> {
            UiStyleInfo selected = (UiStyleInfo) styleBox.getSelectedItem();
            if (selected != null) {
                ModernUiManager.selectStyle(selected.id);
                updateDetails.run();
            }
        });
        uiScale.addActionListener(e -> ModernUiManager.setUiScale(parseScale(uiScale)));
        textScale.addActionListener(e -> ModernUiManager.setTextScale(parseScale(textScale)));
        iconScale.addActionListener(e -> ModernUiManager.setIconScale(parseScale(iconScale)));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton openFolder = new JButton("Open Styles Folder");
        JButton reload = new JButton("Reload Styles");
        JButton validate = new JButton("Validate Styles");
        JButton close = new JButton("Close");

        openFolder.addActionListener(e -> openStylesFolder());
        reload.addActionListener(e -> {
            ModernUiManager.refreshStyles();
            frame.dispose();
            frame = null;
            openWindow();
        });
        validate.addActionListener(e -> updateDetails.run());
        close.addActionListener(e -> window.dispose());

        buttons.add(openFolder);
        buttons.add(reload);
        buttons.add(validate);
        buttons.add(close);
        root.add(buttons, BorderLayout.SOUTH);

        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (frame == window) {
                    frame = null;
                }
            }
        });

        window.setVisible(true);
    }

    private static JComboBox<String> scaleBox(float current) {
        String[] values = {"50%", "75%", "90%", "100%", "110%", "125%", "150%", "175%", "200%", "250%", "300%", "400%"};
        JComboBox<String> box = new JComboBox<>(values);
        String selected = Math.round(current * 100.0F) + "%";
        box.setSelectedItem(selected);
        if (box.getSelectedIndex() < 0) {
            box.addItem(selected);
            box.setSelectedItem(selected);
        }
        return box;
    }

    private static void addScaleRow(JPanel panel, GridBagConstraints c, int row, String label, JComboBox<String> box) {
        c.gridy = row;
        c.gridx = 0;
        c.weightx = 0;
        panel.add(new JLabel(label), c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(box, c);
    }

    private static float parseScale(JComboBox<String> box) {
        Object value = box.getSelectedItem();
        if (value == null) {
            return 1.0F;
        }
        String text = value.toString().replace("%", "").trim();
        try {
            return Float.parseFloat(text) / 100.0F;
        } catch (NumberFormatException ignored) {
            return 1.0F;
        }
    }

    private static void openStylesFolder() {
        File folder = ModernUiPreferences.getStylesDirectory();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(folder);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Unable to open folder", JOptionPane.ERROR_MESSAGE);
        }
    }
}
