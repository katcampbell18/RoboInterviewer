package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class JobController {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String jobList(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        if (userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }
//        long test = 0;
//        try {
//            if (userService.getUser().getId() > test) {
//                model.addAttribute("user_id", userService.getUser().getId());
//                System.out.println("AppController: jobList: userService.getUser(): " + userService.getUser().getId());
//            } else {
//                System.out.println("AppController:jobList:userService.getUser(): is null");
//                return "login"; // test 10-18-2019
//            }
//        } catch (Exception e){
//            System.out.println("AppController:jobList:userService.getUser().getId(): is null");
//            return "login"; // test 10-18-2019
//        }
//        Long id=0;
//        User user = userRepository.findById(id);
        return "listjobs";
    }

    @GetMapping("/add")
    public String addJob(Model model){
        model.addAttribute("job", new Job());

        if (userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        } else {
            System.out.println("AppController:jobList:userService.getUser(): is null");
        }
        return "jobform";
    }

    @PostMapping("/processsearch")
    public String searchResult(Model model, @RequestParam(name="search") String search) {
        model.addAttribute("jobs", jobRepository.findJobByTitleContainingIgnoreCase(search));
        return  "searchlist";
    }

    @PostMapping("/processjob")
    public String processJob(@ModelAttribute Job job, BindingResult result) {
        if(result.hasErrors()){
            return "jobform";
        }
        job.setUser(userService.getUser());
        job.setEmployerEmail("jj@test.com");
        job.setEmployerName("Amazon");
        Date tempDate = new Date();
        job.setPostedDate(tempDate);
        jobRepository.save(job);
        return "redirect:/";
    }


    //Processing interview form
//    @GetMapping("/interviewform")
//    public String showInterviewForm(Model model){
//        model.addAttribute("job", jobRepository.fin)
//    }

    @RequestMapping("/base")
    public String base(@PathVariable("id") long id, Model model){
        long userId = userService.getUser().getId();

        model.addAttribute("job", jobRepository.findByUserId(userId).getId());
        if(userService.getUser() != null)
            model.addAttribute("user_id", userService.getUser().getId());
        return "base";
    }

    @GetMapping("/addinterview")
    public String interviewForm(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "interviewform";
    }

    @RequestMapping("/addinterview/{id}")
    public String showInterviewForm(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "interviewform";
    }

    @PostMapping("/processinterview")
    public String processInterview(@ModelAttribute Job job,
                                   @RequestParam(name="interviewDate") String interviewDate){
        try{
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String formattedDate = interviewDate.substring(0);
            Date realDate = simpleDateFormat.parse(formattedDate);
//            job.setInterviewDate

        } catch(Exception e){
            e.printStackTrace();
        }

//        User user = job.getUser();
//        user.add
//        job.setUser(userService.getUser());
        jobRepository.save(job);
        return "redirect:/";
    }



    @GetMapping("/apply/{id}")
    public String applyJob(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "interviewform";
//        Job job = jobRepository.findById(id).get();
////        job.setCurStatus(StaticData.Status.SUBMITTED);
////        User user = userService.getUser();
////        user.getMatches();  // Evaluate all jobs w/Status == SUBMITTED
////        if (job.getCurStatus() == StaticData.Status.PENDING_INTERVIEW) {
////            System.out.println("applyJob: " + "Interview is pending.");
////            // send an email or popup to user to go to myPage to schedule an interview
////            // during an available window.  When they go to myPage they will see cards for each job they have
////            // applied to.  Each will have a button to apply for an interview
////            // if the interview has not been scheduled - add PENDING_SCHEDULED_INTERVIEW
////        }
////        else {
////            System.out.println("applyJob: " + "Candidate was rejected");
////        }
//////        return "show";
////        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "jobform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id) {
        jobRepository.deleteById(id);
        return "redirect:/";
    }

}
