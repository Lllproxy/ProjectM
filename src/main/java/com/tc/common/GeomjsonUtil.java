package com.tc.common;

import com.alibaba.fastjson.JSONObject;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wm
 * @description: TODO
 * @date 2019/4/2
 */
public class GeomjsonUtil {

      /**
     * 由wkt格式的geometry生成geojson
     * @param wkt
     * @return
     */
    public static String wktToJson(String wkt) {
        String json = null;
        try {
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(wkt);
            StringWriter writer = new StringWriter();
            GeometryJSON g = new GeometryJSON(20);
            g.write(geometry, writer);
            json = writer.toString();
//            json = json.replace("\\","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 由geojson格式的json生成wkt
     * @param geoJson
     * @return
     */
    public static String jsonToWkt(String geoJson) {
        String wkt = "";
        GeometryJSON gjson = new GeometryJSON();
        Reader reader = new StringReader(geoJson);
        try {
            Geometry geometry = gjson.read(reader);
            wkt = geometry.toText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wkt;
    }

    /**
     * 由wkt格式的geometry和map 生成geojson
     * @param wkt
     * @param map
     * @return
     */
    public static String geoToJson(String wkt,Map<String,Object> map) {
        String json = null;
        try {
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(wkt);
            StringWriter writer = new StringWriter();
            GeometryJSON g = new GeometryJSON();
            g.write(geometry, writer);
            map.put("geometry", writer);

            json = JSONObject.toJSONString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void main(String[] args) {
        String wkt = "POINT(118.466 37.4674)";
        String type = "Point";
        String[] ss =  wkt.replace("POINT(", "").replace(")", "").split(" ");

        String lineString = "LINESTRING(118.496 37.4986, 118.496 37.4989, 118.496 37.4993, 118.496 37.4998)";
        String type1 = "LineString";

        String polygonString = "POLYGON((114.225 30.5884, 114.228 30.5854, 114.228 30.5851, 114.228 30.585, 114.229 30.5852, 114.23 30.5851, 114.231 30.5781))";
        String type2 = "Polygon";

        String multiLineString = "MULTILINESTRING((113.715396 30.398834, 113.715199 30.398557, 113.714889 30.398129, 113.714434 30.397481, 113.71393 30.396791, 113.713338 30.395962, 113.71306 30.395573, 113.712279 30.394459, 113.711772 30.393743, 113.71149 30.393354, 113.711324 30.393113))";
        String type4 = "MultiLineString";

        String MULTIPOLYGON = "MULTIPOLYGON(((113.715396 30.398834, 113.715199 30.398557, 113.714889 30.398129)), ((113.715396 30.398834, 113.715199 30.398557, 113.714889 30.398129)))";
        String type5 = "MultiPolygon";

//      JSONObject data = getGeomjsonData(type1, lineString);
//       JSONObject data1 = getGeomjsonData(type2, lineString);
//       JSONObject data2 = getGeomjsonData(type2, polygonString);
//        JSONObject data4 = getGeomjsonData(type2, polygonString);
//        JSONObject data5 = getGeomjsonData(type5, MULTIPOLYGON);

        String wkt1 = "POINT(114.298456 30.553544)";
        Map<String,Object> map = new HashMap<>();
        map.put("name", "北清路");
        map.put("author","李雷");

        String geojson = geoToJson(lineString,map);
        String json = wktToJson(lineString);
        System.out.println(geojson);
//        System.out.println(json.replace("\\",""));
        System.out.println(json);
        System.out.println(jsonToWkt(json));
    }


}
