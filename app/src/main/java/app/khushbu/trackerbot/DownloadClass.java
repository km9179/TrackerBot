package app.khushbu.trackerbot;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class DownloadClass {

    String url;
    private int flag;
    int siteKey;

    DownloadClass(){

    }

    public void setUrl(String url){
        this.url=url;
    }
    public void formUrl(int frag){

        int id=siteKey;
        String url_left="https://clist.by/api/v1/json/contest/?username=abhi&api_key=8f62bf40d07bb9af09535a22f21653ace0da43a4&resource__id=";
        String resource_id="1";
        if(id != -1);
        resource_id=Integer.toString(id);
        String url_right="&order_by=-start";
        if(frag==1)
            url_right="&order_by=-end";
        String url=url_left+resource_id+url_right;
        this.url=url;

        //Log.i("url",this.url);
        //Log.i("url1",url);

    }
    public  void clear(int frag){
        if(frag==2) {
            Upcoming.upcomingContestData.clear();
        }
        else {
            Ongoing.ongoingContestData.clear();
        }
    }
    public void downloadTask(RequestQueue requestQueue, final int frag, final int siteKey){

        //to clear the screen
        clear(frag);
        this.siteKey=siteKey;
        this.formUrl(frag);
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, this.url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray=response.getJSONArray("objects");

                    Time time=new Time();


                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String eventName=jsonObject.getString("event");
                        String eventUrl=jsonObject.getString("href");
                        String eventStartTime=jsonObject.getString("start");
                        String eventEndTime=jsonObject.getString("end");
                        int eventDuration=jsonObject.getInt("duration");

                        String curTime=time.getCurrentTimeStamp();

                        if(frag==2) {
                            if (curTime.compareTo(eventStartTime) < 0) {
                                if (curTime.compareTo(eventEndTime) < 0) {

                                    Upcoming.upcomingContestData.add(new ContestData(eventName,
                                            eventUrl,eventStartTime,eventEndTime,
                                            eventDuration,(int)MainActivity.imgId.get(siteKey)));

                                }
                            }
                            else
                                break;
                        }
                        else if(frag == 1){
                            if (curTime.compareTo(eventEndTime) < 0) {
                                if (curTime.compareTo(eventStartTime) > 0) {
                                    Ongoing.ongoingContestData.add(new ContestData(eventName,
                                            eventUrl,eventStartTime,eventEndTime,
                                            eventDuration,(int)MainActivity.imgId.get(siteKey)));
                                }
                            }
                            else
                                break;
                        }
                    }
                    reverse(frag);
                    try {
                        if (frag == 2)
                            Upcoming.adapter.notifyDataSetChanged();
                        else
                            Ongoing.adapter.notifyDataSetChanged();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(objectRequest);
        //adapter.notifyDataSetChanged();

    }
    public void reverse(int frag){
        if(frag==2) {
            Collections.reverse(Upcoming.upcomingContestData);

        }
        else {
            Collections.reverse(Ongoing.ongoingContestData);
        }
    }


    public void downloadContestTask(RequestQueue requestQueue){

        //to clear the screen

        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, this.url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray=response.getJSONArray("objects");

                    Time time=new Time();


                    for(int i=jsonArray.length()-1;i>=0;i--) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String eventName=jsonObject.getString("event");
                        String eventUrl=jsonObject.getString("href");
                        String eventStartTime=jsonObject.getString("start");
                        String eventEndTime=jsonObject.getString("end");
                        int eventDuration=jsonObject.getInt("duration");
                        JSONObject object= jsonObject.getJSONObject("resource");
                        int siteKey = object.getInt("id");
                        String siteName = object.getString("name");

                        String curTime=time.getCurrentTimeStamp();

                        if (curTime.compareTo(eventEndTime) < 0) {
                            if(curTime.compareTo(eventStartTime) >= 0) {
                                if(siteKey != 4) {
                                    ContestData contestData = new ContestData();
                                    contestData.setEvent_names(eventName);
                                    contestData.setEvent_start_time(eventStartTime);
                                    contestData.setEvent_end_time(eventEndTime);
                                    contestData.setEvent_url(eventUrl);
                                    contestData.setEvent_duration(eventDuration);
                                    contestData.setSite_key(siteKey);
                                    contestData.setSite_name(siteName);
                                    contestData.setImgId(Integer.parseInt(MainActivity.imgId.get(siteKey).toString()));

                                    ALL_CONTEST_Activity.allContestList.add(contestData);
                                }
                            }
                        }


                    }
                    if(flag == 3) {
                        try {
                            ALL_CONTEST_Activity.aAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        flag=0;
                    }


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(objectRequest);
        //ALL_CONTEST_Activity.aAdapter.notifyDataSetChanged();

    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
