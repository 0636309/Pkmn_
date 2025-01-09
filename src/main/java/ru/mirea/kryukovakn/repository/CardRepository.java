package ru.mirea.kryukovakn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.kryukovakn.entity.CardEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    Optional<CardEntity> findByName(String name);

    @Query("SELECT c FROM CardEntity c WHERE c.pokemonOwner.familyName = :familyName AND c.pokemonOwner.firstName = :firstName AND c.pokemonOwner.surName = :surName")
    Optional<CardEntity> findByOwnerFullName(String firstName, String surName, String familyName);

    List<CardEntity> findByPokemonOwner_Id(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);
    Optional<CardEntity> findByNameAndNumber(String name, String number);

}
