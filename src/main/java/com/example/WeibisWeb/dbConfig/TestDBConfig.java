package com.example.WeibisWeb.dbConfig;

import com.example.WeibisWeb.dao.CandidateRepository;
import com.example.WeibisWeb.dao.ClientRepository;
import com.example.WeibisWeb.dao.JobDescriptionRepository;
import com.example.WeibisWeb.dao.UserRepository;
import com.example.WeibisWeb.resources.Candidate;
import com.example.WeibisWeb.resources.Client;
import com.example.WeibisWeb.resources.JobDescription;
import com.example.WeibisWeb.resources.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableAutoConfiguration
@Profile(value = "test")
public class TestDBConfig {

    @Bean
    public CommandLineRunner initDb(CandidateRepository cRepository, JobDescriptionRepository jobRepository, ClientRepository clRepository, UserRepository uRepository) {
        return args -> {
            List<Candidate> candidates = new ArrayList<>(Arrays.asList(
                    new Candidate(UUID.randomUUID(), "Renos", "Bardis", "15/10/1987", "78 BD du President Wilson", "Antibes", "France"),
                    new Candidate(UUID.randomUUID(), "Johny", "Marlen", "15/11/1977", "75 Oxford Street", "London", "UK"),
                    new Candidate(UUID.randomUUID(), "Nick", "Papadopoulos", "11/10/1977", "96 Athens Street", "Athens", "Greece"),
                    new Candidate(UUID.randomUUID(), "George", "Papas", "01/12/1980", "58 Rue du Cannes", "Cannes", "France"),
                    new Candidate(UUID.randomUUID(), "Renos2", "Bardis2", "15/10/1987", "78 BD du President Wilson", "Antibes", "France"),
                    new Candidate(UUID.randomUUID(), "Johny2", "Marlen2", "15/11/1977", "75 Oxford Street", "London", "UK"),
                    new Candidate(UUID.randomUUID(), "Nick2", "Papadopoulos2", "11/10/1977", "96 Athens Street", "Athens", "Greece"),
                    new Candidate(UUID.randomUUID(), "George2", "Papas2", "01/12/1980", "58 Rue du Cannes", "Cannes", "France"),
                    new Candidate(UUID.randomUUID(), "Renos3", "Bardis3", "15/10/1987", "78 BD du President Wilson", "Antibes", "France"),
                    new Candidate(UUID.randomUUID(), "Johny3", "Marlen3", "15/11/1977", "75 Oxford Street", "London", "UK"),
                    new Candidate(UUID.randomUUID(), "Nick3", "Papadopoulos3", "11/10/1977", "96 Athens Street", "Athens", "Greece"),
                    new Candidate(UUID.randomUUID(), "George3", "Papas3", "01/12/1980", "58 Rue du Cannes", "Cannes", "France")));

            List<JobDescription> jobDescriptions = new ArrayList<>(Arrays.asList(
                    new JobDescription(UUID.randomUUID(), "Atos", "Java Developer", "Lille", "Java EE developer in Fintech", "Java", "SQL", "Spring", "Senior", "Open", 1),
                    new JobDescription(UUID.randomUUID(), "Infotell", "C++ Developer", "Nice", "C++ developer in Air department", "C++", "PostgreSql", "QT", "Junior", "Open", 2),
                    new JobDescription(UUID.randomUUID(), "Akka", "Ruby Developer", "Nice", "Ruby on Rails developer in Air France", "Ruby", "PostgreSql", "Rails", "Medium", "Close", 1),
                    new JobDescription(UUID.randomUUID(), "AirFrance", "Java Developer", "Lille", "Java EE developer in Fintech", "Java", "SQL", "Spring", "Senior", "Open", 1),
                    new JobDescription(UUID.randomUUID(), "Atos", "C++ Developer", "Paris", "C++ developer in Air department", "C++", "PostgreSql", "QT", "Junior", "Open", 2),
                    new JobDescription(UUID.randomUUID(), "Atos", "Ruby Developer", "Nice", "Ruby on Rails developer in Air France", "Ruby", "PostgreSql", "Rails", "Medium", "Close", 1),
                    new JobDescription(UUID.randomUUID(), "Atos", "Java Developer", "Madrid", "Java EE developer in Fintech", "Java", "SQL", "Spring", "Senior", "Open", 1),
                    new JobDescription(UUID.randomUUID(), "Infotell", "C++ Developer", "Milano", "C++ developer in Air department", "C++", "PostgreSql", "QT", "Junior", "Open", 2),
                    new JobDescription(UUID.randomUUID(), "Akka", "Ruby Developer", "London", "Ruby on Rails developer in Air France", "Ruby", "PostgreSql", "Rails", "Medium", "Close", 1),
                    new JobDescription(UUID.randomUUID(), "AirFrance", "Java Developer", "Madrid", "Java EE developer in Fintech", "Java", "SQL", "Spring", "Senior", "Open", 1),
                    new JobDescription(UUID.randomUUID(), "Atos", "C++ Developer", "London", "C++ developer in Air department", "C++", "PostgreSql", "QT", "Junior", "Open", 2),
                    new JobDescription(UUID.randomUUID(), "Atos", "Ruby Developer", "Berlin", "Ruby on Rails developer in Air France", "Ruby", "PostgreSql", "Rails", "Medium", "Close", 1)));

            List<Client> clients = new ArrayList<>(Arrays.asList(
                    new Client(UUID.randomUUID(), "Atos", "IT Consulting", "Paris", "France", 10000),
                    new Client(UUID.randomUUID(), "Infotel", "IT Consulting", "Lille", "France", 5000),
                    new Client(UUID.randomUUID(), "Alten", "IT Consulting", "Sophia Antipolis", "France", 17000),
                    new Client(UUID.randomUUID(), "Amadeus", "SAS", "Nice", "France", 15000),
                    new Client(UUID.randomUUID(), "Air France", "Airline", "Paris", "France", 3000),
                    new Client(UUID.randomUUID(), "Atos", "IT Consulting", "Lille", "France", 10000),
                    new Client(UUID.randomUUID(), "Atos", "IT Consulting", "Nice", "France", 10000),
                    new Client(UUID.randomUUID(), "Atos", "IT Consulting", "Lyon", "France", 10000),
                    new Client(UUID.randomUUID(), "Infotel", "IT Consulting", "Marseille", "France", 5000),
                    new Client(UUID.randomUUID(), "Alten", "IT Consulting", "Brest", "France", 10),
                    new Client(UUID.randomUUID(), "Amadeus", "SAS", "Toulouse", "France", 1500),
                    new Client(UUID.randomUUID(), "Air France", "Airline", "Grenoble", "France", 300),
                    new Client(UUID.randomUUID(), "Atos", "IT Consulting", "Monaco", "Monaco", 10),
                    new Client(UUID.randomUUID(), "Atos", "IT Consulting", "Nice", "France", 100)));

            List<User> users = new ArrayList<>(Arrays.asList(
                    new User(UUID.randomUUID(), "Renos", "Renos87", "Bardis", "renos@gmail.com", "ADMIN", new BCryptPasswordEncoder().encode("1234")),
                    new User(UUID.randomUUID(), "Omar", "Omar90", "Matter", "omar@gmail.com", "ADMIN", new BCryptPasswordEncoder().encode("1234"))));

            uRepository.saveAll(users);
            cRepository.saveAll(candidates);
            jobRepository.saveAll(jobDescriptions);
            clRepository.saveAll(clients);

//			JobDescription jobDescription = jobDescriptions.get(0);
//			jobDescription.setCandidates(candidates);
//			jobRepository.save(jobDescription);
//			clRepository.saveAll(clients);

//			Client client8 = new Client(1, "Atos", "IT Consulting", "Nice", "France", 10000);
//			client8.setJobDescriptions(jobDescriptions);
//			Client client9 = new Client(2, "Atos", "IT Consulting", "Paris", "France", 10000);
//			client9.setJobDescriptions(jobDescriptions);
//			Client client10 = new Client(3, "Atos", "IT Consulting", "Lille", "France", 10000);
//			client10.setJobDescriptions(jobDescriptions);
////			jobRepository.saveAll(jobDescriptions);
//			clRepository.save(client8);
//			clRepository.save(client9);
//			clRepository.save(client10);


//			JobDescription jobDescription = jobDescriptions.get(0);
//			jobDescription.setCandidates(candidates);
//			jobRepository.save(jobDescription);
//			clRepository.saveAll(clients);

//			Client client8 = new Client(1, "Atos", "IT Consulting", "Nice", "France", 10000);
//			client8.setJobDescriptions(jobDescriptions);
//			Client client9 = new Client(2, "Atos", "IT Consulting", "Paris", "France", 10000);
//			client9.setJobDescriptions(jobDescriptions);
//			Client client10 = new Client(3, "Atos", "IT Consulting", "Lille", "France", 10000);
//			client10.setJobDescriptions(jobDescriptions);
////			jobRepository.saveAll(jobDescriptions);
//			clRepository.save(client8);
//			clRepository.save(client9);
//			clRepository.save(client10);
//
//			 The first jobDescription with 2 candidates
//			Candidate c1 = cRepository.findById(1);
//			Candidate c2 = cRepository.findById(2);
//
//			Set<Candidate> candidateSet = new HashSet<>();
//			candidateSet.add(c1);
//			candidateSet.add(c2);
//			JobDescription jb1 = new JobDescription(1, "Weibis", "Java Developer", "Lille", "Java EE developer in Fintech", "Java", "SQL", "Spring");
//			jb1.setCandidates(candidateSet);
//
//			// The second jobDescription with 1 candidate
//			Candidate c3 = cRepository.findById(3);
//			Set<Candidate> candidateSet1 = new HashSet<>();
//			candidateSet1.add(c3);
//			JobDescription jb2 = new JobDescription(2, "Amadeus", "C++ Developer", "Nice", "C++ developer in Air department", "C++", "PostgreSql", "QT");
//			jb2.setCandidates(candidateSet1);
//
//			// The third jobDescription with 1 candidate
//			Candidate c4 = cRepository.findById(4);
//			Set<Candidate> candidateSet2 = new HashSet<>();
//			candidateSet2.add(c4);
//			JobDescription jb3 = new JobDescription(3, "AirFrance", "Ruby Developer", "Nice", "Ruby on Rails developer in Air France", "ruby", "PostgreSql", "Rails");
//			jb3.setCandidates(candidateSet2);
//
//			 Inserting all job descriptions
//			List<JobDescription> jobDescriptions = new ArrayList<>(Arrays.asList(jb1, jb2, jb3));
//			jobRepository.saveAll(jobDescriptions);

        };
    }
}
