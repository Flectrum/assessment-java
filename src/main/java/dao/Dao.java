package dao;

import model.Question;

import java.sql.SQLException;

public interface Dao {
    void saveQuestion(Question question) throws SQLException;
    boolean deleteQuestion(int id);
    void updateQuestion(Question question) throws SQLException;
    Question searchQuestionByTopic(String topicToSearch) throws SQLException;
}
