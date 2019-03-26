
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import utilities.Utiles;
import domain.Member;
import domain.Parade;
import domain.Request;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private ParadeService		procService;

	@Autowired
	private Validator			validator;


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
		Collection<Request> rqs;
		rqs = this.requestRepository.getRequestByProcessionId(procId);
		Assert.notNull(rqs);
		return rqs;
	}

	public Collection<Request> findAllByProcessionAndMember(final int procId) {
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER));
		Collection<Request> rqs;
		final Member a = (Member) this.requestRepository.findByUserAccount(LoginService.getPrincipal().getId());
		rqs = this.requestRepository.getRequestByProcessionIdAndMemberId(a.getId(), procId);
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
	public Parade findByRequestId(final int id) {
		return this.requestRepository.getProcessionByRequestId(id);
	}

	public List<String> optimPosition() {
		return Utiles.optimPosition(this.requestRepository.getOptimPosition());
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
		Parade p;
		Member login = null;
		//		login = (Member) this.requestRepository.findByUserAccount(LoginService.getPrincipal().getId());
		if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER))
			login = this.requestRepository.findMemberByUserAccount(LoginService.getPrincipal().getId());

		saved = this.requestRepository.save(r);
		Assert.notNull(saved);
		if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER)) {

			p = this.procService.findOne(procId);
			reqs = p.getRequests();
			if (!reqs.contains(saved)) {
				reqs.add(saved);
				p.setRequests(reqs);
			}
			Collection<Request> reqsMember;
			reqsMember = login.getRequests();

			if (!reqsMember.contains(saved)) {
				reqsMember.add(saved);
				login.setRequests(reqsMember);
			}
		}
		return saved;
	}
	public void delete(final int idRequest) {
		Parade p;
		Collection<Request> reqs;
		Member member;
		member = (Member) this.requestRepository.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.isTrue(Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER));
		if (this.findOne(idRequest).getStatus().equals("PENDING")) {
			p = this.requestRepository.getProcessionByRequestId(idRequest);
			Request r;
			r = this.requestRepository.findOne(idRequest);
			reqs = p.getRequests();
			if (reqs.contains(r)) {
				reqs.remove(r);
				p.setRequests(reqs);
			}
			Collection<Request> reqsMember;
			reqsMember = member.getRequests();
			if (reqsMember.contains(r)) {
				reqsMember.remove(r);
				member.setRequests(reqsMember);
			}

		}
		this.requestRepository.delete(idRequest);
	}

	public Request reconstruc(final RequestForm requestF, final BindingResult binding) {
		Request r;
		if (requestF.getId() == 0) {
			r = this.create();
			r.setMarchColumn(requestF.getMarchColumn());
			r.setMarchRow(requestF.getMarchRow());
			r.setRecord(requestF.getRecord());
			if (requestF.getStatus().equals("0"))
				r.setStatus("PENDING");
			this.validator.validate(r, binding);
		} else {
			r = this.findOne(requestF.getId());
			if (requestF.getStatus().equals("APPROVED")) {
				r.setStatus(requestF.getStatus());
				r.setMarchColumn(requestF.getMarchColumn());
				r.setMarchRow(requestF.getMarchRow());
				this.validator.validate(r, binding);
			} else if (requestF.getStatus().equals("REJECTED")) {
				r.setStatus(requestF.getStatus());
				r.setRecord(requestF.getRecord());
				this.validator.validate(r, binding);
			}

		}

		if (binding.hasErrors())
			throw new ValidationException();

		return r;
	}
	public boolean checkRowAndColumn(final String row, final String column) {
		return StringUtils.isNumeric(row) && StringUtils.isNumeric(column);
	}
}
