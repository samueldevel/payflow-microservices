package com.samueldev.project.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Consumer;

public class DataTestUtil<T, ID> {
    private final T toBeSaved;

    public DataTestUtil(T toBeSaved) {
        this.toBeSaved = toBeSaved;
    }

    public void withEntitySaved(JpaRepository<T, ID> repository, Consumer<T> consumer) {
        T entitySaved = repository.save(toBeSaved);
        consumer.accept(entitySaved);
    }

}
