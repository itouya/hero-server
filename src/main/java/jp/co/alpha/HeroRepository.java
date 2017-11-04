package jp.co.alpha;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Integer>{
	public List<Hero> findByNameContainsOrderByIdAsc(String token);
}
