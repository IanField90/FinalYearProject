class Option < ActiveRecord::Base
  belongs_to :question
  
  # validates_presence_of :option_en
  # validates_presence_of :option_fr
  # validates_presence_of :option_es
  validates_presence_of :content_en
  validates_presence_of :content_fr
  validates_presence_of :content_es
end
