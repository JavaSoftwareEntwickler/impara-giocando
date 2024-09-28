package com.imparagiocando.impara_giocando.mathgame;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MathGameService {

    private Random random = new Random();

    // Genera un problema matematico casuale (somma o sottrazione)
    public MathProblemDTO generateProblem() {
        int num1 = random.nextInt(10) + 1; // Numeri da 1 a 10
        int num2 = random.nextInt(10) + 1;
        String[] operations = {"+", "-", "*"};
        String operation = operations[random.nextInt(operations.length)];
        return new MathProblemDTO(num1, num2, operation);
    }

    public String checkRisposta(int num1, int num2, String operation, int answer) {
        if(checkAnswer(num1,num2,operation,answer)){
            return "Bravo! La risposta è corretta!";
        }else{
            return "Oops! La risposta corretta era " + calcolaRispostaCorretta(num1,num2,operation);
        }
    }

    // Verifica la risposta dell'utente
    public boolean checkAnswer(int num1, int num2, String operation, int answer) {
        int correctAnswer = calcolaRispostaCorretta(num1,num2,operation);

        if (answer == correctAnswer) {
            return true;//"Bravo! La risposta è corretta!";
        } else {
            return false; //"Oops! La risposta corretta era " + correctAnswer;
        }
    }
    public int calcolaRispostaCorretta(int num1, int num2, String operation){
        return switch (operation) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            default -> 0;
        };
    }

    public void setColoreRisposta(){

    }
}
