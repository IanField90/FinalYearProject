class AddQuestionTypeToQuestions < ActiveRecord::Migration
  def change
    add_column :questions, :type_id, :integer
  end
end
