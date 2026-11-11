
package com.proje.blokzincirbagisplatformu.service;

import java.util.List;
import java.util.Optional;

public interface TemelService<T> {

    T kaydet(T varlik);

    Optional<T> idIleGetir(Long id);

    List<T> tumunuGetir();

    void sil(Long id);

}
