package com.proje.blokzincirbagisplatformu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Veritabanında aranan kayıt bulunamadığında fırlatılır.
 * HTTP 404 Not Found döndürür.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private final String entityAdi;
    private final Object entityId;

    public EntityNotFoundException(String entityAdi, Object entityId) {
        super(entityAdi + " bulunamadı. ID: " + entityId);
        this.entityAdi = entityAdi;
        this.entityId = entityId;
    }

    public EntityNotFoundException(String mesaj) {
        super(mesaj);
        this.entityAdi = null;
        this.entityId = null;
    }

    public String getEntityAdi() {
        return entityAdi;
    }

    public Object getEntityId() {
        return entityId;
    }
}
