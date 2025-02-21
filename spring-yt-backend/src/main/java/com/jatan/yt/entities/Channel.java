package com.jatan.yt.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "yt_courses")

public class Channel {
    @Id
    private String id;

    private String ChannelName;

    @OneToMany(mappedBy = "Channel")
    private List<Video> list = new ArrayList<>();

    // Getter for ChannelName
    public String getChannelName() {
        return ChannelName;
    }

    // Setter for ChannelName
    public void setChannelName(String channelName) {
        this.ChannelName = channelName;
    }
}
