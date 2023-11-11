package BusinessLogicLayer;

import DataAccessLayer.MusicianDAO;
import DataAccessLayer.Musician;
import BusinessLogicLayer.IMusicianService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

/**
 * Service class for musician-related business logic.
 */
public class MusicianService implements IMusicianService {

    private MusicianDAO musicianDao;

    /**
     * Constructor injecting the MusicianDAO dependency.
     */
    public MusicianService() {
        this.musicianDao = new MusicianDAO();
    }

    @Override
    public Musician findMusicianBySSN(String ssn) {
        // Logic to find a musician by SSN using the MusicianDAO
        return musicianDao.getMusicianById(ssn);
    }

    @Override
    public List<Musician> listAllMusicians() {
        // Logic to return all musicians using the MusicianDAO
        return musicianDao.getAllMusicians();
    }

    @Override
    public void updateMusician(Musician musician) {
        // Logic to save (insert or update) a musician's details using the MusicianDAO
        if (musician != null) {
            if (musicianDao.getMusicianById(musician.getSsn()) != null) {
                // Check for phone conflict before updating
            	int status=checkAndResolvePhoneConflict(musician);
                if (status!=0) {
                    boolean updateSuccess = musicianDao.updateMusician(musician);
                    if (!updateSuccess) {
                        JOptionPane.showMessageDialog(null, "The musician's update failed due to unresolved phone/address conflict.", "Update Musician Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Handle the case where the phone conflict is not resolved
                    JOptionPane.showMessageDialog(null, "The musician could not be updated due to unresolved phone/address conflict.", "Update Musician Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No musician with the given SSN found. Adding as new musician.", "Update Musician", JOptionPane.INFORMATION_MESSAGE);
                addMusician(musician);
            }
        }
    }
    

    @Override
    public void deleteMusicianBySSN(String ssn) {
        // Logic to delete a musician by SSN using the MusicianDAO
        musicianDao.deleteMusician(ssn);
    }

    @Override
    public boolean addMusician(Musician musician) {
        // Logic to add a new musician using the MusicianDAO
        if (musician != null) {
            if (musicianDao.getMusicianById(musician.getSsn()) == null) {
            	int status = checkAndResolvePhoneConflict(musician);
                if (status!=0 && status!=3) {
                    musicianDao.addMusician(musician,status==1);
                } 
                else if (status==3)
                {
                    return false;
                }
                
                else {
                    // Handle the case where the phone conflict is not resolved
                    JOptionPane.showMessageDialog(null, "The musician could not be added due to unresolved phone/address conflict.", "Add Musician Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Musician already exists
                JOptionPane.showMessageDialog(null, "A musician with the given SSN already exists.", "Add Musician Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return true;
    }
    


    public List<Musician> searchMusicians(String searchQuery) {
        // Get all musicians
        List<Musician> allMusicians = listAllMusicians();
        
        // Filter musicians based on the search query
        return allMusicians.stream()
                           .filter(musician -> musician.getName().toLowerCase().contains(searchQuery.toLowerCase())
                                    // || musician.getInstrument().toLowerCase().contains(searchQuery.toLowerCase())
                                    )
                           .collect(Collectors.toList());
    }

    public void deleteMusicianById(String id) {
        // Implement the logic to delete a musician by ID, using the appropriate DAO method
        musicianDao.deleteMusician(id);
    }

// ... (existing methods)

@Override
public int checkAndResolvePhoneConflict(Musician musician) {
    try {
        String existingPhone = musicianDao.getPhoneByAddress(musician.getAddress());
        if (existingPhone != null && !existingPhone.equals(musician.getPhoneNumber())) {
            // If there's a conflict, resolve it based on user input
            return resolvePhoneConflict(musician, existingPhone);
        }
        // No conflict, or the phone number is the same
        return 1;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return 0;
    }
}

private int resolvePhoneConflict(Musician musician, String existingPhone) {
    String[] options = {"Update Phone Number", "Use Existing Phone Number: " + existingPhone, "Change Address"};
    int choice = JOptionPane.showOptionDialog(null, 
        "The address already has a different phone number: " + existingPhone + ".\n" +
        "Would you like to update the phone number, use the existing one, or change the address?",
        "Phone Number Conflict", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
        null, options, options[0]);

    try {
        switch (choice) {
            case 0: // Update Phone Number
                musicianDao.updatePhoneNumber(musician.getPhoneNumber(), musician.getAddress());
                return 2;
            case 1: // Use Existing Phone Number
                musician.setPhoneNumber(existingPhone);
                return 2;
            case 2: // Change Address
                // The address needs to be changed by the user
                return 3;
            default:
                return 0;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        return 0;
    }
}


    // Additional methods and business logic can be added below

}
