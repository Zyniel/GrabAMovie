/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.utils;

/**
 *
 * @author OCanada
 */
public interface Observer {

    //method to update the observer, used by subject
    public void update();

    //attach with subject to observe
    public void setSubject(Subject sub);

    public void update(Object msg);
}