package com.miaosha.dataObject;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Component
public class BaseEntity {
    @Id
    @GeneratedValue(
            generator = "system-uuid"
    )
    @GenericGenerator(
            name = "system-uuid",
            strategy = "uuid"
    )
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
