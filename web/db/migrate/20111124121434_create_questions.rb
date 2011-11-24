class CreateQuestions < ActiveRecord::Migration
  def change
    create_table :questions do |t|
      t.text :question_en
      t.text :question_fr
      t.text :question_es
      t.integer :question_position
      t.integer :quiz_id

      t.timestamps
    end
  end
end
