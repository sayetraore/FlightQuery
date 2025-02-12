/*Header
/HW 5
/Name:
*/
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Query {
    //Counts total flights
    public static int Query0(Iterable<FlightRecord> input) {
        int sum = 0;
        for (FlightRecord r : input) {
            if (r.ORIGIN.equals("LAX") && r.DEST.equals("ORD") && r.MONTH == 8) {
                sum++;
            }
        }
        return sum;
    }




    //Returns amount of flights leaving from Cedar Rapids (CID)
    public static int Query1(Iterable<FlightRecord> input) {
        //Loop to count flights incrementally
        int CidCount = 0;
        for (FlightRecord p: input){
            if (p.ORIGIN.equals("CID")){
                CidCount ++;
            }
        }
        return CidCount;
    }
    //returns unique destinations that are directly reached from CR in a set
    public static Iterable<String> Query2(Iterable<FlightRecord> input) {
        Set<String> set1 = new HashSet<String>();
        for (FlightRecord s: input){
            if (s.ORIGIN.equals("CID")){
                set1.add(s.DEST + ", "+ s.DEST_STATE_ABR);
            }
        }return set1;

    }
    //Counts the amount of unique flights reached from CR
    public static int Query3(Iterable<FlightRecord> input) {
        Set<String> fromCr = new HashSet<String>();
        for (FlightRecord r: input){
            if (r.ORIGIN.equals("CID")){
                fromCr.add(r.DEST);
            }
        }return fromCr.size();
    }

    public static Iterable<String> Query4(Iterable<FlightRecord> input) {
        //for loop mapping destinations originating from cr to a count
        Map<String, Integer> m= new HashMap<String, Integer>();
        for(FlightRecord i: input){
            if ("CID".equals(i.ORIGIN)){
                m.put(i.DEST, m.getOrDefault(i.DEST, 0) + 1);
            }
        }Set<String> set3= new HashSet<String>();

        //for loop iterating through map to produce string result
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            set3.add(key + "=" + value.toString());
        }
        return set3;
    }
    // Find the month with the max number of flights
    public static String Query5(Iterable<FlightRecord> input) {
        Map<Integer, Integer> fpm = new HashMap<>();

        // for loop to map each month to a flight count incrementally
        for (FlightRecord fr : input) {
            fpm.put(fr.MONTH, fpm.getOrDefault(fr.MONTH, 0) + 1);
        }

        int maxFlights = 0;
        int monthMax = 0;

        //Compare each month's flight count mappings and change maxFlights accordingly
        for (Map.Entry<Integer, Integer> entry : fpm.entrySet()) {
            int flights = entry.getValue();
            if (flights > maxFlights) {
                maxFlights = flights;
                monthMax = entry.getKey();
            }
        }
        return monthMax + " had " + maxFlights + " flights";
    }

    //Finds state with max flight count (to and from)
    public static String Query6(Iterable<FlightRecord> input) {
        Map<String, Integer> between = new HashMap<>();

        //for loop to iterate through flights
        for (FlightRecord fr : input) {
            // map keys to states flying to and from each other
            String flightsTo = fr.ORIGIN_STATE_ABR + "," + fr.DEST_STATE_ABR;
            String flightsFrom = fr.DEST_STATE_ABR + "," + fr.ORIGIN_STATE_ABR;

            // Increment the count of flights given both ways
            between.put(flightsTo, between.getOrDefault(flightsTo, 0) + 1);
            between.put(flightsFrom, between.getOrDefault(flightsFrom, 0) + 1);
        }

        // Find states with max count of flights
        int maxFlights = 0;
        String stateMax = "";
        for (Map.Entry<String, Integer> entry : between.entrySet()) {
            if (entry.getValue() > maxFlights) {
                maxFlights = entry.getValue();
                stateMax = entry.getKey();
            }
        }

        // Return string result
        return "("+stateMax+")";


    }
    public static Iterable<String> Query7(Iterable<FlightRecord> input) {
        Set<String> allStates = new HashSet<>();
        // Make set to represent distinct states we can reach from IA
        Set<String> reachableFromIA = new HashSet<>();
        // For each loop to iterate through all flights in  file
        for (FlightRecord fr : input) {
            allStates.add(fr.ORIGIN_STATE_ABR);
            allStates.add(fr.DEST_STATE_ABR);

            // If the origin is Iowa, add the destination to the reachableFromIA set
            if (fr.ORIGIN_STATE_ABR.equals("IA")) {
                reachableFromIA.add(fr.DEST_STATE_ABR);
            }
        }
        // Remove Iowa from the set of all states
        allStates.remove("IA");
        // Remove states reachable from Iowa from the set of all states
        allStates.removeAll(reachableFromIA);

        // Return the states unreachable from IA
        return allStates;
    }
    //Returns percent of intrastate flights for each State
    public static Iterable<String> Query8(Iterable<FlightRecord> input) {
        Map<String, Integer> allFlights = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.000");
        // Count total flights and intra-state flights for each state using for each loop
        for (FlightRecord fr : input) {
            // Increment to total flight count for each state
            allFlights.put(fr.ORIGIN_STATE_ABR, allFlights.getOrDefault(fr.ORIGIN_STATE_ABR, 0) + 1);
        }
        Set<String> intraPercent = new HashSet<>();
        for (String state : allFlights.keySet()) {
            int totalFlights = allFlights.get(state);
            int intraFlights = 0;
            // Count intra-state flights
            for (FlightRecord fr : input) {
                if (fr.ORIGIN_STATE_ABR.equals(state) && fr.DEST_STATE_ABR.equals(state)) {
                    intraFlights++;
                }
            }
            // We should NOT regard states without intrastate flights
            if (intraFlights == 0) {
                continue;
            }
            // Calculate percentage
            double percentage = (double) intraFlights / totalFlights;
            // Format percentage with three decimal digits using decimal format method
            String formattedPercentage = df.format(percentage);
            // format the state=percentage
            intraPercent.add(state + "=" + formattedPercentage);
        }
        return intraPercent;
    }

    //For each state, gives the airline (UNIQUE_CARRIER_NAME) with the most flights
    public static Iterable<String> Query9(Iterable<FlightRecord> input) {

        Map<String, Map<String, Integer>> airlineState = new HashMap<>();

        // Mapping each destination state to a map of airlines and their counts
        for (FlightRecord q : input) {
            airlineState.putIfAbsent(q.DEST_STATE_ABR, new HashMap<>());
            Map<String, Integer> airlineCounts = airlineState.get(q.DEST_STATE_ABR);
            airlineCounts.put(q.UNIQUE_CARRIER_NAME, airlineCounts.getOrDefault(q.UNIQUE_CARRIER_NAME, 0) + 1);
        }
        Set<String> results = new HashSet<>();
        // Finding the airline with the most flights for each state
        for (String state : airlineState.keySet()) {
            Map<String, Integer> airlineCounts = airlineState.get(state);
            // Finding the airline with the maximum flights
            String maxAirline = "";
            int maxFlights = 0;
            for (Map.Entry<String, Integer> entry : airlineCounts.entrySet()) {
                if (entry.getValue() > maxFlights) {
                    maxAirline = entry.getKey();
                    maxFlights = entry.getValue();
                }
            }
            // Adding the result for the state
            results.add(state + "," + maxAirline);
        }
        // Return each result as “STATE,AIRLINE”
        return results;
    }
    // Find distinct routes from CID to LAX with exactly two flights
    public static Iterable<String> Query10(Iterable<FlightRecord> input) {
        Set<String> routes = new HashSet<>();

       //Nested Loop
        for (FlightRecord k : input) {
            if (k.ORIGIN.equals("CID")) {
                for (FlightRecord m : input) {
                    // Is the destination of the first flight is the origin of the second flight?
                    if (k.DEST.equals(m.ORIGIN) && m.DEST.equals("LAX")) {
                        // if so, then we will add it to our set of routes
                        routes.add(k.ORIGIN + "->" + k.DEST + "->" + m.DEST);
                    }
                }
            }
        }
        return routes;
    }

    //Example implementation on Query 0
    public static void main(String[] args) throws IOException {
        Iterable<FlightRecord> input = DataImporter.getData("flights/flights1990.csv");
        int result = Query0(input);
        System.out.println(result);
    }
}
