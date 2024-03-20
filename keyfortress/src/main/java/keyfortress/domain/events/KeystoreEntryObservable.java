package keyfortress.domain.events;

import java.util.ArrayList;
import java.util.List;

public class KeystoreEntryObservable {
	private List<KeystoreEntryObserver> observers = new ArrayList<>();

	public void addObserver(KeystoreEntryObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(KeystoreEntryObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (KeystoreEntryObserver observer : observers) {
			observer.onKeystoreEntryChange();
		}
	}
}
