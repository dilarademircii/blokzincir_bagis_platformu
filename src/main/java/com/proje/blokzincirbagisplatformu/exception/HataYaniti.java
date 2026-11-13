
package com.proje.blokzincirbagisplatformu.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HataYaniti {

    private int durum;
    private String kod;
    private String mesaj;
    private Map<String, String> alanHatalari;

    public HataYaniti(int durum, String kod, String mesaj) {
        this.durum = durum;
        this.kod = kod;
        this.mesaj = mesaj;
    }

    public HataYaniti(
            int durum,
            String kod,
            String mesaj,
            Map<String, String> alanHatalari) {

        this.durum = durum;
        this.kod = kod;
        this.mesaj = mesaj;
        this.alanHatalari = alanHatalari;
    }
}
