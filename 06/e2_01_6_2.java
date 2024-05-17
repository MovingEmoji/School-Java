//Naoya iida
class e2_01_6_2 {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        System.out.println("logger1 == logger2: " + (logger1 == logger2));
        logger1.log("This is a log message.");
        logger2.log("Another log message.");
    }   
}
class Logger {
    private static Logger instance = new Logger();
    private Logger() {}

    public static Logger getInstance() {
        return instance;
    }

    public void log(String message) {
        System.out.println("Log: " + message);
    }
}
    
/*
logger1 == logger2: true
Log: This is a log message.
Log: Another log message.
 */