import org.apache.commons.csv.CSVRecord;

public class FlightRecord {
    public final String UNIQUE_CARRIER_NAME;
    public final String ORIGIN;
    public final String ORIGIN_STATE_ABR;
    public final String DEST;
    public final String DEST_STATE_ABR;
    public final int MONTH;

    public FlightRecord(CSVRecord from) {
        UNIQUE_CARRIER_NAME = from.get("UNIQUE_CARRIER_NAME");
        ORIGIN = from.get("ORIGIN");
        ORIGIN_STATE_ABR = from.get("ORIGIN_STATE_ABR");
        DEST = from.get("DEST");
        DEST_STATE_ABR = from.get("DEST_STATE_ABR");
        MONTH = Integer.parseInt(from.get("MONTH"));
    }
}
