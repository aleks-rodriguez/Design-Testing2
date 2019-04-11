
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.LoginService;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	repository;

	@Autowired
	private Validator			validator;


	public Collection<Position> findBySingleKey(final String singleKey) {
		List<Position> result;

		result = new ArrayList<Position>(this.repository.findBySingleKey(singleKey));

		Integer finderSize;
		finderSize = Integer.valueOf(System.getProperty("finderSize"));

		if (result.size() > finderSize)
			result = result.subList(0, finderSize);

		return result;
	}
	//Se deben implementar el resto de propiedades

	public Finder create() {
		Finder finder;
		finder = new Finder();
		finder.setSingleKey("");
		return finder;
	}

	public Finder reconstruct(final Finder finder, final BindingResult binding) {

		Finder result;

		try {
			Hacker h;
			h = (Hacker) this.repository.findActorByUserAccountId(LoginService.getPrincipal().getId());
			result = h.getFinder();
			//Aqui vendria montar los setters
		} catch (final IllegalArgumentException e) {
			//Si no esta loggeado se entiende que el finder es el externo y no el de un hacker.
			result = finder;
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}
}
