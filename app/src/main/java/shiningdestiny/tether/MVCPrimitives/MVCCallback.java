package shiningdestiny.tether.MVCPrimitives;

// An interface for callbacks from MVC routines.
public interface MVCCallback<T> {
    public void invoke(T obj);
}
