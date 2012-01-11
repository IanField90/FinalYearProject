class DropFeedbacksTable < ActiveRecord::Migration
  def up
    drop_table(:feedbacks)
  end

  def down
  end
end
