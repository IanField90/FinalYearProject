class AddCourseIdToProgrammes < ActiveRecord::Migration
  def change
    add_column :programmes, :course_id, :integer
  end
end
