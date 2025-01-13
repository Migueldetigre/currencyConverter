package models;

public class APICalcs {private double output;

    public APICalcs(ForExAPI forexAPI) {
        this.output = forexAPI.ForExResult();
    }

    public double getOutput() {
        return output;
    }

}
