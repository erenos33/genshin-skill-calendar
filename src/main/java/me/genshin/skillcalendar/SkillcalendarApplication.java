package me.genshin.skillcalendar;

import me.genshin.skillcalendar.domain.user.entity.User;
import me.genshin.skillcalendar.domain.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.UUID;

@SpringBootApplication
@EnableCaching
public class SkillcalendarApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public SkillcalendarApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SkillcalendarApplication.class, args);
	}

	@Override
	public void run(String... args) {
		User user = new User(UUID.randomUUID(), "연습용 유저");
		userRepository.save(user);
		//앱 실행 시 유저 1명 저장

	}

}
