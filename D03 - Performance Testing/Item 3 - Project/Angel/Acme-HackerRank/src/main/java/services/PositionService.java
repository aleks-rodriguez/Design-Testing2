
package services;

import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PositionRepository;

@Service
@Transactional
public class PositionService extends AbstractService {

	@Autowired
	private PositionRepository	repository;


	public String generateTicker(String commercialName) {
		commercialName = super.limpiaCadena(commercialName).substring(0, 4);
		return commercialName + "-" + new Random().nextInt(10000);
	}
}
