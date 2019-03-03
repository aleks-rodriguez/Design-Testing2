
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BoxService;
import services.MessageService;
import utilities.Utiles;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	MessageService	messageService;

	@Autowired
	BoxService		boxService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMessage(@RequestParam final int boxId) {

		final Box b;
		b = this.boxService.findOne(boxId);

		ModelAndView result;
		result = new ModelAndView("message/list");

		result.addObject("messages", b.getMessage());
		result.addObject("boxName", b.getName());

		result.addObject("requestURI", "message/list.do");
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createMessage(@RequestParam(defaultValue = "0") final int id) {
		ModelAndView result;
		if (id == 0) {
			result = this.createEditModelAndView(this.messageService.createMessage(this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId())));
			result.addObject("requestURI", "message/send.do");
			result.addObject("view", false);
		} else {
			result = this.createEditModelAndView(this.messageService.findOne(id));
			result.addObject("boxesOptional", this.boxService.getBoxesFromActorNoSystem(LoginService.getPrincipal().getId()));
			result.addObject("view", true);
			result.addObject("trash", this.boxService.getActorTrashBox(this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId()).getId()).getId());
			result.addObject("requestURI", "message/dbox.do");
		}

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "send")
	public ModelAndView sendMessage(@ModelAttribute("`message`") Message m, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(m);
			result.addObject("errors", binding.getAllErrors());
		} else
			try {
				m = this.messageService.reconstruct(m, binding);
				this.messageService.sendMessage(m);
				final Box box = this.boxService.getActorSendedBox(this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId()).getId());
				result = new ModelAndView("redirect:list.do?boxId=" + box.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(m, "message.commit.error");
				result.addObject("oops", oops.getMessage());
				result.addObject("errors", binding.getAllErrors());
			}
		return result;
	}
	//volver a in box
	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public ModelAndView moveToInBox(@RequestParam final int id) {
		ModelAndView result;
		Message mesage = null;
		try {
			mesage = this.messageService.findOne(id);
			final int box = this.messageService.moveTo("In Box", mesage);
			result = new ModelAndView("redirect:list.do?boxId=" + box);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(mesage, "message.commit.error");
			System.out.println("=============================OOPS\n" + oops.getMessage());
		}

		return result;
	}
	//ver más
	@RequestMapping(value = "/dbox", method = RequestMethod.GET)
	public ModelAndView moveToOtherBox(@RequestParam final int boxId, @RequestParam(value = "mess") final int mess) {
		ModelAndView result;
		Message mesage = null;
		try {
			mesage = this.messageService.findOne(mess);
			final int box = this.messageService.moveTo(String.valueOf(boxId), mesage);
			result = new ModelAndView("redirect:list.do?boxId=" + box);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(mesage, "message.commit.error");
			System.out.println("=============================OOPS\n" + oops.getMessage());
		}

		return result;
	}
	//mover a la papelera
	//	@RequestMapping(value = "/dbox", method = RequestMethod.GET)
	//	public ModelAndView moveToTrashBox(@RequestParam(defaultValue = "0") final int boxId, final Message mesage) {
	//		ModelAndView result;
	//		try {
	//			final int box = this.messageService.moveTo("Trash Box", mesage);
	//			result = new ModelAndView("redirect:list.do?boxId=" + box);
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(mesage, "message.commit.error");
	//			System.out.println("=============================OOPS\n" + oops.getMessage());
	//		}
	//
	//		return result;
	//	}
	//borrado definitivo
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteFromSystem(@RequestParam final int id) {
		ModelAndView result;
		Message mesage = null;
		try {
			final Box box = this.boxService.getActorTrashBox(this.boxService.getActorByUserAccount(LoginService.getPrincipal().getId()).getId());
			mesage = this.messageService.findOne(id);
			this.messageService.deleteMessage(mesage);
			result = new ModelAndView("redirect:list.do?boxId=" + box.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(mesage, "message.commit.error");
			result.addObject("oops", oops.getMessage());
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Message mess, final String message) {
		ModelAndView result;

		result = this.custom(new ModelAndView("message/edit"));
		result.addObject("form", mess);
		result.addObject("message", message);
		result.addObject("priorities", Utiles.priorities);
		result.addObject("actors", this.boxService.findAllActorsSystem(LoginService.getPrincipal().getId()));

		return result;
	}

}
