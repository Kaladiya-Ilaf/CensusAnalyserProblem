package censusanalyser;

import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {
    public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass,String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> censusCSVIterable = () -> censusCSVIterator;
            if(censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")){
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")){
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            if (csvFilePath.length == 1)
                return censusMap;
            this.loadIndiaStateCodeData(censusMap, csvFilePath[1]);
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INPUT_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }

    }

    private Map<String, CensusDAO> loadIndiaStateCodeData(Map<String, CensusDAO> censusMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCSV.class);
            Iterable<IndiaStateCSV> censusCSVIterable = () -> stateCSVIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(), false)
                    .filter(csvState -> censusMap.get(csvState.stateName) != null)
                    .forEach(censusCSV -> censusMap.get(censusCSV.stateName).state = censusCSV.stateCode);
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INPUT_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

}
