
package com.proje.blokzincirbagisplatformu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // -------------------------------------------------------------------------
    // Kayıt bulunamadı
    // -------------------------------------------------------------------------

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HataYaniti> entityBulunamadi(EntityNotFoundException ex) {

        HataYaniti yanit = new HataYaniti(
                HttpStatus.NOT_FOUND.value(),
                "KAYIT_BULUNAMADI",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(yanit);
    }

    // -------------------------------------------------------------------------
    // Geçersiz istek verisi (@Valid başarısız)
    // -------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HataYaniti> dogrulamaHatasi(
            MethodArgumentNotValidException ex) {

        Map<String, String> hatalar = new HashMap<>();

        for (FieldError hata : ex.getBindingResult().getFieldErrors()) {
            hatalar.put(hata.getField(), hata.getDefaultMessage());
        }

        HataYaniti yanit = new HataYaniti(
                HttpStatus.BAD_REQUEST.value(),
                "DOGRULAMA_HATASI",
                "İstek verisi doğrulaması başarısız",
                hatalar
        );

        return ResponseEntity.badRequest().body(yanit);
    }

    // -------------------------------------------------------------------------
    // Genel geçersiz argüman
    // -------------------------------------------------------------------------

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HataYaniti> gecersizArguman(
            IllegalArgumentException ex) {

        HataYaniti yanit = new HataYaniti(
                HttpStatus.BAD_REQUEST.value(),
                "GECERSIZ_ARGUMAN",
                ex.getMessage()
        );

        return ResponseEntity.badRequest().body(yanit);
    }

    // -------------------------------------------------------------------------
    // HTTP durum kodlu hatalar (400, 403, 404, 502 vb.)
    // -------------------------------------------------------------------------

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<HataYaniti> responseStatusHatasi(
            ResponseStatusException ex) {

        HttpStatusCode durumKodu = ex.getStatusCode();

        HataYaniti yanit = new HataYaniti(
                durumKodu.value(),
                "ISTEK_HATASI",
                ex.getReason() != null
                        ? ex.getReason()
                        : "İstek işlenemedi."
        );

        return ResponseEntity.status(durumKodu).body(yanit);
    }

    // -------------------------------------------------------------------------
    // Beklenmeyen hatalar
    // -------------------------------------------------------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HataYaniti> genelHata(Exception ex) {

        logger.error("Beklenmeyen bir sunucu hatası oluştu:", ex);

        HataYaniti yanit = new HataYaniti(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "SUNUCU_HATASI",
                "Beklenmeyen bir hata oluştu. Lütfen tekrar deneyin."
        );

        return ResponseEntity.internalServerError().body(yanit);
    }
}
