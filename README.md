
QUIZ APPLICATION:
Create and save in DB a class model for a Quiz Application

User stories:
- A Quiz is made of N Questions
- Every Question is related to a topic, and has a difficulty rank number
- Every Question has a content and a list of Response
- Every Response has a text and a boolean (correct)
- Questions can have more than 1 Response correct

DATA LAYER:
- Create a Db with relevant tables (provide script .sql)
- Implement a DaoQuestion class that will 
	- save Question into DB.
	- Update Question with a new Question
	- Delete a Question
	- search Question by topic
	
HOW TO RUN:
- Implement test cases with JUnit
