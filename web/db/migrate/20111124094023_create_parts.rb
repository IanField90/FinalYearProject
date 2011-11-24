class CreateParts < ActiveRecord::Migration
  def change
    create_table :parts do |t|
      t.text :part_name_en
      t.text :part_name_fr
      t.text :part_name_es

      t.timestamps
    end
  end
end
