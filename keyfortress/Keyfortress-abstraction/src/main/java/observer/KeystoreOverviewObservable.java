package observer;

import java.util.ArrayList;
import java.util.List;

public class KeystoreOverviewObservable {
	private List<KeystoreOverviewObserver> observers = new ArrayList<>();

	public void addObserver(KeystoreOverviewObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(KeystoreOverviewObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (KeystoreOverviewObserver observer : observers) {
			observer.onKeystoreChange();
		}
	}
}
