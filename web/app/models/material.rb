class Material < ActiveRecord::Base
  belongs_to :part
  
  validates_presence_of :material_en
  validates_presence_of :material_es
  validates_presence_of :material_fr
end
