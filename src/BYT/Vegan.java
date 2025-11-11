package BYT;

public class Vegan {
    private String certificationID;
    // "ABPL2814394243"
    // A certificationID can in theory be any combination arbitrary numbers and letters

    public Vegan(String certificationID) {
        this.certificationID = Validator.validateAttributes(certificationID);
    }

    public String getCertificationID() {
        return certificationID;
    }

    public void setCertificationID(String certificationID) {
        this.certificationID = Validator.validateAttributes(certificationID);
    }
}
