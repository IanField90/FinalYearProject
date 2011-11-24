class Programme < ActiveRecord::Base
  belongs_to :course
  has_many :parts
end
