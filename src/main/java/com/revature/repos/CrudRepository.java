package com.revature.repos;

import com.revature.utilities.LinkedList;
import com.revature.utilities.Set;

/** the interface for userRepo objects,
 * allows us to save, find, update, and delete
 */
public interface CrudRepository<T> {

    void save(T newObj);
    LinkedList<T> findAll();
    T findById(int id);
    boolean update(T updatedObj);
    boolean deleteById(int id);

}