class Programme < ActiveRecord::Base
  belongs_to :course
  has_many :parts, :dependent => :delete_all
  
  validates_presence_of :programme_name_en
  validates_presence_of :programme_name_fr
  validates_presence_of :programme_name_es
  
end
