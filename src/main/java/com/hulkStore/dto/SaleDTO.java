package com.hulkStore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class SaleDTO {
    private int saleCode;
    private String saleDate;
    private float totalPriceSale;
}
