class AddProgrammeIdToParts < ActiveRecord::Migration
  def change
    add_column :parts, :programme_id, :integer
  end
end
