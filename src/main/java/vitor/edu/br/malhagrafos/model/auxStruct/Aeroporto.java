package vitor.edu.br.malhagrafos.model.auxStruct;

import vitor.edu.br.malhagrafos.control.graphOp.Ponto;

public class Aeroporto {

    private String abreviation;
    private String timeZoneGMT;
    private Ponto position;
    private String airportName;

    /**
     *
     * @param abreviation Airport abbreviation
     * @param timeZoneGMT Time Zone (offset from GMT)
     * @param position coordinate on map
     * @param airportName Name of City/Airport
     */
    public Aeroporto(String abreviation, String timeZoneGMT, Ponto position, String airportName) {
        this.abreviation = abreviation;
        this.timeZoneGMT = timeZoneGMT;
        this.position = position;
        this.airportName = airportName;
    }

    public Aeroporto() {
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getTimeZoneGMT() {
        return timeZoneGMT;
    }

    public void setTimeZoneGMT(String timeZoneGMT) {
        this.timeZoneGMT = timeZoneGMT;
    }

    public Ponto getPosition() {
        return position;
    }

    public void setPosition(Ponto position) {
        this.position = position;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    @Override
    public String toString() {
        return "Aeroporto{" +
                "abreviation='" + abreviation + '\'' +
                ", Position=" + position.toString() +
                ", airportName='" + airportName + '\'' +
                '}';
    }
}
