import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DataImporter {
    public static Iterable<FlightRecord> getData(String filename) throws IOException {
        CSVParser parser;
        parser = CSVFormat.EXCEL.withHeader().parse(new FileReader(filename));

        List<FlightRecord> input = new LinkedList<>();

        for (CSVRecord r : parser.getRecords()) {
            input.add(new FlightRecord(r));
        }
        return input;
    }
}
