
package sample;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mifmif.common.regex.Generex;

public class SampleTest2 {

	public static void main(final String[] args) {
		System.out.println(new Generex("\\d{6}").random().toUpperCase() + ":" + new SimpleDateFormat("yyyy:MM:dd").format(new Date()));
	}
}
