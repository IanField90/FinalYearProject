class Quiz < ActiveRecord::Base
  belongs_to :part
  has_many :questions
  #has_many :attempts
end
