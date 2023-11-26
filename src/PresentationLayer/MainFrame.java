package PresentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;



public class MainFrame extends JFrame {

    // Panels for different entities in the application
    private MusicianPanel musicianPanel;
    private InstrumentPanel instrumentPanel;
    private AlbumPanel albumPanel;
    private SongPanel songPanel;
    private MusicDataPanel musicDataPanel;

    // Menu items
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem musiciansItem;
    private JMenuItem instrumentsItem;
    private JMenuItem albumsItem;
    private JMenuItem songsItem;
    private JMenuItem musicDaItem;
    private JMenuItem logoutItem;

    public MainFrame(String userType) {
        super("Notown Musical Store");

        if (userType.equals("Staff")) {
            this.setTitle("Staff-Notown Musical Store");
        }




        // Initialize the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen

        // Create panels
        musicianPanel = new MusicianPanel();
        instrumentPanel = new InstrumentPanel();
        albumPanel = new AlbumPanel();
        songPanel = new SongPanel();
        musicDataPanel=new MusicDataPanel();

        // Setup the menu bar
        menuBar = new JMenuBar();
        menu = new JMenu("Navigate");
        musiciansItem = new JMenuItem("Musicians");
        instrumentsItem = new JMenuItem("Instruments");
        albumsItem = new JMenuItem("Albums");
        songsItem = new JMenuItem("Songs");
        musicDaItem = new JMenuItem("Home");
        logoutItem = new JMenuItem("Logout");

        // Initially hide all menu items
        musiciansItem.setVisible(false);
        instrumentsItem.setVisible(false);
        albumsItem.setVisible(false);
        songsItem.setVisible(false);
        musicDaItem.setVisible(true);
        logoutItem.setVisible(false);

        setMenuVisibility(userType);

        // Add action listeners for menu items
        musicDaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(musicDataPanel);
                musicDataPanel.loadMusicData();
                validate();
            }
        });
        
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current MainFrame
                dispose();

                // Show the login form again
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        
        // Add action listeners for menu items
        musiciansItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(musicianPanel);
                musicianPanel.loadMusicians();
                validate();
            }
        });

        instrumentsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(instrumentPanel);
                instrumentPanel.loadInstruments();
                validate();
            }
        });

        albumsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(albumPanel);
                albumPanel.loadAlbums();
                validate();
            }
        });

        songsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(songPanel);
                songPanel.loadSongs();
                validate();
            }
        });

        // Add items to menu
        menu.add(musicDaItem);
        menu.add(musiciansItem);
        menu.add(instrumentsItem);
        menu.add(albumsItem);
        menu.add(songsItem);
        menu.add(logoutItem);
        
        // Add menu to menubar
        menuBar.add(menu);

        // Add menubar to the frame
        setJMenuBar(menuBar);

        // Set the initial content pane
        setContentPane(musicDataPanel);
        musicDataPanel.loadMusicData();
    }


    private void setMenuVisibility(String userType) {
        if (userType.equals("Staff")) {
            // Show all menu items for staff
            musiciansItem.setVisible(true);
            instrumentsItem.setVisible(true);
            albumsItem.setVisible(true);
            songsItem.setVisible(true);
            musicDaItem.setVisible(true);
            logoutItem.setVisible(true);

        } else if (userType.equals("Customer")) {
            // Show limited menu items for customers
            musicDaItem.setVisible(false);
            logoutItem.setVisible(true);

        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
//         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

//		FlatDarkLaf.setup();
// 		FlatLightLaf.setup();


    	
    	
        SwingUtilities.invokeLater(() -> {
        	try {
        	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        	        if ("Nimbus".equals(info.getName())) {
        	            UIManager.setLookAndFeel(info.getClassName());
        	            break;
        	        }
        	    }
//        		FlatLightLaf.install();
//    			UIManager.setLookAndFeel(new FlatLightLaf());

        	} catch( Exception ex ) {
        	    System.err.println( "Failed to initialize LaF" );
        	}
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }
}