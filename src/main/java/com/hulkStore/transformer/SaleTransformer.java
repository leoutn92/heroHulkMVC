package com.hulkStore.transformer;

import com.hulkStore.dto.SaleDTO;
import com.hulkStore.model.Sale;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SaleTransformer {
    public List<SaleDTO> transform(Iterable<Sale> sales) {
        List<SaleDTO> salesDTO = new ArrayList<>();
        for (Sale sale : sales) {
            salesDTO.add(trensform(sale));
        }
        return salesDTO;
    };

    public SaleDTO trensform(Sale sale) {
        return SaleDTO.builder()
                .saleCode(sale.getSaleCode())
                .saleDate(sale.getTransactionDateFormatted())
                .totalPriceSale(sale.getTotalSalePrice())
                .build();
    }
}
