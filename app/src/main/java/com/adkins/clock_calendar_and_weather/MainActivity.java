package com.adkins.clock_calendar_and_weather;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.graphics.drawable.Drawable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static android.graphics.Typeface.createFromAsset;
import static com.adkins.clock_calendar_and_weather.optionsMenu.*;

public class MainActivity extends AppCompatActivity {

    static double pageWidth;
    static double pageHeight;
    static double sixCardsWidth;
    static double sixCardsHeight;
    static double mainCardWidth;
    static double mainCardHeight;
    static double cardSpaces;
    static double menuButtonSize;
    static double weatherIconWidth;
    static double weatherIconHeight;
    static double topAndBottomMargin;
    Date currentTime = Calendar.getInstance().getTime();
    //static String[] dateLetters = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
    static int[] calendarCardID = {R.id.calendarcardsun, R.id.calendarcardmon,
            R.id.calendarcardtue, R.id.calendarcardwed, R.id.calendarcardthu,
            R.id.calendarcardfri, R.id.calendarcardsat};
    static int[] calendarCards = {R.drawable.calendarcardsun, R.drawable.calendarcardmon,
            R.drawable.calendarcardtue, R.drawable.calendarcardwed, R.drawable.calendarcardthu,
            R.drawable.calendarcardfri, R.drawable.calendarcardsat};
    private static String[] fontColorList = { "#7fffd4", "#f5f5dc", "#000000", "#0000FF", "#a52a2a", "#00ffff", "#00008b", "#a9a9a9", "#013220", "#8b008b", "#ff8c00", "#8b0000", "#228b22", "#ffd700", "#808080", "#008000", "#ff69b4", "#4b0082", "#967bb6", "#add8e6", "#90ee90", "#ffffed", "#00FF00", "#ff00ff", "#000080", "#ffa500", "#ffc0cb", "#800080", "#FF0000", "#87ceeb", "#ee82ee", "#FFFFFF", "#FFFF00" };
    private static String[] fontColors = { "Aquamarine", "Beige", "Black", "Blue", "Brown", "Cyan", "Dark Blue", "Dark Gray", "Dark Green", "Dark Magenta", "Dark Orange", "Dark Red", "Forest Green", "Gold", "Gray", "Green", "Hot Pink", "Indigo", "Lavender", "Light Blue", "Light Green", "Light Yellow", "Lime Green", "Magenta", "Navy", "Orange", "Pink", "Purple", "Red", "Sky Blue", "Violet", "White", "Yellow" };
    private String WeatherApiKey = "76068897e190edfaf19250bc4c2e2968";
    public static boolean menuOpen = false;
    //RelativeLayout MainLayout;
    private static String[] BGNamesIndex = { "Beach", "Blank", "City", "Flowers", "Mountains", "NightCity", "Ocean", "Richmond", "Sky", "Water" };
    public static int[] BGNames = { R.drawable.beach, R.drawable.blank, R.drawable.city, R.drawable.flowers,
            R.drawable.mountains, R.drawable.nightcity, R.drawable.ocean,
            R.drawable.richmond, R.drawable.sky, R.drawable.water };
    private static String[] usedFonts = { "rabelo_regular.ttf", "digital7.ttf", "bradley_gratis.ttf", "conduit2.ttf",
            "oldlondon.ttf", "summerfire.ttf" , "oaklandhills1991.ttf", "snowtopcaps.ttf", "hultogsnowdrift.ttf" };
    private static String[] fontsName = {"Regular", "Digital" , "Gothic", "Bubble", "Old Time",
            "Fire 01", "Fire 02", "Ice 01", "Ice 02"};
    //protected static int BGIndex = 0;
    TextView[] dateView = new TextView[7];
    private double currentTopMargin = 0;
    private Locale locale;
    private static String weatherZipCode = "23227";
    private String tempColor = "#ffffff";
    //static ConstraintLayout constraintLayout;
    //static Context context;
    public SharedPreferences appSettings;
    public static final String SHARED_PREFS = "sharedPrefs";
    android.support.constraint.ConstraintLayout MainActivityBG;
    static View myView;
    static TextView clockColor;
    static TextView temperatureColor;
    static ArrayList<Typeface> customFonts = new ArrayList<Typeface>();
    //static TextView clockID;
    static Boolean runWeatherAPI = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        screenSizeCheck();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appSettings = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        myView = this.findViewById(R.id.backgroundid);
        storeFonts();
        MainPageLayout();

    }


    private void MainPageLayout() {
        setContentView(R.layout.activity_main);
        MenuButton();
        getCalendarCards();
        getClockTime();
        WeatherAPICall();
        String tempBGIndex = appSettings.getString(BGSavedValue, "Blank");
        String tempFontColorIndex = appSettings.getString(fontColorSavedValue, "Black");
        String tempFontIndex = appSettings.getString(fontSavedValue, "Digital");

        myView = findViewById(R.id.backgroundid);
        if (Arrays.asList(BGNamesIndex).contains(tempBGIndex)) {
            myView.setBackgroundResource(BGNames[Arrays.asList(BGNamesIndex).indexOf(tempBGIndex)]);
        } else {
            myView.setBackgroundResource(R.drawable.blank);
        }

        clockColor = (TextView) findViewById(R.id.digital_clock);
        temperatureColor = (TextView) findViewById(R.id.temperaturetext);
        clockColor.setTextColor(Color.parseColor(fontColorList[Arrays.asList(fontColors).indexOf(tempFontColorIndex)]));
        temperatureColor.setTextColor(Color.parseColor(fontColorList[Arrays.asList(fontColors).indexOf(tempFontColorIndex)]));
        weatherZipCode = appSettings.getString(zipCodeValue, "23227");
        refreshTemperature();

        clockColor.setTypeface(customFonts.get(Arrays.asList(fontsName).indexOf(tempFontIndex)));
    }


  public static void update(String BGName, String fontColorName, String fontName, String zipCode){
//***Update Background**********************************************************************
      if(Arrays.asList(BGNamesIndex).contains(BGName)) {
          myView.setBackgroundResource(BGNames[Arrays.asList(BGNamesIndex).indexOf(BGName)]);
      }else{
          myView.setBackgroundResource(R.drawable.blank);
      }
 //****************************************************************************************

 //***Update Font Color********************************************************************
      clockColor.setTextColor(Color.parseColor(fontColorList[Arrays.asList(fontColors).indexOf(fontColorName)]));
      temperatureColor.setTextColor(Color.parseColor(fontColorList[Arrays.asList(fontColors).indexOf(fontColorName)]));
//*****************************************************************************************

      //***Update Font*********************************************************************
      clockColor.setTypeface(customFonts.get(Arrays.asList(fontsName).indexOf(fontName)));
      //***********************************************************************************

      weatherZipCode = zipCode;
      menuOpen = false;
      runWeatherAPI = false;

  }


