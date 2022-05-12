package model;

import java.util.List;

public class Question {
    private int id;
    private String content;
    private String topic;
    private int difficultyRank;
    private List <Response> response;

    public Question() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getDifficultyRank() {
        return difficultyRank;
    }

    public void setDifficultyRank(int difficultyRank) {
        this.difficultyRank = difficultyRank;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }


    public Question(int id, int difficultyRank,String topic,String content) {
        this.id = id;
        this.content = content;
        this.topic = topic;
        this.difficultyRank = difficultyRank;
    }

    public Question(int id, int difficultyRank, String topic,  String content, List<Response> response) {
        this.id = id;
        this.content = content;
        this.topic = topic;
        this.difficultyRank = difficultyRank;
        this.response = response;
    }

}
