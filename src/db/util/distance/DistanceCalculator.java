package db.util.distance;

import db.entity.Location;

public interface DistanceCalculator {
	
	public double getDistance(double lat_a, double lng_a, double lat_b, double lng_b);
	
	public double[] getlatlng(String address) throws Exception;
	
	public double getDistance(String address1,String address2)throws Exception;
	
	public double getDistance(String address1,String city1,String address2,String city2)throws Exception;
	
	public Location getOrNewLocation(String userLocation);

}
