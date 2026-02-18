package com.staffrating.controller;

import com.staffrating.model.RoleType;
import com.staffrating.model.StaffRating;
import com.staffrating.repository.StaffRatingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private StaffRatingRepository repository;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(RoleType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null);
                } else {
                    setValue(RoleType.valueOf(text));
                }
            }
        });
    }

    @GetMapping("/")
    public String index(Model model) {
        List<StaffRating> ratings = repository.findAll();
        model.addAttribute("ratings", ratings);
        return "index";
    }

    @GetMapping("/rating/{id}")
    public String detail(@PathVariable Long id, Model model) {
        StaffRating rating = repository.findById(id).orElse(null);
        model.addAttribute("rating", rating);
        return "detail";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("rating", new StaffRating());
        model.addAttribute("roleTypes", RoleType.values());
        return "create";
    }

    @PostMapping("/create")
    public String createSubmit(@Valid @ModelAttribute("rating") StaffRating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roleTypes", RoleType.values());
            return "create";
        }
        Optional<StaffRating> existing = repository.findByEmail(rating.getEmail());
        if (existing.isPresent()) {
            result.rejectValue("email", "duplicate", "A rating with this email already exists");
            model.addAttribute("roleTypes", RoleType.values());
            return "create";
        }
        repository.save(rating);
        return "redirect:/";
    }

    @GetMapping("/rating/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        StaffRating rating = repository.findById(id).orElse(null);
        if (rating == null) {
            return "redirect:/";
        }
        model.addAttribute("rating", rating);
        model.addAttribute("roleTypes", RoleType.values());
        return "edit";
    }

    @PostMapping("/rating/{id}/edit")
    public String editSubmit(@PathVariable Long id, @Valid @ModelAttribute("rating") StaffRating rating,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            rating.setId(id);
            model.addAttribute("roleTypes", RoleType.values());
            return "edit";
        }
        Optional<StaffRating> existing = repository.findByEmail(rating.getEmail());
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            result.rejectValue("email", "duplicate", "A rating with this email already exists");
            rating.setId(id);
            model.addAttribute("roleTypes", RoleType.values());
            return "edit";
        }
        rating.setId(id);
        repository.save(rating);
        return "redirect:/rating/" + id;
    }

    @PostMapping("/rating/{id}/delete")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/api/check-email")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email,
            @RequestParam(required = false) Long excludeId) {
        Optional<StaffRating> existing = repository.findByEmail(email);
        boolean taken = existing.isPresent() && (excludeId == null || !existing.get().getId().equals(excludeId));
        return ResponseEntity.ok(Map.of("taken", taken));
    }
}
