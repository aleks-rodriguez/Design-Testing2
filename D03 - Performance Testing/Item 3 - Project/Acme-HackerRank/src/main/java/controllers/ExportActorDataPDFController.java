
package controllers;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import domain.Actor;

public class ExportActorDataPDFController extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(final Map<String, Object> model, final Document document, final PdfWriter writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment; filename=\"data.pdf\"");

		Actor a;
		a = (Actor) model.get("actor");

		Table table;
		table = new Table(1);
		table.addCell(new Paragraph("Acme - Parade \n"));
		table.addCell("GPDR File \n");
		//		if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.ADMIN)) {
		//			Administrator admin;
		//			admin = (Administrator) a;
		//			this.commonThings(table, admin);
		//		} else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.BROTHERHOOD)) {
		//			Brotherhood b;
		//			b = (Brotherhood) a;
		//			this.commonThings(table, b);
		//		} else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.MEMBER)) {
		//			Member member;
		//			member = (Member) a;
		//			this.commonThings(table, member);
		//		} else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.CHAPTER)) {
		//			Chapter ch;
		//			ch = (Chapter) a;
		//			this.commonThings(table, ch);
		//		} else if (Utiles.findAuthority(LoginService.getPrincipal().getAuthorities(), Authority.SPONSOR)) {
		//			Sponsor sponsor;
		//			sponsor = (Sponsor) a;
		//			this.commonThings(table, sponsor);
		//		}

		document.add(table);
	}

	public void commonThings(final Table table, final Actor a) {

		//		try {
		//			table.addCell("Personal Data \n" + Utiles.takeField(a) + "\n");
		//			table.addCell("Boxes \n" + Utiles.takeFields(a.getBoxes()) + "\n");
		//
		//			String m;
		//			m = "Messages \n";
		//
		//			for (final Box b : a.getBoxes()) {
		//				m += "\n" + b.getName() + "\n";
		//				Collection<MessageEntity> mess;
		//				mess = b.getMessageEntity();
		//				m += "\n" + Utiles.takeFields(mess) + "\n";
		//			}
		//			table.addCell("Messages \n" + m + "\n");
		//			table.addCell("Account Values \n" + Utiles.takeFields(a.getAccount()) + "\n");
		//			table.addCell("Profiles \n" + Utiles.takeFields(a.getProfiles()) + "\n");
		//		} catch (final BadElementException e) {
		//			System.out.println("Error al procesar");
		//		}

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
				res = res + name + " = " + value + "\n";

			}
		}
		return res;
	}
}
