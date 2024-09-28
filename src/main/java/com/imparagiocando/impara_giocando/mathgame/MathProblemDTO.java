package com.imparagiocando.impara_giocando.mathgame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MathProblemDTO {
    private int num1;
    private int num2;
    private String operation;
}
