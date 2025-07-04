package com.yourcompany.booking.controller;

import com.yourcompany.booking.model.Train;
import com.yourcompany.booking.repository.TrainRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/train")
public class TrainController {

    @Autowired
    private TrainRepository trainRepository;

    @GetMapping("/add")
    public String showAddTrainForm(Model model) {
        model.addAttribute("train", new Train());
        return "admin-add-train";
    }

    @PostMapping("/add")
    public String addTrain(@Valid @ModelAttribute("train") Train train,
                           BindingResult result,
                           Model model) {

        if (result.hasErrors()) {
            return "admin-add-train";
        }

        train.setAvailableSeats(train.getTotalSeats());
        trainRepository.save(train);
        model.addAttribute("successMessage", "Train added successfully!");
        model.addAttribute("train", new Train()); // Reset form
        return "admin-add-train";
    }
}
