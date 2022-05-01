package com.tarley.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.tarley.workshopmongo.domain.Post;
import com.tarley.workshopmongo.domain.User;
import com.tarley.workshopmongo.dto.AuthorDTO;
import com.tarley.workshopmongo.dto.CommentDTO;
import com.tarley.workshopmongo.repository.PostRepository;
import com.tarley.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		User tarley = new User(null, "Tarley Rodrigues", "tarley@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob, tarley));
		
		Post post1 = new Post(null,sdf.parse("21/03/2018"), "Partiu, viagem!", "Vou viajar para o DF, Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null,sdf.parse("23/04/2018"), "Bom dia!", "Acordei com muita preguiça hoje.", new AuthorDTO(maria));
		Post post3 = new Post(null,sdf.parse("23/04/2018"), "Verdade seja dita!", "Cavalo dado, não se olha os dentes.", new AuthorDTO(tarley));

		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2018"), new AuthorDTO(tarley));
		CommentDTO c2 = new CommentDTO("Aproveite!", sdf.parse("22/03/2018"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2018"), new AuthorDTO(alex));
		CommentDTO c4 = new CommentDTO("Para com isso, cara.", sdf.parse("23/03/2018"), new AuthorDTO(maria));
		CommentDTO c5 = new CommentDTO("Ta chapando, Deolane?.", sdf.parse("25/03/2018"), new AuthorDTO(bob));

		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().add(c3);
		post3.getComments().addAll(Arrays.asList(c4, c5));
		
		postRepository.saveAll(Arrays.asList(post1, post2, post3));
		
		tarley.getPosts().add(post3);
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userRepository.save(maria);
		userRepository.save(tarley);
	}

}
