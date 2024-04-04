package aplikasi_notes;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;

public class NotesAppSource {

    private NotesAppGUI desain;

    public NotesAppSource(NotesAppGUI desain) {
        this.desain = desain;
        addListeners();
    }

    private void addListeners() {
        desain.getOpenMenuItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        desain.getSaveMenuItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        desain.getFileInfoMenuItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFileInfo();
            }
        });

        desain.getScreenshotMenuItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                takeScreenshot();
            }
        });

        desain.getGoToLineMenuItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToLine();
            }
        });

        desain.getColorBackgroundMenuItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setColorBackground();
            }
        });
        
        desain.getHighlightMenuItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                highlightSelectedText();
            }
        });
        
        
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(desain);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Path path = fileChooser.getSelectedFile().toPath();
            try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
                String text = lines.collect(Collectors.joining("\n"));
                desain.getNotesTextPane().setText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(desain);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Path path = fileChooser.getSelectedFile().toPath();
            try {
                Files.write(path, desain.getNotesTextPane().getText().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFileInfo() {
        String text = desain.getNotesTextPane().getText();
        long charCount = text.chars().filter(c -> c != '\n' && c != '\r').count();
        long wordCount = Stream.of(text.split("\\s+")).filter(word -> !word.isEmpty()).count();
        long sentenceCount = Stream.of(text.split("[.!?]")).filter(sentence -> !sentence.isEmpty()).count();

        JOptionPane.showMessageDialog(desain,
                "Jumlah Karakter: " + charCount + "\n"
                + "Jumlah Kata: " + wordCount + "\n"
                + "Jumlah Kalimat: " + sentenceCount,
                "Informasi File",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void setColorBackground() {
        Color selectedColor = JColorChooser.showDialog(desain, "Choose Background Color", Color.WHITE);
        if (selectedColor != null) {
            desain.getNotesTextPane().setBackground(selectedColor);
        }
    }

    private void highlightSelectedText() {
        Color selectedColor = JColorChooser.showDialog(desain, "Choose Highlight Color", Color.YELLOW);
        if (selectedColor != null) {
            StyledDocument doc = desain.getNotesTextPane().getStyledDocument();
            int start = desain.getNotesTextPane().getSelectionStart();
            int end = desain.getNotesTextPane().getSelectionEnd();
            if (start != end) {
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setBackground(attr, selectedColor);
                doc.setCharacterAttributes(start, end - start, attr, false);
            }
        }
    }

    private void takeScreenshot() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(screenSize));
            File file = new File("screenshot.png");
            ImageIO.write(image, "png", file);
            JOptionPane.showMessageDialog(desain, "Screenshot saved to: " + file.getAbsolutePath(), "Screenshot", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(desain, "Failed to take screenshot.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goToLine() {
        String input = JOptionPane.showInputDialog(desain, "Masukkan baris tujuan:", "Go to Line", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {
                int lineNumber = Integer.parseInt(input);
                Element root = desain.getNotesTextPane().getDocument().getDefaultRootElement();
                Element lineElement = root.getElement(lineNumber - 1);
                int startOffset = lineElement.getStartOffset();
                int endOffset = lineElement.getEndOffset();
                desain.getNotesTextPane().setCaretPosition(startOffset);
                desain.getNotesTextPane().moveCaretPosition(endOffset);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(desain, "Invalid line number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
