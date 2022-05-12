package dao;

import model.Question;
import model.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class QuestionDAOTest {
    QuestionDAO questionDAO;
    Question question;
    List<Response> responseList;
    String content;
    String topic;
    int difficultyRank;

    {
        content = "Does this test work?";
        topic = "Test";
        difficultyRank = 3;
        questionDAO = new QuestionDAO();
        question = new Question();
        question.setDifficultyRank(difficultyRank);
        question.setContent(content);
        question.setTopic(topic);
        responseList = Arrays.asList(new Response(true, "yes"),
                new Response(false, "no"));
        question.setResponse(responseList);
    }


    @BeforeEach
    @Test
    void saveQuestion() throws SQLException {
        questionDAO.saveQuestion(question);
        try (Connection connection = questionDAO.getConnection();
             PreparedStatement psTest = connection.prepareStatement(
                     "SELECT content, correct FROM question INNER JOIN response r on question.question_id = r.question_id" +
                             " WHERE topic = 'Test' AND text ='yes'");) {
            ResultSet resultSet = psTest.executeQuery();
            resultSet.next();
            assertEquals(question.getContent(), resultSet.getString("content"));
            assertTrue(resultSet.getBoolean("correct"));
        }
    }

    @Test
    void searchQuestionByTopic() throws SQLException {
        String contentToAssert = questionDAO.searchQuestionByTopic(topic).getContent();
        int difficultyRankToAssert = 3;
        try (Connection connection = questionDAO.getConnection();
             PreparedStatement psTest = connection.prepareStatement(
                     "SELECT difficulty_rank, content FROM question WHERE topic ='Test';")) {
            ResultSet resultSet = psTest.executeQuery();
            resultSet.next();
            assertEquals(contentToAssert, resultSet.getString("content"));
            assertEquals(difficultyRankToAssert, resultSet.getInt("difficulty_rank"));
        }
    }

    @Test
    void updateQuestion() throws SQLException {
        int id;
        try (Connection connection = questionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT question_id FROM question" +
                     " WHERE topic='Test';")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("question_id");
        }
        String afterUpdateContentColumn = "Does this test work after update?";
        Question questionUpdate = new Question(id, difficultyRank, topic, afterUpdateContentColumn, responseList);
        questionDAO.updateQuestion(questionUpdate);
        try (Connection connection = questionDAO.getConnection();
             PreparedStatement psTest = connection.prepareStatement(
                     "SELECT content FROM question WHERE topic ='Test';")) {
            ResultSet resultSet = psTest.executeQuery();
            resultSet.next();
            String content = resultSet.getString("content");
            assertEquals(afterUpdateContentColumn, content);
        }
    }

    @AfterEach
    @Test
    void deleteQuestion() throws SQLException {
        int id;
        try (Connection connection = questionDAO.getConnection();
             PreparedStatement psTest = connection.prepareStatement(
                     "SELECT question_id FROM question WHERE topic ='Test';")) {
            ResultSet resultSet = psTest.executeQuery();
            resultSet.next();
            id = resultSet.getInt("question_id");
        }
         boolean rowDeleted = questionDAO.deleteQuestion(id);
        assertTrue(rowDeleted);
    }
}