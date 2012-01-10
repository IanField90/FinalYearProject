# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20120110095838) do

  create_table "courses", :force => true do |t|
    t.text     "course_name_en"
    t.text     "course_name_fr"
    t.text     "course_name_es"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "feedbacks", :force => true do |t|
    t.text     "feedback_en"
    t.text     "feedback_fr"
    t.text     "feedback_es"
    t.integer  "question_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "materials", :force => true do |t|
    t.integer  "part_id"
    t.text     "material_en"
    t.text     "material_fr"
    t.text     "material_es"
    t.integer  "material_type"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "options", :force => true do |t|
    t.integer  "question_id"
    t.text     "content_en"
    t.text     "content_fr"
    t.text     "content_es"
    t.boolean  "answer"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "parts", :force => true do |t|
    t.text     "part_name_en"
    t.text     "part_name_fr"
    t.text     "part_name_es"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "programme_id"
  end

  create_table "programmes", :force => true do |t|
    t.text     "programme_name_en"
    t.text     "programme_name_fr"
    t.text     "programme_name_es"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "course_id"
  end

  create_table "questions", :force => true do |t|
    t.text     "question_en"
    t.text     "question_fr"
    t.text     "question_es"
    t.integer  "question_position"
    t.integer  "quiz_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "type_id"
    t.text     "feedback_en"
    t.text     "feedback_fr"
    t.text     "feedback_es"
  end

  create_table "quizzes", :force => true do |t|
    t.integer  "user_id"
    t.integer  "part_id"
    t.text     "name_en"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.text     "name_fr"
    t.text     "name_es"
    t.boolean  "published"
  end

  create_table "types", :force => true do |t|
    t.string   "type"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "users", :force => true do |t|
    t.string   "email"
    t.string   "password_digest"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "auth_token"
    t.string   "password_reset_token"
    t.datetime "password_reset_sent_at"
    t.boolean  "admin",                  :default => false
  end

end
