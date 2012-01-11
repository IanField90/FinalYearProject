class RenameColumnInTypeToValue < ActiveRecord::Migration
  def up
    rename_column :types, :type, :value
  end

  def down
  end
end
