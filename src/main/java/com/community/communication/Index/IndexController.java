package com.community.communication.Index;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.community.communication.Approved.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.community.communication.Approved.ApproverRepository;
import com.community.communication.Approved.Block;
import com.community.communication.Approved.StreetInfo;
import com.community.communication.OrganisationInfo.OrganisationInfoRepository;

@Controller
public class IndexController {

    @Autowired
    private ApproverRepository ApproverRepository;
    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/Index";
    }

    @Autowired
    private OrganisationInfoRepository OrganisationInfoRepository;

    @GetMapping("/Index")
    public ModelAndView ApprovedList(@RequestParam(defaultValue = "root") String street, @RequestParam(defaultValue = "1") int Page, @RequestParam(defaultValue = "4") int Size){
        return Street(street, Page, Size);
    }

    @GetMapping("/street/{street}")
    public ModelAndView Street(@PathVariable String street, @RequestParam(defaultValue = "1") int Page, @RequestParam(defaultValue = "4") int Size){
        ModelAndView mav = new ModelAndView("StreetView.html");
        if (street.equals("root"))
            street = "";
        List<Block> Approved = ApproverRepository.getStreetBlocks(street);
        //mav.addObject("Approved", Approved);
        int Cnt = Approved.size();
        System.out.println(Cnt);
        List<Street> Streets = ApproverRepository.getSubStreets(street);
        int StrCnt = Streets.size();
        System.out.println(StrCnt);
        List<Integer> StreetAddr = Streets.stream().map(E -> {String Addr = E.getAddress(); return Integer.valueOf(Addr.substring(Addr.length()-2, Addr.length()));}).collect(Collectors.toList());
        Cnt = Cnt + StrCnt;
        System.out.println(Cnt);
        int Pages = Math.ceilDiv(Cnt, Size);
        System.out.println(Pages);
        List<Integer> PageNumbers = new ArrayList<>();
        if(Pages < 1){
            Pages = 1;
        }
        System.out.println(Pages);
        int First = (Page - 1) * Size;
        System.out.println(First);
        int Last = First + Size;
        System.out.println(Last);
        if(Last > Cnt){
            Last = Cnt;
        }
        System.out.println(Last);
        List<Block> Pagination = new ArrayList<>();
        List<Integer> PaginationLst = new ArrayList<>();
        int BlkIdx = 0;
        for(int i=0; i< First;i++){
            if(!StreetAddr.contains(i)){
                BlkIdx++;
            }
        }
        for(int i=First; i < Last; i++){
            if(!StreetAddr.contains(i)) {
                Pagination.add(Approved.get(BlkIdx++));
            }
            else{
                PaginationLst.add(i);
            }
        }
        System.out.println(BlkIdx);
        for(int i = 1;i<= Pages;i++){
            PageNumbers.add(i);
        }

        mav.addObject("PaginationLst", PaginationLst);
        mav.addObject("PageNumbers", PageNumbers);
        mav.addObject("Approved", Pagination);
        mav.addObject("CurrentPage", Page);
        mav.addObject("TotalPages", Pages);
        mav.addObject("Size", Size);
        mav.addObject("street", street.equals("") ? "root" : street);
        return mav;
    }

    @ResponseBody
    @GetMapping(path="/street-info/{street}", produces=MediaType.APPLICATION_JSON_VALUE)
    public StreetInfo getStreetInfo(@PathVariable String street) {
        StreetInfo info = new StreetInfo(
            street.length() <= 2 ? 
                ApproverRepository.getStreet("root") : 
                ApproverRepository.getStreet(street.substring(0, street.length()-2)), 
            ApproverRepository.getStreet(street),
            ApproverRepository.getSubStreets(street)
        );
        return info;
    }

    @ResponseBody
    @GetMapping("/block-name/{address}")
    public String getBlockName(@PathVariable String address) {
        return OrganisationInfoRepository.getInfo(address).getName();
    }
    

    @GetMapping("/search")
    public ModelAndView submitSearch(@RequestParam("search_value") String search_value) { 
        ModelAndView mav = new ModelAndView("StreetView.html");
        List<Block> Approved = ApproverRepository.getSearch(search_value); 
        mav.addObject("Approved", Approved);
        return mav; 
    }

    @GetMapping("/live-search")
    @ResponseBody
    public List<Block> searchApi(@RequestParam("value") String value) {
        return ApproverRepository.getSearch(value);
    }
}