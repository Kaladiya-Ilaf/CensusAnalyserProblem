package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH =  "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FILE_TYPE_PATH =  "./src/test/resources/IndiaStateCensusData.txt";
    private static final String INCORRECT_DATA_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusDataIncorrectDelimiter.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

     @Test
    public void givenIndianCensusCSVFile_whenProper_shouldReturnsCorrectRecordCount() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            int numberOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_whenWrongPath_shouldThrowCustomException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        ExpectedException expectedException = ExpectedException.none();
        expectedException.expect(CensusAnalyserException.class);

        try {
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_whenWrongFileExtension_shouldThrowCustomException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        ExpectedException expectedException = ExpectedException.none();
        expectedException.expect(CensusAnalyserException.class);
        try {
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_TYPE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_whenIncorrectDelimiter_shouldThrowCustomException() {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException expectedException = ExpectedException.none();
         expectedException.expect(CensusAnalyserException.class);
        try {
            censusAnalyser.loadIndiaCensusData(INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_whenIncorrectHeader_shouldThrowCustomException() {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException expectedException = ExpectedException.none();
         expectedException.expect(CensusAnalyserException.class);
        try {
            censusAnalyser.loadIndiaCensusData(INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeCSVFile_whenProper_shouldReturnsCorrectRecordCount() {
        ExpectedException expectedException = ExpectedException.none();
        expectedException.expect(CensusAnalyserException.class);

        try {
            int numberOfRecords = CensusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numberOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_PROBLEM,e.type);
        }
    }

}
