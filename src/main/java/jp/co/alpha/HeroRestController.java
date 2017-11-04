package jp.co.alpha;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("api/hero")
public class HeroRestController {
	@Autowired
	HeroService service;
	
	// http://hero-server-host:8080/api/hero
	@RequestMapping(method=RequestMethod.GET)
	public List<Hero> getHeroes() {
		return (ArrayList<Hero>)service.findAll();
	}
	
	// http://hero-server-host:8080/api/hero/query?name={token}
	@RequestMapping(method=RequestMethod.GET, value="/query")
	public List<Hero> searchHeroes(@RequestParam String name) {
		return service.search(name);
	}
	
	// http://hero-server-host:8080/api/hero/{id}
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	public Hero getHero(@PathVariable Integer id) {
		return service.findOne(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Hero postHero(@RequestBody Hero hero) {
		return service.create(hero);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="{id}")
	public Hero putHero(@PathVariable Integer id, @RequestBody Hero hero) {
		System.out.println("putHero: " + hero.getAccount_image());
		if(hero.getAccount_image().equals("/images/default.png")) {
			String old_image = service.findOne(id).getAccount_image();
			if(!old_image.equals("/images/default.png")) {
				Path filePath = Paths.get(System.getProperty("user.home"));
				File f = new File(filePath.toString() + old_image);
				System.out.println("delete: " + f.getAbsolutePath());
				if(f.exists()) f.delete();
			}
		}
		hero.setId(id);
		return service.update(hero);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}
}