
package domain;

import javax.persistence.Embeddable;

@Embeddable
public class Coordinate {

	private double	latitude;
	private double	longitude;


	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

}
