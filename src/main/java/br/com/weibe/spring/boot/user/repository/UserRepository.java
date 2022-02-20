package br.com.weibe.spring.boot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.weibe.spring.boot.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
