package censusanalyser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class SortByField {
    static Map<Field, Comparator> comparatorMap = new HashMap<>();

    enum Field {
        STATE, POPULATION, AREA, DENSITY;
    }

    SortByField() {

    }
    public static Comparator getParameter(SortByField.Field field) {

        Comparator<CensusDAO> stateComparator = Comparator.comparing(census -> census.state);
        Comparator<CensusDAO> areaComparator = Comparator.comparing(census -> census.totalArea);
        Comparator<CensusDAO> populationComparator = Comparator.comparing(census -> census.population);
        Comparator<CensusDAO> densityComparator = Comparator.comparing(census -> census.populationDensity);

        comparatorMap.put(Field.STATE, stateComparator);
        comparatorMap.put(Field.POPULATION, populationComparator.reversed());
        comparatorMap.put(Field.AREA,areaComparator.reversed());
        comparatorMap.put(Field.DENSITY, densityComparator.reversed());

        return (Comparator<CensusDAO>) comparatorMap.get(field);
    }
}
