
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import utilities.Utiles;
import domain.Procession;
import domain.Request;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private ProcessionService	procService;
	@Autowired
	Validator					validator;


	/*
	 * public Collection<Request> findAll() {
	 * Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD) || Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER));
	 * Collection<Request> rqs;
	 * rqs = this.requestRepository.findAll();
	 * Assert.notNull(rqs);
	 * return rqs;
	 * }
	 */
	public Collection<Request> findAllByProcession(final int procId) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD));
		Collection<Request> rqs;
		rqs = this.requestRepository.getRequestByProcessionId(procId);
		Assert.notNull(rqs);
		return rqs;
	}

	public Collection<Request> findAllByProcessionAndMember(final int procId) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER));
		Collection<Request> rqs;
		rqs = this.requestRepository.getRequestByProcessionIdAndMemberId(procId, LoginService.getPrincipal().getId());
		Assert.notNull(rqs);
		return rqs;
	}

	public Request findOne(final int id) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD) || Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER));
		Request req;
		req = this.requestRepository.findOne(id);
		Assert.notNull(req);
		return req;
	}

	public Procession findByRequestId(final int id) {
		return this.requestRepository.getProcessionByRequestId(id);
	}
	public Request create() {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER));
		Request r;
		r = new Request();
		r.setMarchColumn(1);
		r.setMarchRow(1);
		r.setStatus("PENDING");
		r.setRecord("");
		return r;
	}

	public Request save(final Request r, final int procId) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD) || Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER));
		final Request saved;
		Collection<Request> reqs;
		Procession p;
		//		final Member login; me falta para setear su coleccion
		if (r.getStatus().equals("APPROVED"))
			Assert.isTrue(r.getMarchRow() != null && r.getMarchColumn() != null, "request.service.accepted.error");
		else if (r.getStatus().equals("REJECTED"))
			Assert.isTrue(r.getRecord() != null, "request.service.rejected.error");
		saved = this.requestRepository.save(r);
		Assert.notNull(saved);
		p = this.procService.findOne(procId);
		reqs = p.getRequests();
		if (!reqs.contains(saved)) {
			reqs.add(saved);
			p.setRequests(reqs);
		}
		return saved;
	}

	public void delete(final int id) {
		Procession p;
		Collection<Request> reqs;
		// Member login; me falta para comprobar el borrado -> borrar de su coleccion la request para que no de fallo SQL
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER));
		if (this.findOne(id).getStatus().equals("PENDING")) {
			p = this.requestRepository.getProcessionByRequestId(id);
			reqs = p.getRequests();
			if (reqs.contains(this.requestRepository.findOne(id))) {
				reqs.remove(this.requestRepository.findOne(id));
				p.setRequests(reqs);
			}
		}
		this.requestRepository.delete(id);
	}

	public Request reconstruc(final RequestForm requestF, final BindingResult binding) {
		Request r;
		if (requestF.getId() == 0)
			r = this.create();
		else {
			r = this.findOne(requestF.getId());
			r.setMarchColumn(requestF.getMarchColumn());
			r.setMarchRow(requestF.getMarchRow());
			r.setRecord(requestF.getRecord());
			r.setStatus(requestF.getStatus());//debuggear para ver si llega "------" o "0"

			this.validator.validate(r, binding);
		}
		return r;
	}
}
