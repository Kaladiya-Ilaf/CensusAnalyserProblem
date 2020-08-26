package censusanalyser;

import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static censusanalyser.SortByField.Field.STATE;
import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {
    public enum Country{ INDIA, US };

    Map<String, CensusDAO> censusMap = null;

    public CensusAnalyser() {
        censusMap = new HashMap<String, CensusDAO>();
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
      censusMap = new CensusLoader().loadCensusData(country, csvFilePath);
      return censusMap.size();
    }
    
    private static <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }

    public String getFieldWiseSortedCensusData(Country country, SortByField.Field... field) throws CensusAnalyserException {
        Comparator<CensusDAO> censusComparator = null;
        if (censusMap == null || censusMap.size() == 0)
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);

        if (field.length == 2)
            censusComparator = SortByField.getParameter(field[0]).thenComparing(SortByField.getParameter(field[1]));

        censusComparator = SortByField.getParameter(field[0]);

        ArrayList censusDTOS = censusMap.values().stream()
                .sorted(censusComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));

        return new Gson().toJson(censusDTOS);
    }
}

