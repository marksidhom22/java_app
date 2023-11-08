package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    // Panels for different entities in the application
    private MusicianPanel musicianPanel;
    private InstrumentPanel instrumentPanel;
    private AlbumPanel albumPanel;
    private SongPanel songPanel;

    // Menu items
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem musiciansItem;
    private JMenuItem instrumentsItem;
    private JMenuItem albumsItem;
    private JMenuItem songsItem;

    public MainFrame(String title) {
        super(title);

        // Initialize the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen

        // Create panels
        musicianPanel = new MusicianPanel();
        instrumentPanel = new InstrumentPanel();
        albumPanel = new AlbumPanel();
        songPanel = new SongPanel();

        // Setup the menu bar
        menuBar = new JMenuBar();
        menu = new JMenu("Navigate");
        musiciansItem = new JMenuItem("Musicians");
        instrumentsItem = new JMenuItem("Instruments");
        albumsItem = new JMenuItem("Albums");
        songsItem = new JMenuItem("Songs");

        // Add action listeners for menu items
        musiciansItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(musicianPanel);
                validate();
            }
        });

        instrumentsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(instrumentPanel);
                validate();
            }
        });

        albumsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(albumPanel);
                validate();
            }
        });

        songsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(songPanel);
                validate();
            }
        });

        // Add items to menu
        menu.add(musiciansItem);
        menu.add(instrumentsItem);
        menu.add(albumsItem);
        menu.add(songsItem);

        // Add menu to menubar
        menuBar.add(menu);

        // Add menubar to the frame
        setJMenuBar(menuBar);

        // Set the initial content pane
        setContentPane(musicianPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame("Notown Records Database Application").setVisible(true);
            }
        });
    }
}
