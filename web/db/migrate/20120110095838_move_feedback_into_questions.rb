class MoveFeedbackIntoQuestions < ActiveRecord::Migration
  def up
    add_column :questions, :feedback_en, :text
    add_column :questions, :feedback_fr, :text
    add_column :questions, :feedback_es, :text
  end

  def down
  end
end
