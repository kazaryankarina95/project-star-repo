package org.example.starrepo.requests;

    public interface CrudInterface {
        Object create(Object obj);

        Object read(String id);

        Object update(Object obj);

        Object delete(String id);
    }