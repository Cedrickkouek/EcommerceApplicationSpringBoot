package com.business.brendaapp.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.business.brendaapp.entities.Commande;
import com.business.brendaapp.enumeration.StatusCommande;
import com.business.brendaapp.exception.CommandeNotFoundException;
import com.business.brendaapp.exception.ProductNotFoundException;
import com.business.brendaapp.payloads.in.CreateCommandePayload;
import com.business.brendaapp.payloads.in.UpdateCommandePayload;
import com.business.brendaapp.repositories.CommandeRepo;
import com.business.brendaapp.repositories.ProductRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommandeService {
    private final CommandeRepo commandeRepo;
    private final ProductRepo productRepo;

    public Commande CreerUneCommande(CreateCommandePayload commandePayload) throws ProductNotFoundException
    {
        if(productRepo.existsById(commandePayload.getProduitId()))
        {
            Commande commande = Commande.builder()
            .addresseLivraison(commandePayload.getAddresseClient())
            .nomClient(commandePayload.getNomClient())
            .numeroClient(commandePayload.getNumeroClient())
            .productId(commandePayload.getProduitId())
            .build();
            commandeRepo.save(commande);

            commande.setStatusCommande(StatusCommande.CREEE_NON_CONFIRMEE);
            LocalDate now = LocalDate.now();

            commande.setDateCreation(now);

            commandeRepo.save(commande);

            return commande;
        }
        else
        {
            throw new ProductNotFoundException("Le produit avec id "+commandePayload.getProduitId()+" n'existe pas");
        }
    }

    public Commande EditCommande(UpdateCommandePayload updateCommandePayload) throws CommandeNotFoundException
    {
        if(commandeRepo.existsById(updateCommandePayload.getId()))
        {
            Commande commande = commandeRepo.findById(updateCommandePayload.getId()).get();
            if(updateCommandePayload.getAddresseLivraison().isEmpty())
            {
                if(updateCommandePayload.getNomClient().isEmpty())
                {
                    if(updateCommandePayload.getNumeroClient().isEmpty())
                    {
                        if(updateCommandePayload.getPaiementId().isEmpty())
                        {
                            if(updateCommandePayload.getProductId().isEmpty())
                            {

                            }
                            else
                            {
                                commande.setProductId(updateCommandePayload.getProductId());
                                commande.setDateModification(LocalDate.now());
                            }
                        }
                        else
                        {
                            commande.setPaiementId(updateCommandePayload.getPaiementId());
                            commande.setDateModification(LocalDate.now());
                        }
                    } else {
                        commande.setNumeroClient(updateCommandePayload.getNumeroClient());
                        commande.setDateModification(LocalDate.now());
                    }
                } else {
                    commande.setNomClient(updateCommandePayload.getNomClient());
                    commande.setDateModification(LocalDate.now());
                }
            }
            else {
                commande.setAddresseLivraison(updateCommandePayload.getAddresseLivraison());
                commande.setDateModification(LocalDate.now());
            }
            return commandeRepo.save(commande);
        } else {
            throw new CommandeNotFoundException("La commande avec id: "+updateCommandePayload.getId()+" n'existe pas!");
        }
    }

    public Commande setStatutConfirmee(String idCommande) throws CommandeNotFoundException
    {
        if(commandeRepo.existsById(idCommande))
        {
            Commande commande = commandeRepo.findById(idCommande).get();
            commande.setStatusCommande(StatusCommande.CONFIRMEE);
            return commandeRepo.save(commande);
        }
        else
        {
            throw new CommandeNotFoundException("La commande avec id "+idCommande+" n'existe pas!");
        }

    }
    public Commande setStatutEnCoursLivraison(String idCommande) throws CommandeNotFoundException
    {
        if(commandeRepo.existsById(idCommande))
        {
            Commande commande = commandeRepo.findById(idCommande).get();
            commande.setStatusCommande(StatusCommande.ENCOURS_LIVRAISON);
            return commandeRepo.save(commande);
        }
        else
        {
            throw new CommandeNotFoundException("La commande avec id "+idCommande+" n'existe pas!");
        }

    }
    public Commande setStatutLivree(String idCommande) throws CommandeNotFoundException
    {
        if(commandeRepo.existsById(idCommande))
        {
            Commande commande = commandeRepo.findById(idCommande).get();
            commande.setStatusCommande(StatusCommande.LIVREE);
            commande.setHeureLivraison(LocalTime.now());

            return commandeRepo.save(commande);
        }
        else
        {
            throw new CommandeNotFoundException("La commande avec id "+idCommande+" n'existe pas!");
        }

    }
    public Commande setStatutAnnulee(String idCommande) throws CommandeNotFoundException
    {
        if(commandeRepo.existsById(idCommande))
        {
            Commande commande = commandeRepo.findById(idCommande).get();
            commande.setStatusCommande(StatusCommande.ANNULEE);
            return commandeRepo.save(commande);
        }
        else
        {
            throw new CommandeNotFoundException("La commande avec id "+idCommande+" n'existe pas!");
        }
    }

    public Commande findCommandeByid(String idCommande) throws CommandeNotFoundException
    {
        boolean checkCommande = commandeRepo.existsById(idCommande);
        if(checkCommande)
        {
            return commandeRepo.findById(idCommande).get();
        }
        else
        {
            throw new CommandeNotFoundException("La Commande dont l'id est "+idCommande+" n'existe pas!");
        }
    }

    public List<Commande> getAllCommande()
    {
        return commandeRepo.findAll();
    }
}
