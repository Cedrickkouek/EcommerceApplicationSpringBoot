package com.business.brendaapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business.brendaapp.entities.Commande;
import com.business.brendaapp.exception.CommandeNotFoundException;
import com.business.brendaapp.exception.ProductNotFoundException;
import com.business.brendaapp.payloads.in.CreateCommandePayload;
import com.business.brendaapp.payloads.in.UpdateCommandePayload;
import com.business.brendaapp.services.CommandeService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.AllArgsConstructor;

@RestController
@OpenAPIDefinition
@AllArgsConstructor
@RequestMapping(value = "/commande")
public class CommandeController {

    private CommandeService commandeService;

    @PostMapping(value = "/createCommande")
    public ResponseEntity<Commande> CreateCommand(@Valid @RequestBody CreateCommandePayload creeCommandePayload) throws ProductNotFoundException
    {
        Commande commande = commandeService.CreerUneCommande(creeCommandePayload);
        return new ResponseEntity<Commande>(commande, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getCommande/{idCommande}")
    public ResponseEntity<Commande> SearchCommande(@PathVariable String idCommande) throws CommandeNotFoundException
    {
        Commande commande = commandeService.findCommandeByid(idCommande);
        return new ResponseEntity<Commande>(commande, HttpStatus.FOUND);
    }

    @PutMapping(value = "/editCommande")
    public ResponseEntity<Commande> EditCommande(@Valid @RequestBody UpdateCommandePayload updateCommandePayload) throws CommandeNotFoundException
    {
        Commande commande = commandeService.EditCommande(updateCommandePayload);
        return new ResponseEntity<Commande>(commande, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllCommandes")
    public ResponseEntity<List<Commande>> getAllCommandes()
    {
        var resp = commandeService.getAllCommande();
        return ResponseEntity.ok(resp);
    }
}
