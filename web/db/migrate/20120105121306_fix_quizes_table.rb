class FixQuizesTable < ActiveRecord::Migration
  def up
    rename_column :quizzes, :name, :name_en
    add_column :quizzes, :name_fr, :text
    add_column :quizzes, :name_es, :text
  end

  def down
  end
end
