class Question < ActiveRecord::Base
  belongs_to :quiz
  has_many :options, :dependent => :delete_all
  
  validates_presence_of :question_en
  validates_presence_of :question_fr
  validates_presence_of :question_es
  
  validates_presence_of :feedback_en
  validates_presence_of :feedback_fr
  validates_presence_of :feedback_es
end
