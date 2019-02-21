
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CustomisationSystemRepository;

@Service
@Transactional
public class CustomisationSystemService {

	@Autowired
	private CustomisationSystemRepository	repositoryCustomisationSystem;
}
