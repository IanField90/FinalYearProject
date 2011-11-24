class CreateQuizzes < ActiveRecord::Migration
  def change
    create_table :quizzes do |t|
      t.integer :user_id
      t.integer :part_id
      t.text :name

      t.timestamps
    end
  end
end
