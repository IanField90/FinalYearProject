class Course < ActiveRecord::Base
  has_many :programmes, :dependent => :delete_all # ON DELETE CASCADE
  
  attr_accessible :course_name_en, :course_name_fr, :course_name_es
  validates_presence_of :course_name_en, :on => :create
end
