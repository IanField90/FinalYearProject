package uk.ac.reading.dp005570.TeachReach;

import java.util.ArrayList;

public class Quiz {
	
	private String quizTitle;
	private ArrayList<Question> questions;
	
	Quiz(){
		
	}
	
	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}

	public String getQuizTitle() {
		return quizTitle;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public class Question{
		private String questionText;
		private ArrayList<Option> options;

		public void setQuestionTexts(String questionText) {
			this.questionText = questionText;
		}

		public String getQuestionText() {
			return questionText;
		}

		public void setOptions(ArrayList<Option> options) {
			this.options = options;
		}

		public ArrayList<Option> getOptions() {
			return options;
		}
		

	}
	
	public class Option {
		private String option;
		private Boolean answer;
		
		Option(String option, Boolean answer){
			this.option = option;
			this.answer = answer;
		}
		
		public void setOption(String option) {
			this.option = option;
		}
		public String getOption() {
			return option;
		}
		public void setAnswer(Boolean answer) {
			this.answer = answer;
		}
		public Boolean getAnswer() {
			return answer;
		}
		
		
	}
}
