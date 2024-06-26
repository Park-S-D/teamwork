//package capweb.capprac.controller;
////0610 남은 구현할 일: html에서 목록에서 특정 일정을 선택하면 해당 일정의 인덱스를 알아내서 컨트롤러로 넘겨주기
//
//import CapstoneDesign.Backendserver.repository.UserRepository;
//import capweb.capprac.dto.PlanCreateFormData;
//import capweb.capprac.entity.Company;
//import capweb.capprac.entity.USer;
//import capweb.capprac.repository.CompanyRepository;
//import capweb.capprac.repository.PlanRepository;
//import capweb.capprac.repository.USerRepository;
//import capweb.capprac.util.CreatePlan;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import capweb.capprac.entity.Plan;
//import capweb.capprac.service.PlanService;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//@RequestMapping("/plans")
//@RequiredArgsConstructor
//public class PlanController {
//
//
//    private final CreatePlan createPlan;
//
//
//    private final PlanService planService;
//
//    private final PlanRepository planRepository;
//
//    private final USerRepository userRepository;
//
//    private final CompanyRepository companyRepository;
//
//    @GetMapping
//    public String planPage(Model model) {
//        return "board/PlanHtml";
//    }
//    @PostMapping("/selectPlan")
//    public String selectPlan(@RequestParam("selectedPlan") int planIndex, Model model) {
//        Plan selectedPlan = planRepository.findPlanByIndex(planIndex);
//        if (selectedPlan != null) {
//            model.addAttribute("selectedPlan", selectedPlan);
//            return "board/PlanHtml"; // 수정 및 삭제 폼이 포함된 뷰로 리턴합니다.
//        } else {
//            model.addAttribute("message", "선택한 일정을 찾을 수 없습니다.");
//            return "board/PlanHtml"; // 오류 메시지를 포함하여 동일한 뷰로 리턴합니다.
//        }
//    }
//
//
//    @PostMapping("/create")
//    public String createPlan(@ModelAttribute PlanCreateFormData planCreateFormData, Model model) {
//        Plan plan = createPlan.createPlan(planCreateFormData);
//        planService.createPlan(plan);
//        model.addAttribute("plan", plan);
//        return "redirect:/plans";
//    }
//
//    @PostMapping("/update/{planIndex}")
//    public String updatePlan(@PathVariable int planIndex, @ModelAttribute PlanCreateFormData planCreateFormData, Model model) {
//        Plan existingPlan = planService.getPlanByIndex(planIndex);
//        existingPlan.setPlanName(planCreateFormData.getPlanName());
//        planService.updatePlan(existingPlan);
//        model.addAttribute("plan", existingPlan);
//        return "redirect:/plans";
//    }
//
//    @GetMapping("/delete/{planIndex}")
//    public String deletePlan(@PathVariable int planIndex, Model model) {
//        planService.deletePlan(planIndex);
//        return "redirect:/plans";
//    }
//
//    @GetMapping("/user")
//    public String getPlansByUserId(@RequestParam String userId, Model model) {
//        List<USer> users = userRepository.findUserById(userId);
//        if (!users.isEmpty()) {
//            List<Plan> plans = planRepository.findPlansByUser(users.get(0));
//            model.addAttribute("plans", plans);
//        }
//        return "board/PlanHtml";
//    }
//
//    @GetMapping("/company")
//    public String getPlansByCompanyId(@RequestParam String companyId, Model model) {
//        List<Company>companies=companyRepository.findCompanyById(companyId);
//        if (!companies.isEmpty()) {
//            List<Plan> plans = planRepository.findPlansByCompany(companies.get(0));
//            model.addAttribute("plans", plans);
//        } else {
//            model.addAttribute("plans", Collections.emptyList());
//        }
//        return "board/PlanHtml";
//    }
//
//    // Other methods remain unchanged...
//}
