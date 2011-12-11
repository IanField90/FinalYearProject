class Course < ActiveRecord::Base
  has_many :programmes, :dependent => :delete_all # ON DELETE CASCADE

  attr_accessible :course_name_en, :course_name_fr, :course_name_es, :id
  validates_presence_of :course_name_en, :on => :create
  # validates_presence_of :course_name_fr, :on => :create
  # validates_presence_of :course_name_es, :on => :create
end
