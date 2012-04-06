class Course < ActiveRecord::Base
  has_many :programmes, :dependent => :delete_all # ON DELETE CASCADE
  attr_accessible :course_name_en, :course_name_fr, :course_name_es, :id
  
  #validations occur on create and edit by default
  validates_presence_of :course_name_en
  validates_presence_of :course_name_es
  validates_presence_of :course_name_fr
  #accepts_nested_attributes_for :programmes, :allow_destroy => true
end
