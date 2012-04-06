class Quiz < ActiveRecord::Base
  belongs_to :part
  has_many :questions, :dependent => :delete_all
end
