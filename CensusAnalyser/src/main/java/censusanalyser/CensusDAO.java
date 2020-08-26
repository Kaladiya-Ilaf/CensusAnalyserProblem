package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double populationDensity;
    public double totalArea;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationDensity =indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(IndiaStateCSV indiaStateCSV){
        stateCode = indiaStateCSV.stateCode;
    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        stateCode = usCensusCSV.stateId;
        population = usCensusCSV.population;
        populationDensity = usCensusCSV.density;
        totalArea = usCensusCSV.area;
    }

    public Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state, stateCode, population, populationDensity, totalArea);
        return new IndiaCensusCSV(state, population, (int) populationDensity, (int) totalArea);
    }
}
