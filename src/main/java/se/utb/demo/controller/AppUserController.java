package se.utb.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import se.utb.demo.data.AppUserRepository;
import se.utb.demo.dto.CreateAppUserForm;
import se.utb.demo.dto.UpdateAppUserForm;
import se.utb.demo.entity.AppUser;
import se.utb.demo.service.AppUserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class AppUserController {
    private AppUserRepository appUserRepository;
    private AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserRepository appUserRepository, AppUserService appUserService) {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }
    //                         domännamnet    / resurs på domän
    // /users/register/form == localhost:8080/users/register/form
    @GetMapping("users/register/form")
    public String getForm(Model model){
        model.addAttribute("form", new CreateAppUserForm());
        return "user-form";
    }

    @PostMapping("users/register/process")
    public String formProcess(@Valid @ModelAttribute("form") CreateAppUserForm form,@RequestParam(required = false) boolean isAdmin, BindingResult bindingResult){

        if(appUserRepository.findByEmailIgnoreCase(form.getEmail()).isPresent()){
            FieldError error = new FieldError("form", "email", "Email is already in use");
            bindingResult.addError(error);
        }

        if(!form.getPassword().equals(form.getPasswordConfirm())){
            FieldError error = new FieldError("form", "passwordConfirm", "Your confirmation didn't match password");
            bindingResult.addError(error);
        }

        if(bindingResult.hasErrors()){
            return "user-form";
        }
        AppUser user = appUserService.registerAppUser(form.getFirstName(),form.getLastName(),form.getEmail(),form.getPassword(), LocalDate.now(), isAdmin);
        return "redirect:/users/"+user.getUserId();

        //appUserRepository.save(new AppUser(form.getFirstName(),form.getLastName(),form.getPassword(),form.getEmail(), LocalDate.now()));
       // return "index";

       // AppUser user = appUserRepository.save(new AppUser(form.getFirstName(),form.getLastName(),form.getPassword(),form.getEmail(), LocalDate.now()));
        //return "redirect:/users/"+user.getUserId();

    }
    //localhost:8080/users/1
    @GetMapping("users/{id}")
    public String getUserView(@PathVariable(name = "id") int id, Model model){
        AppUser appUser = appUserRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("user", appUser);
        return "user-view";
    }
    @GetMapping("users/{id}/update")
    public String getUpdateForm(@PathVariable("id") int id, Model model){
        UpdateAppUserForm appUserForm = new UpdateAppUserForm();
        AppUser appUser = appUserRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        appUserForm.setEmail(appUser.getEmail());
        appUserForm.setFirstName(appUser.getFirstName());
        appUserForm.setLastName(appUser.getLastName());
        appUserForm.setUserId(appUser.getUserId());
        model.addAttribute("form", appUserForm);

        return "update-form";
    }

    @PostMapping("users/{id}/update")
    public String processUpdate(
            @PathVariable("id") int id,
            @Valid @ModelAttribute("form") UpdateAppUserForm form,
            BindingResult result)
    {
        AppUser original = appUserRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Optional<AppUser> optional = appUserRepository.findByEmailIgnoreCase(form.getEmail());
        if(optional.isPresent() && !form.getEmail().equalsIgnoreCase(original.getEmail())){
            FieldError error = new FieldError("form", "email", "Email is already in use");
            result.addError(error);
        }

        if(result.hasErrors()){
            return "update-form";
        }

        original.setEmail(form.getEmail());
        original.setFirstName(form.getFirstName());
        original.setLastName(form.getLastName());

        appUserRepository.save(original);

        return "redirect:/users/"+original.getUserId();
    }

    @GetMapping("/login")
    public String getLoginForm(){
        return "login-form";
    }

}
