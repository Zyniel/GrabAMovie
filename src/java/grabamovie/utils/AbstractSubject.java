/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author OCanada
 */
public abstract class AbstractSubject implements Subject {

    private List<Observer> observers;
    private boolean changed;
    private final Object MUTEX = new Object();

    public AbstractSubject() {
        this.observers = new ArrayList<Observer>();
    }

    @Override
    public void register(Observer obj) {
        if (obj == null) {
            throw new NullPointerException("Null Observer");
        }
        synchronized (MUTEX) {
            if (!observers.contains(obj)) {
                observers.add(obj);
            }
        }
    }

    @Override
    public void unregister(Observer obj) {
        synchronized (MUTEX) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObservers() {
        List<Observer> observersLocal = null;
        //synchronization is used to make sure any observer registered after message is received is not notified
        synchronized (MUTEX) {
            if (!changed) {
                return;
            }
            observersLocal = new ArrayList<Observer>(this.observers);
            this.changed = false;
        }
        for (Observer obj : observersLocal) {
            obj.update();
        }

    }

    public abstract Object getUpdate(Observer obj);
}
