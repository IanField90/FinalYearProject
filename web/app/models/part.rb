class Part < ActiveRecord::Base
  belongs_to :programme
  has_many :quizzes
  #has_many :materials
end
