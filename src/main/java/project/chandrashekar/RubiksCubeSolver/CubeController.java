package project.chandrashekar.RubiksCubeSolver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CubeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cubeState", "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB");
        return "cube";
    }

   @PostMapping(value = "/scramble", produces = "application/json")
@ResponseBody
public Map<String, String> scramble() {
    Map<String, String> response = new HashMap<>();
    response.put("cubeState", Tools.randomCube());
    return response;
}


    
    @PostMapping("/solve")
    public String solve(
            @RequestParam String cubeState,
            @RequestParam(required = false) String patternSteps,
            Model model) {

        String solution = Search.solution(cubeState, 25, 10000, false);

        model.addAttribute("cubeState", cubeState);
        model.addAttribute("solution", solution);

        if (patternSteps != null && !patternSteps.isEmpty()) {
            model.addAttribute("patternSteps", patternSteps);
        }

        return "cube";
    }

    
    @PostMapping(value = "/solve", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map<String, String> solveAjax(@RequestBody Map<String, String> request) {
        String cubeState = request.get("cubeState");
        String patternSteps = request.get("patternSteps");

        String solution;
        try {
            solution = Search.solution(cubeState, 25, 10000, false);
        } catch (Exception e) {
            solution = "Error: " + e.getMessage();
        }

        Map<String, String> response = new HashMap<>();
        if (solution == null || solution.startsWith("Error")) {
            response.put("error", solution != null ? solution : "Failed to solve cube.");
        } else {
            response.put("solution", solution);
        }

        if (patternSteps != null && !patternSteps.isEmpty()) {
            response.put("patternSteps", patternSteps);
        }

        return response;
    }
}
