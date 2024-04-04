package aplikasi_notes;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import java.awt.*;

public class NotesAppGUI extends JFrame {

    private JTextPane notesTextPane;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem fileInfoMenuItem;
    private JMenuItem highlightMenuItem;
    private JMenuItem colorBackgroundMenuItem;
    private JMenuItem screenshotMenuItem;
    private JMenuItem goToLineMenuItem;

    public NotesAppGUI() {
        setTitle("Simple Notes App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        notesTextPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(notesTextPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);

        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutMenuItem = new JMenuItem(new DefaultEditorKit.CutAction());
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        JMenuItem copyMenuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        JMenuItem pasteMenuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        menuBar.add(editMenu);

        JMenu formatMenu = new JMenu("Format");
        JMenuItem boldMenuItem = new JMenuItem(new StyledEditorKit.BoldAction());
        boldMenuItem.setText("Bold");
        formatMenu.add(boldMenuItem);

        JMenuItem underlineMenuItem = new JMenuItem(new StyledEditorKit.UnderlineAction());
        underlineMenuItem.setText("Underline");
        formatMenu.add(underlineMenuItem);

        JMenuItem italicMenuItem = new JMenuItem(new StyledEditorKit.ItalicAction());
        italicMenuItem.setText("Italic");
        formatMenu.add(italicMenuItem);

        highlightMenuItem = new JMenuItem("Highlight");
        formatMenu.add(highlightMenuItem);

        colorBackgroundMenuItem = new JMenuItem("Color Background");
        formatMenu.add(colorBackgroundMenuItem);

        menuBar.add(formatMenu);

        JMenu infoMenu = new JMenu("Info");
        fileInfoMenuItem = new JMenuItem("File Info");
        infoMenu.add(fileInfoMenuItem);
        menuBar.add(infoMenu);

        setJMenuBar(menuBar);

        // Tambah Screenshot menu item
        screenshotMenuItem = new JMenuItem("Screenshot");
        menuBar.add(screenshotMenuItem);

        // Tambah Go to Line menu item
        goToLineMenuItem = new JMenuItem("Go to Line");
        menuBar.add(goToLineMenuItem);

        setJMenuBar(menuBar);

        // Inisialisasi NotesAppSource
        NotesAppSource source = new NotesAppSource(this);

        // Set visible
        setVisible(true);
    }

    public JTextPane getNotesTextPane() {
        return notesTextPane;
    }

    public JMenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public JMenuItem getSaveMenuItem() {
        return saveMenuItem;
    }

    public JMenuItem getFileInfoMenuItem() {
        return fileInfoMenuItem;
    }

    public JMenuItem getHighlightMenuItem() {
        return highlightMenuItem;
    }
    
    public JMenuItem getColorBackgroundMenuItem() {
        return colorBackgroundMenuItem;
    }

    public JMenuItem getScreenshotMenuItem() {
        return screenshotMenuItem;
    }

    public JMenuItem getGoToLineMenuItem() {
        return goToLineMenuItem;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NotesAppGUI();
            }
        });
    }
}
