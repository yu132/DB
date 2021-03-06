package db.util.distance;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import db.dao.LocationDao;
import db.entity.Location;

//用于测算直线距离

@Service("distanceCalculator")
public class BaiduMapDistanceCalculator implements DistanceCalculator{
	
	@Resource
	private LocationDao locationDao;
	
	@Resource
	private DistanceCalculator distanceCalculator;
	
	String url="http://api.map.baidu.com/geocoder/v2/?address=?&city=?&ak=?";
	
	private static final String AK="oeL9503vxXI5Z9Nkm2PG9PGG11oafAFV";

	public static void main(String[] args) throws Exception {
	/*	String address="广州市天河区粤垦路16号春晖苑";
		String city="广州";
		
		String address2="广州市铁一中学";
		
		System.out.println(new BaiduMapDistanceCalculator().getDistance(address, city, address2, city));*/
		
		
		/*SAXReader reader = new SAXReader();
		
		Document document1 = reader.read(new BaiduMapDistanceCalculator().urlGetter("山东大学软件学院2号宿舍楼"));
		
		System.out.println(document1.asXML());*/
		
		String s="http://api.map.baidu.com/geocoder/v2/?location=23.5404836867,111.348063489&output=xml&ak="+AK;
		
		SAXReader reader = new SAXReader();
		Document document1 = reader.read(s);
		
		System.out.println(document1.asXML());
	}
	
	public Location getOrNewLocation(String userLocation) {
		Location userLoc=locationDao.getLocation(userLocation);
		
		if(userLoc==null) {
			userLoc=new Location();
			try {
				double[] latlng=distanceCalculator.getlatlng(userLocation);
				userLoc.setLocName(userLocation);
				userLoc.setLat(latlng[0]);
				userLoc.setLng(latlng[1]);
				locationDao.saveOrUpdateCustomer(userLoc);
			} catch (Exception e) {
				return null;
			}
		}
		
		return userLoc;
	}
	
	private String urlGetter(String address,String city) {
		return "http://api.map.baidu.com/geocoder/v2/?address="+address+"&city="+city+"&ak="+AK;
	}
	
	private String urlGetter(String address) {
		return "http://api.map.baidu.com/geocoder/v2/?address="+address+"&ak="+AK;
	}
	
	public double getDistance(double lat_a, double lng_a, double lat_b, double lng_b){
        double pk = 180 / 3.14169;
        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        return 6371000 * tt;
    }

	@Override
	public double getDistance(String address1, String address2) throws Exception {
		
		SAXReader reader = new SAXReader();
		
		Document document1 = reader.read(urlGetter(address1));
		
		Node node1 = document1.selectSingleNode("//lng");
		Node node2 = document1.selectSingleNode("//lat");
		
		double lng_a=Double.valueOf(node1.getText());
		double lat_a=Double.valueOf(node2.getText());
		
		Document document2 = reader.read(urlGetter(address2));
		
		Node node3 = document2.selectSingleNode("//lng");
		Node node4 = document2.selectSingleNode("//lat");
		
		double lng_b=Double.valueOf(node3.getText());
		double lat_b=Double.valueOf(node4.getText());
		
		return getDistance(lat_a, lng_a, lat_b, lng_b);
	}

	@Override
	public double getDistance(String address1, String city1, String address2, String city2) throws Exception{
		SAXReader reader = new SAXReader();
		
		Document document1 = reader.read(urlGetter(address1,city1));
		
		Node node1 = document1.selectSingleNode("//lng");
		Node node2 = document1.selectSingleNode("//lat");
		
		double lng_a=Double.valueOf(node1.getText());
		double lat_a=Double.valueOf(node2.getText());
		
		Document document2 = reader.read(urlGetter(address2,city2));
		
		Node node3 = document2.selectSingleNode("//lng");
		Node node4 = document2.selectSingleNode("//lat");
		
		double lng_b=Double.valueOf(node3.getText());
		double lat_b=Double.valueOf(node4.getText());
		
		return getDistance(lat_a, lng_a, lat_b, lng_b);
	}

	@Override
	public double[] getlatlng(String address) throws Exception {
		SAXReader reader = new SAXReader();
		
		Document document1 = reader.read(urlGetter(address));
		
		Node node1 = document1.selectSingleNode("//lng");
		Node node2 = document1.selectSingleNode("//lat");
		
		double lng_a=Double.valueOf(node1.getText());
		double lat_a=Double.valueOf(node2.getText());
		return new double[] {lat_a,lng_a};
	}
	
}
