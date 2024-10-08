package interfaces;

import java.util.ArrayList;

public interface IProduit<T> {
    void add (T t);
    ArrayList<T> getAll();
    void update(T t);
    boolean delete(T t);
}
