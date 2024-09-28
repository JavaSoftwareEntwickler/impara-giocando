package com.imparagiocando.imparagiocando.mathgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MathGameService {

    private Random random = new Random();
    @Autowired
    private PunteggioDTO punteggio;
    @Autowired
    private RispostaEsattaDTO rispostaEsattaDTO;

    // Genera un problema matematico casuale (somma o sottrazione)
    public MathProblemDTO generateProblem() {
        int num1 = random.nextInt(10) + 1; // Numeri da 1 a 10
        int num2 = random.nextInt(10) + 1;
        String[] operations = {"+", "-", "*"};
        String operation = operations[random.nextInt(operations.length)];
        return new MathProblemDTO(num1, num2, operation);
    }

    public String checkRisposta(int rispostaUtente) {
        if(verificaRispostaUtente(rispostaUtente)){
            return "Ottimo! La risposta è corretta!";
        }else{
            return "Oops! La risposta corretta era " + rispostaEsattaDTO.getRispostaEsatta();
        }
    }

    public boolean verificaRispostaUtente(int answer) {
         return answer == rispostaEsattaDTO.getRispostaEsatta();
    }

    public void setPunteggio(int answer) {
        if(answer == rispostaEsattaDTO.getRispostaEsatta()){
            punteggio.setTotale(punteggio.getTotale()+1);
            punteggio.setRisposteCorrette(punteggio.getRisposteCorrette()+1);
        }
        else{
            punteggio.setRisposteErrate(punteggio.getRisposteErrate()+1);
        }
    }

    public void calcolaRispostaCorretta(int num1, int num2, String operation){
       rispostaEsattaDTO.setRispostaEsatta(switch (operation) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            default -> 0;
        });
    }

    public SoluzioneDTO generaSoluzioni(MathProblemDTO problema) {
        calcolaRispostaCorretta(problema.getNum1(), problema.getNum2(), problema.getOperation());
        int soluzioneCorretta = rispostaEsattaDTO.getRispostaEsatta();
                Set<Integer> soluzioni = popolaSetSoluzioni(soluzioneCorretta);
        return getSoluzioneDTOConOrdineRandomico(soluzioni);
    }

    private Set<Integer> popolaSetSoluzioni(int soluzioneCorretta) {
        Set<Integer> soluzioni = new HashSet<>(4);
        soluzioni.add(soluzioneCorretta);
        if(soluzioneCorretta < 0) soluzioni.add((random.nextInt(10) + 1)*-1);
        while(soluzioni.size()<4){
            soluzioni.add(getRandomNumber());
        }
        return soluzioni;
    }

    private static List<Integer> getSoluzioniMischiate(Set<Integer> soluzioni) {
        List<Integer> listSoluzioni = new ArrayList<>(soluzioni);
        Collections.shuffle(listSoluzioni);
        return listSoluzioni;
    }

    private int getRandomNumber(){
        return switch (random.nextInt(4) + 1) {
            case 1 -> random.nextInt(33) + 1;
            case 2 -> random.nextInt(66) + 1;
            case 3 -> random.nextInt(99) + 1;
            case 4 -> (random.nextInt(10) + 1)*-1;
            default -> 0;
        };
    }

    private static SoluzioneDTO getSoluzioneDTOConOrdineRandomico(Set<Integer> soluzioni) {
        List<Integer> valori = getSoluzioniMischiate(soluzioni);
        return new SoluzioneDTO(valori.get(0), valori.get(1), valori.get(2), valori.get(3));
    }

    public int getPunteggio() {
        return punteggio.getTotale();
    }
    public PunteggioDTO getStatistiche(){
        return punteggio;
    }
}
