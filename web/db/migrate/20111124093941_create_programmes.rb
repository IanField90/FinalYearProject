class CreateProgrammes < ActiveRecord::Migration
  def change
    create_table :programmes do |t|
      t.text :programme_name_en
      t.text :programme_name_fr
      t.text :programme_name_es

      t.timestamps
    end
  end
end
