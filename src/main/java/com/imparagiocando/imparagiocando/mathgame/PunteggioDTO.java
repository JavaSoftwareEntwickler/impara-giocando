package com.imparagiocando.imparagiocando.mathgame;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class PunteggioDTO {
    private int totale;
    private int risposteCorrette;
    private int risposteErrate;
}
