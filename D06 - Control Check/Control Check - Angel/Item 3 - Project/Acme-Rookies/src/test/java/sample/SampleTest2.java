
package sample;

import com.mifmif.common.regex.Generex;

public class SampleTest2 {

	public static void main(final String[] args) {
		System.out.println(new Generex("[0-9]{4}").random().toUpperCase());
	}
}
