package com.hulkStore.request;


import com.hulkStore.model.ArticleSold;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// Puede notarse que esta request tiene pocos fields que enviar... se intento respetar las buenas practicas de Rest api
public class SaleRequest {
    private List<ArticleSold> articlesToBeSold;
}
