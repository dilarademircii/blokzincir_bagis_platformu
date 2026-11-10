
-- =====================================================
-- V1: BLOKZİNCİR BAĞIŞ PLATFORMU
-- İlk veritabanı şeması
-- =====================================================

-- 1. KURUMLAR
CREATE TABLE kurumlar (
                          id BIGSERIAL PRIMARY KEY,
                          kurum_adi VARCHAR(150) NOT NULL,
                          vergi_numarasi VARCHAR(20) NOT NULL UNIQUE,
                          adres VARCHAR(500),
                          telefon VARCHAR(20),
                          email VARCHAR(100),
                          aciklama VARCHAR(500),
                          aktif BOOLEAN NOT NULL DEFAULT TRUE,
                          olusturma_tarihi TIMESTAMP NOT NULL
);

-- 2. KULLANICILAR

CREATE TABLE kullanicilar (
                              id BIGSERIAL PRIMARY KEY,
                              ad VARCHAR(50) NOT NULL,
                              soyad VARCHAR(50) NOT NULL,
                              email VARCHAR(100) NOT NULL UNIQUE,
                              sifre_hash VARCHAR(255) NOT NULL,
                              telefon VARCHAR(20),
                              rol VARCHAR(30) NOT NULL,
                              hesap_durumu VARCHAR(20) NOT NULL,
                              kurum_id BIGINT,
                              olusturma_tarihi TIMESTAMP NOT NULL,
                              guncelleme_tarihi TIMESTAMP,

                              wallet_address VARCHAR(255) UNIQUE,
                              wallet_nonce VARCHAR(255),

                              CONSTRAINT fk_kullanicilar_kurum
                                  FOREIGN KEY (kurum_id) REFERENCES kurumlar(id)
);

-- 3. CUZDANLAR
CREATE TABLE cuzdanlar (
                           id BIGSERIAL PRIMARY KEY,
                           cuzdan_adresi VARCHAR(42) NOT NULL UNIQUE,
                           durum VARCHAR(30) NOT NULL,
                           kullanici_id BIGINT NOT NULL,
                           ag_adi VARCHAR(100),
                           olusturma_tarihi TIMESTAMP NOT NULL,
                           dogrulama_tarihi TIMESTAMP,

                           CONSTRAINT fk_cuzdan_kullanici
                               FOREIGN KEY (kullanici_id) REFERENCES kullanicilar(id)
);

-- 4. KAMPANYALAR
CREATE TABLE kampanyalar (
                             id BIGSERIAL PRIMARY KEY,
                             baslik VARCHAR(150) NOT NULL,
                             aciklama TEXT NOT NULL,
                             hedef_tutar NUMERIC(36,18) NOT NULL,
                             para_birimi VARCHAR(10) NOT NULL,
                             durum VARCHAR(30) NOT NULL,
                             kurum_id BIGINT NOT NULL,
                             genel_bagis_mi BOOLEAN NOT NULL DEFAULT FALSE,
                             baslangic_tarihi TIMESTAMP,
                             bitis_tarihi TIMESTAMP,
                             olusturma_tarihi TIMESTAMP NOT NULL,

                             CONSTRAINT fk_kampanya_kurum
                                 FOREIGN KEY (kurum_id) REFERENCES kurumlar(id)
);

-- 5. TEDARIKCILER
CREATE TABLE tedarikciler (
                              id BIGSERIAL PRIMARY KEY,
                              firma_adi VARCHAR(150) NOT NULL,
                              vergi_numarasi VARCHAR(20),
                              email VARCHAR(100),
                              telefon VARCHAR(20),
                              adres VARCHAR(500),
                              aktif BOOLEAN NOT NULL DEFAULT TRUE,
                              olusturma_tarihi TIMESTAMP NOT NULL
);

