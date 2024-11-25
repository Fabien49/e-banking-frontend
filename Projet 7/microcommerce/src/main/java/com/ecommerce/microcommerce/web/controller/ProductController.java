package com.ecommerce.microcommerce.web.controller;


import com.ecommerce.microcommerce.web.dao.ProductDao;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.ecommerce.microcommerce.web.model.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(description = "Gestion des produits")
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    //Récupérer la liste des produits
    @RequestMapping(value="/Produits", method=RequestMethod.GET)
    public List<Product> listeProduits() {
        return productDao.findAll();
    }

    //Récupérer un produit par son Id
    @ApiOperation(value = "Récupère un produit selon son id")
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) throws ProduitIntrouvableException {

        Product produit = productDao.findById(id);

        if(produit==null) throw new ProduitIntrouvableException("Le produit avec l'id est INTROUVABLE. Écran Bleu si je pouvais.");

        return produit;
    }

/*    //ajouter un produit
    @PostMapping(value = "/Produits")
    public void ajouterProduit(@RequestBody Product product) {
        productDao.save(product);
    }*/

    //ajouter un produit
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/test/produits/{prixLimit}")
    public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(prixLimit);
    }

}
