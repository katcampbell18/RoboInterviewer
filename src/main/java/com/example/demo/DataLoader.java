//<<<<<<< HEAD
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    QandARepository qandARepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ArrayList<String> kWords(String str){
        ArrayList<String> arrList = new ArrayList();
        String[] kwords = str.split(",");
        for (String s: kwords){
            arrList.add(s.trim());
        }
        return arrList;
    }

    @Override
    public void run(String...strings) throws Exception {
        // test to see if data already exists
//        if (roleRepository.findAll() == null) {
        try {
            if (roleRepository.count() > 0) {
                System.out.println("DataLoader: Accounts exists. Skip DataLoader");
                return;
            } else {
                System.out.println("DataLoader: ADMIN account does not exist.");
            }
        } catch (Exception e) {
            System.out.println("DataLoader: An error occurred trying to find the ADMIN account.");
            System.out.println("DataLoader: " + e.toString());
        }

        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("SUPERVISOR"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");
        Role supervisorRole = roleRepository.findByRole("SUPERVISOR");
        //roleRepository.findAll();

        User user = new User("jim@jim.com", "password", "Jim",
                "Jimmerson", true, "jim");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        // Tests
        user = userRepository.findByUsername(user.getUsername());
        System.out.println("DataLoader: User Name: " + user.getUsername() + " Password: " + user.getPassword());
        System.out.println("DataLoader: User email: " + user.getEmail());
        user = userRepository.findByUsername(user.getUsername());

        user = new User("admin@admin.com", "password", "Admin",
                "User", true, "admin");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);
        ArrayList<String> keyWords = new ArrayList<>();

        keyWords = kWords("CSS, Design, Engineering, Full Stack, HTML, JavaScript, Ruby On Rails, Web Design, " +
                "Web Development, Software Development, Team Management");

        Job job = new Job();
        job.setKeywords(keyWords);
        job.setCurStatus(StaticData.Status.NOT_SUBMITTED);
        job.setDescription("This is a detailed description");
        job.setTitle("Senior Java Developer");
        job.setUser(user);
        job.setPhone("301-879-6524");
        job.setEmployerEmail("jj@test.com");
        job.setEmployerName("Amazon");
        Date tempDate = new Date();
        job.setPostedDate(tempDate);
//
        jobRepository.save(job);

        QsAndAs testQsAndAs = new QsAndAs();
        testQsAndAs.setJob(job);
        testQsAndAs.setQuestion("Question 1");
        testQsAndAs.setAnswer("Answer 1");
        qandARepository.save(testQsAndAs);

        QsAndAs testQaAndAs2 = new QsAndAs();
        testQaAndAs2.setJob(job);
        testQaAndAs2.setQuestion("Question 2");
        testQaAndAs2.setAnswer("Answer 2");
        qandARepository.save(testQaAndAs2);


        Set<QsAndAs> testQAList = new HashSet<>();
        testQAList.add(testQsAndAs);
        testQAList.add(testQaAndAs2);

        job.setQuestionsAndAnswers(testQAList);

        for(QsAndAs q : testQAList) {
            System.out.println(q.getQuestion() + " " + q.getAnswer());
        }

        System.out.println("DataLoader: postedDate: " + job.getPostedDate());

        // Add another job with the same user
        keyWords = kWords("Programming Dev, Mobile apps, iPhone, Android Development, " +
                "PhoneGap Software Development, Mobile Development, Ios App Development, Mobile Programming Languages");


        job = new Job();
//        job.setQuestionsAndAnswers(testQAList);
        job.setKeywords(keyWords);
        job.setCurStatus(StaticData.Status.NOT_SUBMITTED);
        job.setDescription("This is a detailed description");
        job.setTitle("Senior Solutions Architect");
        job.setUser(user);
        job.setPhone("301-345-6524");
        job.setEmployerEmail("bb@test.com");
        job.setEmployerName("Ascension Enterprizes");
        tempDate = new Date();
        job.setPostedDate(tempDate);
        jobRepository.save(job);

//        String tesStr = "  Way Good, Better than Good ";
//        ArrayList<String> testArray = kWords(tesStr);
//        System.out.println("DataLoader: Test kWords(0) - (1)" + testArray.get(0) + " - " + testArray.get(1));
    }
}
//=======
//package com.example.demo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    JobRepository jobRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    public ArrayList<String> kWords(String str){
//        ArrayList<String> arrList = new ArrayList();
//        String[] kwords = str.split(",");
//        for (String s: kwords){
//            arrList.add(s.trim());
//        }
//        return arrList;
//    }
//
//    @Override
//    public void run(String...strings) throws Exception {
//        // test to see if data already exists
////        if (roleRepository.findAll() == null) {
//        try {
//            if (roleRepository.count() > 0) {
//                System.out.println("DataLoader: Accounts exists. Skip DataLoader");
//                return;
//            } else {
//                System.out.println("DataLoader: ADMIN account does not exist.");
//            }
//        } catch (Exception e) {
//            System.out.println("DataLoader: An error occurred trying to find the ADMIN account.");
//            System.out.println("DataLoader: " + e.toString());
//        }
//
//        roleRepository.save(new Role("USER"));
//        roleRepository.save(new Role("ADMIN"));
//        roleRepository.save(new Role("SUPERVISOR"));
//
//        Role adminRole = roleRepository.findByRole("ADMIN");
//        Role userRole = roleRepository.findByRole("USER");
//        Role supervisorRole = roleRepository.findByRole("SUPERVISOR");
//        //roleRepository.findAll();
//
//        // Test Jobs
//        ArrayList<String> testKeywords = new ArrayList<String>(){{
//            add("key1");
//            add("key2");
//            add("key3");
//        }};
//
//        HashMap<Integer, ArrayList<String>> testQsAndAs = new HashMap<>();
//        testQsAndAs.put(1, new ArrayList<String>(){{
//            add("Question 1");
//            add("Answer 1");
//        }});
//
//
//        //End of Test Jobs
//
//        User user = new User("jim@jim.com", "password", "Jim",
//                "Jimmerson", true, "jim");
//
//        Job testJob = new Job("title1", "0123456789", "employer",
//                "employer@gmail.com", "description1",
//                testKeywords, testQsAndAs, new Date(), user);
//
//        testJob.setCurStatus(StaticData.Status.NOT_SUBMITTED);
//        Set<Job> testSetJobs = new HashSet<Job>();
//        testSetJobs.add(testJob);
//
//       // user.setResume(new StringBuffer("testString"));
//        user.setRoles(Arrays.asList(userRole));
//
//        user.setJobs(testSetJobs);
////        try {
//            jobRepository.save(testJob);
//            userRepository.save(user);
////        } catch (Exception e){
////            e.printStackTrace();
////            return;
////        }
//
//        // Tests
//        user = userRepository.findByUsername(user.getUsername());
//        System.out.println("DataLoader: User Name: " + user.getUsername() + " Password: " + user.getPassword());
//        System.out.println("DataLoader: User email: " + user.getEmail());
//        user = userRepository.findByUsername(user.getUsername());
//
//        user = new User("admin@admin.com", "password", "Admin",
//                "User", true, "admin");
//        user.setRoles(Arrays.asList(adminRole));
//
//        //save job to user
//        user.setJobs(testSetJobs);
//        testJob.setUser(user);
//
//        userRepository.save(user);
//        jobRepository.save(testJob);
//
//        String tesStr = "  Way Good ";
//        tesStr = tesStr.trim();
//        System.out.println(tesStr);
//    }
//}
////>>>>>>> 3a49c13a0cfd325cf09e88d42f2a0a46fa596503
