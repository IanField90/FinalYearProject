class Part < ActiveRecord::Base
  belongs_to :programme
  has_many :quizzes#, :conditions => { :published => true }
  has_many :materials
end
