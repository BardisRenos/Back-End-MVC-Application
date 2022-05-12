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
@Profile(value = "dev")
public class DevDBconfig {

    @Bean
	public CommandLineRunner initDb(CandidateRepository cRepository, JobDescriptionRepository jobRepository, ClientRepository clRepository, UserRepository uRepository)  {
		return args ->{
			List<Candidate> candidates = new ArrayList<>(Arrays.asList(
					new Candidate(UUID.randomUUID(), "Renos", "Bardis", "15/10/1987", "78 BD du President Wilson", "Antibes", "France"),
					new Candidate(UUID.randomUUID(), "Johny", "Marlen", "15/11/1977", "75 Oxford Street", "London" ,"UK"),
					new Candidate(UUID.randomUUID(), "Nick", "Papadopoulos", "11/10/1977", "96 Athens Street", "Athens", "Greece"),
					new Candidate(UUID.randomUUID(), "George", "Papas", "01/12/1980", "58 Rue du Cannes", "Cannes", "France")));

			List<JobDescription> jobDescriptions = new ArrayList<>(Arrays.asList(
					new JobDescription(UUID.randomUUID(), "Atos", "Java Developer", "Lille", "Java EE developer in Fintech", "Java", "SQL", "Spring", "Senior", "Open", 1),
					new JobDescription(UUID.randomUUID(), "Infotell", "C++ Developer", "Nice", "C++ developer in Air department", "C++", "PostgreSql", "QT", "Junior", "Open", 2),
					new JobDescription(UUID.randomUUID(), "Akka", "Ruby Developer", "Nice", "Ruby on Rails developer in Air France", "Ruby", "PostgreSql", "Rails", "Medium", "Close", 1),
					new JobDescription(UUID.randomUUID(), "AirFrance", "Java Developer", "Lille", "Java EE developer in Fintech", "Java", "SQL", "Spring", "Senior", "Open", 1),
					new JobDescription(UUID.randomUUID(), "Atos", "C++ Developer", "Paris", "C++ developer in Air department", "C++", "PostgreSql", "QT", "Junior", "Open", 2),
					new JobDescription(UUID.randomUUID(), "Atos", "Ruby Developer", "Nice", "Ruby on Rails developer in Air France", "Ruby", "PostgreSql", "Rails", "Medium", "Close", 1)));

			List<Client> clients = new ArrayList<>(Arrays.asList(
					new Client(UUID.randomUUID(), "Atos", "IT Consulting", "Paris", "France", 10000),
					new Client(UUID.randomUUID(), "Infotel", "IT Consulting", "Lille", "France", 5000),
					new Client(UUID.randomUUID(), "Alten", "IT Consulting", "Sophia Antipolis", "France", 17000),
					new Client(UUID.randomUUID(), "Amadeus", "SAS", "Nice", "France", 15000),
					new Client(UUID.randomUUID(), "Air France", "Airline", "Paris", "France", 3000),
					new Client(UUID.randomUUID(), "Atos", "IT Consulting", "Lille", "France", 10000),
					new Client(UUID.randomUUID(), "Atos", "IT Consulting", "Nice", "France", 10000)));

			List<User> users = new ArrayList<>(Arrays.asList(
					new User(UUID.randomUUID(), "Renos", "Renos87", "Bardis", "renos@gmail.com", "ADMIN", new BCryptPasswordEncoder().encode("1234")),
					new User(UUID.randomUUID(), "Omar", "Omar90", "Matter", "omar@gmail.com", "ADMIN", new BCryptPasswordEncoder().encode("1234"))));

			uRepository.saveAll(users);
			cRepository.saveAll(candidates);
			jobRepository.saveAll(jobDescriptions);
			clRepository.saveAll(clients);

 		};
 	}
 }
