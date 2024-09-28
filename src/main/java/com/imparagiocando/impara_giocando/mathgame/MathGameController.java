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
    public String checkAnswer(@RequestParam("answer") String answer,
                              @RequestParam("num1") String num1,
                              @RequestParam("num2") String num2,
                              @RequestParam("operation") String operation,
                              Model model) {
        String result = mathGameService.checkRisposta(Integer.valueOf(num1), Integer.valueOf(num2), operation, Integer.valueOf(answer));
        model.addAttribute("message", result);
        model.addAttribute("messageClass", "error");
        if(result.contains("Bravo"))
            model.addAttribute("messageClass", "correct");
        return "result";
    }
}
