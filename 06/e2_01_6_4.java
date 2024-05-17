//Naoya iida
import java.util.ArrayList;
import java.util.List;

class e2_01_6_4 {
    public static void main(String[] args) {
        NewsPublisher newsPublisher = new NewsPublisher();
        NewsSubscriber emailSubscriber = new EmailSubscriber("john@example.com");
        NewsSubscriber appSubscriber = new MobileAppSubscriber("NewsApp");
        newsPublisher.addSubscriber(emailSubscriber);
        newsPublisher.addSubscriber(appSubscriber);
        newsPublisher.issueNews("NEWS1");
        newsPublisher.removeSubscriber(emailSubscriber);
        newsPublisher.issueNews("NEWS2");
    }
}
interface NewsSubscriber {
    void update(String news);
}
class EmailSubscriber implements NewsSubscriber {
    private String email;
    public EmailSubscriber(String email) {
        this.email = email;
    }
    @Override
    public void update(String news) {
        System.out.println("Email Subscriber (" + email + "): Received News - " + news);
    }
}
class MobileAppSubscriber implements NewsSubscriber {
    private String appName;
    public MobileAppSubscriber(String appName) {
        this.appName = appName;
    }
    @Override
    public void update(String news) {
        System.out.println("Mobile App Subscriber (" + appName + "): Received News - " + news);
    }
}
class NewsPublisher {
    private List<NewsSubscriber> subscribers = new ArrayList<>();
    public void addSubscriber(NewsSubscriber subscriber) {
        subscribers.add(subscriber);
    }
    public void removeSubscriber(NewsSubscriber subscriber) {
        subscribers.remove(subscriber);
    }
    public void issueNews(String news) {
        System.out.println("[Breaking News] " + news);
        notifySubscribers(news);
        System.out.println();
    }
    private void notifySubscribers(String news) {
        for(NewsSubscriber subscriber : subscribers) {
            subscriber.update(news);
        }
    }
}
    
/*
[Breaking News] NEWS1
Email Subscriber (john@example.com): Received News - NEWS1
Mobile App Subscriber (NewsApp): Received News - NEWS1

[Breaking News] NEWS2
Mobile App Subscriber (NewsApp): Received News - NEWS2

 */