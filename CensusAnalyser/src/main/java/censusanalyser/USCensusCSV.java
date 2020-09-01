package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    public USCensusCSV() {}

    public USCensusCSV(String state, String stateId, int population, Double populationDensity, Double totalArea) {
        this.state = state;
        this.stateId = stateId;
        this.population = population;
        this.density = populationDensity;
        this.area = totalArea;
    }

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Total area", required = true)
    public double area;

    @CsvBindByName(column = "Population Density", required = true)
    public double density;

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "state='" + state + '\'' +
                ", population=" + population +
                ", areaInAqKm=" + area +
                ", densityPerSqKm=" + density +
                '}';
    }
}
