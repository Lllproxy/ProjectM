package com.tc.common;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class Global {
	
	 private static double EARTH_RADIUS = 6378.137;//地球半径
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}
	
	public static double calDist(double[] dimA,double[] dimB){
        
		   // ����
		   double b = rad(dimA[0]) - rad(dimB[0]);
		   
		   // 
		   double radLat1 = rad(dimA[1]);
		   double radLat2 = rad(dimB[1]);
		   double a = radLat1 - radLat2;

		   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
		    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		   s = s * EARTH_RADIUS;
		   s = Math.round(s * 10000) / 10;
		   return s;
	}
	public static double[] getCenterPoint(List<double[]> points){
		
		if(points.size()==1){
			return points.get(0);
		}
		
		 GeometryFactory fact = new GeometryFactory();
		 Geometry read = fact.createPoint(new Coordinate(points.get(0)[0],points.get(0)[1]));
		 Geometry readB = fact.createPoint(new Coordinate(points.get(1)[0],points.get(1)[1]));
		 Geometry union = read.union(readB);
		 for (int i = 2; i < points.size(); i++) {
			 double[] ds = points.get(i);
			 readB = fact.createPoint(new Coordinate(ds[0],ds[1]));
			 union = union.union(readB);
		 }
		 
		Point centroid = union.getCentroid();
		double[] a = {centroid.getX(),centroid.getY()};
		return a;

	}
	
	/**
	 * 非汉字长度
	 * @param str
	 * @return
	 */
	public static int getLength(String str){
	    str =      str.replaceAll("[^x00-xff]*" , "");
	    System.out.println(str);
	    return str.length();
	  }

	
	public static void main(String[] args) {
		System.out.println(calDist(new double[]{114.253631,30.633866},new double[]{114.249275,30.580874}));
//		
//		List<double[]> points = new ArrayList<double[]>();
//		points.add(new double[]{101.69547300,32.91083000});
//		points.add(new double[]{102.69547300,32.91083000});
//		System.out.println(StringUtils.join(new double[]{101.69547300,32.91083000}, ";"));
		
//		System.out.println();
		
		System.out.println(getLength("****1*"));
		
//		List<double[]> points = new ArrayList<double[]>();
//		String[] a = "116.345208,39.853326;116.344559,39.851341;116.345369,39.851358;116.345187,39.853194;116.34435,39.851069;116.343256,39.852733;116.341488,39.852148;116.344135,39.85097;116.345337,39.851481;116.34508,39.851695;116.343804,39.85226;116.344816,39.853199;116.343899,39.853153;116.345133,39.853025;116.344722,39.851427;116.34627,39.849331;116.345476,39.853116;116.341303,39.852255".split(";"); 
//		for (int i = 0; i < a.length; i++) {
//			points.add(new double[]{Double.parseDouble(a[i].split(",")[0]),Double.parseDouble(a[i].split(",")[1])});
//		}
//		
//		double[] point = Global.getCenterPoint(points);
//		System.out.println(point[0] + ";" + point[1]);
		
	}
}
