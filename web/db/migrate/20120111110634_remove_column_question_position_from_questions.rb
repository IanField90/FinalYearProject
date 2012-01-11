class RemoveColumnQuestionPositionFromQuestions < ActiveRecord::Migration
  def up
    remove_column :questions, :question_position
  end

  def down
  end
end
