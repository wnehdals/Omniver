package com.example.omniver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Climate {
    @SerializedName("weather")
    @Expose
    public ArrayList<Weather> weather;
    public class Weather {
        @SerializedName("id")
        public String id;
        @SerializedName("main")
        public String mainWeather;
        @SerializedName("description")
        public String description;
        @SerializedName("icon")
        public String icon;

        public String getId() {
            return id;
        }

        public String getMainWeather() {
            return mainWeather;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }
    @SerializedName("main")
    @Expose
    public Main main;

    public class Main {
        @SerializedName("temp")
        public Double temp;
        @SerializedName("pressure")
        public Integer pressure;
        @SerializedName("humidity")
        public Integer humidity;
        @SerializedName("temp_min")
        public Double temp_min;
        @SerializedName("temp_max")
        public Double temp_max;

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public void setPressure(Integer pressure) {
            this.pressure = pressure;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public void setTemp_min(Double temp_min) {
            this.temp_min = temp_min;
        }

        public void setTemp_max(Double temp_max) {
            this.temp_max = temp_max;
        }

        public Double getTemp() {
            return temp;
        }

        public Integer getPressure() {
            return pressure;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public Double getTemp_min() {
            return temp_min;
        }

        public Double getTemp_max() {
            return temp_max;
        }
    }

    @SerializedName("wind")
    @Expose
    public Wind wind;

    public class Wind{
        @SerializedName("speed")
        public Double speed;
        @SerializedName("deg")
        public Double deg;

        public Double getSpeed() {
            return speed;
        }

        public Double getDeg() {
            return deg;
        }
    }
    @SerializedName("name")
    public String name;


    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }
    public Climate getInstanceClimate(){
        return this;
    }
}

