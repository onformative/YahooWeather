package com.onformative.yahooweather.processing15;

import java.util.Date;

import processing.core.PApplet;
import processing.xml.XMLElement;

/**
 * YahooWeather.java
 * last edit: 05.10.2012
 * @author marcel schwittlick
 *
 */
public class YahooWeather {
  private int WOEID;
  private String temperatureUnit;

  private YahooWeatherThread thread;
  private XMLElement mainXML;

  /**
   * http://weather.yahooapis.com/forecastrss?w=638242&u=c
   * 
   * more information about the units/api/etc here:
   * http://developer.yahoo.com/weather/
   * 
   * @param parent
   */
  public YahooWeather(PApplet parent, int WOEID, String temperatureUnit, int updateIntervallInMillis) {
    this.WOEID = WOEID;
    this.temperatureUnit = temperatureUnit;

    String URL = "http://weather.yahooapis.com/forecastrss?w=" + WOEID + "&u=" + temperatureUnit;
    thread = new YahooWeatherThread(parent, URL, updateIntervallInMillis);
    update();
  }

  public void update() {
    mainXML = thread.getMainXML();
  }

  public void setWOEID(int WOEID) {
    this.WOEID = WOEID;
    setThreadURL();
  }

  /**
   * if you set f here the following units apply: 
   * wind speed = mph 
   * temperature = fahrenheit 
   * distance = mile
   * pressure = in
   * 
   * if you set c the following units apply:
   * wind speed = km/h
   * temperature  = celsius
   * distance = km
   * pressure = millibar    
   * 
   * @param tempertureUnit
   */
  public void setTempertureUnit(String tempertureUnit) {
    this.temperatureUnit = tempertureUnit;
    setThreadURL();
  }
  
  public String getURL() {
    return thread.getURL();
  }

  private void setThreadURL() {
    thread.setURL("http://weather.yahooapis.com/forecastrss?w=" + WOEID + "&u=" + temperatureUnit);
    thread.updateThread();
  }

  public float getLongitude() {
    return Float.parseFloat(mainXML.getChild("channel/item/geo:long").getContent());
  }

  public float getLatitude() {
    return Float.parseFloat(mainXML.getChild("channel/item/geo:lat").getContent());
  }

  public int getWindTemperature() {
    return mainXML.getChild("channel/yweather:wind").getInt("chill");
  }
  
  public float getWindSpeed(){
    return mainXML.getChild("channel/yweather:wind").getFloat("speed");
  }
  
  public int getWindDirection(){
    return mainXML.getChild("channel/yweather:wind").getInt("direction");
  }
  
  public String getCityName(){
    return mainXML.getChild("channel/yweather:location").getString("city"); 
  }
  
  public String getCountryName(){
    return mainXML.getChild("channel/yweather:location").getString("country"); 
  }
  
  public String getRegionName(){
    return mainXML.getChild("channel/yweather:location").getString("region"); 
  }
  
  public int getHumidity(){
    return mainXML.getChild("channel/yweather:atmosphere").getInt("humidity"); 
  }
  
  public float getVisibleDistance(){
    return mainXML.getChild("channel/yweather:atmosphere").getFloat("visibility"); 
  }
  
  public float getPressure(){
    return mainXML.getChild("channel/yweather:atmosphere").getFloat("pressure"); 
  }
  
  /**
   * 0 = steady
   * 1 = rising
   * 2 = falling
   * @return
   */
  public int getRising(){
    return mainXML.getChild("channel/yweather:atmosphere").getInt("rising"); 
  }
  
  public String getSunrise(){
    return mainXML.getChild("channel/yweather:astronomy").getString("sunrise"); 
  }
  
  public String getSunset(){
    return mainXML.getChild("channel/yweather:astronomy").getString("sunset");
  }
  
  public int getTemperature(){
    return mainXML.getChild("channel/item/yweather:condition").getInt("temp");
  }
  
  public String getWeatherCondition(){
    return mainXML.getChild("channel/item/yweather:condition").getString("text");
  }
  
  public int getWeatherConditionCode(){
    return mainXML.getChild("channel/item/yweather:condition").getInt("code");
  }
  
  public Date getLastUpdated(){
    return thread.getLastUpdate();
  }

  public int getTemperatureLowTomorrow(){
    return mainXML.getChild("channel/item").getChild(7).getInt("low");
  }
  
  public int getTemperatureHighTomorrow(){
    return mainXML.getChild("channel/item").getChild(7).getInt("high");
  }
  
  public String getWeatherConditionTomorrow(){
    return mainXML.getChild("channel/item").getChild(7).getString("text");
  }
  
  public int getWeatherConditionCodeTomorrow(){
    return mainXML.getChild("channel/item").getChild(7).getInt("code");
  }
  
  public String getWeekdayTomorrow(){
    return mainXML.getChild("channel/item").getChild(7).getString("day");
  }
  
  public int getTemperatureLowDayAfterTomorrow(){
    return mainXML.getChild("channel/item").getChild(8).getInt("low");
  }
  
  public int getTemperatureHighDayAfterTomorrow(){
    return mainXML.getChild("channel/item").getChild(8).getInt("high");
  }
  
  public String getWeatherConditionDayAfterTomorrow(){
    return mainXML.getChild("channel/item").getChild(8).getString("text");
  }
  
  public int getWeatherConditionCodeDayAfterTomorrow(){
    return mainXML.getChild("channel/item").getChild(8).getInt("code");
  }
  
  public String getWeekdayDayAfterTomorrow(){
    return mainXML.getChild("channel/item").getChild(8).getString("day");
  }
}
