package tacos.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.entities.Ingredient;
import tacos.entities.Ingredient.Type;
import tacos.entities.Order;
import tacos.entities.Taco;
import tacos.entities.User;
import tacos.repositories.IngredientRepository;
import tacos.repositories.TacoRepository;
import tacos.repositories.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@RequiredArgsConstructor
@Slf4j
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepo;
    private final UserRepository userRepo;

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco design() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model, Principal principal) {
        log.info("   --- Designing taco");
        Map<String, List<Ingredient>> groupedByType = StreamSupport.stream(ingredientRepo.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(ingredient -> ingredient.getType().toString().toLowerCase()));
        model.addAllAttributes(groupedByType);
//    List<Ingredient> ingredients = new ArrayList<>();
//    ingredientRepo.findAll().forEach(ingredients::add);
//
//    Type[] types = Ingredient.Type.values();
//    for (Type type : types) {
//      model.addAttribute(type.toString().toLowerCase(),
//          filterByType(ingredients, type));
//    }

        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        model.addAttribute("user", user);

        return "design";
    }

    @PostMapping
    public String processDesign(
            @Valid Taco taco, Errors errors,
            @ModelAttribute Order order) {

        log.info("   --- Saving taco");

        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = tacoRepo.save(taco);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
