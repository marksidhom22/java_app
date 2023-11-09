package DataAccessLayer;

import java.io.Serializable;

import BusinessLogicLayer.InstrumentService;

/**
 * Represents a Musician entity as a plain old Java object (POJO).
 */
public class Musician implements Serializable {

    private String ssn;
    private String name;
    private String address;
    private String phoneNumber;
    private String instr_id;
    private String instr_name;
    private InstrumentService instrumentService;

    /**
     * Constructs a new Musician with default values.
     */
    public Musician() {
        // Default constructor for initialization
    }

    /**
     * Constructs a new Musician with the specified details.
     *
     * @param ssn          the social security number of the musician
     * @param name         the name of the musician
     * @param address      the address of the musician
     * @param phoneNumber  the phone number of the musician
     */
    public Musician(String ssn, String name, String address, String phoneNumber) {
        this.ssn = ssn;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Musician(String ssn, String name, String address, String phoneNumber,String instr_id) {
        try {
              this.instrumentService=new InstrumentService();
            this.ssn = ssn;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.instr_id=instr_id;
        // this.instr_name=this.instrumentService.findInstrumentById(this.instr_id).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
  
    }

    // Getters and setters for the musician properties

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public String getIntsrument_id() {
        return this.instr_id;
    }

    public String getIntsrument_name() {
        return this.instr_name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

        public void setInstrId(String inst_id) {
        this.instr_id = inst_id;
    }

        public void setInstrName(String instr_name) {
        this.instr_name = instr_name;
        this.instr_id=instrumentService.findInstrumentByName(instr_name).getName();
    }

        public void setInstrNameUsingInstrId(String instr_id) {
        this.instr_name=this.instrumentService.findInstrumentById(instr_id).getName();
    }

    @Override
    public String toString() {
        return "Musician{" +
                "ssn='" + ssn + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    // Implement equals and hashCode based on 'ssn' because it's a unique identifier for Musician

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Musician)) return false;

        Musician musician = (Musician) o;

        return getSsn() != null ? getSsn().equals(musician.getSsn()) : musician.getSsn() == null;
    }

    @Override
    public int hashCode() {
        return getSsn() != null ? getSsn().hashCode() : 0;
    }
}