-- 6. BAGISLAR
CREATE TABLE bagislar (
                          id BIGSERIAL PRIMARY KEY,
                          tutar NUMERIC(36,18) NOT NULL,
                          para_birimi VARCHAR(10) NOT NULL,
                          durum VARCHAR(30) NOT NULL,
                          kampanya_id BIGINT NOT NULL,
                          bagisci_id BIGINT NOT NULL,
                          cuzdan_id BIGINT,
                          olusturma_tarihi TIMESTAMP NOT NULL,

                          CONSTRAINT fk_bagis_kampanya
                              FOREIGN KEY (kampanya_id) REFERENCES kampanyalar(id),

                          CONSTRAINT fk_bagis_bagisci
                              FOREIGN KEY (bagisci_id) REFERENCES kullanicilar(id),

                          CONSTRAINT fk_bagis_cuzdan
                              FOREIGN KEY (cuzdan_id) REFERENCES cuzdanlar(id)
);

-- 7. HARCAMALAR
CREATE TABLE harcamalar (
                            id BIGSERIAL PRIMARY KEY,
                            baslik VARCHAR(200) NOT NULL,
                            aciklama TEXT NOT NULL,
                            tutar NUMERIC(36,18) NOT NULL,
                            para_birimi VARCHAR(10) NOT NULL,
                            durum VARCHAR(30) NOT NULL,
                            kampanya_id BIGINT NOT NULL,
                            tedarikci_id BIGINT,
                            olusturan_id BIGINT NOT NULL,
                            olusturma_tarihi TIMESTAMP NOT NULL,
                            gerceklesme_tarihi TIMESTAMP,

                            CONSTRAINT fk_harcama_kampanya
                                FOREIGN KEY (kampanya_id) REFERENCES kampanyalar(id),

                            CONSTRAINT fk_harcama_tedarikci
                                FOREIGN KEY (tedarikci_id) REFERENCES tedarikciler(id),

                            CONSTRAINT fk_harcama_olusturan
                                FOREIGN KEY (olusturan_id) REFERENCES kullanicilar(id)
);

-- 8. KANIT BELGELERI
CREATE TABLE kanit_belgeleri (
                                 id BIGSERIAL PRIMARY KEY,
                                 dosya_adi VARCHAR(255) NOT NULL,
                                 dosya_yolu VARCHAR(500) NOT NULL,
                                 dosya_hash VARCHAR(64) NOT NULL,
                                 belge_turu VARCHAR(30) NOT NULL,
                                 harcama_id BIGINT NOT NULL,
                                 yukleyen_id BIGINT NOT NULL,
                                 yuklenme_tarihi TIMESTAMP NOT NULL,

                                 CONSTRAINT fk_kanit_harcama
                                     FOREIGN KEY (harcama_id) REFERENCES harcamalar(id),

                                 CONSTRAINT fk_kanit_yukleyen
                                     FOREIGN KEY (yukleyen_id) REFERENCES kullanicilar(id)
);

-- 9. HARCAMA ONAYLARI
CREATE TABLE harcama_onaylari (
                                  id BIGSERIAL PRIMARY KEY,
                                  harcama_id BIGINT NOT NULL,
                                  onaylayan_id BIGINT NOT NULL,
                                  karar VARCHAR(30) NOT NULL,
                                  aciklama VARCHAR(1000),
                                  onay_tarihi TIMESTAMP NOT NULL,

                                  CONSTRAINT fk_onay_harcama
                                      FOREIGN KEY (harcama_id) REFERENCES harcamalar(id),

                                  CONSTRAINT fk_onay_kullanici
                                      FOREIGN KEY (onaylayan_id) REFERENCES kullanicilar(id)
);

-- 10. BLOCKCHAIN ISLEMLERI
CREATE TABLE blockchain_islemleri (
                                      id BIGSERIAL PRIMARY KEY,
                                      islem_turu VARCHAR(30) NOT NULL,
                                      durum VARCHAR(30) NOT NULL,
                                      transaction_hash VARCHAR(66) UNIQUE,
                                      chain_id BIGINT NOT NULL,
                                      tutar NUMERIC(36,18),
                                      gonderen_adres VARCHAR(42),
                                      alici_adres VARCHAR(42),
                                      bagis_id BIGINT,
                                      harcama_id BIGINT,
                                      blok_numarasi BIGINT,
                                      olusturma_tarihi TIMESTAMP NOT NULL,
                                      onaylanma_tarihi TIMESTAMP,

                                      CONSTRAINT fk_blockchain_bagis
                                          FOREIGN KEY (bagis_id) REFERENCES bagislar(id),

                                      CONSTRAINT fk_blockchain_harcama
                                          FOREIGN KEY (harcama_id) REFERENCES harcamalar(id)
);

