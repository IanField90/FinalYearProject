class Question < ActiveRecord::Base
  belongs_to :quiz
  has_many :options
end
