package uk.co.paulcowie.stopwatchcompanion.parsers;

/**
 * Parses time in milliseconds, giving a timestamp
 */
public class StopwatchParser {
    private long time_ms;

    public StopwatchParser(long time_ms){
        this.time_ms = time_ms;
    }

    public StopwatchParser(){
        this(0L);
    }

    public void set_time_ms(long time_ms){
        this.time_ms = time_ms;
    }

    public long get_time_ms(){
        return time_ms;
    }

    /**
     * @return Timestamp in form mm:ss:cc
     */
    public String parse_ms(){
        long temp = time_ms;

        long minutes = temp/60000;
        temp -= 60000*minutes;
        long seconds = temp/1000;
        temp -= 1000*seconds;
        long centis = temp/10;

        return String.format("%02d:%02d:%02d", minutes, seconds, centis);
    }
}
