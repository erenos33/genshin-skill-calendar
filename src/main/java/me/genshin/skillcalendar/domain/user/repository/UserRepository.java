package me.genshin.skillcalendar.domain.user.repository;

import me.genshin.skillcalendar.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
