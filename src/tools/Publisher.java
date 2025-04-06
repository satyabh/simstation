package tools;

import java.util.ArrayList;

public class Publisher {
    private final ArrayList<Subscriber> subscriberList = new ArrayList<>();

    public void notifySubscribers() {
        for (Subscriber subscriber : subscriberList) {
            subscriber.update();
        }
    }

    public void subscribe(Subscriber subscriber) {
        subscriberList.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscriberList.remove(subscriber);
    }
}
