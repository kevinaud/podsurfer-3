package com.credera;

import java.util.Date;

/**
 * Created by kevinaud on 10/4/16.
 */
public class Podcast {

    private String name;
    private String link;
    private Date release;
    private String producer;
    private String description;
    private Object[] episodes;
    private int episodeCount;
    private String primaryGenre;
    private String[] tags;
    private String imageUrl;


    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getLink() { return this.link; }
    public void setLink(String link) { this.link = link; }

    public Date getRelease() { return this.release; }
    public void setRelease(Date release) { this.release = release; }

    public String getProducer() { return this.producer; }
    public void setProducer(String producer) { this.producer = producer; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public Object[] getEpisodes() { return this.episodes; }
    public void setEpisodes(Object[] episodes) { this.episodes = episodes; }

    public int getEpisodeCount() { return this.episodeCount; }
    public void setEpisodeCount(int count) { this.episodeCount = count; }

    public String getPrimaryGenre() { return this.primaryGenre; }
    public void setPrimaryGenre(String genre) { this.primaryGenre = genre; }

    public String[] getTags() { return this.tags; }
    public void setTags(String[] tags) { this.tags = tags; }

    public String getImageUrl() { return this.imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

}
