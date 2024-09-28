package com.imparagiocando.impara_giocando.mathgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MathGameController {

    @Autowired
    private MathGameService mathGameService;

    @GetMapping({"/", "/game"})
    public String home(Model model) {
        MathProblemDTO problema = mathGameService.generateProblem();
        model.addAttribute("problem", problema);
        model.addAttribute("soluzione", mathGameService.generaSoluzioni(problema));
        return "mathgame";
    }

    @PostMapping("/answer")
    public String checkAnswer(@RequestParam("answer") int answer,
                              @RequestParam("num1") int num1,
                              @RequestParam("num2") int num2,
                              @RequestParam("operation") String operation,
                              Model model) {
        String result = mathGameService.checkRisposta(
                num1,
                num2,
                operation, answer);
        model.addAttribute("message", result);
        model.addAttribute("messageClass", "error");
        if(result.contains("Ottimo"))
            model.addAttribute("messageClass", "correct");
        return "result";
    }
}
