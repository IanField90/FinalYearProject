class CreateOptions < ActiveRecord::Migration
  def change
    create_table :options do |t|
      t.integer :question_id
      t.text :content_en
      t.text :content_fr
      t.text :content_es
      t.boolean :answer

      t.timestamps
    end
  end
end
