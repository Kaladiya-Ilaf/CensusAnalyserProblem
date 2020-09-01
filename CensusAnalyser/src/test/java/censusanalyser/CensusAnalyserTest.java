package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FILE_TYPE_PATH = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String INCORRECT_DATA_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusDataIncorrectDelimiter.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    CensusAnalyser censusAnalyser = null;
    ExpectedException expectedException = null;

    @Before
    public void setUp() throws Exception {
        censusAnalyser = new CensusAnalyser();
        expectedException = ExpectedException.none();
        expectedException.expect(CensusAnalyserException.class);
    }

    @Test
    public void givenIndianCensusCSVFile_whenProper_shouldReturnsCorrectRecordCount() throws CensusAnalyserException {
        int numberOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        Assert.assertEquals(29, numberOfRecords);
    }

    @Test
    public void givenIndianCensusCSVFile_whenProper_shouldReturnsIncorrectRecordCount() throws CensusAnalyserException {
        int numberOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        Assert.assertNotEquals(20, numberOfRecords);
    }

    @Test
    public void givenCSVFile_whenWrongPath_shouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INPUT_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenFile_whenWrongFileExtension_shouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_FILE_TYPE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INPUT_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenCSVFile_whenIncorrectDelimiter_shouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_whenIncorrectHeader_shouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_whenSortedOnState_shouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.INDIA, SortByField.Field.STATE);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
    }

    @Test
    public void givenIndianCensusCSVFile_whenSortedOnStateCode_shouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.INDIA, SortByField.Field.STATE);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("AP", censusCSV[0].state);
    }

    @Test
    public void givenUSCensusCSVFile_whenProper_shouldReturnsCorrectRecordCount() throws CensusAnalyserException {
        int numberOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51, numberOfRecords);
    }

    @Test
    public void givenUSCensusCSVFile_whenProper_shouldReturnsIncorrectRecordCount() throws CensusAnalyserException {
        int numberOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        Assert.assertNotEquals(50, numberOfRecords);
    }

    @Test
    public void givenCSVFileForUS_whenWrongPath_shouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INPUT_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenFileForUS_whenWrongFileExtension_shouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, WRONG_FILE_TYPE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INPUT_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenUSCSVFile_whenIncorrectDelimiter_shouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM, e.type);
        }
    }

    @Test
    public void givenUSCensusCSVFile_whenIncorrectHeader_shouldThrowCustomException() {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_whenSortedOnPopulation_shouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.INDIA, SortByField.Field.POPULATION);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
    }

    @Test
    public void givenIndianCensusCSVFile_whenSortedOnPopulationDensity_shouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.INDIA, SortByField.Field.DENSITY);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Bihar", censusCSV[0].state);
    }

    @Test
    public void givenIndianCensusCSVFile_whenSortedOnArea_shouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.INDIA, SortByField.Field.AREA);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Rajasthan", censusCSV[0].state);
    }



    @Test
    public void givenUSCensusCSVFile_whenSortedOnPopulation_shouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.US, SortByField.Field.POPULATION);
        USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("California", censusCSV[0].state);
    }

    @Test
    public void givenUSCensusCSVFile_whenSortedOnPopulationDensity_shouldReturnSortedResult() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.US, SortByField.Field.DENSITY);
        USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("District of Columbia", censusCSV[0].state);
    }

    @Test
    public void givenUSCensusCSVFile_whenSortedOnArea_shouldReturnSortedResult() throws CensusAnalyserException {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.US, SortByField.Field.AREA);
            USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[0].state);
    }

    @Test
    public void givenUSCensusCSVFile_WhenSortedByPopulationAndDensity_ShouldReturnMostPopulousStateWithDencity() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.US, SortByField.Field.POPULATION, SortByField.Field.DENSITY);
        USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("California", censusCSV[0].state);
    }

    @Test
    public void givenIndiaCensusCSVFile_WhenSortedByPopulationAndDensity_ShouldReturnMostPopulousStateWithDensity() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.INDIA, SortByField.Field.POPULATION, SortByField.Field.DENSITY);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
    }

    @Test
    public void givenUSCensusCSVFile_WhenSortedByPopulationAndDensity_ShouldReturnMostPopulousStateWithDensity() throws CensusAnalyserException {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(CensusAnalyser.Country.US, SortByField.Field.POPULATION, SortByField.Field.DENSITY);
        USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("California", censusCSV[0].state);
    }
}
