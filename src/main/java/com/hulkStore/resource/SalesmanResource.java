package com.hulkStore.resource;

import com.hulkStore.dto.ArticleDTO;
import com.hulkStore.dto.SaleDTO;
import com.hulkStore.exception.ArticleNotFoundException;
import com.hulkStore.exception.BusinessException;
import com.hulkStore.model.Sale;
import com.hulkStore.request.SaleRequest;
import com.hulkStore.service.ArticleService;
import com.hulkStore.service.SaleService;
import com.hulkStore.transformer.ArticleTransformer;
import com.hulkStore.transformer.SaleTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;


/* Este recurso deberia estar en otro artefacto (una api rest) por cuestiones de tiempo esta esta definido
aca y se encarga de gestinar las ventas*/
@RestController
@RequestMapping("/salesman")
public class SalesmanResource {
    @Autowired
    private ArticleTransformer articleTransformer;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SaleTransformer saleTransformer;

    @Autowired
    private SaleService saleService;

    @GetMapping(value="/articles")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody Iterable<ArticleDTO> getArticles() {
        return articleTransformer.transform(articleService.getArticles());
    }

    @GetMapping(value="/sales")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody Iterable<SaleDTO> getSales() {
        return saleTransformer.transform(saleService.getSales());
    }

    @PostMapping(value="/sales")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody
    ResponseEntity<Object> completeSale(@RequestBody SaleRequest saleRequest) {
        try {
            Sale sale = saleService.addSale(saleRequest.getArticlesToBeSold());
            return ResponseEntity.ok(saleTransformer.trensform(sale));
        } catch (ArticleNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (BusinessException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE).body(e.getMessage());
        }
    }
}
