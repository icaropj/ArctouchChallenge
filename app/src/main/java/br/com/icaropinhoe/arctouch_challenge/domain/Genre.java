package br.com.icaropinhoe.arctouch_challenge.domain;

import org.parceler.Parcel;

/**
 * Created by icaro on 27/12/2017.
 */

@Parcel
public class Genre {

    private Integer id;

    private String name;

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
