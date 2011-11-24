class CreateFeedbacks < ActiveRecord::Migration
  def change
    create_table :feedbacks do |t|
      t.text :feedback_en
      t.text :feedback_fr
      t.text :feedback_es
      t.integer :question_id

      t.timestamps
    end
  end
end
