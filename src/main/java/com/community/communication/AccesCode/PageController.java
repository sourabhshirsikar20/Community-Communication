    package com.community.communication.AccesCode;

    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.ui.Model;


    import java.util.Optional;

    @Controller
    public class PageController {
        private final PageRepository repo;
        private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        public PageController(PageRepository repo) {
            this.repo = repo;
        }
        @GetMapping("/AccessPage")
        public String SubmitBlock(Model model){
            model.addAttribute("block",new Page());
            return "AccessPage";
        }
        @PostMapping("/AccessPage")
        public String submit(
                @RequestParam String pageName,
                @RequestParam String secretKey,
                @ModelAttribute("block") Page block,
                Model model
        ){
            Optional<Page> optional=repo.findByPageName(pageName);
            if(optional.isEmpty()){
                Page page=new Page();
                page.setPageName(pageName);
                page.setSecretHash(encoder.encode(secretKey));
                page.setName(block.getPageName());
                page.setMainText(block.getMainText());
                page.setLinksText(block.getLinksText());
                page.setInfoText(block.getInfoText());
                page.setPromoText(block.getPromoText());
                repo.save(page);
                return "redirect:/page/" + pageName;
            }
            Page page=optional.get();
            if(!encoder.matches(secretKey,page.getSecretHash())){
                model.addAttribute("errorMessage","Wrong access code!");
                return "AccessPage";
            }
            page.setName(block.getName());
            page.setMainText(block.getMainText());
            page.setLinksText(block.getLinksText());
            page.setInfoText(block.getInfoText());
            page.setPromoText(block.getPromoText());
            repo.save(page);
            return "redirect:/page/" + pageName;
        }
    @GetMapping("/page/{pageName}")
        public String view(@PathVariable("pageName") String pageName, Model model){
            Optional<Page> optional=repo.findByPageName(pageName);
            if(optional.isEmpty()) return "404";
            model.addAttribute("page", optional.get());
            return "ViewPage";
        }

    }