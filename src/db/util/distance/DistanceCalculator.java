package db.util.distance;

public interface DistanceCalculator {
	
	public double getDistance(String address1,String address2)throws Exception;
	
	public double getDistance(String address1,String city1,String address2,String city2)throws Exception;

}
