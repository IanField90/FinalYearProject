class CreateCourses < ActiveRecord::Migration
  def change
    create_table :courses do |t|
      t.text :course_name_en
      t.text :course_name_fr
      t.text :course_name_es

      t.timestamps
    end
  end
end
