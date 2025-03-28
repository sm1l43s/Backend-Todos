package by.program.restAPI.model;

import org.springframework.util.Assert;

public interface Entity {
    Long getId();

    void setId(Long id);

    default boolean isNew() {
        return getId() == null;
    }

    default Long id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
