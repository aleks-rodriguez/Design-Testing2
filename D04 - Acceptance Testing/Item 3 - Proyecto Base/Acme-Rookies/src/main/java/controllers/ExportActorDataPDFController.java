
package controllers;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import security.Authority;
import security.LoginService;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Audit;
import domain.Auditor;
import domain.Box;
import domain.Company;
import domain.CreditCard;
import domain.Curricula;
import domain.EducationData;
import domain.Item;
import domain.MessageEntity;
import domain.MiscellaneousData;
import domain.Position;
import domain.PositionData;
import domain.Problem;
import domain.Provider;
import domain.Rookie;
import domain.Sponsorship;

public class ExportActorDataPDFController extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(final Map<String, Object> model, final Document document, final PdfWriter writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment; filename=\"Personal-Information.pdf\"");

		Actor a;
		a = (Actor) model.get("actor");

		Table table;
		table = new Table(1);
		table.addCell(new Paragraph("Acme - Rookies\n"));

		String value;
		value = "";
		final CreditCard cr = (CreditCard) model.get("credit");
		value = " Cvv: " + cr.getCvv() + "\n Holder: " + cr.getHolder() + "\n Make: " + cr.getMake() + "\n Number: " + cr.getNumber() + "\n Expiration: " + cr.getExpiration() + "\n";
		table.addCell("Credit Card: " + value + "\n");

		if (this.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN)) {
			Administrator admin;
			admin = (Administrator) a;
			this.commonThings(table, admin);
		} else if (this.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.COMPANY)) {
			Company c;
			c = (Company) a;
			this.commonThings(table, c);

			final Collection<Position> pos = (Collection<Position>) model.get("positions");
			String m;
			m = "";
			for (final Position p : pos)
				m += "\n" + " Title: " + p.getTitle() + ",\n Description: " + p.getDescription() + ",\n Profile Required: " + p.getProfileRequired() + ",\n Skills Required: " + p.getSkillsRequired() + ",\n Technologies: " + p.getTechnologies()
					+ ",\n Salary: " + p.getSalary() + ",\n Deadline: " + p.getDeadline() + "\n";

			table.addCell("Positions: " + m + "\n");

			final Collection<Problem> pro = (Collection<Problem>) model.get("problems");
			String g;
			g = "";
			for (final Problem p : pro)
				g += "\n" + " Title: " + p.getTitle() + ",\n Attachments: " + p.getAttachments() + ",\n Hint: " + p.getHint() + ",\n Statement: " + p.getStatement() + ",\n FinalMode: " + p.getFinalMode() + "\n";

			table.addCell("Problems: " + g + "\n");
		} else if (this.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ROOKIE)) {
			Rookie h;
			h = (Rookie) a;
			this.commonThings(table, h);

			final Collection<Curricula> cur = (Collection<Curricula>) model.get("curricula");

			String m;
			m = "";
			for (final Curricula p : cur) {
				m += "\n" + " Full Name: " + p.getFullName() + ",\n Statament: " + p.getStatement() + ",\n Github Profile: " + p.getGithubProfile() + ",\n LinkedIn Profile: " + p.getLinkedInProfile() + ",\n Phone Number: " + p.getPhoneNumber() + "\n";

				for (final MiscellaneousData md : p.getMiscellaneousData())
					m += "\n Miscellaneous Data:" + "\n Text: " + md.getText() + ",\n Urls: " + md.getUrls() + "\n";
				for (final PositionData pd : p.getPositionsData())
					m += "\n Positions Data:" + "\n Title: " + pd.getTitle() + ",\n Description: " + pd.getDescription() + ",\n EndDate: " + pd.getEndDate() + ",\n StartDate: " + pd.getStartDate() + "\n";
				for (final EducationData ed : p.getEducationData())
					m += "\n Education Data:" + "\n Degree: " + ed.getDegree() + ",\n Institution: " + ed.getInstitution() + ",\n Mark: " + ed.getMark() + ",\n EndDate: " + ed.getEndDate() + ",\n StartDate: " + ed.getStartDate() + "\n";
			}
			table.addCell("Curriculas: " + m + "\n");

			final Collection<Application> app = (Collection<Application>) model.get("apps");

			String g;
			g = "";
			for (final Application p : app) {
				g += "\n" + "\n Status: " + p.getStatus() + ",\n Application Moment: " + p.getApplicationMoment() + "\n";
				if (p.getMoment() != null)
					g += " Moment: " + p.getMoment();
				if (p.getAnswer() != null)
					g += ",\n Answer Explanation: " + p.getAnswer().getExplanation();
				if (p.getAnswer() != null)
					g += ",\n Answer Link: " + p.getAnswer().getLink();
			}

			table.addCell("Applications: " + g + "\n\n");

		} else if (this.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.AUDITOR)) {
			Auditor aud;
			aud = (Auditor) a;
			this.commonThings(table, aud);

			final Collection<Audit> au = (Collection<Audit>) model.get("audits");

			String m;
			m = "";
			for (final Audit audit : au)
				m += "\n" + "\n Text: " + audit.getText() + ",\n Score: " + audit.getScore() + "\n Moment: " + audit.getMoment() + ",\n Position: " + audit.getPosition().getTitle() + "\n";

			table.addCell("Audits: " + m + "\n\n");

		} else if (this.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.PROVIDER)) {
			Provider pro;
			pro = (Provider) a;
			this.commonThings(table, pro);

			final Collection<Item> i = (Collection<Item>) model.get("items");

			String m;
			m = "";
			for (final Item it : i)
				m += "\n" + "\n Name: " + it.getName() + ",\n Description: " + it.getDescription() + "\n Pictures: " + it.getPictures() + ",\n Urls: " + it.getUrls() + "\n";

			table.addCell("Items: " + m + "\n\n");

			final Collection<Sponsorship> spo = (Collection<Sponsorship>) model.get("sponsorshipsAct");
			String j;
			j = "";
			for (final Sponsorship spon : spo)
				j += "\n" + "\n Banner URL: " + spon.getBanner() + ",\n Target URL: " + spon.getTarget() + "\n Flat Rate: " + spon.getFlat_rate() + ",\n Holder Credit Card Associated: " + spon.getCreditCard().getHolder()
					+ ",\n Number Credit Card Associated: " + spon.getCreditCard().getNumber() + "\n";

			table.addCell("Activate Sponsorships: " + j + "\n\n");

			final Collection<Sponsorship> spod = (Collection<Sponsorship>) model.get("sponsorshipsDes");
			String p;
			p = "";
			for (final Sponsorship spons : spod)
				p += "\n" + "\n Banner URL: " + spons.getBanner() + ",\n Target URL: " + spons.getTarget() + "\n Flat Rate: " + spons.getFlat_rate() + ",\n Holder Credit Card Associated: " + spons.getCreditCard().getHolder()
					+ ",\n Number Credit Card Associated: " + spons.getCreditCard().getNumber() + "\n";

			table.addCell("Desactivate Sponsorships: " + p + "\n\n");
		}

		document.add(table);
	}

	public void commonThings(final Table table, final Actor a) {

		try {
			table.addCell("Personal Data \n" + this.takeField(a) + "\n");
			table.addCell("Boxes \n" + this.takeFields(a.getBoxes()) + "\n");

			String m;
			m = "\n";
			for (final Box b : a.getBoxes()) {
				m += "\n" + b.getName() + "\n";
				Collection<MessageEntity> mess;
				mess = b.getMessageEntity();
				for (final MessageEntity message : mess)
					m += "\n" + " Sender: " + message.getSender().getName() + "\n Body: " + message.getBody() + "\n Priority: " + message.getPriority() + "\n Subject: " + message.getSubject() + "\n Tags: " + message.getTags() + "\n Moment Sent: "
						+ message.getMomentsent() + "\n";
			}
			table.addCell("Messages \n" + m + "\n");
			table.addCell("Account Values \n" + this.takeFields(a.getAccount()) + "\n");
			table.addCell("Profiles \n" + this.takeFields(a.getProfiles()) + "\n");
		} catch (final BadElementException e) {
			System.out.println("Error al procesar");
		}

	}
	private String takeFields(final Object o) {
		String res = "";

		if (o instanceof Collection)
			for (final Object e : (Collection<Object>) o)
				res += this.takeField(e);
		else
			res += this.takeField(o);

		return res;
	}

	private String takeField(final Object o) {
		String res = "";
		Field[] iterate;

		if (o instanceof Actor)
			iterate = o.getClass().getSuperclass().getDeclaredFields();
		else
			iterate = o.getClass().getDeclaredFields();

		for (final Field field : iterate) {
			field.setAccessible(true);
			String name;
			name = field.getName();

			if (name == "id" || name == "account" || name == "profiles" || name == "serialVersionUID" || name == "enabled" || name == "spammer" || name == "fromSystem" || name == "messageEntity")
				continue;
			else {

				Object value = null;

				try {
					value = field.get(o);
				} catch (final IllegalArgumentException e) {
					e.printStackTrace();
				} catch (final IllegalAccessException e) {
					e.printStackTrace();
				}
				if (name.equals("creditCard") || name.equals("boxes"))
					res = res;
				else
					res = res + name + " = " + value + "\n";

			}
		}
		return res;
	}

	private Boolean findAuthority(final Collection<Authority> comp, final String a) {
		Boolean res = false;
		if (comp.size() > 1) {
			Authority aut;
			aut = new Authority();
			aut.setAuthority(a);
			res = comp.contains(aut);
		} else
			for (final Authority authority : comp)
				if (authority.toString().equals(a))
					res = true;

		return res;
	}

}
