
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.DomainEntity;

@Controller
@RequestMapping()
public class MiscellaneousDataController extends BasicController {

	@Override
	public <T extends DomainEntity> ModelAndView saveAction(final T e, final BindingResult binding, final String nameResolver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends DomainEntity> ModelAndView deleteAction(final T e, final String nameResolver) {
		// TODO Auto-generated method stub
		return null;
	}

}
