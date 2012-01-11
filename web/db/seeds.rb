# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Emanuel', :city => cities.first)

  if User.count == 0
    User.create(:email => 'test@example.com', :password => 'Password', :admin => true)
  end
  
  Type.delete_all
  Type.create(:value => "Multiple Choice")
  Type.create(:value => 'Fill-in-the-blanks')
  Type.create(:value => 'Match-up')
  Type.create(:value => 'Slider/Opinion')
