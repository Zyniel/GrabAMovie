/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.utils;

/**
 *
 * @author OCanada
 */
public interface Subject {

	//methods to register and unregister observers
	public void register(Observer obj);
	public void unregister(Observer obj);
	
	//method to notify observers of change
	public void notifyObservers(Object msg);
	
	//method to get updates from subject
	public Object getUpdate(Observer obj);
	
}