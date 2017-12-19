package org.launchcode.controllers;

import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    private String index(Model model) {
        model.addAttribute("menus", menuDao.findAll());
        return "index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    private String addForm(Model model) {
        model.addAttribute(new Menu());
        model.addAttribute("title", "Add Menu");
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(Model model, @ModelAttribute @Valid Menu menu, Errors errors) {

        if (errors.hasErrors()) {
            return "menu/add";
        }

        menuDao.save(menu);
        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id) {

        Menu menuDisplay = menuDao.findOne(id);
        model.addAttribute("menu", menuDisplay);

        return "view" + id;
    }

    @RequestMapping(value = "add-item", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int id) {
        Menu menuDisplay = menuDao.findOne(id);
        model.addAttribute("menu", menuDisplay);
        return "view" + id;
    }
}
