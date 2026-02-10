package com.example.sqlide.observables;

import com.example.sqlide.interfaces.DatabaseObserver;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnectionObservable {
  private List<DatabaseObserver> observers = new ArrayList<>();
  private boolean state = true;

  public void subscribe(DatabaseObserver observer) {
    observers.add(observer);
  }

  public void delete(DatabaseObserver observer) {
    observers.remove(observer);
  }

  public void notifyObservers() {
    for (DatabaseObserver observer : observers) {
      if(state)
        return;
      observer.update(state);
    }
  }

  public List<DatabaseObserver> getObservers() {
    return observers;
  }

  public void setObservers(
      List<DatabaseObserver> observers) {
    this.observers = observers;
  }

  public boolean isState() {
    return state;
  }

  public void setState(boolean state) {
    this.state = state;
  }
}
