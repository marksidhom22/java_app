package BusinessLogicLayer;

import DataAccessLayer.InstrumentDAO;
import DataAccessLayer.Instrument;
import java.util.List;

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

    @Override
    public List<Instrument> listAllInstruments() {
        // Implement the logic to list all instruments using InstrumentDAO
        return instrumentDao.getAllInstruments();
    }

    @Override
    public void addInstrument(Instrument instrument) {
        // Implement the logic to add a new instrument using InstrumentDAO
        instrumentDao.addInstrument(instrument);
    }

    @Override
    public void updateInstrument(Instrument instrument) {
        // Implement the logic to update an instrument's information using InstrumentDAO
        instrumentDao.updateInstrument(instrument);
    }

    @Override
    public void deleteInstrumentById(String instrId) {
        // Implement the logic to delete an instrument by its ID using InstrumentDAO
        instrumentDao.deleteInstrument(instrId);
    }

    // Additional methods for other instrument-related operations can be implemented here
}
