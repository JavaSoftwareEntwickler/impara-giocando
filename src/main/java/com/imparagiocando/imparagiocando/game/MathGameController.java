package com.imparagiocando.imparagiocando.game;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MathGameController {
    private final MathGameService mathGameService;

    @GetMapping({"","/", "/game"})
    public String home(Model model) {
        MathProblemDTO problema = mathGameService.generateProblem();
        model.addAttribute("problem", problema);
        model.addAttribute("soluzione", mathGameService.generaSoluzioni(problema));
        model.addAttribute("punteggio", mathGameService.getStatistiche());
        return "mathgame";
    }

    @PostMapping("/answer")
    public String checkAnswer(@RequestParam("answer") int answer,
                              Model model) {
        String result = mathGameService.checkRisposta(answer);
        mathGameService.setPunteggio(answer);
        model.addAttribute("message", result);
        model.addAttribute("messageClass", "error");
        if(result.contains("Ottimo")) {
            model.addAttribute("messageClass", "correct");
        }
        return "result";
    }
}
