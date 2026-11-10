
package com.proje.blokzincirbagisplatformu.service.impl;

import com.proje.blokzincirbagisplatformu.service.TemelService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class TemelServiceImpl<T> implements TemelService<T> {

    protected final JpaRepository<T, Long> repository;

    protected TemelServiceImpl(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public T kaydet(T varlik) {
        return repository.save(varlik);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> idIleGetir(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> tumunuGetir() {
        return repository.findAll();
    }

    @Override
    public void sil(Long id) {
        repository.deleteById(id);
    }
}
