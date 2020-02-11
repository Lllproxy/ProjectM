package com.tc.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.tc.constant.DConstant;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class GeomsConvertUtil {
	private static GeometryFactory geometryFactory = new GeometryFactory();
//	private static int pointCxNum = 10;// 连续抽稀pointCxNum个点
//	private static int distance = 2;// 抽稀距离 distance 米
 
	 public static Geometry wktToGeometry(String wktPoint) {
	        WKTReader fromText = new WKTReader();
	        Geometry geom = null;
	        try {
	            geom = fromText.read(wktPoint);
	        } catch (com.vividsolutions.jts.io.ParseException e) {
	            throw new RuntimeException("Not a WKT string:" + wktPoint);
	        }
	        return geom;
	    }

	 
	/**
	 * 构建GEOMtry
	 * @param areaType 01矩形 02多边形 03圆形
	 * @param coords 01时：左上;右下坐标 02时：x,y;x1,y1;...;03时：圆心坐标[;边界坐标]|[半径]
	 * @return
	 */
	public static Geometry getGeomtry(String areaType,String coords){
		Geometry geom = null;
//		String sql = "select e from "+poName+" e where within(e.POINT, GeomFromText(:filter)) = true";
		if (StringUtils.equals(areaType, "03")){
			String[] sps = coords.split("\\|");
//			double dis = 0;
			if (sps.length==1){
//				String[] psps = sps[0].split(";"); 
//				String cp = psps[0];
//				String ep = psps[1];
//				double cpx = Double.parseDouble(cp.split(",")[0]);
//				double cpy = Double.parseDouble(cp.split(",")[1]);
//				geom = GeomsConvertUtil.createPoint(cp);
				
//				double epx = Double.parseDouble(ep.split(",")[0]);
//				double epy = Double.parseDouble(ep.split(",")[1]);
//				dis = Global.calDist(new double[]{cpx,cpy}, new double[]{epx,epy});
//			    geom = GeomsConvertUtil.createCircle(cpx, cpy, dis);
			
			}else{  
//				dis = Double.parseDouble(sps[1]); 
				geom = GeomsConvertUtil.createPoint(sps[0]);
			}
			//(st_distance 计算的结果单位是度，需要乘111195（地球半径6371000*PI/180）是将值转化为米);
//			sql  ="select e from VehiclePo e where st_distance(e.POINT, GeomFromText(:filter)) <= "+dis/111195;

		}else if (StringUtils.equals(areaType, "01")){
			String[] points = coords.split(";");
			String[] leftUp = points[0].split(",");
			String[] rightDown = points[1].split(",");
			String xys = points[0]+";"+rightDown[0]+","+leftUp[1]+";";
			xys += points[1]+";"+leftUp[0]+","+rightDown[1];
			geom = GeomsConvertUtil.genGeometry(xys);
			System.out.println("rectangle coords:"+GeomsConvertUtil.getPolygonString(geom));
		}else if (StringUtils.equals(areaType, "02")){
			geom = GeomsConvertUtil.genGeometry(coords);
			System.out.println("multi poly coords:"+GeomsConvertUtil.getPolygonString(geom));
		} 
		
		return geom;
	}
	
	public static String getMultiLinearString(Geometry geomBuf) {
		String multis = "";
		int num = geomBuf.getNumGeometries();
		for (int i = 0; i < num; i++) {
			Geometry polygon = geomBuf.getGeometryN(i);
			String ps = getLinearRingString(polygon);
			if (i != num - 1)
				multis += ps + "|";
			else
				multis += ps;
		}
		return multis;
	}
	/**
	 * 点空间坐标转换为字符串
	 * @param geomBuf
	 * @return
	 */
	public static String getPointGeomString(Point geomBuf) {
		String xys = "";
		Coordinate coord = geomBuf.getCoordinate();
		xys = coord.x+","+coord.y;
		return xys;
	}
	
 
	/**
	 * 格式化boundary点为x,y;x1,y1;...
	 * 
	 * @param geomBuf
	 * @return
	 */
	public static String getLinearRingString(Geometry geomBuf) {
		String geomstring = geomBuf.toString();

		geomstring = geomstring.substring(12, geomstring.length() - 1);
		geomstring = geomstring.replaceAll(", ", ";");
		geomstring = geomstring.replaceAll(" ", ",");
		return geomstring;
	}
	 

	/**
	 * 格式化boundary点为x,y;x1,y1;...
	 * 
	 * @param geomBuf
	 * @return
	 */
	public static String getPolygonString(Geometry geomBuf) {
		String geomstring = geomBuf.toString();

		geomstring = geomstring.substring(10, geomstring.length() - 2);
		geomstring = geomstring.replaceAll(", ", ";");
		geomstring = geomstring.replaceAll(" ", ",");
		return geomstring;
	}

	/**
	 * create a polygon(多边形) by WKT
	 * 20 10, 30 0, 40 10, 30 20, 20 10
	 * @return
	 * @throws ParseException
	 */
	public static Polygon createPolygonByWkt(String wkt) throws ParseException {
		WKTReader reader = new WKTReader(geometryFactory);
		Polygon polygon = (Polygon) reader.read("POLYGON(("+wkt+"))");
		return polygon;
	}

	/**
	 * create a Circle 创建一个圆，圆心(x,y) 半径RADIUS
	 * 
	 * @param x
	 * @param y
	 * @param RADIUS
	 * @return
	 */
	public static Polygon createCircle(double x, double y, final double RADIUS) {
		final int SIDES = 32;// 圆上面的点个数
		Coordinate coords[] = new Coordinate[SIDES + 1];
		for (int i = 0; i < SIDES; i++) {
			double angle = ((double) i / (double) SIDES) * Math.PI * 2.0;
			double dx = Math.cos(angle) * RADIUS;
			double dy = Math.sin(angle) * RADIUS;
			coords[i] = new Coordinate((double) x + dx, (double) y + dy);
		}
		coords[SIDES] = coords[0];
		LinearRing ring = geometryFactory.createLinearRing(coords);
		Polygon polygon = geometryFactory.createPolygon(ring, null);
		return polygon;
	}

	// 毫秒经纬度转换为浮点经纬度，格式为x,y;x,y
	public static String millsToDu(String coords) {
		String cs = "";
		String cdsplit[] = coords.split(" ");
		int i = 0;
		for (String point : cdsplit) {
			String[] s = point.split(";");
			if (i == cdsplit.length - 1)
				cs += Long.parseLong(s[0]) / 3600000d + ","
						+ Long.parseLong(s[1]) / 3600000d;
			else
				cs += Long.parseLong(s[0]) / 3600000d + ","
						+ Long.parseLong(s[1]) / 3600000d + ";";
			i++;
		}

		return cs;
	}

	/**
	 * 获取线路缓冲
	 * 
	 * @param lineGeom
	 *            线路geom
	 * @param width
	 *            宽度 单位米
	 * @return
	 */
	public static Geometry getGeometryBuf(Geometry lineGeom, String width) {
		return lineGeom.buffer(getWidth(width));
	}

	public static Geometry getGeometryBuf(Geometry lineGeom, String width,
			int quadrantSegments, int endCapStyle) {
		return lineGeom.buffer(getWidth(width), quadrantSegments, endCapStyle);
	}

	/**
	 * JTS采用平面直角坐标系 大地坐标转换为地理坐标？
	 * 
	 * @param width
	 * @return
	 */
	public static double getWidth(String width) {
		// 得出宽度占用多少经度？
		double dwidth = Double.parseDouble(width) / DConstant.rate;
		return dwidth;
	}

	/**
	 * 获取路段geometry
	 * 
	 * @param points
	 *            格式：x,y;xy
	 * @return
	 */
	public static LineString getLineString(String points) {
		String[] linepoints = points.split(";");
		Coordinate[] coordinates1 = new Coordinate[linepoints.length];
		int i = 0;
		for (String lp : linepoints) {
			String[] sp = lp.split(",");
			Coordinate cd = new Coordinate(Double.parseDouble(sp[0]),
					Double.parseDouble(sp[1]));
			coordinates1[i] = cd;
			i++;
		}
		LineString lineString1 = geometryFactory.createLineString(coordinates1);
		return lineString1;
	}

	/**
	 * 联合多边形
	 * 
	 * @param geoms
	 * @return
	 */
	public static GeometryCollection createGeomCollection(Geometry[] geoms) {
		GeometryCollection polygonCollection = geometryFactory
				.createGeometryCollection(geoms);
		return polygonCollection;
	}
	
	public static MultiLineString createMultiLineString(LineString[] geoms) {
		return geometryFactory.createMultiLineString(geoms);
	}
// 	private static WKTReader reader = new WKTReader(geometryFactory);

	public static Point createPoint(String xy) {
		if (StringUtils.isNotBlank(xy)) {
			String[] xys = StringUtils.split(StringUtils.trim(xy), ",");
			if (xys.length == 2) {
				Coordinate coord = new Coordinate(Double.parseDouble(xys[0]),
						Double.parseDouble(xys[1]));
				Point point = geometryFactory.createPoint(coord);
				return point;
			}
		}
		return null;
	}

	// 地球的赤道半径是6378137米
	public final static double Rc = 6378137;
	// 地球的极半径是6356725米
	public final static double Rj = 6356725;

	/**
	 * 求B点经纬度
	 * 
	 * @param A
	 *            已知点的经纬度，
	 * @param distance
	 *            AB两地的距离 单位km
	 * @param angle
	 *            AB连线与正北方向的夹角（0~360）
	 * @return B点的经纬度
	 */
	public static double[] getLatLng(double longitude, double latitude,
			double distance, double angle) {
		double m_RadLo = longitude * Math.PI / 180.;
		double m_RadLa = latitude * Math.PI / 180.;
		double Ec = Rj + (Rc - Rj) * (90. - latitude) / 90.;
		double Ed = Ec * Math.cos(m_RadLa);

		double dx = distance * 1000 * Math.sin(Math.toRadians(angle));
		double dy = distance * 1000 * Math.cos(Math.toRadians(angle));

		double bjd = (dx / Ed + m_RadLo) * 180. / Math.PI;
		double bwd = (dy / Ec + m_RadLa) * 180. / Math.PI;

		return new double[] { bjd, bwd };
	}

	/**
	 * 获取AB连线与正北方向的角度
	 * 
	 * @param A
	 *            A点的经纬度
	 * @param B
	 *            B点的经纬度
	 * @return AB连线与正北方向的角度（0~360）
	 */
	public static double getAngle(double longitudeA, double latitudeA,
			double longitudeB, double latitudeB) {
		double m_RadLoA = longitudeA * Math.PI / 180.;
		double m_RadLaA = latitudeA * Math.PI / 180.;
		double EcA = Rj + (Rc - Rj) * (90. - latitudeA) / 90.;
		double EdA = EcA * Math.cos(m_RadLaA);

		double m_RadLoB = longitudeB * Math.PI / 180.;
		double m_RadLaB = latitudeB * Math.PI / 180.;

		double dx = (m_RadLoB - m_RadLoA) * EdA;
		double dy = (m_RadLaB - m_RadLaA) * EcA;
		double angle = 0.0;
		angle = Math.atan(Math.abs(dx / dy)) * 180. / Math.PI;
		double dLo = longitudeB - longitudeA;
		double dLa = latitudeB - latitudeA;
		if (dLo > 0 && dLa <= 0) {
			angle = (90. - angle) + 90;
		} else if (dLo <= 0 && dLa < 0) {
			angle = angle + 180.;
		} else if (dLo < 0 && dLa >= 0) {
			angle = (90. - angle) + 270;
		}
		return angle;
	}
	
	/** 
	 * 根据源经纬度与目标经纬度计算出距离（米）
     * @param long1 经度1 
     * @param lat1 维度1 
     * @param long2 经度2 
     * @param lat2 纬度2 
     * @return 
     */  
    public static double getDistance(double long1, double lat1, double long2, double lat2) {  
        double a, b, R;  
        R = 6378137; // 地球半径  
        lat1 = lat1 * Math.PI / 180.0;  
        lat2 = lat2 * Math.PI / 180.0;  
        a = lat1 - lat2;  
        b = (long1 - long2) * Math.PI / 180.0;  
        double d;  
        double sa2, sb2;  
        sa2 = Math.sin(a / 2.0);  
        sb2 = Math.sin(b / 2.0);  
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));  
        return d;  
    }

	/**
	 * 
	 * @param roadLine
	 *            路段中心线
	 * @param distance
	 *            外扩距离 单位米
	 * @param directionFlag
	 *            0正向 1逆向
	 * @return
	 */
	public static String getOffsetRoadLine(String roadLine, double distance,
			int directionFlag) {
		String line = "";
		distance = distance / 1000;

		String[] sps = roadLine.split(";");
		List<String> list = Arrays.asList(sps);

		if (directionFlag == 1) {// 逆向
			Collections.reverse(list);
		} 
		line =  getCoordByAngleDist(list,distance,directionFlag);
		return line;
	}
	/**
	 * 根据方位角和距离求坐标
	 * @param list
	 * @param distance
	 * @param flag
	 * @return
	 */
	public static String getCoordByAngleDist(List<String> list, double distance,
			int flag) {
		String line = "";
		for (String xys : list) {
			String[] xy = xys.split(",");
			double x = Double.parseDouble(xy[0]);
			double y = Double.parseDouble(xy[1]);
			double angle = getAngle(0, 0, x, y);
			double[] nxy = null;
			if (flag == 0) {
				nxy = getLatLng(x, y, distance, angle + 180);
			} else {
				nxy = getLatLng(x, y, distance, angle);
			}
			line += nxy[0] + "," + nxy[1] + ";";
		}
		if (line.length() > 0)
			line = line.substring(0, line.length() - 1);
		return line;
	}
	
	/**
	 * wkt转x,y;x,y#x,y;x,y格式
	 * @param wkt
	 * @return
	 * @throws ParseException 
	 */
	//MULTILINESTRING((113.715396 30.398834,113.715199 30.398557,113.714889 30.398129,113.714434 30.397481,113.71393 30.396791,113.713338 30.395962,113.71306 30.395573,113.712279 30.394459,113.711772 30.393743,113.71149 30.393354,113.711324 30.393113))
	public static String formatWkt(String wkt) throws ParseException{
		String xys = "";
		 
			WKTReader wktReader  = new WKTReader();
			Geometry geoms = wktReader.read(wkt);
			int size = geoms.getNumGeometries();
			for (int i=0; i<size;i++){
				Geometry geom = geoms.getGeometryN(i);
				String text = geom.toText();
				text = text.substring(text.indexOf("(")+1,text.length()-1);
				text = text.replaceAll(", ", ";");
				text = text.replaceAll(" ", ",");
				
				if (i != size-1){
					xys += text+ "#";
				}else{
					xys += text;
				}
			} 
		
		return xys;
	}
	
	public static JSONArray formatWktToJsonArray(String wkt) throws ParseException{
		JSONArray list = new JSONArray();
		 
			WKTReader wktReader  = new WKTReader();
			Geometry geoms = wktReader.read(wkt);
			int size = geoms.getNumGeometries();
			for (int i=0; i<size;i++){
				Geometry geom = geoms.getGeometryN(i);
				String text = geom.toText();
				text = text.substring(text.indexOf("(")+1,text.length()-1);
				text = text.replaceAll(", ", ";");
				text = text.replaceAll(" ", ",");
				list.add(text);
			} 
		
		return list;
	}
	
	/**
	 * 道路线集合
	 * @param geomBuf
	 * @return
	 */
	public static JSONArray getMultiLineString(Geometry geomBuf) {
		JSONArray list = new JSONArray();
		int num = geomBuf.getNumGeometries();
		for (int i = 0; i < num; i++) {
			Geometry polygon = geomBuf.getGeometryN(i);
			String ps = getLinearRingString(polygon); 
			list.add(ps);
		}
		return list;
	}
	
	public static JSONArray getMultiPolyString(Geometry geomBuf) {
		JSONArray list = new JSONArray();
		int num = geomBuf.getNumGeometries();
		for (int i = 0; i < num; i++) {
			Geometry polygon = geomBuf.getGeometryN(i);
			String ps = getPolygonString(polygon);
			ps = ps.replaceAll("\\)", "").replaceAll("\\(", "");
			list.add(ps);
		}
		return list;
	}
	
	public static String getMaxAreaPolygon(Geometry geomBuf) {
		 
		int num = geomBuf.getNumGeometries();
		double maxarea = 0;
		Geometry maxPolygon = null;
		for (int i = 0; i < num; i++) {
			Geometry polygon = geomBuf.getGeometryN(i);
			double area = polygon.getArea();
			if (i==0){
				maxarea=area;
				maxPolygon = polygon;
			}else{
				if (area > maxarea){
					maxarea = area;
					maxPolygon = polygon;
				}
			}
		}
		if (maxPolygon != null){
			String ps = getPolygonString(maxPolygon);
			ps = ps.replaceAll("\\)", "").replaceAll("\\(", "");
			return ps;
		}
		return null;
	}
	
 	/**
	 * 创建geom 
	 * @param points eg:x,y;x,y;...
	 * @return
	 */
	public static Geometry genGeometry(String points){
 		String[] linepoints = points.split(";");
		Coordinate[] coordinates1 = new Coordinate[linepoints.length+1];
		int i = 0;
		for (String lp : linepoints) {
			String[] sp = lp.split(",");
			Coordinate cd = new Coordinate(Double.parseDouble(sp[0]),
					Double.parseDouble(sp[1]));
			coordinates1[i] = cd;
			i++;
		}
		coordinates1[coordinates1.length-1]=coordinates1[0];
		Polygon geom = geometryFactory.createPolygon(coordinates1);
		return geom;
	
	}

    /**
     * 判断坐标的点point(x,y)是否在geometry表示的Polygon中
     * @param poing
     * @param geometry wkt格式
     * @return
     */
    public static boolean withinGeo(String poing,String geometry) throws ParseException {
        WKTReader reader = new WKTReader( geometryFactory );
        Point point = (Point)reader.read(poing);
        MultiPolygon polygon = (MultiPolygon) reader.read(geometry);
        return point.within(polygon);
    }
	
	public static void main(String[] args) {

//		String s = "114.249275,30.580874;114.249609,30.580607;114.249776,30.580477;114.250007,30.580293;114.250081,30.580235;114.250226,30.580119;114.250339,30.58001;114.250601,30.579756;114.250671,30.579664;114.250753,30.579555;114.250831,30.57946;114.250968,30.579296;114.251008,30.579251";
//		s = "114.221209,30.596496;114.221111,30.597127";
		
		System.out.println(getAngle(114.249275, 30.580874, 114.249609, 30.580607));
//		System.out.println(getOffsetRoadLine(s, 4.5, 0));
//
//		System.out.println(getOffsetRoadLine(s, 4.5, 1));
		// String[] sps = s.split(";");
		// String s1 = "";
		// String s2 = "";
		// for (String xys : sps) {
		// String[] xy = xys.split(",");
		// double angle = getAngle(0, 0, Double.parseDouble(xy[0]),
		// Double.parseDouble(xy[1]));
		// double[] nxy = getLatLng(Double.parseDouble(xy[0]),
		// Double.parseDouble(xy[1]), 0.003, angle);
		// s1 += nxy[0] + ";" + nxy[1] + ";";
		// double[] nxy1 = getLatLng(Double.parseDouble(xy[0]),
		// Double.parseDouble(xy[1]), 0.003, angle + 180);
		// s2 += nxy1[0] + ";" + nxy1[1] + ";";
		// }
		// System.out.println(s1.substring(0, s1.length() - 1));
		// System.out.println(s2.substring(0, s2.length() - 1));
		
		Polygon g = createCircle(114.249275,30.580874, 10);
		System.out.println(getPolygonString(g));
		 
	        Polygon p = createCircle(114.249275,30.580874, 10);
	        //圆上所有的坐标(32个)
	        Coordinate coords[] = p.getCoordinates();
	        String cxy = "";
	        for(Coordinate coord:coords){
	        	cxy += coord.x+","+coord.y+";";
	        }
	        System.out.println(cxy);
	}
	
	 
}
