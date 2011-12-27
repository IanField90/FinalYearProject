require 'test_helper'

class UserTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
  
  test "creation empty" do
    user = User.new
    assert !user.save
  end
  
  test "creation password blank" do
    user = User.new
    user.email = "testing@test.com"
    assert !user.save
  end
  
  test "creation password confirmation blank" do
    user = User.new
    user.email = "testing@test.com"
    user.password = "test"
    user.password_confirmation = ""
    assert !user.save
  end
  
  test "login empty password" do
    user = User.find_by_email('test@example.com')
    assert !user.authenticate("")
  end
    
  test "login valid password" do
    user = User.find_by_email('test@example.com')
    assert user.authenticate("Password")
  end
  
  test "login invalid password" do
    user = User.find_by_email('test@example.com')
    assert !user.authenticate("ewarewarewarpu")
  end
  
end
