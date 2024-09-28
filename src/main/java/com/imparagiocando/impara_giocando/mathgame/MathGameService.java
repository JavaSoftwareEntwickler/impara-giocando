package com.imparagiocando.impara_giocando.mathgame;

import org.springframework.stereotype.Service;

import java.util.*;

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

    public SoluzioneDTO generaSoluzioni(MathProblemDTO problema) {
        int soluzioneCorretta = calcolaRispostaCorretta(problema.getNum1(), problema.getNum2(), problema.getOperation());

        Set<Integer> soluzioni = new HashSet<>(4);
        soluzioni.add(soluzioneCorretta);
        while(soluzioni.size()<4){
            soluzioni.add(random.nextInt(100) + 1);
        }
        List<Integer> listSoluzioni = new ArrayList<>(soluzioni);
        Collections.shuffle(listSoluzioni);
        if (soluzioneCorretta<0) {
            int negativo = listSoluzioni.getFirst()*-1;
            listSoluzioni.set(0, negativo);
        }
        return getSoluzioneDTOConOrdineRandomico(soluzioneCorretta, listSoluzioni.get(0), listSoluzioni.get(1), listSoluzioni.get(2));
    }

    private static SoluzioneDTO getSoluzioneDTOConOrdineRandomico(int soluzioneCorretta, int sol2, int sol3, int sol4) {
        List<Integer> valori = new ArrayList<>();
        valori.add(soluzioneCorretta);
        valori.add(sol2);
        valori.add(sol3);
        valori.add(sol4);
        Collections.shuffle(valori);
        return new SoluzioneDTO(valori.get(0), valori.get(1), valori.get(2), valori.get(3));
    }
}
