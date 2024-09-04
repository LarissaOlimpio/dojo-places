package br.com.alura.dojoplaces.controller;

import br.com.alura.dojoplaces.domain.local.Local;
import br.com.alura.dojoplaces.domain.local.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.domain.local.dto.LocalResponseDTO;
import br.com.alura.dojoplaces.domain.local.dto.LocalUpdateDTO;
import br.com.alura.dojoplaces.domain.local.repository.LocalRepository;
import br.com.alura.dojoplaces.exception.NotFoundException;
import br.com.alura.dojoplaces.validator.LocalCreateValidator;
import br.com.alura.dojoplaces.validator.LocalUpdateValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LocalController {

    private final LocalRepository localRepository;
    private final LocalCreateValidator localCreateValidator;
    private final LocalUpdateValidator localUpdateValidator;

    @Autowired
    public LocalController(LocalRepository localRepository, LocalCreateValidator localCreateValidator, LocalUpdateValidator localUpdateValidator) {
        this.localRepository = localRepository;
        this.localCreateValidator = localCreateValidator;
        this.localUpdateValidator = localUpdateValidator;
    }

    @InitBinder("localCreateDTO")
    protected void initBinderCreate(WebDataBinder binder) {
        binder.addValidators(localCreateValidator);
    }

    @InitBinder("localUpdateDTO")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(localUpdateValidator);
    }

    @GetMapping("/local/create")
    public String formRegisterLocation(LocalCreateDTO form) {
        return "/local/registerLocation";
    }

    @PostMapping("/local/submit")
    public String createLocal(@ModelAttribute("localCreateDTO") @Valid LocalCreateDTO localCreateDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("localCreateDTO", localCreateDTO);
            return formRegisterLocation(localCreateDTO);
        }

        Local local = localCreateDTO.createLocalEntity();
        localRepository.save(local);

        return "redirect:/local/list";
    }

    @GetMapping("/local/list")
    public String listLocal(Model model) {
        List<Local> locals = localRepository.findAll();
        List<LocalResponseDTO> localResponseDTOList = locals.stream()
                .map(LocalResponseDTO::new)
                .collect(Collectors.toList());
        model.addAttribute("localResponseDTOList", localResponseDTOList);
        return "/local/listLocations";
    }

    @GetMapping("/local/update/{id}")
    public String showLocalUpdateForm(@PathVariable("id") Long id,LocalUpdateDTO localUpdate,Model model) {
        Local local = localRepository.findById(id).orElseThrow(NotFoundException::new);
        if(!localUpdate.isDirty()){
            LocalUpdateDTO localUpdateDTO = new LocalUpdateDTO(local);
        }
        LocalUpdateDTO localUpdateDTO = LocalUpdateDTO.fromEntity(local);
        model.addAttribute("localUpdateDTO", localUpdateDTO);
        return "/local/updateFormLocal";
    }

    @PostMapping("/local/update")
    public String updateLocal(@ModelAttribute("localUpdateDTO") @Valid LocalUpdateDTO localUpdateDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            localUpdateDTO.markAsDirty();
            model.addAttribute("localUpdateDTO", localUpdateDTO);
            return "/local/updateFormLocal";
        }

        Local local = localRepository.findById(localUpdateDTO.getId()).orElseThrow(NotFoundException::new);
        local.updateLocal(localUpdateDTO);
        localRepository.save(local);

        return "redirect:/local/list";
    }

    @PostMapping("/local/delete/{id}")
    public String deleteLocal(@PathVariable("id") Long id) {
        if (!localRepository.existsById(id)) {
            throw new NotFoundException();
        }
        localRepository.deleteById(id);
        return "redirect:/local/list";
    }

}
