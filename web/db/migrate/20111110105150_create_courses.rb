class CreateCourses < ActiveRecord::Migration
  def change
    create_table :courses do |t|
      t.string :name_en
      t.string :name_fr
      t.string :name_es

      t.timestamps
    end
  end
end
