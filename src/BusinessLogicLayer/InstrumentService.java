package BusinessLogicLayer;

import DataAccessLayer.InstrumentDAO;
import DataAccessLayer.Song;
import DataAccessLayer.Instrument;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements instrument-related business logic and operations.
 */
public class InstrumentService implements IInstrumentService {

    private final InstrumentDAO instrumentDao;

    public InstrumentService() {
        // The actual DAO implementation should be injected here.
        // This could be done via a constructor, a factory, or a dependency injection framework.
        this.instrumentDao = new InstrumentDAO();
    }

    @Override
    public Instrument findInstrumentById(String instrId) {
        // Implement the logic to find an instrument by its ID using InstrumentDAO
        return instrumentDao.getInstrumentById(instrId);


    }

    

    public Instrument findInstrumentByName(String instrName) {
        // Implement the logic to find an instrument by its ID using InstrumentDAO
        return instrumentDao.getInstrumentByName(instrName);

    }


    @Override
    public List<Instrument> listAllInstruments() {
        // Implement the logic to list all instruments using InstrumentDAO
        return instrumentDao.getAllInstruments();
    }

    @Override
    public boolean addInstrument(Instrument instrument) {
        // Implement the logic to add a new instrument using InstrumentDAO
        instrumentDao.addInstrument(instrument);
        return true;
    }

    @Override
    public boolean updateInstrument(Instrument instrument) {
        // Implement the logic to update an instrument's information using InstrumentDAO
        instrumentDao.updateInstrument(instrument);
        return true;
    }

    @Override
    public boolean deleteInstrumentById(String instrId) {
        // Implement the logic to delete an instrument by its ID using InstrumentDAO
        return instrumentDao.deleteInstrument(instrId);
    }

    public List<Instrument> searchInstruments(String searchQuery, boolean searchByName, boolean searchByKey) {
        String query = searchQuery.toLowerCase();
        return listAllInstruments().stream()
            .filter(instrument -> {
                boolean matchesQuery = false;
                if (searchByName && instrument.getName().toLowerCase().contains(query)) {
                    matchesQuery = true;
                }
                if (searchByKey && instrument.getKey().toLowerCase().contains(query)) {
                    matchesQuery = true;
                }
                return matchesQuery;
            })
            .collect(Collectors.toList());
    }

    // Additional methods for other instrument-related operations can be implemented here
}
