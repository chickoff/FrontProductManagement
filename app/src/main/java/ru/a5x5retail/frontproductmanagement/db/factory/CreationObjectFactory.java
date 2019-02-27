package ru.a5x5retail.frontproductmanagement.db.factory;

public class CreationObjectFactory<T> {

    private Class<T> objClass;
    public CreationObjectFactory(Class<T> objClass) {
        this.objClass = objClass;
    }

    public T Create(){

        T o = null;
        try {
            o = objClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }
}
