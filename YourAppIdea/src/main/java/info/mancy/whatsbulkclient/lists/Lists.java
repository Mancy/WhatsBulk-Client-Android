package info.mancy.whatsbulkclient.lists;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

// {"callsign":"VLG1982","iata":"LIS","type":"A320","lat":39.379,"lon":-8.195,"spd":434,"alt":26175,"flight":"VY1982","name":"Lisbon Lisboa","eta":1364422653}
public class Lists {


    private Integer list_count ;

    private String name ;

    private Integer id ;

    public Integer getCount() {
        return list_count;
    }

    public void setCount(Integer count) {
        this.list_count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