-- 11. BLOCKCHAIN EVENTLERI
CREATE TABLE blockchain_eventleri (
                                      id BIGSERIAL PRIMARY KEY,
                                      chain_id BIGINT NOT NULL,
                                      transaction_hash VARCHAR(66) NOT NULL,
                                      log_index BIGINT NOT NULL,
                                      event_adi VARCHAR(100) NOT NULL,
                                      blok_numarasi BIGINT NOT NULL,
                                      event_verisi TEXT,
                                      islendi BOOLEAN NOT NULL DEFAULT FALSE,
                                      blockchain_islem_id BIGINT,
                                      olusturma_tarihi TIMESTAMP NOT NULL,
                                      islenme_tarihi TIMESTAMP,

                                      CONSTRAINT fk_event_blockchain_islem
                                          FOREIGN KEY (blockchain_islem_id)
                                              REFERENCES blockchain_islemleri(id),

                                      CONSTRAINT uk_event_islem
                                          UNIQUE (chain_id, transaction_hash, log_index)
);

-- 12. BILDIRIMLER
CREATE TABLE bildirimler (
                             id BIGSERIAL PRIMARY KEY,
                             baslik VARCHAR(150) NOT NULL,
                             mesaj VARCHAR(1000) NOT NULL,
                             bildirim_turu VARCHAR(30) NOT NULL,
                             durum VARCHAR(20) NOT NULL,
                             kullanici_id BIGINT NOT NULL,
                             olusturma_tarihi TIMESTAMP NOT NULL,
                             okunma_tarihi TIMESTAMP,

                             CONSTRAINT fk_bildirim_kullanici
                                 FOREIGN KEY (kullanici_id) REFERENCES kullanicilar(id)
);

-- 13. DENETIM KAYITLARI
CREATE TABLE denetim_kayitlari (
                                   id BIGSERIAL PRIMARY KEY,
                                   kullanici_id BIGINT,
                                   islem_turu VARCHAR(100) NOT NULL,
                                   varlik_turu VARCHAR(100) NOT NULL,
                                   varlik_id BIGINT,
                                   ip_adresi VARCHAR(45),
                                   eski_deger TEXT,
                                   yeni_deger TEXT,
                                   islem_tarihi TIMESTAMP NOT NULL,

                                   CONSTRAINT fk_denetim_kullanici
                                       FOREIGN KEY (kullanici_id) REFERENCES kullanicilar(id)
);

-- 11. ZINCIR ISLEMLERI
CREATE TABLE zincir_islemleri (
                                  id BIGSERIAL PRIMARY KEY,
                                  tx_hash VARCHAR(66) NOT NULL,
                                  chain_id BIGINT,
                                  contract_address VARCHAR(42),
                                  event_name VARCHAR(100),
                                  block_number BIGINT,
                                  log_index INTEGER,
                                  status VARCHAR(255) NOT NULL DEFAULT 'PENDING',
                                  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                  CONSTRAINT uk_zincir_islemleri_tx_log
                                      UNIQUE (tx_hash, log_index)
);


-- =====================================================
-- INDEKSLER
-- =====================================================

CREATE INDEX idx_kampanya_kurum
    ON kampanyalar(kurum_id);

CREATE INDEX idx_bagis_kampanya
    ON bagislar(kampanya_id);

CREATE INDEX idx_bagis_bagisci
    ON bagislar(bagisci_id);

CREATE INDEX idx_harcama_kampanya
    ON harcamalar(kampanya_id);

CREATE INDEX idx_harcama_onay_harcama
    ON harcama_onaylari(harcama_id);

CREATE INDEX idx_blockchain_transaction_hash
    ON blockchain_islemleri(transaction_hash);

CREATE INDEX idx_bildirim_kullanici
    ON bildirimler(kullanici_id);

CREATE INDEX idx_denetim_kullanici
    ON denetim_kayitlari(kullanici_id);
