package com.imparagiocando.imparagiocando.mathgame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    MathProblemDTO problem;
    RispostaEsattaDTO rispostaEsatta;
    SoluzioneDTO soluzione;
    PunteggioDTO punteggio;

    public GameDto(MathProblemDTO problema, SoluzioneDTO soluzioni, PunteggioDTO punteggio) {
        this.problem=problema;
        this.soluzione=soluzioni;
        this.punteggio=punteggio;
    }
}
