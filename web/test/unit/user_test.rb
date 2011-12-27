require 'test_helper'

class UserTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
  
  test "valid password" do
    user = User.find_by_email('test@example.com')
    assert user.authenticate("Password")
  end
  
  test "invalid password" do
    user = User.find_by_email('test@example.com')
    assert !user.authenticate("ewarewarewarpu")
  end
  
end
