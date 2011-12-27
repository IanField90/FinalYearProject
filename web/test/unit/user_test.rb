require 'test_helper'

class UserTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
  
  test "empty creation" do
    user = User.new
    assert !user.save
  end
  
  test "password blank creation" do
    user = User.new
    user.email = "testing@test.com"
    assert !user.save
  end
  
  test "password confirmation blank" do
    user = User.new
    user.email = "testing@test.com"
    user.password = "test"
    user.password_confirmation = ""
    assert !user.save
  end
  
  test "empty password" do
    user = User.find_by_email('test@example.com')
    assert !user.authenticate("")
  end
    
  test "valid password" do
    user = User.find_by_email('test@example.com')
    assert user.authenticate("Password")
  end
  
  test "invalid password" do
    user = User.find_by_email('test@example.com')
    assert !user.authenticate("ewarewarewarpu")
  end
  
end