public void storeFonts(){

      for(int x=0; usedFonts.length > x; x++){
          customFonts.add(createFromAsset(getAssets(), "fonts/"+usedFonts[x]));
      }
}
    public void screenSizeCheck()
        {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            pageWidth = metrics.widthPixels;
            pageHeight = metrics.heightPixels;

            sixCardsWidth = pageWidth / 8;
            sixCardsHeight = sixCardsWidth * 1.5;
            mainCardWidth = sixCardsWidth * 1.5;
            mainCardHeight = sixCardsHeight * 1.5;
            cardSpaces = (sixCardsWidth / 2) / 8;
            menuButtonSize = pageWidth / 12.8571;
            weatherIconWidth = pageWidth * .3;
            weatherIconHeight = (pageWidth * .3) * 1.333333;
            topAndBottomMargin = (pageHeight /200);
        }

        @SuppressLint("ClickableViewAccessibility")
        private void MenuButton(){

         ImageView menuIcon =(ImageView) findViewById(R.id.menuicon);
            Drawable  menuIconDraw  = getResources().getDrawable(R.drawable.menuicon);
            menuIcon.setImageDrawable(menuIconDraw);
            menuIcon.getLayoutParams().height = (int) menuButtonSize;
            menuIcon.getLayoutParams().width = (int) menuButtonSize;
            menuIcon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event){
                if(!menuOpen) {
                    menuOpen = true;
                    startActivity(new Intent(MainActivity.this, optionsMenu.class));
                    refreshWeatherAPI();
                }

                return true;
                }
            });

            android.view.ViewGroup.LayoutParams layoutParams = menuIcon.getLayoutParams();
            layoutParams.width = (int) menuButtonSize;
            layoutParams.height = (int) menuButtonSize;
            menuIcon.setLayoutParams(layoutParams);

        }


        private void getCalendarCards(){
            int count = 0;
            double calandarCardFontSize = (sixCardsWidth * .5);
            double labelMargin;
            String BeginningDateString = "";
            String TodaysDateString = "";
            int[] dateIDs = {R.id.suncalendardate, R.id.moncalendardate, R.id.tuecalendardate,
                    R.id.wedcalendardate, R.id.thucalendardate, R.id.fricalendardate, R.id.satcalendardate};
            ImageView[] calendarImages = new ImageView[7];
            currentTopMargin += topAndBottomMargin + menuButtonSize;

            DateFormat df = new SimpleDateFormat("d", Locale.getDefault());
           Calendar cal = Calendar.getInstance();
            cal.setTime(currentTime);
            Date todaysDate = cal.getTime();
            TodaysDateString = df.format(todaysDate);
            TextView tv1;

            for(int x=0; x < 7; x++){

                cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
                cal.add(Calendar.DATE, x);
                Date firstDayOfTheWeek = cal.getTime();

                BeginningDateString = df.format(firstDayOfTheWeek);

                calendarImages[x] =(ImageView) findViewById(calendarCardID[x]);
                Drawable  calendarCardDraw  = getResources().getDrawable(calendarCards[x]);

                calendarImages[x].setImageResource(calendarCards[x]);
                calendarImages[x].setImageDrawable(calendarCardDraw);

                tv1 = (TextView)findViewById(dateIDs[x]);
                tv1.setText(BeginningDateString);
                tv1.setTextColor(Color.BLACK);

                if(BeginningDateString.equals(TodaysDateString)) {
                    calendarImages[x].getLayoutParams().height = (int) mainCardHeight;
                    calendarImages[x].getLayoutParams().width = (int) mainCardWidth;
                }else{
                    calendarImages[x].getLayoutParams().height = (int) sixCardsHeight;
                    calendarImages[x].getLayoutParams().width = (int) sixCardsWidth;

                }
            }
        }

        private void getClockTime(){
            TextClock DigitalClock = findViewById(R.id.digital_clock);
        }

        public void refreshTemperature() {
            TimerTask refreshTemp = new TimerTask() {
                public void run() {
                    WeatherAPICall();
                }

            };
            Timer temperatureTimer = new Timer();

            temperatureTimer.scheduleAtFixedRate(refreshTemp, 600000L, 600000L);
        }

    public void refreshWeatherAPI() {
        final Timer weatherAPITimer = new Timer();
        TimerTask refreshWeather = new TimerTask() {
            public void run() {
                checkWeatherAPI(weatherAPITimer);
            }

        };


        weatherAPITimer.scheduleAtFixedRate(refreshWeather, 3000L, 3000L);
    }

    public void checkWeatherAPI(Timer weatherAPITimer){
        if(!menuOpen) {

            WeatherAPICall();
            weatherAPITimer.cancel();
        }
    }



        public void WeatherAPICall() {

            OkHttpClient weatherClient = new OkHttpClient();
            URL Weatherurl;
            String weatherURL = ("http://api.openweathermap.org/data/2.5/weather?zip="+
                    weatherZipCode+",us&APPID="+WeatherApiKey);
            Request weatherAPIRequest = new Request.Builder()
                    .url(weatherURL)
                    .build();

            weatherClient.newCall(weatherAPIRequest).enqueue((new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    findDayTimeIcon("badinternetconnection");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        String weatherResponse = response.body().string();
                        OpenWeatherMap weatherObject = new Gson().fromJson(weatherResponse,OpenWeatherMap.class);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(currentTime);
                        Date todaysDate = cal.getTime();

                        Gson gson = new Gson();

                        Type mType = new TypeToken<OpenWeatherMap>(){}.getType();

                        Date sunriseTime = new Date((long) weatherObject.getSys().getSunrise() * 1000L);
                        Date sunsetTime = new Date((long) weatherObject.getSys().getSunset() * 1000L);

                        double tempPass = weatherObject.getMain().getTemp();
                        String iconPass = (weatherObject.getWeather().get(0).getMain()).toLowerCase();

                       if((todaysDate.compareTo(sunriseTime) >= 0) && (todaysDate.compareTo(sunsetTime) < 0)){
                                findDayTimeIcon(iconPass);
                            }else{
                                findNightTimeIcon(iconPass);
                           }

                        getTemperature(tempPass);
                    }else{
                        findDayTimeIcon("badzip");

                    }
                }
            }));
        }

    public void findDayTimeIcon(String iconPass)
    {
      int WIcon;


        switch (iconPass)
        {
            case "clear sky":
                WIcon = R.drawable.clearskyday;
                break;
            case "few clouds":
                WIcon = R.drawable.fewcloudsday;
                break;
            case "scattered clouds":
                WIcon = R.drawable.scatteredclouds;
                break;
            case "broken clouds":
                WIcon = R.drawable.brokenclouds;
                break;
            case "shower rain":
                WIcon = R.drawable.showerrain;
                break;
            case "rain":
                WIcon = R.drawable.rainday;
                break;
            case "thunderstorm":
                WIcon = R.drawable.thunderstorm;
                break;
            case "snow":
                WIcon = R.drawable.snow;
                break;
            case "mist":
                WIcon = R.drawable.mist;
                break;
            case "badzip":
                WIcon = R.drawable.invalidzipcode;
                break;
            case "badinternetconnection":
                WIcon = R.drawable.internetconnection;
                break;
            default:
                WIcon = R.drawable.clearskyday;
                break;
        }

       getWeatherIcon(WIcon);

    }

    protected void findNightTimeIcon(String iconPass)
    {
        int WIcon;

        switch (iconPass)
        {
            case "clear sky":
                WIcon = R.drawable.clearskynight;
                break;
            case "few clouds":
                WIcon = R.drawable.fewcloudsnight;
                break;
            case "scattered clouds":
                WIcon = R.drawable.scatteredclouds;
                break;
            case "broken clouds":
                WIcon = R.drawable.brokenclouds;
                break;
            case "shower rain":
                WIcon = R.drawable.showerrain;
                break;
            case "rain":
                WIcon = R.drawable.rainnight;
                break;
            case "thunderstorm":
                WIcon = R.drawable.thunderstorm;
                break;
            case "snow":
                WIcon = R.drawable.snow;
                break;
            case "mist":
                WIcon = R.drawable.mist;
                break;
                default:
                    WIcon = R.drawable.clearskynight;
                    break;
        }
       getWeatherIcon(WIcon);

    }

    public void getWeatherIcon(final int WIcon){
        runOnUiThread(new Runnable() {
            public void run(){
            ImageView weatherIcon = findViewById(R.id.weathericon);
            Drawable weatherIconDraw = getResources().getDrawable(WIcon);
            weatherIcon.setImageDrawable(weatherIconDraw);
                android.view.ViewGroup.LayoutParams layoutParams = weatherIcon.getLayoutParams();
                layoutParams.width = (int) weatherIconWidth;
                layoutParams.height = (int) weatherIconHeight;
                weatherIcon.setLayoutParams(layoutParams);
        }});
    }

    public void getTemperature(final double tempPass) {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            public void run(){

            TextView menuIcon = findViewById(R.id.temperaturetext);
            menuIcon.setText("");
        menuIcon.setText(Math.round((tempPass -273.15)*(9/5)+32)+"°");
        menuIcon.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) (pageWidth/8));

            }});

    }

        //Declarations from the Weather API*******************************************
    //********************************************************************************

    public static class OpenWeatherMap {
        private List<Weather> weather;
        private String base;
        private  Main main;
        private Sys sys;
        private int id;
        private String name;
        private int cod;


        public  List<Weather> getWeather() {
            return weather;
        }

        public  void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public  Main getMain() {
            return main;
        }

        public  void setMain(Main main) {
            this.main = main;
        }


        public  Sys getSys() {
            return sys;
        }

        public  void setSys(Sys sys) {
            this.sys = sys;
        }

        public  int getId() {
            return id;
        }

        public  void setId(int id) {
            this.id = id;
        }

        public  String getName() {
            return name;
        }

        public  void setName(String name) {
            this.name = name;
        }
    }

    public class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        public  Weather(int id, String main, String description, String icon) {
            this.id = id;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }

        public  int getId() {
            return id;
        }

        public  void setId(int id) {
            this.id = id;
        }

        public  String getMain() {
            return main;
        }

        public  void setMain(String main) {
            this.main = main;
        }

        public  String getDescription() {
            return description;
        }

        public  void setDescription(String description) {
            this.description = description;
        }

        public  String getIcon() {
            return icon;
        }

        public  void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public class Sys {
        private int type;
        private int id;
        private double message;
        private String country;
        private double sunrise;
        private double sunset;

        public  Sys(int type, int id, double message, String country, double sunrise, double sunset) {
            this.type = type;
            this.id = id;
            this.message = message;
            this.country = country;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public  int getType() {
            return type;
        }

        public  void setType(int type) {
            this.type = type;
        }

        public  int getId() {
            return id;
        }

        public  void setId(int id) {
            this.id = id;
        }

        public  double getMessage() {
            return message;
        }

        public  void setMessage(double message) {
            this.message = message;
        }

        public  String getCountry() {
            return country;
        }

        public  void setCountry(String country) {
            this.country = country;
        }

        public  double getSunrise() {
            return sunrise;
        }

        public  void setSunrise(double sunrise) {
            this.sunrise = sunrise;
        }

        public  double getSunset() {
            return sunset;
        }

        public  void setSunset(double sunset) {
            this.sunset = sunset;
        }
    }

    public class Main {
        private double temp;
        private double pressure;
        private int humidity;
        private double temp_min;
        private double temp_max;

        public  Main(double temp, double pressure, int humidity, double temp_min, double temp_max) {
            this.temp = temp;
            this.pressure = pressure;
            this.humidity = humidity;
            this.temp_min = temp_min;
            this.temp_max = temp_max;
        }

        public  double getTemp() {
            return temp;
        }

        public  void setTemp(double temp) {
            this.temp = temp;
        }

        public  double getPressure() {
            return pressure;
        }

        public  void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public  int getHumidity() {
            return humidity;
        }

        public  void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public  double getTemp_min() {
            return temp_min;
        }

        public  void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }

        public  double getTemp_max() {
            return temp_max;
        }

        public  void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }
    }

    //****************************************************************************
    //****************************************************************************


}
