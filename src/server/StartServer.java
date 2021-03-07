package server;

public class StartServer {
    public static void main(String[] args) {
        MessageServer messageServer = new MessageServer(new MessageManager(), 2555);
        LogFileManager lfm = new LogFileManager("files/log.dat");
        lfm.addLog("A log");
        System.out.println(System.currentTimeMillis());
        for (Log log: lfm.readLogFile(System.currentTimeMillis()-10000l, System.currentTimeMillis())) { //tests for logs made within last 10 seconds
            System.out.println(log);
        }
    }
}
