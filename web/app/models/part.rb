class Part < ActiveRecord::Base
  belongs_to :programme
  #has_many :quizes
  #has_many :materials
end
