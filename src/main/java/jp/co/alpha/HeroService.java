package jp.co.alpha;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HeroService {
	@Autowired
	HeroRepository repository;
	
	public List<Hero> findAll() {
		return repository.findAll();
	}
	
	public Hero findOne(Integer id) {
		return repository.findOne(id);
	}
	
	public Hero create(Hero hero) {
		return repository.save(hero);
	}
	
	public Hero update(Hero hero) {
		return repository.save(hero);
	}
	
	public void delete(Integer id) {
		repository.delete(id);
	}
	
	public List<Hero> search(String token) {
		return repository.findByNameContainsOrderByIdAsc(token);
	}
}