//Naoya iida
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class e2_01_6_5 {
    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        downloader.addListener(new RawPrintListener());
        downloader.addListener(new YearPrintListener());
        downloader.downloadData("https://www.timeapi.io/api/Time/current/zone?timeZone=Asia/Tokyo");
        System.out.println("Other work...");
        downloader.addListener(new TimeZonePrintListener());

    }
}

interface DownloadListener {
    void onDownloadComplete(String data);
}

class RawPrintListener implements DownloadListener {
    @Override
    public void onDownloadComplete(String data) {
        System.out.println("[RawPrintListener] " + data);
    }
}
    
class YearPrintListener implements DownloadListener {

    @Override
    public void onDownloadComplete(String data) {
        String[] datas = data.split(",");
        for(String minidata : datas) {
            if(minidata.contains("year")) {
                String[] miniminidata = minidata.split(":");
                System.out.println("[YearPrintListener] " + miniminidata[1]);
                break;
            }
        }
    }
}
    
class TimeZonePrintListener implements DownloadListener {

    @Override
    public void onDownloadComplete(String data) {
        String[] datas = data.split(",");
        for(String minidata : datas) {
            if(minidata.contains("timeZone")) {
                String[] miniminidata = minidata.split(":");
                System.out.println("[TimeZonePrintListener] " + miniminidata[1]);
                break;
            }
        }
    }
}

class Downloader {
    private List<DownloadListener> listeners = new ArrayList<>();
    public void addListener(DownloadListener listener) {listeners.add(listener);}
    public void removeListener(DownloadListener listener) {listeners.remove(listener);}
    public void downloadData(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = fetchData(url);
                    notifyListeners(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String fetchData(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
            return data.toString();
        } finally {
            connection.disconnect();
        }
    }
    private void notifyListeners(String data) {
        for(DownloadListener listener : listeners) {
            listener.onDownloadComplete(data);
        }
    }
}

    
    
/*
Other work...
[RawPrintListener] {"year":2024,"month":5,"day":17,"hour":15,"minute":41,"seconds":47,"milliSeconds":177,"dateTime":"2024-05-17T15:41:47.1777431","date":"05/17/2024","time":"15:41","timeZone":"Asia/Tokyo","dayOfWeek":"Friday","dstActive":false}
[YearPrintListener] 2024
[TimeZonePrintListener] "Asia/Tokyo"
 */