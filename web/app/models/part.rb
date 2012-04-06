class Part < ActiveRecord::Base
  belongs_to :programme
  has_many :quizzes, :dependent => :delete_all#, :conditions => { :published => true }
  has_many :materials, :dependent => :delete_all
  
  validates_presence_of :part_name_en
  validates_presence_of :part_name_fr
  validates_presence_of :part_name_es
end
