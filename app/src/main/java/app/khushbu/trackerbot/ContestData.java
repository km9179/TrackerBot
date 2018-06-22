package app.khushbu.trackerbot;

public class ContestData {

    private String event_names;
    private String event_url;
    private String event_start_time;
    private String event_end_time;
    private int event_duration;
    private int imgId;
    private boolean isSelected;

    public ContestData(String event_names,String event_url,String event_start_time,String event_end_time,int event_duration,int imgId){
        this.setEvent_names(event_names);
        this.setEvent_url(event_url);
        this.setEvent_start_time(event_start_time);
        this.setEvent_end_time(event_end_time);
        this.setEvent_duration(event_duration);
        this.setImgId(imgId);
    }
    public ContestData(int imgId,String event_names,String event_start_time)
    {
        this.setEvent_names(event_names);
        this.setEvent_start_time(event_start_time);
        this.setImgId(imgId);

    }

    public ContestData(){

    }
    public String getEvent_names() {
        return event_names;
    }

    public void setEvent_names(String event_names) {
        this.event_names = event_names;
    }

    public String getEvent_url() {
        return event_url;
    }

    public void setEvent_url(String event_url) {
        this.event_url = event_url;
    }

    public String getEvent_start_time() {
        return event_start_time;
    }

    public void setEvent_start_time(String event_start_time) {
        this.event_start_time = event_start_time;
    }

    public String getEvent_end_time() {
        return event_end_time;
    }

    public void setEvent_end_time(String event_end_time) {
        this.event_end_time = event_end_time;
    }

    public int getEvent_duration() {
        return event_duration;
    }

    public void setEvent_duration(int event_duration) {
        this.event_duration = event_duration;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

