package com.onformative.yahooweather.processing20;

import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import processing.core.PApplet;
import processing.data.XML;

/**
 * YahooWeatherThread.java last update: 05.10.2012
 * 
 * @author marcel schwittlick
 * 
 */
public class YahooWeatherThread extends Thread {

  private int updateIntervallMilis;
  private boolean running;
  private PApplet parent;
  private String URL;
  private Date lastUpdate;

  private XML mainXML;

  public YahooWeatherThread(PApplet p, String yahooWeatherLink, int updateIntervallInMillis) {
    this.updateIntervallMilis = 5 * 1000 * 60; // 5 minutes update intervall
    this.running = false;
    this.parent = p;
    this.URL = yahooWeatherLink;
    this.updateIntervallMilis = updateIntervallInMillis;
    start();
  }

  public void start() {
    running = true;
    System.out.println("YahooWeather thread started. Update frequency: " + updateIntervallMilis
        / 1000 + "s");
    setMainXML();
    super.start();
  }

  public void run() {
    while (running) {
      setMainXML();
      try {
        sleep(updateIntervallMilis);
      } catch (Exception e) {
        PApplet.println(e);
      }
    }
  }

  public void quit() {
    PApplet.println("YahooWeather thread stopped.");
    running = false;
    interrupt();
  }

  private void setMainXML() {
    // mainXML = new XML(parent, URL);
    mainXML = parent.loadXML(URL);
    System.out.println("Weather Data updated!");
    lastUpdate = new Date();
  }

  public XML getMainXML() {
    return mainXML;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }

  public String getURL() {
    return this.URL;
  }

  public void updateThread() {
    setMainXML();
  }
}
