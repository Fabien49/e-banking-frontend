package com.finastra.productapp.endpoint;

import com.finastra.productapp.api.FfdcApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class ProductController {

    @Autowired
    private FfdcApi ffdcApi;

    @RequestMapping("/results")
    public String resultsPage (Model model){
        model.addAttribute("entities" ,ffdcApi.getReferenceSourcesLegalEntities().getItems());
        return "results";
    }

    @RequestMapping("/")
    public String indexPage (Model model){
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(Model model) {
        return "logout";
    }

}