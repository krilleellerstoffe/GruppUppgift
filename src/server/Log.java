package server;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {

    private long timeCreated;
    private String logText;

    public Log (String logText) {
        this.timeCreated = System.currentTimeMillis();
        this.logText = logText;
    }

    public long getTimeCreated () {
        return timeCreated;
    }

    @Override
    public String toString() {
        Date date = new Date(timeCreated);
        return date + " " + logText;
    }
}
