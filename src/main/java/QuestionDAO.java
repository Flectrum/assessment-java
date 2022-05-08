import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_application";
    static final String USER = "user";
    static final String PASS = "password";

    public QuestionDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void saveQuestion(Question question) {
        int id;
        try (Connection connection = getConnection();
             PreparedStatement psQuestion = connection.prepareStatement(
                     "INSERT INTO question (difficulty_rank, topic, content) VALUES " +
                             " (?, ?, ?);");
             PreparedStatement statementForId = connection.prepareStatement(
                     "SELECT question_id FROM question WHERE difficulty_rank = ? AND topic = ? AND " +
                             "content = ?;");
             PreparedStatement psResponse = connection.prepareStatement(
                     "INSERT INTO response (question_id, correct, text) VALUES " +
                             "(?, ?, ?);")) {
            psQuestion.setInt(1, question.getDifficultyRank());
            psQuestion.setString(2, question.getTopic());
            psQuestion.setString(3, question.getContent());
            psQuestion.executeUpdate();
            statementForId.setInt(1, question.getDifficultyRank());
            statementForId.setString(2, question.getTopic());
            statementForId.setString(3, question.getContent());
            ResultSet resultSet = statementForId.executeQuery();
            resultSet.next();
            id = resultSet.getInt("question_id");
            for (Response response : question.getResponse()) {
                psResponse.setInt(1, id);
                psResponse.setBoolean(2, response.isCorrect());
                psResponse.setString(3, response.getText());
                psResponse.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM question WHERE question_id = ?;"
             )) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void updateQuestion(Question question) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement psQuestion = connection.prepareStatement(
                     "update question set difficulty_rank = ?,topic= ?, content =? where question_id = ?;");
             PreparedStatement psResponse = connection.prepareStatement(
                     "UPDATE response SET correct = ?, text = ? where question_id = ?")
        ) {
            psQuestion.setInt(1, question.getDifficultyRank());
            psQuestion.setString(2, question.getTopic());
            psQuestion.setString(3, question.getContent());
            psQuestion.setInt(4, question.getId());
            psQuestion.executeUpdate();
            for(Response response: question.getResponse()){
                psResponse.setBoolean(1, response.isCorrect());
                psResponse.setString(2, response.getText());
                psResponse.setInt(3, question.getId());
                psResponse.executeUpdate();
            }
        }
    }

    public Question searchQuestionByTopic(String topicToSearch) {
        List<Response> responseList = new ArrayList<>();
        Question question = null;
        Response response;
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement psQuestion = connection.prepareStatement(
                     "select question_id,difficulty_rank,topic,content from question where topic =?");
             PreparedStatement psResponse = connection.prepareStatement(
                     "select correct, text from response where question_id =?")) {
            psQuestion.setString(1, topicToSearch);
            ResultSet rs = psQuestion.executeQuery();
            while (rs.next()) {
                int difficultyRank = rs.getInt("difficulty_rank");
                id = rs.getInt("question_id");
                String content = rs.getString("content");
                question = new Question(id, difficultyRank, topicToSearch, content);
            }
            psResponse.setInt(1, id);
            ResultSet resultSet = psResponse.executeQuery();
            while (resultSet.next()) {
                boolean isCorrect = resultSet.getBoolean("correct");
                String text = resultSet.getString("text");
                response = new Response(isCorrect, text);
                responseList.add(response);
            }
            assert question != null;
            question.setResponse(responseList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }
}
