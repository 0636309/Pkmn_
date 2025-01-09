package ru.mirea.kryukovakn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.kryukovakn.models.Card;
import ru.mirea.kryukovakn.models.Student;
import ru.mirea.kryukovakn.services.CardService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    // норм
    @GetMapping("")
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    // норм
    @GetMapping("/{name}")
    public Card getCardByName(@PathVariable String name) {
        return cardService.getCardByName(name);
    }

    // работает!!!!
    @GetMapping("/owner")
    public Card getCardByOwnerFullName(@RequestBody Student student) {
        return cardService.getCardByOwnerFullName(student.getFirstName(), student.getSurName(), student.getFamilyName());
    }

    // работает(?)
    @GetMapping("/id/{id}")
    public Card getCardById(@PathVariable UUID id) {
        return cardService.getCardById(id);
    }

    // ем......
    // что-то с группой
    @PostMapping
    public Card createOrUpdateCard(@RequestBody Card cardRequest) throws JsonProcessingException {
        return cardService.saveCard(cardRequest);
    }
}
