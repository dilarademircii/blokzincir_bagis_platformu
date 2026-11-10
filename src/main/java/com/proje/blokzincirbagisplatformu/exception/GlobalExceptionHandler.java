package com.proje.blokzincirbagisplatformu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Merkezi hata yönetim sınıfı.
 * Tüm controller'lardan fırlatılan istisnaları yakalar ve
 * tutarlı bir JSON hata yanıtı döndürür.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

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
    public ResponseEntity<HataYaniti> dogrulamaHatasi(MethodArgumentNotValidException ex) {
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
    public ResponseEntity<HataYaniti> gecersizArguman(IllegalArgumentException ex) {
        HataYaniti yanit = new HataYaniti(
                HttpStatus.BAD_REQUEST.value(),
                "GECERSIZ_ARGUMAN",
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(yanit);
    }

    // -------------------------------------------------------------------------
    // Beklenmeyen hatalar
    // -------------------------------------------------------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HataYaniti> genelHata(Exception ex) {
        HataYaniti yanit = new HataYaniti(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "SUNUCU_HATASI",
                "Beklenmeyen bir hata oluştu. Lütfen tekrar deneyin."
        );
        return ResponseEntity.internalServerError().body(yanit);
    }

    // =========================================================================
    // İç sınıf: Standart hata yanıt modeli
    // =========================================================================

    public static class HataYaniti {

        private final int durum;
        private final String kod;
        private final String mesaj;
        private final Map<String, String> alanHatalari;
        private final LocalDateTime zaman;

        public HataYaniti(int durum, String kod, String mesaj) {
            this(durum, kod, mesaj, null);
        }

        public HataYaniti(int durum, String kod, String mesaj,
                          Map<String, String> alanHatalari) {
            this.durum = durum;
            this.kod = kod;
            this.mesaj = mesaj;
            this.alanHatalari = alanHatalari;
            this.zaman = LocalDateTime.now();
        }

        public int getDurum()                        { return durum; }
        public String getKod()                       { return kod; }
        public String getMesaj()                     { return mesaj; }
        public Map<String, String> getAlanHatalari() { return alanHatalari; }
        public LocalDateTime getZaman()              { return zaman; }
    }
}
