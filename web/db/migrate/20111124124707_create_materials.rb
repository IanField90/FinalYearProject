class CreateMaterials < ActiveRecord::Migration
  def change
    create_table :materials do |t|
      t.integer :part_id
      t.text :material_en
      t.text :material_fr
      t.text :material_es
      t.integer :material_type

      t.timestamps
    end
  end
end
