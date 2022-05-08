import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends QuestionDAO{

    public static void main(String[] args) throws SQLException {
        QuestionDAO questionDAO = new QuestionDAO();
            questionDAO.deleteQuestion(questionDAO.searchQuestionByTopic("UPDATED").getId());

//        Response response = new Response(true, "first");
//        Response response1 = new Response(false, "second");
//        Response response2 = new Response(true, "third");
//        List<Response> responseList = new ArrayList<>();
//        responseList.add(response);
//        responseList.add(response1);
//        responseList.add(response2);
//
//        Question question = new Question(8, "UPDATED", "How you doing?", responseList);
//        questionDAO.saveQuestion(question);
//        System.out.println(questionDAO.searchQuestionByTopic1("SEA").getDifficultyRank());
//        System.out.println(questionDAO.searchQuestionByTopic1("SEA").getTopic());
//        System.out.println(questionDAO.searchQuestionByTopic1("SEA").getContent());
//        System.out.println(questionDAO.searchQuestionByTopic1("SEA").getId());
//        System.out.println(questionDAO.searchQuestionByTopic1("SEA").getResponse().size());

        }
    }

