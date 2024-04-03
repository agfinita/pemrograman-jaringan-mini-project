package aplikasi_notes;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.TextAction;

public class NotesAppGUI extends JFrame {

    private JTextArea notesTextArea;
    private List<String> notes;

    public NotesAppGUI() {
        setTitle("Notes App");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Simple NotesApp");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);

        notesTextArea = new JTextArea();
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);

        JButton newButton = new JButton("Tambah Note");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newNote();
            }
        });

        JButton openButton = new JButton("Buka Note");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNote();
            }
        });

        JButton saveButton = new JButton("Simpan Note");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNote();
            }
        });

        JButton editButton = new JButton("Edit Note");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editNote();
            }
        });

        JButton deleteButton = new JButton("Hapus Note");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteNote();
            }
        });

        //format text
        JButton cutButton = new JButton("Cut");
        cutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notesTextArea.cut();
            }
        });

        JButton copyButton = new JButton("Copy");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notesTextArea.copy();
            }
        });

        JButton pasteButton = new JButton("Paste");
        pasteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notesTextArea.paste();
            }
        });

        JButton boldButton = new JButton("Bold");
        boldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StyledEditorKit.BoldAction boldAction = new StyledEditorKit.BoldAction();
                boldAction.actionPerformed(e);
            }
        });

        JButton underlineButton = new JButton("Underline");
        underlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StyledEditorKit.UnderlineAction underlineAction = new StyledEditorKit.UnderlineAction();
                underlineAction.actionPerformed(e);
            }
        });

        JButton italicButton = new JButton("Italic");
        italicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StyledEditorKit.ItalicAction italicAction = new StyledEditorKit.ItalicAction();
                italicAction.actionPerformed(e);
            }
        });

        //format text
        JPanel formatButtonPanel = new JPanel();
        formatButtonPanel.add(cutButton);
        formatButtonPanel.add(copyButton);
        formatButtonPanel.add(pasteButton);
        formatButtonPanel.add(boldButton);
        formatButtonPanel.add(underlineButton);
        formatButtonPanel.add(italicButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(newButton);
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        // Tambahkan tombol-tombol ke panel buttonPanel
        buttonPanel.add(newButton);
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cutButton);
        buttonPanel.add(copyButton);
        buttonPanel.add(pasteButton);
        buttonPanel.add(boldButton);
        buttonPanel.add(underlineButton);
        buttonPanel.add(italicButton);

        //otomatisasi posisi
        // Mengatur layout panel buttonPanel menggunakan GridBagLayout
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Membuat jarak antara komponen

        // Menambahkan tombol-tombol ke panel buttonPanel dengan mengatur gridx dan gridy
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(newButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(openButton, gbc);

        gbc.gridx = 2;
        buttonPanel.add(saveButton, gbc);

        gbc.gridx = 3;
        buttonPanel.add(editButton, gbc);

        gbc.gridx = 4;
        buttonPanel.add(deleteButton, gbc);

        gbc.gridy = 1; // Pindah ke baris kedua
        gbc.gridx = 0;
        buttonPanel.add(cutButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(copyButton, gbc);

        gbc.gridx = 2;
        buttonPanel.add(pasteButton, gbc);

        gbc.gridx = 3;
        buttonPanel.add(boldButton, gbc);

        gbc.gridx = 4;
        buttonPanel.add(underlineButton, gbc);

        gbc.gridx = 5;
        buttonPanel.add(italicButton, gbc);

        JScrollPane scrollPane = new JScrollPane(notesTextArea);
        //panel format text
        buttonPanel.add(formatButtonPanel);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        notes = new ArrayList<>();
    }

    private void newNote() {
        notesTextArea.setText("");
    }

    private void openNote() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                notesTextArea.setText(sb.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to open file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveNote() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.print(notesTextArea.getText());
                writer.flush();
                JOptionPane.showMessageDialog(this, "Note saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to save note: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editNote() {
        String editedNote = JOptionPane.showInputDialog(this, "Edit Note:", notesTextArea.getText());
        if (editedNote != null && !editedNote.isEmpty()) {
            notesTextArea.setText(editedNote);
        }
    }

    private void deleteNote() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this note?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            notesTextArea.setText("");
        }
    }

    private void showFileInfo() {
        String text = notesTextArea.getText();
        long charCount = text.chars().count();
        long wordCount = Pattern.compile("\\s+").splitAsStream(text).filter(word -> !word.isEmpty()).count();
        long sentenceCount = Pattern.compile("[.!?]").splitAsStream(text).filter(sentence -> !sentence.isEmpty()).count();

        JOptionPane.showMessageDialog(this,
                "Jumlah Karakter: " + charCount + "\n"
                + "Jumlah Kata: " + wordCount + "\n"
                + "Jumlah Kalimat: " + sentenceCount,
                "Informasi File",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NotesAppGUI app = new NotesAppGUI();
                app.setVisible(true);
            }
        });
    }
}
