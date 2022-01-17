package ru.bootjava.graduating.restaurantsvoting.util;

import ru.bootjava.graduating.restaurantsvoting.error.IllegalRequestDataException;
import ru.bootjava.graduating.restaurantsvoting.model.BaseEntity;

public class ValidationUtil {

    public static void checkNew(BaseEntity entity) {
        if(!entity.isNew()) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + "must be new (id=null");
        }
    }

    public static void assureIdConsistent(BaseEntity entity, int id) {
        if(entity.isNew()) {
            entity.setId(id);
        } else if(entity.id() != id) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " must have id = " + id);
        }
    }
}
