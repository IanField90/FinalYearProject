class Question < ActiveRecord::Base
  belongs_to :quiz
  has_many :feedbacks
  has_many :options
end
