package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
  @Autowired // This means to get the bean called userRepository
         // Which is auto-generated by Spring, we will use it to handle the data
  private UserRepository userRepository;

  @Autowired // This means to get the bean called drinkRepository
         // Which is auto-generated by Spring, we will use it to handle the data
  private DrinkRepository drinkRepository;

  @PostMapping(path="/add") // Map ONLY POST Requests
  public @ResponseBody String addNewUser (@RequestParam String name
      , @RequestParam String email) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }

  @GetMapping(path="/all_drink")
  public @ResponseBody Iterable<Drink> getAllDrinks() {
    // This returns a JSON or XML with the users
    return drinkRepository.findAll();
  }

  @PostMapping(path="/add_drink") // Map ONLY POST Requests
  public @ResponseBody String addNewDrink (@RequestParam Integer amount
      , @RequestParam String unit) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    Drink d = new Drink();
    d.setAmount(amount);
    d.setUnit(unit);
    int amount_in_ml = amount;
    if ( (unit.compareTo("oz") == 0) || (unit.compareTo("sp") == 0) ) {
      amount_in_ml = (int)(amount * 29.5735);
    }
    d.setAmountInMl(amount_in_ml);
    drinkRepository.save(d);
    return "Saved";
  }

}