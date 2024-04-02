package observer;

import java.util.ArrayList;
import java.util.List;

public class KeystoreObservable {
	private List<KeystoreObserver> observers = new ArrayList<>();

	public void addObserver(KeystoreObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(KeystoreObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (KeystoreObserver observer : observers) {
			observer.onChange();
		}
	}
}
